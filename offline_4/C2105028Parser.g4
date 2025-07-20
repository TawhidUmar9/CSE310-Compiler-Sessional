parser grammar C2105028Parser;

options
{
    tokenVocab = C2105028Lexer;
}

@parser::header
{
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
    extern std::ofstream assemblyFile;

    extern int syntaxErrorCount;
    extern symbol_table st;
    extern std::string var_type;
    extern bool in_print;
    extern std::string data_type;
    extern bool code_segment_started;
    extern int current_local_offset;
    extern int label_count;
    extern std::vector<int> label_stack;
    extern std::stringstream code_buffer;
    extern bool buffer_mode;
    extern bool is_unreachable;
}

@parser::members
{
    void writeIntoparserLogFile(const std::string message)
    {
        if (!parserLogFile)
        {
            std::cout << "Error opening parserLogFile.txt" << std::endl;
            return;
        }

        parserLogFile << message << std::endl;
        parserLogFile.flush();
    }
    void writeAsm(const std::string &code)
    {
        if (is_unreachable)
        {
            return; // Skip writing code if we are in an unreachable state
        }
        if (buffer_mode)
        {
            code_buffer << code;
        }
        else
        {
            assemblyFile << code;
        }
    }

    void writeIntoErrorFile(const std::string message)
    {
        if (!errorFile)
        {
            std::cout << "Error opening errorFile.txt" << std::endl;
            return;
        }
        errorFile << message << std::endl;
        errorFile.flush();
    }

    void log_rule_to_file(const std::string &rule_name, int line_number)
    {
        writeIntoparserLogFile("Line " + std::to_string(line_number) + ": " + rule_name + "\n");
    }
    std::vector<std::string> get_symbol_types(const std::string &input, symbol_table &st)
    {
        std::vector<std::string> result;
        std::stringstream ss(input);
        std::string token;

        while (std::getline(ss, token, ','))
        {
            size_t first = token.find_first_not_of(" \t\n\r");
            if (std::string::npos == first)
                continue;
            token = token.substr(first);

            size_t last = token.find_last_not_of(" \t\n\r");
            token = token.substr(0, last + 1);

            bool is_numeric = true;
            bool is_float = false;
            bool has_operator = false;
            for (char const &c : token)
            {
                if (std::isdigit(c) == 0 && c != '.' && c != '+' && c != '-' && c != '*' && c != '/' && c != '%')
                {
                    is_numeric = false;
                }
                if (c == '.')
                    is_float = true;
                if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%')
                    has_operator = true;
            }

            if (is_numeric && !token.empty())
            {
                if (has_operator)
                {
                    if (is_float)
                    {
                        result.push_back("float");
                    }
                    else
                    {
                        result.push_back("int");
                    }
                }
                else
                {
                    result.push_back(is_float ? "float" : "int");
                }
                continue;
            }
            std::string base_name = token.substr(0, token.find("["));
            symbol_info *info = st.lookup(base_name);
            if (info == nullptr)
            {
                result.push_back("UNDEFINED");
            }
            else
            {
                if (info->is_array())
                {
                    if (token.find("[") != std::string::npos)
                    {
                        result.push_back(info->get_type());
                    }
                    else
                    {
                        result.push_back(info->get_type() + "_array");
                    }
                }
                else
                {
                    result.push_back(info->get_type());
                }
            }
        }
        return result;
    }
}

start : p = program
{
    is_unreachable = false;
    log_rule_to_file("start : program", $p.ctx->stop->getLine());
    std::string symbol_table_str = st.get_all_scopes_as_string();
    writeIntoparserLogFile(symbol_table_str);
    writeIntoparserLogFile("Total number of lines: " + std::to_string($p.ctx->stop->getLine()));
    writeIntoparserLogFile("Total number of errors: " + std::to_string(syntaxErrorCount) + "\n");
    writeAsm("\tnew_line proc\n");
    writeAsm("\t\tpush ax\n");
    writeAsm("\t\tpush dx\n");
    writeAsm("\t\tmov ah, 2\n");
    writeAsm("\t\tmov dl, 0Dh\n");
    writeAsm("\t\tint 21h\n");
    writeAsm("\t\tmov ah, 2\n");
    writeAsm("\t\tmov dl, 0Ah\n");
    writeAsm("\t\tint 21h\n");
    writeAsm("\t\tpop dx\n");
    writeAsm("\t\tpop ax\n");
    writeAsm("\t\tret\n");
    writeAsm("\tnew_line endp\n");

    writeAsm("\tprint_output proc\n");
    writeAsm("\t\tpush ax\n");
    writeAsm("\t\tpush bx\n");
    writeAsm("\t\tpush cx\n");
    writeAsm("\t\tpush dx\n");
    writeAsm("\t\tpush si\n");
    writeAsm("\t\tlea si, number\n");
    writeAsm("\t\tmov bx, 10\n");
    writeAsm("\t\tadd si, 4\n");
    writeAsm("\t\tcmp ax, 0\n");
    writeAsm("\t\tjnge negate\n");
    writeAsm("\t\tprint:\n");
    writeAsm("\t\txor dx, dx\n");
    writeAsm("\t\tdiv bx\n");
    writeAsm("\t\tmov [si], dl\n");
    writeAsm("\t\tadd [si], '0'\n");
    writeAsm("\t\tdec si\n");
    writeAsm("\t\tcmp ax, 0\n");
    writeAsm("\t\tjne print\n");
    writeAsm("\t\tinc si\n");
    writeAsm("\t\tlea dx, si\n");
    writeAsm("\t\tmov ah, 9\n");
    writeAsm("\t\tint 21h\n");
    writeAsm("\t\tpop si\n");
    writeAsm("\t\tpop dx\n");
    writeAsm("\t\tpop cx\n");
    writeAsm("\t\tpop bx\n");
    writeAsm("\t\tpop ax\n");
    writeAsm("\t\tret\n");
    writeAsm("\t\tnegate:\n");
    writeAsm("\t\tpush ax\n");
    writeAsm("\t\tmov ah, 2\n");
    writeAsm("\t\tmov dl, '-'\n");
    writeAsm("\t\tint 21h\n");
    writeAsm("\t\tpop ax\n");
    writeAsm("\t\tneg ax\n");
    writeAsm("\t\tjmp print\n");
    writeAsm("\tprint_output endp\n");
    writeAsm("\nEND MAIN\n");
    if (parserLogFile.is_open())
        parserLogFile.close();
    if (errorFile.is_open())
        errorFile.close();
    if (assemblyFile.is_open())
        assemblyFile.close();
};

program returns[std::string formatted_text]
    : p = program u = unit
{
    $formatted_text = $p.formatted_text + "\n" + $u.formatted_text;
    log_rule_to_file("program : program unit", $u.ctx->stop->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| u = unit
{
    $formatted_text = $u.formatted_text;
    log_rule_to_file("program : unit", $u.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
};

unit returns[std::string formatted_text]
    : v = var_declaration
{
    $formatted_text = $v.formatted_text;
    log_rule_to_file("unit : var_declaration", $v.ctx->stop->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| f = func_declaration
{
    $formatted_text = $f.formatted_text;
    log_rule_to_file("unit : func_declaration", $f.ctx->stop->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| fd = func_definition
{
    $formatted_text = $fd.formatted_text;
    log_rule_to_file("unit : func_definition", $fd.ctx->stop->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
};

func_declaration returns[std::string formatted_text]
    : t = type_specifier
{
    var_type = $t.formatted_text;
}
id = ID
{
    symbol_info *info = st.lookup($id->getText());
    if (info != nullptr)
    {
        writeIntoErrorFile("Error at line " + std::to_string($id->getLine()) + ": Function redeclaration - Function '" + $id->getText() + "' is already declared");
        writeIntoparserLogFile("Error at line " + std::to_string($id->getLine()) + ": Function redeclaration - Function '" + $id->getText() + "' is already declared");
        log_rule_to_file("func_declaration : type_specifier ID", $id->getLine());
        syntaxErrorCount++;
    }
    else
    {
        st.insert($id->getText(), "ID", $t.formatted_text, 0, "function", "function_declaration", "");
    }
    var_type = "";
}
LPAREN { st.enter_scope(); }
p = parameter_list[4] RPAREN { st.exit_scope(); }
sm = SEMICOLON
{
    $formatted_text = $t.formatted_text + " " + $id->getText() + "(" + $p.formatted_text + ")" + $sm->getText();
    symbol_info *info_temp = st.lookup($id->getText());
    if (info_temp != nullptr)
    {
        info_temp->setParams_list($p.formatted_text);
    }
    log_rule_to_file("func_declaration : type_specifier ID LPAREN parameter_list RPAREN SEMICOLON", $t.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| t = type_specifier
{
    var_type = $t.formatted_text;
}
id = ID
{
    symbol_info *info = st.lookup($id->getText());
    if (info != nullptr)
    {
        writeIntoErrorFile(
            std::string("Line# ") + std::to_string($id->getLine()) +
            " with error name: Function redeclaration" +
            " - Function '" + $id->getText() + "' is already declared");
        syntaxErrorCount++;
    }
    else
    {
        st.insert($id->getText(), "ID", $t.formatted_text, 0, "function", "function_declaration", "");
    }
    var_type = "";
}
LPAREN RPAREN sm = SEMICOLON
{
    $formatted_text = $t.formatted_text + " " + $id->getText() + "()" + $sm->getText();
    log_rule_to_file("func_declaration : type_specifier ID LPAREN RPAREN SEMICOLON", $t.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
};

func_definition returns[std::string formatted_text]
    : t = type_specifier { is_unreachable = false; }
id = ID
{
    symbol_info *info = st.lookup($id->getText());
    if (info != nullptr)
    {
        if (info->return_type != $t.formatted_text && info->symbol_type == "function")
        {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Return type mismatch of " + $id->getText() + "\n");
            writeIntoparserLogFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Return type mismatch of " + $id->getText() + "\n");

            syntaxErrorCount++;
        }
        if (info->symbol_type != "function")
        {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Multiple declaration of " + $id->getText() + "\n");
            writeIntoparserLogFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Multiple declaration of " + $id->getText() + "\n");
            syntaxErrorCount++;
        }
    }
    else
    {
        st.insert($id->getText(), "ID", $t.formatted_text, 0, "function", "function_definition");
    }
    is_unreachable = false;
    std::cout << "Set flag to false for function definition\n";
    if (!code_segment_started)
    {
        code_segment_started = true;
        writeAsm(".CODE\n");
    }
    if ($id->getText() == "main")
    {
        writeAsm("\n" + $id->getText() + " PROC\n");
        writeAsm("    MOV AX, @DATA\n");
        writeAsm("    MOV DS, AX\n");
        writeAsm("    PUSH BP\n");
        writeAsm("    MOV BP, SP\n");
    }
    else
    {
        writeAsm("\n" + $id->getText() + " PROC\n");
        writeAsm("    PUSH BP\n");
        writeAsm("    MOV BP, SP\n");
    }
    current_local_offset = 0;
    int param_offset = 4;
}
LPAREN { st.enter_scope(); }
p = parameter_list[4] RPAREN cs = compound_statement_for_func
{
    if ($t.formatted_text == "void" && $cs.formatted_text.find("return") != std::string::npos)
    {
        writeIntoErrorFile(
            std::string("Error at line ") + std::to_string($id->getLine()) +
            ": Cannot return value from function " + $id->getText() + " with void return type\n");
        writeIntoparserLogFile(
            std::string("Error at line ") + std::to_string($id->getLine()) +
            ": Cannot return value from function " + $id->getText() + " with void return type\n");
        syntaxErrorCount++;
    }

    if (info != nullptr && info->symbol_type == "function")
    {
        int count1 = std::count(info->parameter_list.begin(), info->parameter_list.end(), ',') + 1;
        if (info->parameter_list == "")
            count1 = 0;

        int count2 = std::count($p.formatted_text.begin(), $p.formatted_text.end(), ',') + 1;
        if ($p.formatted_text == "")
            count2 = 0;
        if (count1 != count2)
        {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Total number of arguments mismatch with declaration in function " + $id->getText() + "\n");
            writeIntoparserLogFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Total number of arguments mismatch with declaration in function " + $id->getText() + "\n");
            syntaxErrorCount++;
        }
    }

    if (info == nullptr)
    {
        info = st.lookup($id->getText());
        info->setParams_list($p.formatted_text);
    }
    $formatted_text = $t.formatted_text + " " + $id->getText() + "(" + $p.formatted_text + ")" + $cs.formatted_text;
    if ($cs.flag)
        log_rule_to_file("compound_statement : LCURL statements RCURL", $cs.ctx->stop->getLine());
    else
        log_rule_to_file("compound_statement : LCURL RCURL", $cs.ctx->stop->getLine());
    writeIntoparserLogFile($cs.formatted_text + "\n");
    std::string symbol_table_str = st.get_all_scopes_as_string();
    writeIntoparserLogFile(symbol_table_str);
    log_rule_to_file("func_definition : type_specifier ID LPAREN parameter_list RPAREN compound_statement", $cs.ctx->stop->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
    is_unreachable = false;
    writeAsm("    MOV SP, BP\n");
    writeAsm("    POP BP\n");
    if ($id->getText() == "main")
    {
        writeAsm("    MOV AH, 4CH\n");
        writeAsm("    INT 21H\n");
    }
    else
    {
        writeAsm("    RET\n");
    }
    writeAsm($id->getText() + " ENDP\n");

    st.exit_scope();
}
| t = type_specifier id = ID
{
    is_unreachable = false;
    symbol_info *info = st.lookup($id->getText());
    if (info != nullptr)
    {
        if (info->return_type != $t.formatted_text && info->symbol_type == "function")
        {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Return type mismatch of " + $id->getText() + "\n");
            syntaxErrorCount++;
        }
    }
    else
    {
        st.insert($id->getText(), "ID", $t.formatted_text, 0, "function", "function_declaration");
        if (!code_segment_started)
        {
            code_segment_started = true;
            writeAsm(".CODE\n");
        }
        if ($id->getText() == "main")
        {
            writeAsm("\n" + $id->getText() + " PROC\n");
            writeAsm("    MOV AX, @DATA\n");
            writeAsm("    MOV DS, AX\n");
            writeAsm("    PUSH BP\n");
            writeAsm("    MOV BP, SP\n");
        }
        else
        {
            writeAsm("\n" + $id->getText() + " PROC\n");
            writeAsm("    PUSH BP\n");
            writeAsm("    MOV BP, SP\n");
        }
        current_local_offset = 0;
    }
}
LPAREN RPAREN { st.enter_scope(); }
cs = compound_statement_for_func
{
    if ($t.formatted_text == "void" && $cs.formatted_text.find("return") != std::string::npos)
    {
        writeIntoErrorFile(
            std::string("Error at line ") + std::to_string($id->getLine()) +
            ": Cannot return value from function " + $id->getText() + " with void return type\n");
        syntaxErrorCount++;
    }
    $formatted_text = $t.formatted_text + " " + $id->getText() + "()" + $cs.formatted_text;
    if ($cs.flag)
        log_rule_to_file("compound_statement : LCURL statements RCURL", $cs.ctx->stop->getLine());
    else
        log_rule_to_file("compound_statement : LCURL RCURL", $cs.ctx->stop->getLine());
    writeIntoparserLogFile($cs.formatted_text + "\n");
    std::string symbol_table_str = st.get_all_scopes_as_string();
    writeIntoparserLogFile(symbol_table_str);
    log_rule_to_file("func_definition : type_specifier ID LPAREN RPAREN compound_statement", $cs.ctx->stop->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
    is_unreachable = false;
    writeAsm("    MOV SP, BP\n");
    writeAsm("    POP BP\n");
    if ($id->getText() == "main")
    {
        writeAsm("    MOV AH, 4CH\n");
        writeAsm("    INT 21H\n");
    }
    else
    {
        writeAsm("    RET\n");
    }
    writeAsm($id->getText() + " ENDP\n");
    st.exit_scope();
};

parameter_list[int current_offset] returns[std::string formatted_text, int total_size]
    : t = type_specifier id = ID
{
    st.insert($id->getText(), "ID", "null", 0, $t.formatted_text, "parameter");
    symbol_info *info = st.get_current_scope_table()->lookup($id->getText());
    if (info)
        info->set_offset(current_offset);

    $formatted_text = $t.formatted_text + " " + $id->getText();
    int my_size = ($t.formatted_text == "int" ? 2 : 4);
    $total_size = my_size;
}
(COMMA pl = parameter_list[current_offset + my_size] {
    $formatted_text += ", " + $pl.formatted_text;
    $total_size += $pl.total_size;
})
    ? | err = parameter_list_err
{
    $formatted_text = $err.formatted_text;
    writeIntoErrorFile("Error at line " + std::to_string($err.ctx->start->getLine()) + ": " + $err.error_name + "\n");
    syntaxErrorCount++;

    log_rule_to_file("parameter_list : type_specifier", $err.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
    writeIntoparserLogFile("Error at line " + std::to_string($err.ctx->start->getLine()) + ": " + $err.error_name + "\n");
};

parameter_list_err returns[std::string error_name, std::string formatted_text]
    : t = type_specifier ADDOP ID
{
    $error_name = "invalid parameter declaration";
    $formatted_text = $t.formatted_text;
}

| t = type_specifier ADDOP
{
    $error_name = "syntax error, unexpected ADDOP, expecting RPAREN or COMMA";
    $formatted_text = $t.formatted_text;
}

| t = type_specifier
{
    $error_name = "syntax error, missing parameter identifier";
    $formatted_text = $t.formatted_text;
};

compound_statement_for_func returns[std::string formatted_text, bool flag]
    : lc = LCURL s = statements rc = RCURL
{
    bool flag = true;
    $formatted_text = $lc->getText() + "\n" + $s.formatted_text + "\n" + $rc->getText();
}
| lc = LCURL rc = RCURL
{
    bool flag = false;
    $formatted_text = $lc->getText() + $rc->getText();
};

compound_statement returns[std::string formatted_text]
    : lc = LCURL { st.enter_scope(); }
s = statements rc = RCURL
{

    $formatted_text = $lc->getText() + "\n" + $s.formatted_text + "\n" + $rc->getText();
    log_rule_to_file("compound_statement : LCURL statements RCURL", $rc->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| lc = LCURL { st.enter_scope(); }
rc = RCURL
{
    $formatted_text = $lc->getText() + "\n" + $rc->getText();
    log_rule_to_file("compound_statement : LCURL RCURL", $rc->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
    st.exit_scope();
};

var_declaration returns[std::string formatted_text]
    : t = type_specifier
{
    var_type = $t.formatted_text;
    if (var_type == "void")
    {
        writeIntoErrorFile(
            std::string("Error at line ") + std::to_string($t.ctx->start->getLine()) +
            ": Variable type cannot be void\n");
        writeIntoparserLogFile(
            std::string("Error at line ") + std::to_string($t.ctx->start->getLine()) +
            ": Variable type cannot be void\n");
        syntaxErrorCount++;
    }
}
dl = declaration_list sm = SEMICOLON
{
    $formatted_text = $t.formatted_text + " " + $dl.formatted_text + ";";
    log_rule_to_file("var_declaration : type_specifier declaration_list SEMICOLON", $t.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
    var_type = "";
};

type_specifier returns[std::string formatted_text]
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
};

declaration_list returns[std::string formatted_text]
    : dl = declaration_list COMMA id = ID
{
    $formatted_text = $dl.formatted_text + "," + $id->getText();
    symbol_info *info = st.get_current_scope_table()->lookup($id->getText());
    if (info != nullptr)
    {
        writeIntoErrorFile(
            std::string("Error at line ") + std::to_string($id->getLine()) +
            ": Multiple declaration of " + $id->getText());
        writeIntoparserLogFile(std::string("Error at line ") + std::to_string($id->getLine()) +
                               ": Multiple declaration of " + $id->getText());
        log_rule_to_file("declaration_list : declaration_list COMMA ID", $id->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        syntaxErrorCount++;
    }
    else
    {
        st.insert($id->getText(), "ID", "null", 0, var_type, "variable");
        log_rule_to_file("declaration_list : declaration_list COMMA ID", $id->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        if (st.get_current_scope_id() == 1)
        {
            writeAsm("    " + $id->getText() + (var_type == "int" ? " DW ?\n" : " DD ?\n"));
        }
        else
        {
            int size = (var_type == "int" ? 2 : 4);
            current_local_offset -= size;
            st.set_offset_for_symbol($id->getText(), current_local_offset);
            writeAsm("    SUB SP, " + std::to_string(size) + " ; " + $id->getText() + " at [BP" + std::to_string(current_local_offset) + "]\n");
        }
    }
}
| dl = declaration_list COMMA id = ID LTHIRD ci = CONST_INT RTHIRD
{
    $formatted_text = $dl.formatted_text + "," + $id->getText() + "[" + $ci->getText() + "]";
    if ($ci->getText().find('.') != std::string::npos)
    {
        writeIntoErrorFile(
            std::string("Error at line ") + std::to_string($ci->getLine()) +
            ": Expression inside third brackets not an integer\n");
        writeIntoparserLogFile(std::string("Error at line ") + std::to_string($ci->getLine()) +
                               ": Expression inside third brackets not an integer\n");
        log_rule_to_file("declaration_list : declaration_list COMMA ID LTHIRD CONST_INT RTHIRD", $ci->getLine());
        writeIntoparserLogFile($dl.formatted_text + "," + $id->getText() + "[" + $ci->getText() + "]\n");
        syntaxErrorCount++;
    }
    else
    {
        symbol_info *info = st.get_current_scope_table()->lookup($id->getText());
        if (info != nullptr)
        {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Multiple declaration of " + $id->getText() + "\n");
            writeIntoparserLogFile(std::string("Error at line ") + std::to_string($id->getLine()) +
                                   ": Multiple declaration of " + $id->getText() + "\n");
            log_rule_to_file("declaration_list : declaration_list COMMA ID LTHIRD CONST_INT RTHIRD", $id->getLine());
            writeIntoparserLogFile($formatted_text + "\n");
            syntaxErrorCount++;
        }
        else
        {
            st.insert($id->getText(), "ID", "null", 0, var_type, "array");
            log_rule_to_file("declaration_list : declaration_list COMMA ID LTHIRD CONST_INT RTHIRD", $dl.ctx->start->getLine());
            writeIntoparserLogFile($formatted_text + "\n");
        }
        if (st.get_current_scope_id() == 1)
        {
            writeAsm("    " + $id->getText() + (var_type == "int" ? " DW " : " DD ") + $ci->getText() + " DUP(?)\n");
        }
        else
        {
            int element_size = (var_type == "int" ? 2 : 4);
            int array_size = std::stoi($ci->getText());
            int total_size = element_size * array_size;
            current_local_offset -= total_size;
            st.set_offset_for_symbol($id->getText(), current_local_offset);
            writeAsm("    SUB SP, " + std::to_string(total_size) + " ; Array " + $id->getText() + " starts at [BP" + std::to_string(current_local_offset) + "]\n");
        }
    }
}
| id = ID
{
    $formatted_text = $id->getText();
    symbol_info *info = st.get_current_scope_table()->lookup($id->getText());
    if (info != nullptr)
    {
        writeIntoErrorFile(
            std::string("Error at line ") + std::to_string($id->getLine()) +
            ": Multiple declaration of " + $id->getText() + "\n");
        writeIntoparserLogFile(std::string("Error at line ") + std::to_string($id->getLine()) +
                               ": Multiple declaration of " + $id->getText() + "\n");
        log_rule_to_file("declaration_list : ID", $id->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        syntaxErrorCount++;
    }
    else
    {
        st.insert($id->getText(), "ID", var_type, 0, var_type, "variable");
        log_rule_to_file("declaration_list : ID", $id->getLine());
        writeIntoparserLogFile($formatted_text + "\n");
        if (st.get_current_scope_id() == 1)
        {
            writeAsm("    " + $id->getText() + (var_type == "int" ? " DW ?\n" : " DD ?\n"));
        }
        else
        {
            int size = (var_type == "int" ? 2 : 4);
            current_local_offset -= size;
            st.set_offset_for_symbol($id->getText(), current_local_offset);
            writeAsm("    SUB SP, " + std::to_string(size) + " ; " + $id->getText() + " at [BP" + std::to_string(current_local_offset) + "]\n");
        }
    }
}
| id = ID LTHIRD ci = CONST_INT RTHIRD
{
    if ($ci->getText().find('.') != std::string::npos)
    {
        writeIntoErrorFile(
            std::string("Error at line ") + std::to_string($ci->getLine()) +
            ": Expression inside third brackets not an integer\n");
        writeIntoparserLogFile(std::string("Error at line ") + std::to_string($ci->getLine()) +
                               ": Expression inside third brackets not an integer");
        log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD", $ci->getLine());
        writeIntoparserLogFile($id->getText() + "[" + $ci->getText() + "]\n");
        syntaxErrorCount++;
    }
    else
    {
        $formatted_text = $id->getText() + "[" + $ci->getText() + "]";
        symbol_info *info = st.get_current_scope_table()->lookup($id->getText());
        if (info != nullptr)
        {
            writeIntoErrorFile(
                std::string("Error at line ") + std::to_string($id->getLine()) +
                ": Multiple declaration of " + $id->getText() + "\n");
            writeIntoparserLogFile(std::string("Error at line ") + std::to_string($id->getLine()) +
                                   ": Multiple declaration of " + $id->getText() + "\n");
            log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD", $id->getLine());
            writeIntoparserLogFile($formatted_text + "\n");
            syntaxErrorCount++;
        }
        else
        {
            st.insert($id->getText(), "ID", "null", 0, var_type, "array");
            log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD", $id->getLine());
            writeIntoparserLogFile($formatted_text + "\n");
            if (st.get_current_scope_id() == 1)
            {
                writeAsm("    " + $id->getText() + (var_type == "int" ? " DW " : " DD ") + $ci->getText() + " DUP(?)\n");
            }
            else
            {
                int element_size = (var_type == "int" ? 2 : 4);
                int array_size = std::stoi($ci->getText());
                int total_size = element_size * array_size;
                current_local_offset -= total_size;
                st.set_offset_for_symbol($id->getText(), current_local_offset);
                writeAsm("    SUB SP, " + std::to_string(total_size) + " ; Array " + $id->getText() + " starts at [BP" + std::to_string(current_local_offset) + "]\n");
            }
        }
    }
}
| err = declaration_list_err
{
    $formatted_text = $err.formatted_text;
    writeIntoErrorFile("Error at line " + std::to_string($err.ctx->start->getLine()) + ": " + $err.error_name + "\n");
    writeIntoparserLogFile("Error at line " + std::to_string($err.ctx->start->getLine()) + ": " + $err.error_name + "\n");
    syntaxErrorCount++;
};

declaration_list_err returns[std::string error_name, std::string formatted_text]
    : id1 = ID op = (ADDOP | MULOP) id2 = ID
{
    $error_name = "syntax error, unexpected ADDOP, expecting COMMA or SEMICOLON";
    log_rule_to_file("declaration_list : ID", $id1->getLine());
    $formatted_text = $id1->getText();
    writeIntoparserLogFile($formatted_text + "\n");
}
| id1 = ID id2 = ID
{
    $error_name = "syntax error, expecting COMMA\n";
    log_rule_to_file("declaration_list : ID ID", $id1->getLine());
    $formatted_text = $id1->getText();
    writeIntoparserLogFile($formatted_text + "\n");
}
| id1 = ID LTHIRD ci1 = CONST_INT RTHIRD id2 = ID
{
    $error_name = "syntax error, expecting COMMA\n";
    $formatted_text = $id1->getText() + "[" + $ci1->getText() + "]";
    log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD ID", $id1->getLine());
    writeIntoparserLogFile($formatted_text + "," + $id2->getText() + "\n");
}
| id = ID LTHIRD e = expression RTHIRD
{
    $error_name = "syntax error, unexpected EXPRESSION\n";
    $formatted_text = $id->getText() + "[" + $e.formatted_text + "]";
    log_rule_to_file("declaration_list : ID LTHIRD expression RTHIRD", $id->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
};
statements returns[std::string formatted_text]
    : s = statement
{
    $formatted_text = $s.formatted_text;
    log_rule_to_file("statements : statement", $s.ctx->stop->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| sList = statements s = statement
{
    $formatted_text = $sList.formatted_text + "\n" + $s.formatted_text;
    log_rule_to_file("statements : statements statement", $s.ctx->stop->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
};

statement returns[std::string formatted_text]
    : kw = FOR LPAREN
          e1 = expression_statement
{
    {
        // 1. Create labels for the start and end of the loop.
        int start_label = label_count++;
        int end_label = label_count++;

        // 2. Place the START label.
        writeAsm("L" + std::to_string(start_label) + ":\n");

        // 3. Push BOTH labels onto the stack to be used by later actions.
        // Order is important: end label, then start label.
        label_stack.push_back(end_label);
        label_stack.push_back(start_label);
    }
}
e2 = expression_statement
{
    {
        // 4. Test the condition.
        writeAsm("    CMP AX, 0\n");

        // Peek at the end_label (it's second from the top of the stack).
        int end_label = label_stack.at(label_stack.size() - 2);
        writeAsm("    JE L" + std::to_string(end_label) + "\n");

        // 5. Turn on buffer mode to capture the increment expression's code.
        buffer_mode = true;
        code_buffer.str("");
    }
}
e3 = expression
{
    // 6. Turn off buffer mode.
    buffer_mode = false;
}
RPAREN s = statement
{
    {
        // 7. Retrieve the labels from the stack.
        int start_label = label_stack.back();
        label_stack.pop_back();
        int end_label = label_stack.back();
        label_stack.pop_back();

        // 8. Write the buffered increment code.
        writeAsm(code_buffer.str());

        // 9. Jump back to the start of the loop.
        writeAsm("    JMP L" + std::to_string(start_label) + "\n");

        // 10. Place the END label.
        writeAsm("L" + std::to_string(end_label) + ":\n");
    }

    // Logging/formatting
    $formatted_text = $kw->getText() + "(" + $e1.formatted_text + $e2.formatted_text + $e3.formatted_text + ")" + $s.formatted_text;
    log_rule_to_file("statement : FOR LPAREN ... RPAREN statement", $kw->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| kw = IF LPAREN e = expression RPAREN
{
    {
        writeAsm("    CMP AX, 0 ; Check if the condition is false\n");
        int end_if_label = label_count++;
        writeAsm("    JE L" + std::to_string(end_if_label) + "\n");
        label_stack.push_back(end_if_label);
    }
}
s = statement
{
    {
        int end_if_label = label_stack.back();
        label_stack.pop_back();
        writeAsm("L" + std::to_string(end_if_label) + ":\n");
    }

    $formatted_text = $kw->getText() + "(" + $e.formatted_text + ")" + $s.formatted_text;
    log_rule_to_file("statement : IF LPAREN expression RPAREN statement", $kw->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| kw = IF LPAREN e = expression RPAREN
{
    {
        writeAsm("    CMP AX, 0 ; Check if the condition is false\n");
        int else_label = label_count++;
        int end_label = label_count++;
        writeAsm("    JE L" + std::to_string(else_label) + "\n");
        label_stack.push_back(end_label);
        label_stack.push_back(else_label);
    }
}
s1 = statement kw2 = ELSE
{
    {
        int else_label = label_stack.back();
        label_stack.pop_back();
        int end_label = label_stack.back();
        writeAsm("    JMP L" + std::to_string(end_label) + "\n");
        writeAsm("L" + std::to_string(else_label) + ":\n");
    }
}
s2 = statement
{
    {
        int end_label = label_stack.back();
        label_stack.pop_back();

        writeAsm("L" + std::to_string(end_label) + ":\n");
    }

    $formatted_text = $kw->getText() + "(" + $e.formatted_text + ")" + $s1.formatted_text + " " + $kw2->getText() + " " + $s2.formatted_text;
    log_rule_to_file("statement : IF LPAREN expression RPAREN statement ELSE statement", $kw->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| kw = WHILE
{
    {
        // ACTION 1: Before the condition
        // Create start and end labels for the loop.
        int start_label = label_count++;
        int end_label = label_count++;

        // Place the start label in the code.
        writeAsm("L" + std::to_string(start_label) + ":\n");

        // Push the labels onto the stack for later actions to use.
        label_stack.push_back(end_label);
        label_stack.push_back(start_label);
    }
}
LPAREN e = expression RPAREN
{
    {
        // ACTION 2: After the condition
        // The result of the expression is in AX. Test if it's false.
        writeAsm("    CMP AX, 0\n");

        // Peek at the end label (it's second-from-the-top of the stack)
        // and jump to it if the condition is false.
        int end_label = label_stack.at(label_stack.size() - 2);
        writeAsm("    JE L" + std::to_string(end_label) + "\n");
    }
}
s = statement
{
    {
        // ACTION 3: After the loop body
        // Retrieve the start and end labels from the stack.
        int start_label = label_stack.back();
        label_stack.pop_back();
        int end_label = label_stack.back();
        label_stack.pop_back();

        // Generate the unconditional jump back to the start of the loop.
        writeAsm("    JMP L" + std::to_string(start_label) + "\n");
        // Place the end label.
        writeAsm("L" + std::to_string(end_label) + ":\n");
    }

    // Logging/formatting
    $formatted_text = $kw->getText() + "(" + $e.formatted_text + ")" + $s.formatted_text;
    log_rule_to_file("statement : WHILE LPAREN expression RPAREN statement", $kw->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| kw = PRINTLN LPAREN id = ID RPAREN sm = SEMICOLON
{
    symbol_info *info = st.lookup($id->getText());

    int offset = info->get_offset();
    if (offset == 0)
    {
        writeAsm("    MOV AX, " + $id->getText() + "\n");
    }
    else
    {
        if (offset > 0)
        {
            writeAsm("    MOV AX, WORD PTR [BP+" + std::to_string(offset) + "]\n");
        }
        else
        {
            writeAsm("    MOV AX, WORD PTR [BP" + std::to_string(offset) + "]\n");
        }
    }
    writeAsm("    CALL print_output\n");
    writeAsm("    CALL new_line\n");
    $formatted_text = $kw->getText() + "(" + $id->getText() + ")" + $sm->getText();
    log_rule_to_file("statement : PRINTLN LPAREN ID RPAREN SEMICOLON", $kw->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| kw = RETURN e = expression sm = SEMICOLON
{
    is_unreachable = true;
    $formatted_text = $kw->getText() + " " + $e.formatted_text + $sm->getText();
    log_rule_to_file("statement : RETURN expression SEMICOLON", $kw->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
|
    v = var_declaration
{
    $formatted_text = $v.formatted_text;
    log_rule_to_file("statement : var_declaration", $v.ctx->stop->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| es = expression_statement
{
    $formatted_text = $es.formatted_text;
    log_rule_to_file("statement : expression_statement", $es.ctx->stop->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| c = compound_statement
{
    $formatted_text = $c.formatted_text;
    writeIntoparserLogFile(st.get_all_scopes_as_string());
    log_rule_to_file("statement : compound_statement", $c.ctx->stop->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
    st.exit_scope();
}
|
    assignment_error_statement;

assignment_error_statement returns[std::string error_name, ParserRuleContext *ctx]
    : id = ID ASSIGNOP SEMICOLON
{
    $error_name = "syntax error: missing expression after assignment operator '='";
    $ctx = _localctx;

    writeIntoErrorFile("Error at line " + std::to_string($id->getLine()) + ": " + $error_name + "\n");
    syntaxErrorCount++;
};

expression_statement returns[std::string formatted_text]
    : sm = SEMICOLON
{
    $formatted_text = $sm->getText();
    log_rule_to_file("expression_statement : SEMICOLON", $sm->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| e = expression sm = SEMICOLON
{
    $formatted_text = $e.formatted_text + $sm->getText();
    log_rule_to_file("expression_statement : expression SEMICOLON", $e.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
};

variable returns[std::string formatted_text, std::string variable_name]
    : id = ID
{
    $formatted_text = $id->getText();
    $variable_name = $id->getText();
    log_rule_to_file("variable : ID", $id->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| id = ID LTHIRD e = expression RTHIRD
{
    $formatted_text = $id->getText() + "[" + $e.formatted_text + "]";
    $variable_name = $id->getText();
    if ($e.formatted_text.find('.') != std::string::npos)
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
};

expression returns[std::string formatted_text]
    : id = ID LTHIRD idx = expression RTHIRD
{
    // ACTION 1: After the index ('idx') is evaluated, its value is in AX.
    // Save it on the stack before we evaluate the right side.
    writeAsm("    PUSH AX\n");
}
op = ASSIGNOP rhs = expression
{
    // ACTION 2: After the RHS is evaluated, its value is in AX.
    // The saved index is on the stack.
    $formatted_text = $id->getText() + "[" + $idx.formatted_text + "]" + $op->getText() + $rhs.formatted_text;
    log_rule_to_file("expression : ID '[' expression ']' ASSIGNOP expression", $id->getLine());

    writeAsm("    POP BX\n");    // Pop the index into BX
    writeAsm("    SHL BX, 1\n"); // Scale index by 2 for WORDs

    symbol_info *info = st.lookup($id->getText());
    if (info != nullptr)
    {
        int offset = info->get_offset();
        if (offset == 0)
        { // Global array
            writeAsm("    LEA SI, " + $id->getText() + "\n");
        }
        else
        { // Local array
            if (offset > 0)
            {
                writeAsm("    LEA SI, [BP+" + std::to_string(offset) + "]\n");
            }
            else
            {
                writeAsm("    LEA SI, [BP" + std::to_string(offset) + "]\n");
            }
        }
    }

    writeAsm("    ADD SI, BX\n");   // Final address is in SI
    writeAsm("    MOV [SI], AX\n"); // Store the RHS value (in AX) at that address

    writeIntoparserLogFile($formatted_text + "\n");
}
| id = ID op = ASSIGNOP rhs = expression
{
    $formatted_text = $id->getText() + $op->getText() + $rhs.formatted_text;
    log_rule_to_file("expression : ID ASSIGNOP expression", $id->getLine());

    symbol_info *lhs_info = st.lookup($id->getText());
    if (lhs_info != nullptr)
    {
        int offset = lhs_info->get_offset();
        std::string ptr_type = (lhs_info->get_type() == "int") ? "WORD PTR" : "DWORD PTR";

        if (offset == 0)
        {
            writeAsm("    MOV " + $id->getText() + ", AX\n");
        }
        else
        {
            if (offset > 0)
            {
                writeAsm("    MOV " + ptr_type + " [BP+" + std::to_string(offset) + "], AX\n");
            }
            else
            {
                writeAsm("    MOV " + ptr_type + " [BP" + std::to_string(offset) + "], AX\n");
            }
        }
    }
    writeIntoparserLogFile($formatted_text + "\n");
}
| l = logic_expression
{
    $formatted_text = $l.formatted_text;
    log_rule_to_file("expression : logic_expression", $l.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
};

logic_expression returns[std::string formatted_text]
    : r = rel_expression
{
    $formatted_text = $r.formatted_text;
    log_rule_to_file("logic_expression : rel_expression", $r.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| l = rel_expression
{
    writeAsm("    PUSH AX\n");
}
op = LOGICOP r = rel_expression
{
    $formatted_text = $l.formatted_text + $op->getText() + $r.formatted_text;
    log_rule_to_file("logic_expression : rel_expression LOGICOP rel_expression", $l.ctx->start->getLine());

    writeAsm("    POP BX\n");

    if ($op->getText() == "&&")
    {
        writeAsm("    AND AX, BX ; AX = (LHS result) AND (RHS result)\n");
    }
    else
    { // ||
        writeAsm("    OR AX, BX  ; AX = (LHS result) OR (RHS result)\n");
    }

    writeIntoparserLogFile($formatted_text + "\n");
};

rel_expression returns[std::string formatted_text]
    : s = simple_expression
{
    $formatted_text = $s.formatted_text;
    log_rule_to_file("rel_expression : simple_expression", $s.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| s1 = simple_expression
{
    writeAsm("    PUSH AX\n");
}
op = RELOP s2 = simple_expression
{
    $formatted_text = $s1.formatted_text + $op->getText() + $s2.formatted_text;
    log_rule_to_file("rel_expression : simple_expression RELOP simple_expression", $s1.ctx->start->getLine());

    writeAsm("    POP BX\n");
    writeAsm("    CMP BX, AX ; Compare LHS (BX) with RHS (AX)\n");

    std::string op_text = $op->getText();
    std::string jump_instruction;

    if (op_text == "==")
        jump_instruction = "JE";
    else if (op_text == "!=")
        jump_instruction = "JNE";
    else if (op_text == "<")
        jump_instruction = "JL";
    else if (op_text == "<=")
        jump_instruction = "JLE";
    else if (op_text == ">")
        jump_instruction = "JG";
    else if (op_text == ">=")
        jump_instruction = "JGE";

    int true_label = label_count++;
    int end_label = label_count++;

    writeAsm("    " + jump_instruction + " L" + std::to_string(true_label) + " ; Jump on true\n");

    writeAsm("    MOV AX, 0           ; False case\n");
    writeAsm("    JMP L" + std::to_string(end_label) + "\n");

    writeAsm("L" + std::to_string(true_label) + ":\n");
    writeAsm("    MOV AX, 1           ; True case\n");

    writeAsm("L" + std::to_string(end_label) + ":\n");

    writeIntoparserLogFile($formatted_text + "\n");
};

simple_expression returns[std::string formatted_text]
    : t = term
{
    $formatted_text = $t.formatted_text;
    log_rule_to_file("simple_expression : term", $t.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| s = simple_expression
{
    writeAsm("    PUSH AX\n");
}
op = ADDOP t = term
{
    $formatted_text = $s.formatted_text + $op->getText() + $t.formatted_text;
    log_rule_to_file("simple_expression : simple_expression ADDOP term", $s.ctx->start->getLine());

    writeAsm("    POP BX\n");
    if ($op->getText() == "+")
    {
        writeAsm("    ADD AX, BX ; AX = RHS + LHS\n");
    }
    else
    {
        writeAsm("    SUB BX, AX\n");
        writeAsm("    MOV AX, BX ; Move result back to AX\n");
    }

    writeIntoparserLogFile($formatted_text + "\n");
};

term returns[std::string formatted_text]
    : u = unary_expression
{
    $formatted_text = $u.formatted_text;
    log_rule_to_file("term : unary_expression", $u.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| t = term
{
    writeAsm("    PUSH AX\n");
}
op = MULOP u = unary_expression
{
    $formatted_text = $t.formatted_text + $op->getText() + $u.formatted_text;
    log_rule_to_file("term : term MULOP unary_expression", $t.ctx->start->getLine());

    writeAsm("    POP BX\n");
    if ($op->getText() == "*")
    {
        writeAsm("    IMUL BX  ; AX = AX * BX\n");
    }
    else
    {
        writeAsm("    XCHG AX, BX ; AX has LHS(BX), BX has RHS(AX)\n");
        writeAsm("    CWD         ; Extend sign of AX into DX for division\n");
        writeAsm("    IDIV BX     ; AX = DX:AX / BX\n");
        if ($op->getText() == "%")
        {
            writeAsm("    MOV AX, DX ; For modulus, the result is the remainder in DX\n");
        }
    }

    writeIntoparserLogFile($formatted_text + "\n\n");
};

unary_expression returns[std::string formatted_text]
    : op = ADDOP u = unary_expression
{
    $formatted_text = $op->getText() + $u.formatted_text;
    log_rule_to_file("unary_expression : ADDOP unary_expression", $op->getLine());
    if ($op->getText() == "-")
    {
        writeAsm("    NEG AX ; Negate the value in AX\n");
    }

    writeIntoparserLogFile($formatted_text + "\n");
}
| op = NOT u = unary_expression
{
    $formatted_text = $op->getText() + $u.formatted_text;
    log_rule_to_file("unary_expression : NOT unary_expression", $op->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| f = factor
{
    $formatted_text = $f.formatted_text;
    log_rule_to_file("unary_expression : factor", $f.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
};

factor returns[std::string formatted_text]
    : id = ID LTHIRD e = expression RTHIRD op = INCOP
{
    $formatted_text = $id->getText() + "[" + $e.formatted_text + "]" + $op->getText();
    log_rule_to_file("factor : ID LTHIRD ... RTHIRD INCOP", $id->getLine());

    writeAsm("    MOV BX, AX\n");
    writeAsm("    SHL BX, 1\n");
    symbol_info *info = st.lookup($id->getText());
    if (info != nullptr)
    {
        int offset = info->get_offset();
        if (offset == 0)
        {
            writeAsm("    LEA SI, " + $id->getText() + "\n");
        }
        else
        {
            if (offset > 0)
            {
                writeAsm("    LEA SI, [BP+" + std::to_string(offset) + "]\n");
            }
            else
            {
                writeAsm("    LEA SI, [BP" + std::to_string(offset) + "]\n");
            }
        }
        writeAsm("    ADD SI, BX\n");
    }
    writeAsm("    MOV AX, [SI]\n");
    writeAsm("    PUSH AX\n");
    writeAsm("    INC WORD PTR [SI]\n");
    writeAsm("    POP AX\n");

    writeIntoparserLogFile($formatted_text + "\n");
}
| id = ID LTHIRD e = expression RTHIRD op = DECOP
{
    $formatted_text = $id->getText() + "[" + $e.formatted_text + "]" + $op->getText();
    log_rule_to_file("factor : ID LTHIRD ... RTHIRD DECOP", $id->getLine());

    writeAsm("    MOV BX, AX\n");
    writeAsm("    SHL BX, 1\n");
    symbol_info *info = st.lookup($id->getText());
    if (info != nullptr)
    {
        int offset = info->get_offset();
        if (offset == 0)
        {
            writeAsm("    LEA SI, " + $id->getText() + "\n");
        }
        else
        {
            if (offset > 0)
            {
                writeAsm("    LEA SI, [BP+" + std::to_string(offset) + "]\n");
            }
            else
            {
                writeAsm("    LEA SI, [BP" + std::to_string(offset) + "]\n");
            }
        }
        writeAsm("    ADD SI, BX\n");
    }
    writeAsm("    MOV AX, [SI]\n");
    writeAsm("    PUSH AX\n");
    writeAsm("    DEC WORD PTR [SI]\n");
    writeAsm("    POP AX\n");

    writeIntoparserLogFile($formatted_text + "\n");
}
| id = ID LTHIRD e = expression RTHIRD
{
    $formatted_text = $id->getText() + "[" + $e.formatted_text + "]";
    log_rule_to_file("factor : ID LTHIRD expression RTHIRD", $id->getLine());

    writeAsm("    MOV BX, AX\n");
    writeAsm("    SHL BX, 1\n");
    symbol_info *info = st.lookup($id->getText());
    if (info != nullptr)
    {
        int offset = info->get_offset();
        if (offset == 0)
        {
            writeAsm("    LEA SI, " + $id->getText() + "\n");
        }
        else
        {
            if (offset > 0)
            {
                writeAsm("    LEA SI, [BP+" + std::to_string(offset) + "]\n");
            }
            else
            {
                writeAsm("    LEA SI, [BP" + std::to_string(offset) + "]\n");
            }
        }
    }
    writeAsm("    ADD SI, BX\n");
    writeAsm("    MOV AX, [SI]\n");

    writeIntoparserLogFile($formatted_text + "\n");
}
| id = ID op = INCOP
{
    $formatted_text = $id->getText() + $op->getText();
    log_rule_to_file("factor : ID INCOP", $id->getLine());
    symbol_info *info = st.lookup($id->getText());
    if (info != nullptr)
    {
        int offset = info->get_offset();
        std::string ptr_type = (info->get_type() == "int") ? "WORD PTR" : "DWORD PTR";
        if (offset == 0)
        {
            writeAsm("    MOV AX, " + $id->getText() + "\n");
        }
        else
        {
            if (offset > 0)
            {
                writeAsm("    MOV AX, " + ptr_type + " [BP+" + std::to_string(offset) + "]\n");
            }
            else
            {
                writeAsm("    MOV AX, " + ptr_type + " [BP" + std::to_string(offset) + "]\n");
            }
        }
        writeAsm("    PUSH AX\n");
        writeAsm("    INC AX\n");
        if (offset == 0)
        {
            writeAsm("    MOV " + $id->getText() + ", AX\n");
        }
        else
        {
            if (offset > 0)
            {
                writeAsm("    MOV " + ptr_type + " [BP+" + std::to_string(offset) + "], AX\n");
            }
            else
            {
                writeAsm("    MOV " + ptr_type + " [BP" + std::to_string(offset) + "], AX\n");
            }
        }
        writeAsm("    POP AX\n");
    }
    writeIntoparserLogFile($formatted_text + "\n");
}
| id = ID op = DECOP
{
    $formatted_text = $id->getText() + $op->getText();
    log_rule_to_file("factor : ID DECOP", $id->getLine());
    symbol_info *info = st.lookup($id->getText());
    if (info != nullptr)
    {
        int offset = info->get_offset();
        std::string ptr_type = (info->get_type() == "int") ? "WORD PTR" : "DWORD PTR";
        if (offset == 0)
        {
            writeAsm("    MOV AX, " + $id->getText() + "\n");
        }
        else
        {
            if (offset > 0)
            {
                writeAsm("    MOV AX, " + ptr_type + " [BP+" + std::to_string(offset) + "]\n");
            }
            else
            {
                writeAsm("    MOV AX, " + ptr_type + " [BP" + std::to_string(offset) + "]\n");
            }
        }
        writeAsm("    PUSH AX\n");
        writeAsm("    DEC AX\n");
        if (offset == 0)
        {
            writeAsm("    MOV " + $id->getText() + ", AX\n");
        }
        else
        {
            if (offset > 0)
            {
                writeAsm("    MOV " + ptr_type + " [BP+" + std::to_string(offset) + "], AX\n");
            }
            else
            {
                writeAsm("    MOV " + ptr_type + " [BP" + std::to_string(offset) + "], AX\n");
            }
        }
        writeAsm("    POP AX\n");
    }
    writeIntoparserLogFile($formatted_text + "\n");
}
| id = ID LPAREN al = argument_list ? RPAREN
{
    std::string arg_text = ($al.ctx != nullptr) ? $al.formatted_text : "";
    $formatted_text = $id->getText() + "(" + arg_text + ")";
    log_rule_to_file("factor : ID LPAREN argument_list RPAREN", $id->getLine());
    writeAsm("    CALL " + $id->getText() + "\n");
    symbol_info *func_info = st.lookup($id->getText());
    if (func_info != nullptr)
    {
        std::string params_str = func_info->get_parameter_list();
        int total_size = 0;
        if (!params_str.empty())
        {
            std::stringstream ss(params_str);
            std::string segment;
            while (std::getline(ss, segment, ','))
            {
                if (segment.find("int") != std::string::npos)
                    total_size += 2;
                else if (segment.find("float") != std::string::npos)
                    total_size += 4;
            }
        }

        if (total_size > 0)
        {
            writeAsm("    ADD SP, " + std::to_string(total_size) + "\n");
        }
    }

    writeIntoparserLogFile($formatted_text + "\n\n");
}
| id = ID
{
    $formatted_text = $id->getText();
    log_rule_to_file("factor : ID", $id->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
    symbol_info *info = st.lookup($id->getText());
    if (info != nullptr)
    {
        int offset = info->get_offset();
        std::string ptr_type = (info->get_type() == "int") ? "WORD PTR" : "DWORD PTR";
        if (offset == 0)
        {
            writeAsm("    MOV AX, " + $id->getText() + "\n");
        }
        else
        {
            if (offset > 0)
            {
                writeAsm("    MOV AX, " + ptr_type + " [BP+" + std::to_string(offset) + "]\n");
            }
            else
            {
                writeAsm("    MOV AX, " + ptr_type + " [BP" + std::to_string(offset) + "]\n");
            }
        }
    }
}
| LPAREN e = expression RPAREN
{
    $formatted_text = "(" + $e.formatted_text + ")";
    log_rule_to_file("factor : LPAREN expression RPAREN", $e.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| c = CONST_INT
{
    $formatted_text = $c->getText();
    log_rule_to_file("factor : CONST_INT", $c->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
    data_type = "int";

    writeAsm("    MOV AX, " + $c->getText() + "\n");
}
| c = CONST_FLOAT
{
    $formatted_text = $c->getText();
    log_rule_to_file("factor : CONST_FLOAT", $c->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
    data_type = "float";

    writeAsm("    ; Loading float as integer for now. True floats require FPU instructions.\n");
    writeAsm("    MOV AX, " + $c->getText() + "\n");
};

argument_list returns[std::string formatted_text]
    : a = arguments
{
    $formatted_text = $a.formatted_text;
    log_rule_to_file("argument_list : arguments", $a.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
|
{
    $formatted_text = "";
};

arguments returns[std::string formatted_text]
    : l = logic_expression COMMA a = arguments
{
    // find argument offset
    symbol_info *info = st.lookup($l.formatted_text);
    int offset = 0;
    if (info != nullptr)
    {
        offset = info->get_offset();
    }
    // make the move
    //  ACTION: After the logic expression is evaluated, its value is in AX.
    if (offset == 0)
    {
        writeAsm("    MOV " + $l.formatted_text + ", AX\n");
    }
    else
    {
        if (offset > 0)
        {
            writeAsm("    MOV AX, WORD PTR [BP+" + std::to_string(offset) + "]\n");
        }
        else
        {
            writeAsm("    MOV AX, WORD PTR [BP" + std::to_string(offset) + "]\n");
        }
    }
    writeAsm("    PUSH AX\n");
    $formatted_text = $l.formatted_text + "," + $a.formatted_text;
    log_rule_to_file("arguments : logic_expression COMMA arguments", $l.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
}
| l = logic_expression
{
    // find argument offset
    symbol_info *info = st.lookup($l.formatted_text);
    int offset = 0;
    if (info != nullptr)
    {
        offset = info->get_offset();
    }
    // make the move
    //  ACTION: After the logic expression is evaluated, its value is in AX.
    if (offset == 0)
    {
        writeAsm("    MOV " + $l.formatted_text + ", AX\n");
    }
    else
    {
        if (offset > 0)
        {
            writeAsm("    MOV AX, WORD PTR [BP+" + std::to_string(offset) + "]\n");
        }
        else
        {
            writeAsm("    MOV AX, WORD PTR [BP" + std::to_string(offset) + "]\n");
        }
    }
    writeAsm("    PUSH AX\n");
    $formatted_text = $l.formatted_text;
    log_rule_to_file("arguments : logic_expression", $l.ctx->start->getLine());
    writeIntoparserLogFile($formatted_text + "\n");
};