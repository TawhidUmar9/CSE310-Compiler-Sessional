parser grammar C2105028Parser;

options {
    tokenVocab = C2105028Lexer;
}

@parser::header {
    #pragma once
    #include <iostream>
    #include <fstream>
    #include <string>
    #include <cstdlib>
    #include <iomanip>
    #include <algorithm>
    #include "C2105028Lexer.h"
    #include "2105028_symbol_table.hpp"


    extern std::ofstream parserLogFile;
    extern std::ofstream errorFile;

    extern int syntaxErrorCount;
    extern symbol_table st;
    extern std::string var_type;
    extern bool in_print;
    extern std::string data_type;
}

@parser::members {
    void writeIntoparserLogFile(const std::string message) {
        if (!parserLogFile) {
            std::cout << "Error opening parserLogFile.txt" << std::endl;
            return;
        }

        parserLogFile << message << std::endl;
        parserLogFile.flush();
    }

    void writeIntoErrorFile(const std::string message) {
        if (!errorFile) {
            std::cout << "Error opening errorFile.txt" << std::endl;
            return;
        }
        errorFile << message << std::endl;
        errorFile.flush();
    }

    void log_rule_to_file(const std::string& rule_name, int line_number) {
        writeIntoparserLogFile("Line " + std::to_string(line_number) + ": " + rule_name + "\n");
    }
    std::vector<std::string> get_symbol_types(const std::string& input, symbol_table& st) {
        std::vector<std::string> result;
        std::stringstream ss(input);
        std::string token;
        
        while (std::getline(ss, token, ',')) {
            size_t first = token.find_first_not_of(" \t\n\r");
            if (std::string::npos == first) continue;
            token = token.substr(first);

            size_t last = token.find_last_not_of(" \t\n\r");
            token = token.substr(0, last + 1);

            bool is_numeric = true;
            bool is_float = false;
            bool has_operator = false;
            for(char const &c : token) {
                if (std::isdigit(c) == 0 && c != '.' && c != '+' && c != '-' && c != '*' && c != '/' && c != '%') {
                    is_numeric = false;
                }
                if (c == '.') is_float = true;
                if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') has_operator = true;
            }
            
            if (is_numeric && !token.empty()) {
                if (has_operator) {
                    if (is_float) {
                        result.push_back("float");
                    } else {
                        result.push_back("int"); 
                    }
                } else {
                    result.push_back(is_float ? "float" : "int");
                }
                continue;
            }
            std::string base_name = token.substr(0, token.find("["));
            symbol_info* info = st.lookup(base_name); 
            if (info == nullptr) {
                result.push_back("UNDEFINED");
            } else {
                if (info->is_array()) {
                    if (token.find("[") != std::string::npos) {
                        result.push_back(info->get_type());
                    } else {
                        result.push_back(info->get_type() + "_array");
                    }
                } else {
                    result.push_back(info->get_type());
                }
            }
        }
        return result;
    }
}

start : p=program
    {
        log_rule_to_file("start : program", $p.ctx->stop->getLine());
        std::string symbol_table_str = st.get_all_scopes_as_string();
        writeIntoparserLogFile(symbol_table_str);
        writeIntoparserLogFile("Total number of lines: " + std::to_string($p.ctx->stop->getLine()));
        writeIntoparserLogFile("Total number of errors: " + std::to_string(syntaxErrorCount) + "\n");
    }
    ;

program returns [std::string formatted_text]
    : p=program u=unit
    {
        $formatted_text = $p.formatted_text + "\n" + $u.formatted_text;
        log_rule_to_file("program : program unit", $u.ctx->stop->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | u=unit
    {
        $formatted_text = $u.formatted_text;
        log_rule_to_file("program : unit", $u.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;

unit returns [std::string formatted_text]
    : v=var_declaration
    {
        $formatted_text = $v.formatted_text;
        log_rule_to_file("unit : var_declaration", $v.ctx->stop->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | f=func_declaration
    {
        $formatted_text = $f.formatted_text;
        log_rule_to_file("unit : func_declaration", $f.ctx->stop->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | fd=func_definition
    {
        $formatted_text = $fd.formatted_text;
        log_rule_to_file("unit : func_definition", $fd.ctx->stop->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;


func_declaration returns [std::string formatted_text]
    : t=type_specifier
    {
        var_type = $t.formatted_text;
    } 
    id=ID
    {
        symbol_info *info = st.lookup($id->getText());
        if (info != nullptr) {
            writeIntoErrorFile("Error at line " + std::to_string($id->getLine()) + ": Function redeclaration - Function '" + $id->getText() + "' is already declared");
            writeIntoparserLogFile("Error at line " + std::to_string($id->getLine()) + ": Function redeclaration - Function '" + $id->getText() + "' is already declared");
            log_rule_to_file("func_declaration : type_specifier ID", $id->getLine());
            syntaxErrorCount++;
        } else {
            st.insert($id->getText(), "ID", $t.formatted_text, 0, "function", "function_declaration", "");
        }
        var_type = "";
    }
    LPAREN {st.enter_scope(); } p=parameter_list RPAREN { st.exit_scope();} sm=SEMICOLON
    {
        $formatted_text = $t.formatted_text + " " + $id->getText() + "(" + $p.formatted_text + ")" + $sm->getText();
        symbol_info *info_temp = st.lookup($id->getText());
        if(info_temp != nullptr)
        {
            info_temp->setParams_list($p.formatted_text);
        }
        log_rule_to_file("func_declaration : type_specifier ID LPAREN parameter_list RPAREN SEMICOLON", $t.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | t=type_specifier
    {
        var_type = $t.formatted_text;
    }
    id=ID
    {
        symbol_info *info = st.lookup($id->getText());
        if (info != nullptr) {
            writeIntoErrorFile(
                std::string("Line# ") + std::to_string($id->getLine()) +
                " with error name: Function redeclaration" +
                " - Function '" + $id->getText() + "' is already declared"
            );
            syntaxErrorCount++;
        } else {
            st.insert($id->getText(), "ID", $t.formatted_text, 0, "function", "function_declaration", "");
        }
        var_type = "";
    }
    LPAREN RPAREN sm=SEMICOLON
    {
        $formatted_text = $t.formatted_text + " " + $id->getText() + "()" + $sm->getText();
        log_rule_to_file("func_declaration : type_specifier ID LPAREN RPAREN SEMICOLON", $t.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;

func_definition returns [std::string formatted_text]
    : t=type_specifier
    id=ID 
    {
        symbol_info *info = st.lookup($id->getText());
        if (info != nullptr) {
            if (info->return_type != $t.formatted_text && info->symbol_type == "function") {
                writeIntoErrorFile(
                    std::string("Error at line ") + std::to_string($id->getLine()) +
                    ": Return type mismatch of " + $id->getText() + "\n"
               );
                writeIntoparserLogFile(
                     std::string("Error at line ") + std::to_string($id->getLine()) +
                    ": Return type mismatch of " + $id->getText() + "\n"
                );

                syntaxErrorCount++;
            }
            if(info->symbol_type != "function") {
                writeIntoErrorFile(
                    std::string("Error at line ") + std::to_string($id->getLine()) +
                    ": Multiple declaration of " + $id->getText() + "\n"
                );
                writeIntoparserLogFile(
                    std::string("Error at line ") + std::to_string($id->getLine()) +
                    ": Multiple declaration of " + $id->getText() + "\n"
                );
                syntaxErrorCount++;
            }
        } else {
            st.insert($id->getText(), "ID", $t.formatted_text, 0, "function", "function_definition");
        }
    } LPAREN { st.enter_scope();} p=parameter_list RPAREN cs=compound_statement_for_func
    {
        if ($t.formatted_text == "void" && $cs.formatted_text.find("return") != std::string::npos) {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Cannot return value from function " + $id->getText() + " with void return type\n"
            );
            writeIntoparserLogFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Cannot return value from function " + $id->getText() + " with void return type\n"
            );
            syntaxErrorCount++;
        }

        if(info != nullptr && info->symbol_type == "function")
        {
            int count1 = std::count(info->parameter_list.begin(), info->parameter_list.end(), ',') + 1;
            if(info->parameter_list == "")
                count1 = 0;
            
            int count2 = std::count($p.formatted_text.begin(), $p.formatted_text.end(), ',') + 1;
            if($p.formatted_text == "")
                count2 = 0;
            if(count1 != count2)
            {
                writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Total number of arguments mismatch with declaration in function " + $id->getText() + "\n"
                );
                writeIntoparserLogFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Total number of arguments mismatch with declaration in function " + $id->getText() + "\n"
                );
                syntaxErrorCount++;
            }
        }

        if(info == nullptr)
        {
            info = st.lookup($id->getText());
            info->setParams_list($p.formatted_text);
        }
        $formatted_text = $t.formatted_text + " " + $id->getText() + "(" + $p.formatted_text + ")" + $cs.formatted_text;
        if($cs.flag)
            log_rule_to_file("compound_statement : LCURL statements RCURL", $cs.ctx->stop->getLine());
        else
            log_rule_to_file("compound_statement : LCURL RCURL", $cs.ctx->stop->getLine());
        writeIntoparserLogFile($cs.formatted_text + "\n");
        std::string symbol_table_str = st.get_all_scopes_as_string();
        writeIntoparserLogFile(symbol_table_str);
        log_rule_to_file("func_definition : type_specifier ID LPAREN parameter_list RPAREN compound_statement", $cs.ctx->stop->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        st.exit_scope();
    }
    | t=type_specifier id=ID
    {
        symbol_info *info = st.lookup($id->getText());
        if (info != nullptr) {
            if (info->return_type != $t.formatted_text && info->symbol_type == "function") {
                writeIntoErrorFile(
                    std::string("Error at line ") + std::to_string($id->getLine()) +
                    ": Return type mismatch of " + $id->getText() + "\n"
                );
                syntaxErrorCount++;
            }
        } else {
            st.insert($id->getText(), "ID", $t.formatted_text, 0, "function", "function_declaration");
        }
    }
    LPAREN RPAREN {st.enter_scope();} cs=compound_statement_for_func
    {
        if ($t.formatted_text == "void" && $cs.formatted_text.find("return") != std::string::npos) {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Cannot return value from function " + $id->getText() + " with void return type\n"
            );
            syntaxErrorCount++;
        }
        $formatted_text = $t.formatted_text + " " + $id->getText() + "()" + $cs.formatted_text;
        if($cs.flag)
            log_rule_to_file("compound_statement : LCURL statements RCURL", $cs.ctx->stop->getLine());
        else
            log_rule_to_file("compound_statement : LCURL RCURL", $cs.ctx->stop->getLine());
        writeIntoparserLogFile($cs.formatted_text + "\n");
        std::string symbol_table_str = st.get_all_scopes_as_string();
        writeIntoparserLogFile(symbol_table_str);
        log_rule_to_file("func_definition : type_specifier ID LPAREN RPAREN compound_statement", $cs.ctx->stop->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        st.exit_scope();
    }
    ;

parameter_list returns [std::string formatted_text]
    : pl=parameter_list COMMA t=type_specifier id=ID
    {
        symbol_info *info = st.get_current_scope_table()->lookup($id->getText());
        if (info != nullptr) {
            writeIntoErrorFile("Error at line " + std::to_string($id->getLine()) + ": Multiple declaration of " + $id->getText() + " in parameter\n");
            syntaxErrorCount++;
        } else {
            st.insert($id->getText(), "ID", "null", 0, $t.formatted_text, "parameter");
        }
        $formatted_text = $pl.formatted_text + "," + $t.formatted_text + " " + $id->getText();
        log_rule_to_file("parameter_list : parameter_list COMMA type_specifier ID", $t.ctx->start->getLine());
    }
    | t=type_specifier id=ID
    {
        symbol_info *info = st.get_current_scope_table()->lookup($id->getText());
        if (info != nullptr) {
            writeIntoErrorFile("Error at line " + std::to_string($id->getLine()) + ": Parameter '" + $id->getText() + "' is already declared\n");
            syntaxErrorCount++;
        } else {
            st.insert($id->getText(), "ID", "null", 0, $t.formatted_text, "parameter");
        }
        $formatted_text = $t.formatted_text + " " + $id->getText();
        log_rule_to_file("parameter_list : type_specifier ID", $t.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | err=parameter_list_err
    {
        $formatted_text = $err.formatted_text;
        writeIntoErrorFile("Error at line " + std::to_string($err.ctx->start->getLine()) + ": " + $err.error_name + "\n");
        syntaxErrorCount++;
        
        log_rule_to_file("parameter_list : type_specifier", $err.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        writeIntoparserLogFile("Error at line " + std::to_string($err.ctx->start->getLine()) + ": " + $err.error_name + "\n");


    }
    ;

parameter_list_err returns [std::string error_name, std::string formatted_text]
    : t=type_specifier ADDOP ID
    {
        $error_name = "invalid parameter declaration";
        $formatted_text = $t.formatted_text;
    }

    | t=type_specifier ADDOP
    {
        $error_name = "syntax error, unexpected ADDOP, expecting RPAREN or COMMA";
        $formatted_text = $t.formatted_text;
    }

    | t=type_specifier
    {
        $error_name = "syntax error, missing parameter identifier";
        $formatted_text = $t.formatted_text;
    }
    ;


compound_statement_for_func returns [std::string formatted_text, bool flag]
    : lc=LCURL s=statements rc=RCURL
    {
        bool flag = true;
        $formatted_text = $lc->getText() + "\n" + $s.formatted_text + "\n" + $rc->getText();
    }
    | lc=LCURL rc=RCURL
    {
        bool flag = false;
        $formatted_text = $lc->getText() + $rc->getText();
    }
    ;

compound_statement returns [std::string formatted_text]
    : lc=LCURL {st.enter_scope();} s=statements rc=RCURL 
    {

        $formatted_text = $lc->getText() + "\n" + $s.formatted_text + "\n" + $rc->getText();
        log_rule_to_file("compound_statement : LCURL statements RCURL", $rc->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | lc=LCURL {st.enter_scope();} rc=RCURL 
    {
        $formatted_text = $lc->getText() + "\n" + $rc->getText();
        log_rule_to_file("compound_statement : LCURL RCURL", $rc->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        st.exit_scope();
    }
    ;

var_declaration returns [std::string formatted_text]
    : t=type_specifier 
    { 
        var_type = $t.formatted_text;
        if (var_type == "void") {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($t.ctx->start->getLine()) +
                ": Variable type cannot be void\n"
            );
            writeIntoparserLogFile(
                std::string("Error at line ") + std::to_string($t.ctx->start->getLine()) +
                ": Variable type cannot be void\n"
            );
            syntaxErrorCount++;
        }
    }
    dl=declaration_list sm=SEMICOLON
    {
        $formatted_text = $t.formatted_text + " " + $dl.formatted_text + ";";
        log_rule_to_file("var_declaration : type_specifier declaration_list SEMICOLON", $t.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        var_type = ""; 
    }
    ;

type_specifier returns [std::string formatted_text]
    : INT
    {
        $formatted_text = $INT->getText();
        log_rule_to_file("type_specifier : INT", $INT->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | FLOAT
    {
        $formatted_text = $FLOAT->getText();
        log_rule_to_file("type_specifier : FLOAT", $FLOAT->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | VOID
    {
        $formatted_text = $VOID->getText();
        log_rule_to_file("type_specifier : VOID", $VOID->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;

declaration_list returns [std::string formatted_text]
    : dl=declaration_list COMMA id=ID
    {
        $formatted_text = $dl.formatted_text + "," + $id->getText();
        symbol_info *info = st.get_current_scope_table()->lookup($id->getText());
        if (info != nullptr) {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Multiple declaration of " + $id->getText());
            writeIntoparserLogFile(std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Multiple declaration of " + $id->getText());
            log_rule_to_file("declaration_list : declaration_list COMMA ID", $id->getLine());
            writeIntoparserLogFile($formatted_text + "\n");
            syntaxErrorCount++;
        } else {
            st.insert($id->getText(),"ID", "null", 0, var_type, "variable");
            log_rule_to_file("declaration_list : declaration_list COMMA ID", $id->getLine());
            writeIntoparserLogFile($formatted_text + "\n");
        }
    }
    | dl=declaration_list COMMA id=ID LTHIRD ci=CONST_INT RTHIRD
    {
        $formatted_text = $dl.formatted_text + "," + $id->getText() + "[" + $ci->getText() + "]";
        if($ci->getText().find('.') != std::string::npos)
        {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($ci->getLine()) +
                ": Expression inside third brackets not an integer\n");
            writeIntoparserLogFile(std::string("Error at line ") + std::to_string($ci->getLine()) +
                ": Expression inside third brackets not an integer\n");
            log_rule_to_file("declaration_list : declaration_list COMMA ID LTHIRD CONST_INT RTHIRD", $ci->getLine());
            writeIntoparserLogFile($dl.formatted_text + "," + $id->getText() + "[" + $ci->getText() + "]\n");
            syntaxErrorCount++;
        }else{
            symbol_info *info = st.get_current_scope_table()->lookup($id->getText());
            if (info != nullptr) {
                writeIntoErrorFile(
                    std::string("Error at line ") + std::to_string($id->getLine()) +
                    ": Multiple declaration of " + $id->getText() + "\n");
                writeIntoparserLogFile(std::string("Error at line ") + std::to_string($id->getLine()) +
                    ": Multiple declaration of " + $id->getText() + "\n");
                log_rule_to_file("declaration_list : declaration_list COMMA ID LTHIRD CONST_INT RTHIRD", $id->getLine());
                writeIntoparserLogFile($formatted_text + "\n");
                syntaxErrorCount++;
            } else {
                st.insert($id->getText(),"ID", "null", 0, var_type, "array");
                log_rule_to_file("declaration_list : declaration_list COMMA ID LTHIRD CONST_INT RTHIRD", $dl.ctx->start->getLine());
                writeIntoparserLogFile($formatted_text + "\n");
            }
        }
    }
    | id=ID
    {
        $formatted_text = $id->getText();
        symbol_info *info = st.get_current_scope_table()->lookup($id->getText());
        if (info != nullptr) {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Multiple declaration of " + $id->getText() + "\n");
            writeIntoparserLogFile(std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Multiple declaration of " + $id->getText() + "\n");
            log_rule_to_file("declaration_list : ID", $id->getLine());
            writeIntoparserLogFile($formatted_text + "\n");
            syntaxErrorCount++;
        } else {
            st.insert($id->getText(),"ID", var_type, 0, var_type, "variable");
            log_rule_to_file("declaration_list : ID", $id->getLine());
            writeIntoparserLogFile($formatted_text + "\n");
        }
    }
    | id=ID LTHIRD ci=CONST_INT RTHIRD
    {
        if($ci->getText().find('.') != std::string::npos)
        {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($ci->getLine()) +
                ": Expression inside third brackets not an integer\n");
            writeIntoparserLogFile(std::string("Error at line ") + std::to_string($ci->getLine()) +
                ": Expression inside third brackets not an integer");
            log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD", $ci->getLine());
            writeIntoparserLogFile($id->getText() + "[" + $ci->getText() + "]\n");
            syntaxErrorCount++;
        }else{
        $formatted_text = $id->getText() + "[" + $ci->getText() + "]";
        symbol_info *info = st.get_current_scope_table()->lookup($id->getText());
        if (info != nullptr) {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Multiple declaration of " + $id->getText() + "\n");
            writeIntoparserLogFile(std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Multiple declaration of " + $id->getText() + "\n");
            log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD", $id->getLine());
            writeIntoparserLogFile($formatted_text + "\n");
            syntaxErrorCount++;
        } else {
            st.insert($id->getText(),"ID", "null", 0, var_type, "array");
            log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD", $id->getLine());
            writeIntoparserLogFile($formatted_text + "\n");
        }
        }
        
    }
    | err=declaration_list_err
    {
        $formatted_text = $err.formatted_text;
        writeIntoErrorFile("Error at line " + std::to_string($err.ctx->start->getLine()) + ": " + $err.error_name + "\n");
        writeIntoparserLogFile("Error at line " + std::to_string($err.ctx->start->getLine()) + ": " + $err.error_name + "\n");
        syntaxErrorCount++;
    }
    ;

declaration_list_err returns [std::string error_name, std::string formatted_text]
    : id1=ID op=(ADDOP | MULOP) id2=ID
    {
        $error_name = "syntax error, unexpected ADDOP, expecting COMMA or SEMICOLON";
        log_rule_to_file("declaration_list : ID", $id1->getLine());
        $formatted_text = $id1->getText();
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | id1=ID id2=ID
    {
        $error_name = "syntax error, expecting COMMA\n";
        log_rule_to_file("declaration_list : ID ID", $id1->getLine());
        $formatted_text = $id1->getText();
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | id1=ID LTHIRD ci1=CONST_INT RTHIRD id2=ID
    {
        $error_name = "syntax error, expecting COMMA\n";
        $formatted_text = $id1->getText() + "[" + $ci1->getText() + "]";
        log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD ID", $id1->getLine());
        writeIntoparserLogFile($formatted_text + "," + $id2->getText() + "\n");
    }
    | id=ID LTHIRD e=expression RTHIRD
    {
        $error_name = "syntax error, unexpected EXPRESSION\n";
        $formatted_text = $id->getText() + "[" + $e.formatted_text + "]";
        log_rule_to_file("declaration_list : ID LTHIRD expression RTHIRD", $id->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;
statements returns [std::string formatted_text]
    : s=statement
    {
        $formatted_text = $s.formatted_text;
        log_rule_to_file("statements : statement", $s.ctx->stop->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | sList=statements s=statement
    {
        $formatted_text = $sList.formatted_text + "\n" + $s.formatted_text;
        log_rule_to_file("statements : statements statement", $s.ctx->stop->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;

statement returns [std::string formatted_text]
    :kw=FOR LPAREN e1=expression_statement e2=expression_statement e3=expression RPAREN s=statement
    {
        $formatted_text = $kw->getText() + "(" + $e1.formatted_text + $e2.formatted_text + $e3.formatted_text + ")" + $s.formatted_text;
        log_rule_to_file("statement : FOR LPAREN expression_statement expression_statement expression RPAREN statement", $kw->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | kw=IF LPAREN e=expression RPAREN s=statement
    {
        $formatted_text = $kw->getText() + "(" + $e.formatted_text + ")" + $s.formatted_text;
        log_rule_to_file("statement : IF LPAREN expression RPAREN statement", $kw->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | kw=IF LPAREN e=expression RPAREN s1=statement kw2=ELSE s2=statement
    {
        $formatted_text = $kw->getText() + "(" + $e.formatted_text + ")" + $s1.formatted_text+ $kw2->getText() + " " + $s2.formatted_text;
        log_rule_to_file("statement : IF LPAREN expression RPAREN statement ELSE statement", $kw->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | kw=WHILE LPAREN e=expression RPAREN s=statement
    {
        $formatted_text = $kw->getText() + "(" + $e.formatted_text + ")" + $s.formatted_text;
        log_rule_to_file("statement : WHILE LPAREN expression RPAREN statement", $kw->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | kw=PRINTLN LPAREN id=ID RPAREN sm=SEMICOLON
    {
        symbol_info *info = st.lookup($id->getText());
        if (info == nullptr) {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Undeclared variable " + $id->getText() + "\n"
            );
            writeIntoparserLogFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Undeclared variable " + $id->getText() + "\n"
            );
            syntaxErrorCount++;
        }
        $formatted_text = $kw->getText() + "(" + $id->getText() + ")" + $sm->getText();
        log_rule_to_file("statement : PRINTLN LPAREN ID RPAREN SEMICOLON", $kw->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | kw=RETURN e=expression sm=SEMICOLON
    {
        $formatted_text = $kw->getText() + " " + $e.formatted_text + $sm->getText();
        log_rule_to_file("statement : RETURN expression SEMICOLON", $kw->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    |
    v=var_declaration
    {
        $formatted_text = $v.formatted_text;
        log_rule_to_file("statement : var_declaration", $v.ctx->stop->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | es=expression_statement
    {
        $formatted_text = $es.formatted_text;
        log_rule_to_file("statement : expression_statement", $es.ctx->stop->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | c=compound_statement
    {
        $formatted_text = $c.formatted_text;
        writeIntoparserLogFile(st.get_all_scopes_as_string());
        log_rule_to_file("statement : compound_statement", $c.ctx->stop->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        st.exit_scope();
    }
    | 
    assignment_error_statement
    ;

assignment_error_statement returns [std::string error_name, ParserRuleContext *ctx]
    : id=ID ASSIGNOP SEMICOLON
    {
        $error_name = "syntax error: missing expression after assignment operator '='";
        $ctx = _localctx; 
        
        writeIntoErrorFile("Error at line " + std::to_string($id->getLine()) + ": " + $error_name + "\n");
        syntaxErrorCount++;
    }
    ;

expression_statement returns [std::string formatted_text]
    : sm=SEMICOLON
    {
        $formatted_text = $sm->getText();
        log_rule_to_file("expression_statement : SEMICOLON", $sm->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | e=expression sm=SEMICOLON
    {
        $formatted_text = $e.formatted_text + $sm->getText();
        log_rule_to_file("expression_statement : expression SEMICOLON", $e.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;

variable returns [std::string formatted_text, std::string variable_name]
    : id=ID
    {
        $formatted_text = $id->getText();
        $variable_name = $id->getText();
        log_rule_to_file("variable : ID", $id->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | id=ID LTHIRD e=expression RTHIRD
    {
        $formatted_text = $id->getText() + "[" + $e.formatted_text + "]";
        $variable_name = $id->getText();
        if($e.formatted_text.find('.') != std::string::npos)
        {
            log_rule_to_file("variable : ID LTHIRD expression RTHIRD", $e.ctx->start->getLine());
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($e.ctx->start->getLine()) +
                ": Expression inside third brackets not an integer\n");
            writeIntoparserLogFile(std::string("Error at line ") + std::to_string($e.ctx->start->getLine()) +
                ": Expression inside third brackets not an integer\n");
            writeIntoparserLogFile($id->getText() + "[" + $e.formatted_text + "]\n");
            syntaxErrorCount++;
        }
        else
        {
            log_rule_to_file("variable : ID LTHIRD expression RTHIRD", $id->getLine());
            writeIntoparserLogFile($formatted_text + "\n");
        }

    }
    ;

expression returns [std::string formatted_text]
    : l=logic_expression
    {
        $formatted_text = $l.formatted_text;
        log_rule_to_file("expression : logic_expression", $l.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    |v=variable op=ASSIGNOP l=logic_expression
    {
        $formatted_text = $v.formatted_text + $op->getText() + $l.formatted_text;
        bool success = true; 
        std::string lhs_name = $v.formatted_text.substr(0, $v.formatted_text.find('['));
        symbol_info* lhs_info = st.lookup(lhs_name);
        if (lhs_info == nullptr) {
            success = false;
            writeIntoErrorFile("Error at line " + std::to_string($v.ctx->start->getLine()) + ": Undeclared variable " + lhs_name + "\n");
            writeIntoparserLogFile("Error at line " + std::to_string($v.ctx->start->getLine()) + ": Undeclared variable " + lhs_name + "\n");
            syntaxErrorCount++;
        } 
        else {
            std::string lhs_type = lhs_info->get_type();
            bool lhs_is_array = lhs_info->is_array();
            bool lhs_is_indexed = ($v.formatted_text.find('[') != std::string::npos);

            std::string rhs_type = "int";
            
            std::string func_name_check = $l.formatted_text.substr(0, $l.formatted_text.find('('));
            symbol_info* func_info = st.lookup(func_name_check);
            if (func_info != nullptr && func_info->is_function()) {
                std::string return_type = func_info->get_return_type();
                if (return_type == "void") {
                    success = false;
                    writeIntoErrorFile("Error at line " + std::to_string($l.ctx->start->getLine()) + ": Void function used in expression\n");
                    syntaxErrorCount++;
                }
                rhs_type = return_type;
            }
            else if ($l.formatted_text.find('.') != std::string::npos && $l.formatted_text.find('%') == std::string::npos) {
                rhs_type = "float";
            }
            else {
                symbol_info* rhs_var_info = st.lookup($l.formatted_text);
                if (rhs_var_info != nullptr) {
                    rhs_type = rhs_var_info->get_type();
                }
            }
            if (lhs_is_array && !lhs_is_indexed) {
                success = false;
                writeIntoErrorFile("Error at line " + std::to_string($v.ctx->start->getLine()) + ": Type mismatch, " + lhs_name + " is an array\n");
                writeIntoparserLogFile("Error at line " + std::to_string($v.ctx->start->getLine()) + ": Type mismatch, " + lhs_name + " is an array\n");
                writeIntoparserLogFile($formatted_text + "\n");
                syntaxErrorCount++;
            }
            else if (!lhs_is_array && lhs_is_indexed) {
                success = false;
                writeIntoErrorFile("Error at line " + std::to_string($v.ctx->start->getLine()) + ": " + lhs_name + " not an array\n");
                syntaxErrorCount++;
            }
            else if (lhs_type == "int" && rhs_type == "float") {
                success = false;
                writeIntoErrorFile("Error at line " + std::to_string($v.ctx->start->getLine()) + ": Type Mismatch\n");
                writeIntoparserLogFile("Error at line " + std::to_string($v.ctx->start->getLine()) + ": Type Mismatch\n");
                writeIntoparserLogFile($formatted_text + "\n");
                syntaxErrorCount++;
            }
        }

        log_rule_to_file("expression : variable ASSIGNOP logic_expression", $v.ctx->start->getLine());
        if (success) {
            writeIntoparserLogFile($formatted_text + "\n");
        } else {
            writeIntoparserLogFile($formatted_text + "\n");
        }
    }
        

    ;

logic_expression returns [std::string formatted_text]
    : r=rel_expression
    {
        $formatted_text = $r.formatted_text;
        log_rule_to_file("logic_expression : rel_expression", $r.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | l=rel_expression op=LOGICOP r=rel_expression
    {
        $formatted_text = $l.formatted_text + $op->getText() + $r.formatted_text;
        log_rule_to_file("logic_expression : rel_expression LOGICOP rel_expression", $l.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;

rel_expression returns [std::string formatted_text]
    : s=simple_expression
    {
        $formatted_text = $s.formatted_text;
        log_rule_to_file("rel_expression : simple_expression", $s.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | s1=simple_expression op=RELOP s2=simple_expression
    {
        $formatted_text = $s1.formatted_text + $op->getText() + $s2.formatted_text;
        log_rule_to_file("rel_expression : simple_expression RELOP simple_expression", $s1.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;

simple_expression returns [std::string formatted_text]
    : t=term
    {
        $formatted_text = $t.formatted_text;
        log_rule_to_file("simple_expression : term", $t.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | s=simple_expression op=ADDOP t=term
    {
        $formatted_text = $s.formatted_text + $op->getText() + $t.formatted_text;
        log_rule_to_file("simple_expression : simple_expression ADDOP term", $s.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;

term returns [std::string formatted_text]
    : u=unary_expression
    {
        $formatted_text = $u.formatted_text;
        log_rule_to_file("term : unary_expression", $u.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | t=term op=MULOP u=unary_expression
    {
        $formatted_text = $t.formatted_text  + $op->getText() + $u.formatted_text;
        log_rule_to_file("term : term MULOP unary_expression", $t.ctx->start->getLine());
        bool success = true;
        bool u_is_void_func = false;
        std::string u_text = $u.formatted_text;
        size_t paren_pos = u_text.find('(');
        if (paren_pos != std::string::npos && paren_pos > 0) {
            std::string func_name_check = u_text.substr(0, paren_pos);
            symbol_info* func_info = st.lookup(func_name_check);
            if (func_info && func_info->is_function() && func_info->get_return_type() == "void") {
                u_is_void_func = true;
            }
        }
        bool t_is_void_func = false;
        std::string t_text = $t.formatted_text;
        paren_pos = t_text.find('(');
        if (paren_pos != std::string::npos && paren_pos > 0) {
            std::string func_name_check = t_text.substr(0, paren_pos);
            symbol_info* func_info = st.lookup(func_name_check);
            if (func_info && func_info->is_function() && func_info->get_return_type() == "void") {
                t_is_void_func = true;
            }
        }
        if (u_is_void_func || t_is_void_func) {
            success = false;
            writeIntoErrorFile("Error at line " + std::to_string($op->getLine()) + ": Void function used in expression\n");
            writeIntoparserLogFile("Error at line " + std::to_string($op->getLine()) + ": Void function used in expression\n");
            writeIntoparserLogFile($formatted_text + "\n");
            syntaxErrorCount++;
        }
        else if ($op->getText() == "%") {
            if ($t.formatted_text.find('.') != std::string::npos || $u.formatted_text.find('.') != std::string::npos) {
                success = false;
                writeIntoErrorFile("Error at line " + std::to_string($op->getLine()) + ": Non-Integer operand on modulus operator\n");
                writeIntoparserLogFile("Error at line " + std::to_string($op->getLine()) + ": Non-Integer operand on modulus operator\n");
                writeIntoparserLogFile($formatted_text + "\n");
                syntaxErrorCount++;
            }
            if ($u.formatted_text == "0") {
                success = false;
                writeIntoErrorFile("Error at line " + std::to_string($op->getLine()) + ": Modulus by Zero\n");
                writeIntoparserLogFile("Error at line " + std::to_string($op->getLine()) + ": Modulus by Zero\n");
                writeIntoparserLogFile($formatted_text + "\n");
                syntaxErrorCount++;
            }
        }

        if (success) {
            writeIntoparserLogFile($formatted_text + "\n\n");
        }
    }
    ;

unary_expression returns [std::string formatted_text]
    : op=ADDOP u=unary_expression
    {
        $formatted_text = $op->getText() + $u.formatted_text;
        log_rule_to_file("unary_expression : ADDOP unary_expression", $op->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | op=NOT u=unary_expression
    {
        $formatted_text = $op->getText() + $u.formatted_text;
        log_rule_to_file("unary_expression : NOT unary_expression", $op->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | f=factor
    {
        $formatted_text = $f.formatted_text;
        log_rule_to_file("unary_expression : factor", $f.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;

factor returns [std::string formatted_text]
    : v=variable
    {
        $formatted_text = $v.formatted_text;
        log_rule_to_file("factor : variable", $v.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        symbol_info *info = st.lookup($v.formatted_text);
    }
    |  id=ID LPAREN al=argument_list? RPAREN
    {
        std::string arg_text = ($al.ctx != nullptr) ? $al.formatted_text : "";
        $formatted_text = $id->getText() + "(" + arg_text + ")";
        log_rule_to_file("factor : ID LPAREN argument_list RPAREN", $id->getLine());

        bool success = true;
        symbol_info* func_info = st.lookup($id->getText());

        if (func_info == nullptr || !func_info->is_function()) {
            success = false;
            writeIntoErrorFile("Error at line " + std::to_string($id->getLine()) + ": Undefined function " + $id->getText() + "\n");
            writeIntoparserLogFile("Error at line " + std::to_string($id->getLine()) + ": Undefined function " + $id->getText() + "\n");
            writeIntoparserLogFile($formatted_text + "\n");
            syntaxErrorCount++;

        } else {
            var_type = func_info->get_return_type();

            std::vector<std::string> defined_param_types;
            std::string param_list_str = func_info->get_parameter_list();
            if (!param_list_str.empty()) {
                std::stringstream p_ss(param_list_str);
                std::string p_token;
                while (std::getline(p_ss, p_token, ',')) {
                    size_t first = p_token.find_first_not_of(" \t");
                    if (std::string::npos == first) continue;
                    p_token = p_token.substr(first);
                    size_t first_space = p_token.find(' ');
                    std::string param_type = p_token.substr(0, first_space);
                    if (p_token.find('[') != std::string::npos) {
                        defined_param_types.push_back(param_type + "_array");
                    } else {
                        defined_param_types.push_back(param_type);
                    }
                }
            }
            std::vector<std::string> call_arg_types = get_symbol_types(arg_text, st);
            if (defined_param_types.size() != call_arg_types.size()) {
                success = false;
                writeIntoErrorFile("Error at line " + std::to_string($id->getLine()) + ": Total number of arguments mismatch with declaration in function " + $id->getText() + "\n");
                syntaxErrorCount++;
            } else {
                for (size_t i = 0; i < call_arg_types.size(); ++i) {
                    std::string expected_type = defined_param_types[i];
                    std::string provided_type = call_arg_types[i];
                    if (expected_type == provided_type) {
                        continue;
                    }
                    if (expected_type == "float" && provided_type == "int") {
                        continue;
                    }
                    success = false;
                    if (provided_type.find("_array") != std::string::npos) {
                        writeIntoErrorFile("Error at line " + std::to_string($id->getLine()) + ": Type mismatch, argument " + std::to_string(i+1) + " is an array\n");
                        writeIntoparserLogFile("Error at line " + std::to_string($id->getLine()) + ": Type mismatch, argument " + std::to_string(i+1) + " is an array\n");
                        writeIntoparserLogFile($formatted_text + "\n");
                    } else {
                        writeIntoErrorFile("Error at line " + std::to_string($id->getLine()) + ": " + std::to_string(i+1) + "th argument mismatch in function " + $id->getText() + "\n");
                        writeIntoparserLogFile("Error at line " + std::to_string($id->getLine()) + ": " + std::to_string(i+1) + "th argument mismatch in function " + $id->getText() + "\n");
                        writeIntoparserLogFile($formatted_text + "\n");
                    }
                    
                    syntaxErrorCount++;
                    break;
                }
            }
        }
        
        if(success) {
            writeIntoparserLogFile($formatted_text + "\n\n");
        }
    }
    | LPAREN e=expression RPAREN
    {
        $formatted_text = "(" + $e.formatted_text + ")";
        log_rule_to_file("factor : LPAREN expression RPAREN", $e.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | c=CONST_INT
    {
        $formatted_text = $c->getText();
        log_rule_to_file("factor : CONST_INT", $c->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        data_type = "int";
    }
    | c=CONST_FLOAT
    {
        $formatted_text = $c->getText();
        log_rule_to_file("factor : CONST_FLOAT", $c->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        data_type = "float";
    }
    | v=variable op=INCOP
    {
        $formatted_text = $v.formatted_text + $op->getText();
        log_rule_to_file("factor : variable INCOP", $v.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | v=variable op=DECOP
    {
        $formatted_text = $v.formatted_text + $op->getText();
        log_rule_to_file("factor : variable DECOP", $v.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | factor_err 
    ;

factor_err returns [std::string error_name, ParserRuleContext *ctx]
    : op=(ASSIGNOP | ADDOP | MULOP | RELOP | INCOP | DECOP)
    {
        std::string op_string = "";
        if($op->getText() == "=") {
            op_string = "ASSIGNOP";
        } else if ($op->getText() == "+") {
            op_string = "ADDOP";
        } else if ($op->getText() == "-") {
            op_string = "ADDOP";
        } else if ($op->getText() == "*") {
            op_string = "MULOP";
        } else if ($op->getText() == "/") {
            op_string = "MULOP";
        } else if ($op->getText() == "%") {
            op_string = "MULOP";
        } else if ($op->getText() == "==") {
            op_string = "RELOP";
        } else if ($op->getText() == "!=") {
            op_string = "RELOP";
        } else if ($op->getText() == "<") {
            op_string = "RELOP";
        } else if ($op->getText() == "<=") {
            op_string = "RELOP";
        } else if ($op->getText() == ">") {
            op_string = "RELOP";
        } else if ($op->getText() == ">=") {
            op_string = "RELOP";
        } else if ($op->getText() == "++") {
            op_string = "INCOP";
        } else if ($op->getText() == "--") {
            op_string = "DECOP";
        }
        $error_name = "syntax error: unexpected " + op_string + "\n";
        $ctx = _localctx;
        
        writeIntoErrorFile("Error at line " + std::to_string($op->getLine()) + ": " + $error_name + "\n");
        syntaxErrorCount++;
    }
    ;

argument_list returns [std::string formatted_text]
    : a=arguments
    {
        $formatted_text = $a.formatted_text;
        log_rule_to_file("argument_list : arguments", $a.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    |
    {
        $formatted_text = "";
    }
    ;

arguments returns [std::string formatted_text]
    : a=arguments COMMA l=logic_expression
    {
        $formatted_text = $a.formatted_text + "," + $l.formatted_text;
        log_rule_to_file("arguments : arguments COMMA logic_expression", $a.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    | l=logic_expression
    {
        $formatted_text = $l.formatted_text;
        log_rule_to_file("arguments : logic_expression", $l.ctx->start->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
    }
    ;