// Generated from /home/tawhidumar/Academics/CSE310-Compiler-Sessional/offline_3/C2105028Parser.g4 by ANTLR 4.13.1

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

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class C2105028Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LINE_COMMENT=1, BLOCK_COMMENT=2, STRING=3, WS=4, IF=5, ELSE=6, FORIN=7, 
		FOREACH=8, FOR=9, WHILE=10, PRINTLN=11, RETURN=12, INT=13, FLOAT=14, VOID=15, 
		IN=16, TO=17, LPAREN=18, RPAREN=19, LCURL=20, RCURL=21, LTHIRD=22, RTHIRD=23, 
		SEMICOLON=24, COMMA=25, ADDOP=26, SUBOP=27, MULOP=28, INCOP=29, DECOP=30, 
		NOT=31, RELOP=32, LOGICOP=33, ASSIGNOP=34, ID=35, CONST_INT=36, CONST_FLOAT=37, 
		ERROR_CHAR=38;
	public static final int
		RULE_start = 0, RULE_program = 1, RULE_unit = 2, RULE_func_declaration = 3, 
		RULE_func_definition = 4, RULE_parameter_list = 5, RULE_parameter_list_err = 6, 
		RULE_compound_statement_for_func = 7, RULE_compound_statement = 8, RULE_var_declaration = 9, 
		RULE_type_specifier = 10, RULE_declaration_list = 11, RULE_declaration_list_err = 12, 
		RULE_statements = 13, RULE_statement = 14, RULE_assignment_error_statement = 15, 
		RULE_expression_statement = 16, RULE_variable = 17, RULE_expression = 18, 
		RULE_logic_expression = 19, RULE_rel_expression = 20, RULE_simple_expression = 21, 
		RULE_term = 22, RULE_unary_expression = 23, RULE_factor = 24, RULE_factor_err = 25, 
		RULE_argument_list = 26, RULE_arguments = 27;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "program", "unit", "func_declaration", "func_definition", "parameter_list", 
			"parameter_list_err", "compound_statement_for_func", "compound_statement", 
			"var_declaration", "type_specifier", "declaration_list", "declaration_list_err", 
			"statements", "statement", "assignment_error_statement", "expression_statement", 
			"variable", "expression", "logic_expression", "rel_expression", "simple_expression", 
			"term", "unary_expression", "factor", "factor_err", "argument_list", 
			"arguments"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'if'", "'else'", "'forin'", "'foreach'", 
			"'for'", "'while'", "'printf'", "'return'", "'int'", "'float'", "'void'", 
			"'in'", "'to'", "'('", "')'", "'{'", "'}'", "'['", "']'", "';'", "','", 
			null, null, null, "'++'", "'--'", "'!'", null, null, "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LINE_COMMENT", "BLOCK_COMMENT", "STRING", "WS", "IF", "ELSE", 
			"FORIN", "FOREACH", "FOR", "WHILE", "PRINTLN", "RETURN", "INT", "FLOAT", 
			"VOID", "IN", "TO", "LPAREN", "RPAREN", "LCURL", "RCURL", "LTHIRD", "RTHIRD", 
			"SEMICOLON", "COMMA", "ADDOP", "SUBOP", "MULOP", "INCOP", "DECOP", "NOT", 
			"RELOP", "LOGICOP", "ASSIGNOP", "ID", "CONST_INT", "CONST_FLOAT", "ERROR_CHAR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "C2105028Parser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


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

	public C2105028Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartContext extends ParserRuleContext {
		public ProgramContext p;
		public ProgramContext program() {
			return getRuleContext(ProgramContext.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			((StartContext)_localctx).p = program(0);

			        log_rule_to_file("start : program", ((StartContext)_localctx).p->stop->getLine());
			        std::string symbol_table_str = st.get_all_scopes_as_string();
			        writeIntoparserLogFile(symbol_table_str);
			        writeIntoparserLogFile("Total number of lines: " + std::to_string(((StartContext)_localctx).p->stop->getLine()));
			        writeIntoparserLogFile("Total number of errors: " + std::to_string(syntaxErrorCount) + "\n");
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public std::string formatted_text;
		public ProgramContext p;
		public UnitContext u;
		public UnitContext unit() {
			return getRuleContext(UnitContext.class,0);
		}
		public ProgramContext program() {
			return getRuleContext(ProgramContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		return program(0);
	}

	private ProgramContext program(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ProgramContext _localctx = new ProgramContext(_ctx, _parentState);
		ProgramContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_program, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(60);
			((ProgramContext)_localctx).u = unit();

			        ((ProgramContext)_localctx).formatted_text =  ((ProgramContext)_localctx).u.formatted_text;
			        log_rule_to_file("program : unit", ((ProgramContext)_localctx).u->start->getLine());
			        writeIntoparserLogFile(_localctx.formatted_text + "\n");
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(69);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ProgramContext(_parentctx, _parentState);
					_localctx.p = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_program);
					setState(63);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(64);
					((ProgramContext)_localctx).u = unit();

					                  ((ProgramContext)_localctx).formatted_text =  ((ProgramContext)_localctx).p.formatted_text + "\n" + ((ProgramContext)_localctx).u.formatted_text;
					                  log_rule_to_file("program : program unit", ((ProgramContext)_localctx).u->stop->getLine());
					                  writeIntoparserLogFile(_localctx.formatted_text + "\n");
					              
					}
					} 
				}
				setState(71);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UnitContext extends ParserRuleContext {
		public std::string formatted_text;
		public Var_declarationContext v;
		public Func_declarationContext f;
		public Func_definitionContext fd;
		public Var_declarationContext var_declaration() {
			return getRuleContext(Var_declarationContext.class,0);
		}
		public Func_declarationContext func_declaration() {
			return getRuleContext(Func_declarationContext.class,0);
		}
		public Func_definitionContext func_definition() {
			return getRuleContext(Func_definitionContext.class,0);
		}
		public UnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unit; }
	}

	public final UnitContext unit() throws RecognitionException {
		UnitContext _localctx = new UnitContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_unit);
		try {
			setState(81);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				((UnitContext)_localctx).v = var_declaration();

				        ((UnitContext)_localctx).formatted_text =  ((UnitContext)_localctx).v.formatted_text;
				        log_rule_to_file("unit : var_declaration", ((UnitContext)_localctx).v->stop->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				((UnitContext)_localctx).f = func_declaration();

				        ((UnitContext)_localctx).formatted_text =  ((UnitContext)_localctx).f.formatted_text;
				        log_rule_to_file("unit : func_declaration", ((UnitContext)_localctx).f->stop->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(78);
				((UnitContext)_localctx).fd = func_definition();

				        ((UnitContext)_localctx).formatted_text =  ((UnitContext)_localctx).fd.formatted_text;
				        log_rule_to_file("unit : func_definition", ((UnitContext)_localctx).fd->stop->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Func_declarationContext extends ParserRuleContext {
		public std::string formatted_text;
		public Type_specifierContext t;
		public Token id;
		public Parameter_listContext p;
		public Token sm;
		public TerminalNode LPAREN() { return getToken(C2105028Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(C2105028Parser.RPAREN, 0); }
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public TerminalNode ID() { return getToken(C2105028Parser.ID, 0); }
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(C2105028Parser.SEMICOLON, 0); }
		public Func_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_declaration; }
	}

	public final Func_declarationContext func_declaration() throws RecognitionException {
		Func_declarationContext _localctx = new Func_declarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_func_declaration);
		try {
			setState(104);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				((Func_declarationContext)_localctx).t = type_specifier();

				        var_type = ((Func_declarationContext)_localctx).t.formatted_text;
				    
				setState(85);
				((Func_declarationContext)_localctx).id = match(ID);

				        symbol_info *info = st.lookup(((Func_declarationContext)_localctx).id->getText());
				        if (info != nullptr) {
				            writeIntoErrorFile("Error at line " + std::to_string(((Func_declarationContext)_localctx).id->getLine()) + ": Function redeclaration - Function '" + ((Func_declarationContext)_localctx).id->getText() + "' is already declared");
				            writeIntoparserLogFile("Error at line " + std::to_string(((Func_declarationContext)_localctx).id->getLine()) + ": Function redeclaration - Function '" + ((Func_declarationContext)_localctx).id->getText() + "' is already declared");
				            log_rule_to_file("func_declaration : type_specifier ID", ((Func_declarationContext)_localctx).id->getLine());
				            syntaxErrorCount++;
				        } else {
				            st.insert(((Func_declarationContext)_localctx).id->getText(), "ID", ((Func_declarationContext)_localctx).t.formatted_text, 0, "function", "function_declaration", "");
				        }
				        var_type = "";
				    
				setState(87);
				match(LPAREN);
				st.enter_scope(); 
				setState(89);
				((Func_declarationContext)_localctx).p = parameter_list(0);
				setState(90);
				match(RPAREN);
				 st.exit_scope();
				setState(92);
				((Func_declarationContext)_localctx).sm = match(SEMICOLON);

				        ((Func_declarationContext)_localctx).formatted_text =  ((Func_declarationContext)_localctx).t.formatted_text + " " + ((Func_declarationContext)_localctx).id->getText() + "(" + ((Func_declarationContext)_localctx).p.formatted_text + ")" + ((Func_declarationContext)_localctx).sm->getText();
				        symbol_info *info_temp = st.lookup(((Func_declarationContext)_localctx).id->getText());
				        if(info_temp != nullptr)
				        {
				            info_temp->setParams_list(((Func_declarationContext)_localctx).p.formatted_text);
				        }
				        log_rule_to_file("func_declaration : type_specifier ID LPAREN parameter_list RPAREN SEMICOLON", ((Func_declarationContext)_localctx).t->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(95);
				((Func_declarationContext)_localctx).t = type_specifier();

				        var_type = ((Func_declarationContext)_localctx).t.formatted_text;
				    
				setState(97);
				((Func_declarationContext)_localctx).id = match(ID);

				        symbol_info *info = st.lookup(((Func_declarationContext)_localctx).id->getText());
				        if (info != nullptr) {
				            writeIntoErrorFile(
				                std::string("Line# ") + std::to_string(((Func_declarationContext)_localctx).id->getLine()) +
				                " with error name: Function redeclaration" +
				                " - Function '" + ((Func_declarationContext)_localctx).id->getText() + "' is already declared"
				            );
				            syntaxErrorCount++;
				        } else {
				            st.insert(((Func_declarationContext)_localctx).id->getText(), "ID", ((Func_declarationContext)_localctx).t.formatted_text, 0, "function", "function_declaration", "");
				        }
				        var_type = "";
				    
				setState(99);
				match(LPAREN);
				setState(100);
				match(RPAREN);
				setState(101);
				((Func_declarationContext)_localctx).sm = match(SEMICOLON);

				        ((Func_declarationContext)_localctx).formatted_text =  ((Func_declarationContext)_localctx).t.formatted_text + " " + ((Func_declarationContext)_localctx).id->getText() + "()" + ((Func_declarationContext)_localctx).sm->getText();
				        log_rule_to_file("func_declaration : type_specifier ID LPAREN RPAREN SEMICOLON", ((Func_declarationContext)_localctx).t->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Func_definitionContext extends ParserRuleContext {
		public std::string formatted_text;
		public Type_specifierContext t;
		public Token id;
		public Parameter_listContext p;
		public Compound_statement_for_funcContext cs;
		public TerminalNode LPAREN() { return getToken(C2105028Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(C2105028Parser.RPAREN, 0); }
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public TerminalNode ID() { return getToken(C2105028Parser.ID, 0); }
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public Compound_statement_for_funcContext compound_statement_for_func() {
			return getRuleContext(Compound_statement_for_funcContext.class,0);
		}
		public Func_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_definition; }
	}

	public final Func_definitionContext func_definition() throws RecognitionException {
		Func_definitionContext _localctx = new Func_definitionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_func_definition);
		try {
			setState(125);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				((Func_definitionContext)_localctx).t = type_specifier();
				setState(107);
				((Func_definitionContext)_localctx).id = match(ID);

				        symbol_info *info = st.lookup(((Func_definitionContext)_localctx).id->getText());
				        if (info != nullptr) {
				            if (info->return_type != ((Func_definitionContext)_localctx).t.formatted_text && info->symbol_type == "function") {
				                writeIntoErrorFile(
				                    std::string("Error at line ") + std::to_string(((Func_definitionContext)_localctx).id->getLine()) +
				                    ": Return type mismatch of " + ((Func_definitionContext)_localctx).id->getText() + "\n"
				               );
				                writeIntoparserLogFile(
				                     std::string("Error at line ") + std::to_string(((Func_definitionContext)_localctx).id->getLine()) +
				                    ": Return type mismatch of " + ((Func_definitionContext)_localctx).id->getText() + "\n"
				                );

				                syntaxErrorCount++;
				            }
				            if(info->symbol_type != "function") {
				                writeIntoErrorFile(
				                    std::string("Error at line ") + std::to_string(((Func_definitionContext)_localctx).id->getLine()) +
				                    ": Multiple declaration of " + ((Func_definitionContext)_localctx).id->getText() + "\n"
				                );
				                writeIntoparserLogFile(
				                    std::string("Error at line ") + std::to_string(((Func_definitionContext)_localctx).id->getLine()) +
				                    ": Multiple declaration of " + ((Func_definitionContext)_localctx).id->getText() + "\n"
				                );
				                syntaxErrorCount++;
				            }
				        } else {
				            st.insert(((Func_definitionContext)_localctx).id->getText(), "ID", ((Func_definitionContext)_localctx).t.formatted_text, 0, "function", "function_definition");
				        }
				    
				setState(109);
				match(LPAREN);
				 st.enter_scope();
				setState(111);
				((Func_definitionContext)_localctx).p = parameter_list(0);
				setState(112);
				match(RPAREN);
				setState(113);
				((Func_definitionContext)_localctx).cs = compound_statement_for_func();

				        if (((Func_definitionContext)_localctx).t.formatted_text == "void" && ((Func_definitionContext)_localctx).cs.formatted_text.find("return") != std::string::npos) {
				            writeIntoErrorFile(
				                std::string("Error at line ") + std::to_string(((Func_definitionContext)_localctx).id->getLine()) +
				                ": Cannot return value from function " + ((Func_definitionContext)_localctx).id->getText() + " with void return type\n"
				            );
				            writeIntoparserLogFile(
				                std::string("Error at line ") + std::to_string(((Func_definitionContext)_localctx).id->getLine()) +
				                ": Cannot return value from function " + ((Func_definitionContext)_localctx).id->getText() + " with void return type\n"
				            );
				            syntaxErrorCount++;
				        }

				        if(info != nullptr && info->symbol_type == "function")
				        {
				            int count1 = std::count(info->parameter_list.begin(), info->parameter_list.end(), ',') + 1;
				            if(info->parameter_list == "")
				                count1 = 0;
				            
				            int count2 = std::count(((Func_definitionContext)_localctx).p.formatted_text.begin(), ((Func_definitionContext)_localctx).p.formatted_text.end(), ',') + 1;
				            if(((Func_definitionContext)_localctx).p.formatted_text == "")
				                count2 = 0;
				            if(count1 != count2)
				            {
				                writeIntoErrorFile(
				                std::string("Error at line ") + std::to_string(((Func_definitionContext)_localctx).id->getLine()) +
				                ": Total number of arguments mismatch with declaration in function " + ((Func_definitionContext)_localctx).id->getText() + "\n"
				                );
				                writeIntoparserLogFile(
				                std::string("Error at line ") + std::to_string(((Func_definitionContext)_localctx).id->getLine()) +
				                ": Total number of arguments mismatch with declaration in function " + ((Func_definitionContext)_localctx).id->getText() + "\n"
				                );
				                syntaxErrorCount++;
				            }
				        }

				        if(info == nullptr)
				        {
				            info = st.lookup(((Func_definitionContext)_localctx).id->getText());
				            info->setParams_list(((Func_definitionContext)_localctx).p.formatted_text);
				        }
				        ((Func_definitionContext)_localctx).formatted_text =  ((Func_definitionContext)_localctx).t.formatted_text + " " + ((Func_definitionContext)_localctx).id->getText() + "(" + ((Func_definitionContext)_localctx).p.formatted_text + ")" + ((Func_definitionContext)_localctx).cs.formatted_text;
				        if(((Func_definitionContext)_localctx).cs.flag)
				            log_rule_to_file("compound_statement : LCURL statements RCURL", ((Func_definitionContext)_localctx).cs->stop->getLine());
				        else
				            log_rule_to_file("compound_statement : LCURL RCURL", ((Func_definitionContext)_localctx).cs->stop->getLine());
				        writeIntoparserLogFile(((Func_definitionContext)_localctx).cs.formatted_text + "\n");
				        std::string symbol_table_str = st.get_all_scopes_as_string();
				        writeIntoparserLogFile(symbol_table_str);
				        log_rule_to_file("func_definition : type_specifier ID LPAREN parameter_list RPAREN compound_statement", ((Func_definitionContext)_localctx).cs->stop->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        st.exit_scope();
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(116);
				((Func_definitionContext)_localctx).t = type_specifier();
				setState(117);
				((Func_definitionContext)_localctx).id = match(ID);

				        symbol_info *info = st.lookup(((Func_definitionContext)_localctx).id->getText());
				        if (info != nullptr) {
				            if (info->return_type != ((Func_definitionContext)_localctx).t.formatted_text && info->symbol_type == "function") {
				                writeIntoErrorFile(
				                    std::string("Error at line ") + std::to_string(((Func_definitionContext)_localctx).id->getLine()) +
				                    ": Return type mismatch of " + ((Func_definitionContext)_localctx).id->getText() + "\n"
				                );
				                syntaxErrorCount++;
				            }
				        } else {
				            st.insert(((Func_definitionContext)_localctx).id->getText(), "ID", ((Func_definitionContext)_localctx).t.formatted_text, 0, "function", "function_declaration");
				        }
				    
				setState(119);
				match(LPAREN);
				setState(120);
				match(RPAREN);
				st.enter_scope();
				setState(122);
				((Func_definitionContext)_localctx).cs = compound_statement_for_func();

				        if (((Func_definitionContext)_localctx).t.formatted_text == "void" && ((Func_definitionContext)_localctx).cs.formatted_text.find("return") != std::string::npos) {
				            writeIntoErrorFile(
				                std::string("Error at line ") + std::to_string(((Func_definitionContext)_localctx).id->getLine()) +
				                ": Cannot return value from function " + ((Func_definitionContext)_localctx).id->getText() + " with void return type\n"
				            );
				            syntaxErrorCount++;
				        }
				        ((Func_definitionContext)_localctx).formatted_text =  ((Func_definitionContext)_localctx).t.formatted_text + " " + ((Func_definitionContext)_localctx).id->getText() + "()" + ((Func_definitionContext)_localctx).cs.formatted_text;
				        if(((Func_definitionContext)_localctx).cs.flag)
				            log_rule_to_file("compound_statement : LCURL statements RCURL", ((Func_definitionContext)_localctx).cs->stop->getLine());
				        else
				            log_rule_to_file("compound_statement : LCURL RCURL", ((Func_definitionContext)_localctx).cs->stop->getLine());
				        writeIntoparserLogFile(((Func_definitionContext)_localctx).cs.formatted_text + "\n");
				        std::string symbol_table_str = st.get_all_scopes_as_string();
				        writeIntoparserLogFile(symbol_table_str);
				        log_rule_to_file("func_definition : type_specifier ID LPAREN RPAREN compound_statement", ((Func_definitionContext)_localctx).cs->stop->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        st.exit_scope();
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Parameter_listContext extends ParserRuleContext {
		public std::string formatted_text;
		public Parameter_listContext pl;
		public Type_specifierContext t;
		public Token id;
		public Parameter_list_errContext err;
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public TerminalNode ID() { return getToken(C2105028Parser.ID, 0); }
		public Parameter_list_errContext parameter_list_err() {
			return getRuleContext(Parameter_list_errContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(C2105028Parser.COMMA, 0); }
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public Parameter_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_list; }
	}

	public final Parameter_listContext parameter_list() throws RecognitionException {
		return parameter_list(0);
	}

	private Parameter_listContext parameter_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Parameter_listContext _localctx = new Parameter_listContext(_ctx, _parentState);
		Parameter_listContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_parameter_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(128);
				((Parameter_listContext)_localctx).t = type_specifier();
				setState(129);
				((Parameter_listContext)_localctx).id = match(ID);

				        symbol_info *info = st.get_current_scope_table()->lookup(((Parameter_listContext)_localctx).id->getText());
				        if (info != nullptr) {
				            writeIntoErrorFile("Error at line " + std::to_string(((Parameter_listContext)_localctx).id->getLine()) + ": Parameter '" + ((Parameter_listContext)_localctx).id->getText() + "' is already declared\n");
				            syntaxErrorCount++;
				        } else {
				            st.insert(((Parameter_listContext)_localctx).id->getText(), "ID", "null", 0, ((Parameter_listContext)_localctx).t.formatted_text, "parameter");
				        }
				        ((Parameter_listContext)_localctx).formatted_text =  ((Parameter_listContext)_localctx).t.formatted_text + " " + ((Parameter_listContext)_localctx).id->getText();
				        log_rule_to_file("parameter_list : type_specifier ID", ((Parameter_listContext)_localctx).t->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 2:
				{
				setState(132);
				((Parameter_listContext)_localctx).err = parameter_list_err();

				        ((Parameter_listContext)_localctx).formatted_text =  ((Parameter_listContext)_localctx).err.formatted_text;
				        writeIntoErrorFile("Error at line " + std::to_string(((Parameter_listContext)_localctx).err->start->getLine()) + ": " + ((Parameter_listContext)_localctx).err.error_name + "\n");
				        syntaxErrorCount++;
				        
				        log_rule_to_file("parameter_list : type_specifier", ((Parameter_listContext)_localctx).err->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        writeIntoparserLogFile("Error at line " + std::to_string(((Parameter_listContext)_localctx).err->start->getLine()) + ": " + ((Parameter_listContext)_localctx).err.error_name + "\n");


				    
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(145);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Parameter_listContext(_parentctx, _parentState);
					_localctx.pl = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_parameter_list);
					setState(137);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(138);
					match(COMMA);
					setState(139);
					((Parameter_listContext)_localctx).t = type_specifier();
					setState(140);
					((Parameter_listContext)_localctx).id = match(ID);

					                  symbol_info *info = st.get_current_scope_table()->lookup(((Parameter_listContext)_localctx).id->getText());
					                  if (info != nullptr) {
					                      writeIntoErrorFile("Error at line " + std::to_string(((Parameter_listContext)_localctx).id->getLine()) + ": Multiple declaration of " + ((Parameter_listContext)_localctx).id->getText() + " in parameter\n");
					                      syntaxErrorCount++;
					                  } else {
					                      st.insert(((Parameter_listContext)_localctx).id->getText(), "ID", "null", 0, ((Parameter_listContext)_localctx).t.formatted_text, "parameter");
					                  }
					                  ((Parameter_listContext)_localctx).formatted_text =  ((Parameter_listContext)_localctx).pl.formatted_text + "," + ((Parameter_listContext)_localctx).t.formatted_text + " " + ((Parameter_listContext)_localctx).id->getText();
					                  log_rule_to_file("parameter_list : parameter_list COMMA type_specifier ID", ((Parameter_listContext)_localctx).t->start->getLine());
					              
					}
					} 
				}
				setState(147);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Parameter_list_errContext extends ParserRuleContext {
		public std::string error_name;
		public std::string formatted_text;
		public Type_specifierContext t;
		public TerminalNode ADDOP() { return getToken(C2105028Parser.ADDOP, 0); }
		public TerminalNode ID() { return getToken(C2105028Parser.ID, 0); }
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public Parameter_list_errContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_list_err; }
	}

	public final Parameter_list_errContext parameter_list_err() throws RecognitionException {
		Parameter_list_errContext _localctx = new Parameter_list_errContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_parameter_list_err);
		try {
			setState(160);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(148);
				((Parameter_list_errContext)_localctx).t = type_specifier();
				setState(149);
				match(ADDOP);
				setState(150);
				match(ID);

				        ((Parameter_list_errContext)_localctx).error_name =  "invalid parameter declaration";
				        ((Parameter_list_errContext)_localctx).formatted_text =  ((Parameter_list_errContext)_localctx).t.formatted_text;
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(153);
				((Parameter_list_errContext)_localctx).t = type_specifier();
				setState(154);
				match(ADDOP);

				        ((Parameter_list_errContext)_localctx).error_name =  "syntax error, unexpected ADDOP, expecting RPAREN or COMMA";
				        ((Parameter_list_errContext)_localctx).formatted_text =  ((Parameter_list_errContext)_localctx).t.formatted_text;
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(157);
				((Parameter_list_errContext)_localctx).t = type_specifier();

				        ((Parameter_list_errContext)_localctx).error_name =  "syntax error, missing parameter identifier";
				        ((Parameter_list_errContext)_localctx).formatted_text =  ((Parameter_list_errContext)_localctx).t.formatted_text;
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compound_statement_for_funcContext extends ParserRuleContext {
		public std::string formatted_text;
		public bool flag;
		public Token lc;
		public StatementsContext s;
		public Token rc;
		public TerminalNode LCURL() { return getToken(C2105028Parser.LCURL, 0); }
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public TerminalNode RCURL() { return getToken(C2105028Parser.RCURL, 0); }
		public Compound_statement_for_funcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_statement_for_func; }
	}

	public final Compound_statement_for_funcContext compound_statement_for_func() throws RecognitionException {
		Compound_statement_for_funcContext _localctx = new Compound_statement_for_funcContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_compound_statement_for_func);
		try {
			setState(170);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(162);
				((Compound_statement_for_funcContext)_localctx).lc = match(LCURL);
				setState(163);
				((Compound_statement_for_funcContext)_localctx).s = statements(0);
				setState(164);
				((Compound_statement_for_funcContext)_localctx).rc = match(RCURL);

				        bool flag = true;
				        ((Compound_statement_for_funcContext)_localctx).formatted_text =  ((Compound_statement_for_funcContext)_localctx).lc->getText() + "\n" + ((Compound_statement_for_funcContext)_localctx).s.formatted_text + "\n" + ((Compound_statement_for_funcContext)_localctx).rc->getText();
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(167);
				((Compound_statement_for_funcContext)_localctx).lc = match(LCURL);
				setState(168);
				((Compound_statement_for_funcContext)_localctx).rc = match(RCURL);

				        bool flag = false;
				        ((Compound_statement_for_funcContext)_localctx).formatted_text =  ((Compound_statement_for_funcContext)_localctx).lc->getText() + ((Compound_statement_for_funcContext)_localctx).rc->getText();
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compound_statementContext extends ParserRuleContext {
		public std::string formatted_text;
		public Token lc;
		public StatementsContext s;
		public Token rc;
		public TerminalNode LCURL() { return getToken(C2105028Parser.LCURL, 0); }
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public TerminalNode RCURL() { return getToken(C2105028Parser.RCURL, 0); }
		public Compound_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_statement; }
	}

	public final Compound_statementContext compound_statement() throws RecognitionException {
		Compound_statementContext _localctx = new Compound_statementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_compound_statement);
		try {
			setState(182);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(172);
				((Compound_statementContext)_localctx).lc = match(LCURL);
				st.enter_scope();
				setState(174);
				((Compound_statementContext)_localctx).s = statements(0);
				setState(175);
				((Compound_statementContext)_localctx).rc = match(RCURL);


				        ((Compound_statementContext)_localctx).formatted_text =  ((Compound_statementContext)_localctx).lc->getText() + "\n" + ((Compound_statementContext)_localctx).s.formatted_text + "\n" + ((Compound_statementContext)_localctx).rc->getText();
				        log_rule_to_file("compound_statement : LCURL statements RCURL", ((Compound_statementContext)_localctx).rc->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(178);
				((Compound_statementContext)_localctx).lc = match(LCURL);
				st.enter_scope();
				setState(180);
				((Compound_statementContext)_localctx).rc = match(RCURL);

				        ((Compound_statementContext)_localctx).formatted_text =  ((Compound_statementContext)_localctx).lc->getText() + "\n" + ((Compound_statementContext)_localctx).rc->getText();
				        log_rule_to_file("compound_statement : LCURL RCURL", ((Compound_statementContext)_localctx).rc->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        st.exit_scope();
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Var_declarationContext extends ParserRuleContext {
		public std::string formatted_text;
		public Type_specifierContext t;
		public Declaration_listContext dl;
		public Token sm;
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public Declaration_listContext declaration_list() {
			return getRuleContext(Declaration_listContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(C2105028Parser.SEMICOLON, 0); }
		public Var_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_declaration; }
	}

	public final Var_declarationContext var_declaration() throws RecognitionException {
		Var_declarationContext _localctx = new Var_declarationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_var_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			((Var_declarationContext)_localctx).t = type_specifier();
			 
			        var_type = ((Var_declarationContext)_localctx).t.formatted_text;
			        if (var_type == "void") {
			            writeIntoErrorFile(
			                std::string("Error at line ") + std::to_string(((Var_declarationContext)_localctx).t->start->getLine()) +
			                ": Variable type cannot be void\n"
			            );
			            writeIntoparserLogFile(
			                std::string("Error at line ") + std::to_string(((Var_declarationContext)_localctx).t->start->getLine()) +
			                ": Variable type cannot be void\n"
			            );
			            syntaxErrorCount++;
			        }
			    
			setState(186);
			((Var_declarationContext)_localctx).dl = declaration_list(0);
			setState(187);
			((Var_declarationContext)_localctx).sm = match(SEMICOLON);

			        ((Var_declarationContext)_localctx).formatted_text =  ((Var_declarationContext)_localctx).t.formatted_text + " " + ((Var_declarationContext)_localctx).dl.formatted_text + ";";
			        log_rule_to_file("var_declaration : type_specifier declaration_list SEMICOLON", ((Var_declarationContext)_localctx).t->start->getLine());
			        writeIntoparserLogFile(_localctx.formatted_text + "\n");
			        var_type = ""; 
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Type_specifierContext extends ParserRuleContext {
		public std::string formatted_text;
		public Token INT;
		public Token FLOAT;
		public Token VOID;
		public TerminalNode INT() { return getToken(C2105028Parser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(C2105028Parser.FLOAT, 0); }
		public TerminalNode VOID() { return getToken(C2105028Parser.VOID, 0); }
		public Type_specifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_specifier; }
	}

	public final Type_specifierContext type_specifier() throws RecognitionException {
		Type_specifierContext _localctx = new Type_specifierContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_type_specifier);
		try {
			setState(196);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(190);
				((Type_specifierContext)_localctx).INT = match(INT);

				        ((Type_specifierContext)_localctx).formatted_text =  ((Type_specifierContext)_localctx).INT->getText();
				        log_rule_to_file("type_specifier : INT", ((Type_specifierContext)_localctx).INT->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(192);
				((Type_specifierContext)_localctx).FLOAT = match(FLOAT);

				        ((Type_specifierContext)_localctx).formatted_text =  ((Type_specifierContext)_localctx).FLOAT->getText();
				        log_rule_to_file("type_specifier : FLOAT", ((Type_specifierContext)_localctx).FLOAT->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case VOID:
				enterOuterAlt(_localctx, 3);
				{
				setState(194);
				((Type_specifierContext)_localctx).VOID = match(VOID);

				        ((Type_specifierContext)_localctx).formatted_text =  ((Type_specifierContext)_localctx).VOID->getText();
				        log_rule_to_file("type_specifier : VOID", ((Type_specifierContext)_localctx).VOID->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Declaration_listContext extends ParserRuleContext {
		public std::string formatted_text;
		public Declaration_listContext dl;
		public Token id;
		public Token ci;
		public Declaration_list_errContext err;
		public TerminalNode ID() { return getToken(C2105028Parser.ID, 0); }
		public TerminalNode LTHIRD() { return getToken(C2105028Parser.LTHIRD, 0); }
		public TerminalNode RTHIRD() { return getToken(C2105028Parser.RTHIRD, 0); }
		public TerminalNode CONST_INT() { return getToken(C2105028Parser.CONST_INT, 0); }
		public Declaration_list_errContext declaration_list_err() {
			return getRuleContext(Declaration_list_errContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(C2105028Parser.COMMA, 0); }
		public Declaration_listContext declaration_list() {
			return getRuleContext(Declaration_listContext.class,0);
		}
		public Declaration_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration_list; }
	}

	public final Declaration_listContext declaration_list() throws RecognitionException {
		return declaration_list(0);
	}

	private Declaration_listContext declaration_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Declaration_listContext _localctx = new Declaration_listContext(_ctx, _parentState);
		Declaration_listContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_declaration_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(199);
				((Declaration_listContext)_localctx).id = match(ID);

				        ((Declaration_listContext)_localctx).formatted_text =  ((Declaration_listContext)_localctx).id->getText();
				        symbol_info *info = st.get_current_scope_table()->lookup(((Declaration_listContext)_localctx).id->getText());
				        if (info != nullptr) {
				            writeIntoErrorFile(
				                std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).id->getLine()) +
				                ": Multiple declaration of " + ((Declaration_listContext)_localctx).id->getText() + "\n");
				            writeIntoparserLogFile(std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).id->getLine()) +
				                ": Multiple declaration of " + ((Declaration_listContext)_localctx).id->getText() + "\n");
				            log_rule_to_file("declaration_list : ID", ((Declaration_listContext)_localctx).id->getLine());
				            writeIntoparserLogFile(_localctx.formatted_text + "\n");
				            syntaxErrorCount++;
				        } else {
				            st.insert(((Declaration_listContext)_localctx).id->getText(),"ID", var_type, 0, var_type, "variable");
				            log_rule_to_file("declaration_list : ID", ((Declaration_listContext)_localctx).id->getLine());
				            writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        }
				    
				}
				break;
			case 2:
				{
				setState(201);
				((Declaration_listContext)_localctx).id = match(ID);
				setState(202);
				match(LTHIRD);
				setState(203);
				((Declaration_listContext)_localctx).ci = match(CONST_INT);
				setState(204);
				match(RTHIRD);

				        if(((Declaration_listContext)_localctx).ci->getText().find('.') != std::string::npos)
				        {
				            writeIntoErrorFile(
				                std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).ci->getLine()) +
				                ": Expression inside third brackets not an integer\n");
				            writeIntoparserLogFile(std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).ci->getLine()) +
				                ": Expression inside third brackets not an integer");
				            log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD", ((Declaration_listContext)_localctx).ci->getLine());
				            writeIntoparserLogFile(((Declaration_listContext)_localctx).id->getText() + "[" + ((Declaration_listContext)_localctx).ci->getText() + "]\n");
				            syntaxErrorCount++;
				        }else{
				        ((Declaration_listContext)_localctx).formatted_text =  ((Declaration_listContext)_localctx).id->getText() + "[" + ((Declaration_listContext)_localctx).ci->getText() + "]";
				        symbol_info *info = st.get_current_scope_table()->lookup(((Declaration_listContext)_localctx).id->getText());
				        if (info != nullptr) {
				            writeIntoErrorFile(
				                std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).id->getLine()) +
				                ": Multiple declaration of " + ((Declaration_listContext)_localctx).id->getText() + "\n");
				            writeIntoparserLogFile(std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).id->getLine()) +
				                ": Multiple declaration of " + ((Declaration_listContext)_localctx).id->getText() + "\n");
				            log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD", ((Declaration_listContext)_localctx).id->getLine());
				            writeIntoparserLogFile(_localctx.formatted_text + "\n");
				            syntaxErrorCount++;
				        } else {
				            st.insert(((Declaration_listContext)_localctx).id->getText(),"ID", "null", 0, var_type, "array");
				            log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD", ((Declaration_listContext)_localctx).id->getLine());
				            writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        }
				        }
				        
				    
				}
				break;
			case 3:
				{
				setState(206);
				((Declaration_listContext)_localctx).err = declaration_list_err();

				        ((Declaration_listContext)_localctx).formatted_text =  ((Declaration_listContext)_localctx).err.formatted_text;
				        writeIntoErrorFile("Error at line " + std::to_string(((Declaration_listContext)_localctx).err->start->getLine()) + ": " + ((Declaration_listContext)_localctx).err.error_name + "\n");
				        writeIntoparserLogFile("Error at line " + std::to_string(((Declaration_listContext)_localctx).err->start->getLine()) + ": " + ((Declaration_listContext)_localctx).err.error_name + "\n");
				        syntaxErrorCount++;
				    
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(224);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(222);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new Declaration_listContext(_parentctx, _parentState);
						_localctx.dl = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_declaration_list);
						setState(211);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(212);
						match(COMMA);
						setState(213);
						((Declaration_listContext)_localctx).id = match(ID);

						                  ((Declaration_listContext)_localctx).formatted_text =  ((Declaration_listContext)_localctx).dl.formatted_text + "," + ((Declaration_listContext)_localctx).id->getText();
						                  symbol_info *info = st.get_current_scope_table()->lookup(((Declaration_listContext)_localctx).id->getText());
						                  if (info != nullptr) {
						                      writeIntoErrorFile(
						                          std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).id->getLine()) +
						                          ": Multiple declaration of " + ((Declaration_listContext)_localctx).id->getText());
						                      writeIntoparserLogFile(std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).id->getLine()) +
						                          ": Multiple declaration of " + ((Declaration_listContext)_localctx).id->getText());
						                      log_rule_to_file("declaration_list : declaration_list COMMA ID", ((Declaration_listContext)_localctx).id->getLine());
						                      writeIntoparserLogFile(_localctx.formatted_text + "\n");
						                      syntaxErrorCount++;
						                  } else {
						                      st.insert(((Declaration_listContext)_localctx).id->getText(),"ID", "null", 0, var_type, "variable");
						                      log_rule_to_file("declaration_list : declaration_list COMMA ID", ((Declaration_listContext)_localctx).id->getLine());
						                      writeIntoparserLogFile(_localctx.formatted_text + "\n");
						                  }
						              
						}
						break;
					case 2:
						{
						_localctx = new Declaration_listContext(_parentctx, _parentState);
						_localctx.dl = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_declaration_list);
						setState(215);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(216);
						match(COMMA);
						setState(217);
						((Declaration_listContext)_localctx).id = match(ID);
						setState(218);
						match(LTHIRD);
						setState(219);
						((Declaration_listContext)_localctx).ci = match(CONST_INT);
						setState(220);
						match(RTHIRD);

						                  ((Declaration_listContext)_localctx).formatted_text =  ((Declaration_listContext)_localctx).dl.formatted_text + "," + ((Declaration_listContext)_localctx).id->getText() + "[" + ((Declaration_listContext)_localctx).ci->getText() + "]";
						                  if(((Declaration_listContext)_localctx).ci->getText().find('.') != std::string::npos)
						                  {
						                      writeIntoErrorFile(
						                          std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).ci->getLine()) +
						                          ": Expression inside third brackets not an integer\n");
						                      writeIntoparserLogFile(std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).ci->getLine()) +
						                          ": Expression inside third brackets not an integer\n");
						                      log_rule_to_file("declaration_list : declaration_list COMMA ID LTHIRD CONST_INT RTHIRD", ((Declaration_listContext)_localctx).ci->getLine());
						                      writeIntoparserLogFile(((Declaration_listContext)_localctx).dl.formatted_text + "," + ((Declaration_listContext)_localctx).id->getText() + "[" + ((Declaration_listContext)_localctx).ci->getText() + "]\n");
						                      syntaxErrorCount++;
						                  }else{
						                      symbol_info *info = st.get_current_scope_table()->lookup(((Declaration_listContext)_localctx).id->getText());
						                      if (info != nullptr) {
						                          writeIntoErrorFile(
						                              std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).id->getLine()) +
						                              ": Multiple declaration of " + ((Declaration_listContext)_localctx).id->getText() + "\n");
						                          writeIntoparserLogFile(std::string("Error at line ") + std::to_string(((Declaration_listContext)_localctx).id->getLine()) +
						                              ": Multiple declaration of " + ((Declaration_listContext)_localctx).id->getText() + "\n");
						                          log_rule_to_file("declaration_list : declaration_list COMMA ID LTHIRD CONST_INT RTHIRD", ((Declaration_listContext)_localctx).id->getLine());
						                          writeIntoparserLogFile(_localctx.formatted_text + "\n");
						                          syntaxErrorCount++;
						                      } else {
						                          st.insert(((Declaration_listContext)_localctx).id->getText(),"ID", "null", 0, var_type, "array");
						                          log_rule_to_file("declaration_list : declaration_list COMMA ID LTHIRD CONST_INT RTHIRD", ((Declaration_listContext)_localctx).dl->start->getLine());
						                          writeIntoparserLogFile(_localctx.formatted_text + "\n");
						                      }
						                  }
						              
						}
						break;
					}
					} 
				}
				setState(226);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Declaration_list_errContext extends ParserRuleContext {
		public std::string error_name;
		public std::string formatted_text;
		public Token id1;
		public Token op;
		public Token id2;
		public Token ci1;
		public Token id;
		public ExpressionContext e;
		public List<TerminalNode> ID() { return getTokens(C2105028Parser.ID); }
		public TerminalNode ID(int i) {
			return getToken(C2105028Parser.ID, i);
		}
		public TerminalNode ADDOP() { return getToken(C2105028Parser.ADDOP, 0); }
		public TerminalNode MULOP() { return getToken(C2105028Parser.MULOP, 0); }
		public TerminalNode LTHIRD() { return getToken(C2105028Parser.LTHIRD, 0); }
		public TerminalNode RTHIRD() { return getToken(C2105028Parser.RTHIRD, 0); }
		public TerminalNode CONST_INT() { return getToken(C2105028Parser.CONST_INT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Declaration_list_errContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration_list_err; }
	}

	public final Declaration_list_errContext declaration_list_err() throws RecognitionException {
		Declaration_list_errContext _localctx = new Declaration_list_errContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_declaration_list_err);
		int _la;
		try {
			setState(246);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(227);
				((Declaration_list_errContext)_localctx).id1 = match(ID);
				setState(228);
				((Declaration_list_errContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ADDOP || _la==MULOP) ) {
					((Declaration_list_errContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(229);
				((Declaration_list_errContext)_localctx).id2 = match(ID);

				        ((Declaration_list_errContext)_localctx).error_name =  "syntax error, unexpected ADDOP, expecting COMMA or SEMICOLON";
				        log_rule_to_file("declaration_list : ID", ((Declaration_list_errContext)_localctx).id1->getLine());
				        ((Declaration_list_errContext)_localctx).formatted_text =  ((Declaration_list_errContext)_localctx).id1->getText();
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(231);
				((Declaration_list_errContext)_localctx).id1 = match(ID);
				setState(232);
				((Declaration_list_errContext)_localctx).id2 = match(ID);

				        ((Declaration_list_errContext)_localctx).error_name =  "syntax error, expecting COMMA\n";
				        log_rule_to_file("declaration_list : ID ID", ((Declaration_list_errContext)_localctx).id1->getLine());
				        ((Declaration_list_errContext)_localctx).formatted_text =  ((Declaration_list_errContext)_localctx).id1->getText();
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(234);
				((Declaration_list_errContext)_localctx).id1 = match(ID);
				setState(235);
				match(LTHIRD);
				setState(236);
				((Declaration_list_errContext)_localctx).ci1 = match(CONST_INT);
				setState(237);
				match(RTHIRD);
				setState(238);
				((Declaration_list_errContext)_localctx).id2 = match(ID);

				        ((Declaration_list_errContext)_localctx).error_name =  "syntax error, expecting COMMA\n";
				        ((Declaration_list_errContext)_localctx).formatted_text =  ((Declaration_list_errContext)_localctx).id1->getText() + "[" + ((Declaration_list_errContext)_localctx).ci1->getText() + "]";
				        log_rule_to_file("declaration_list : ID LTHIRD CONST_INT RTHIRD ID", ((Declaration_list_errContext)_localctx).id1->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "," + ((Declaration_list_errContext)_localctx).id2->getText() + "\n");
				    
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(240);
				((Declaration_list_errContext)_localctx).id = match(ID);
				setState(241);
				match(LTHIRD);
				setState(242);
				((Declaration_list_errContext)_localctx).e = expression();
				setState(243);
				match(RTHIRD);

				        ((Declaration_list_errContext)_localctx).error_name =  "syntax error, unexpected EXPRESSION\n";
				        ((Declaration_list_errContext)_localctx).formatted_text =  ((Declaration_list_errContext)_localctx).id->getText() + "[" + ((Declaration_list_errContext)_localctx).e.formatted_text + "]";
				        log_rule_to_file("declaration_list : ID LTHIRD expression RTHIRD", ((Declaration_list_errContext)_localctx).id->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementsContext extends ParserRuleContext {
		public std::string formatted_text;
		public StatementsContext sList;
		public StatementContext s;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
	}

	public final StatementsContext statements() throws RecognitionException {
		return statements(0);
	}

	private StatementsContext statements(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StatementsContext _localctx = new StatementsContext(_ctx, _parentState);
		StatementsContext _prevctx = _localctx;
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_statements, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(249);
			((StatementsContext)_localctx).s = statement();

			        ((StatementsContext)_localctx).formatted_text =  ((StatementsContext)_localctx).s.formatted_text;
			        log_rule_to_file("statements : statement", ((StatementsContext)_localctx).s->stop->getLine());
			        writeIntoparserLogFile(_localctx.formatted_text + "\n");
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(258);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new StatementsContext(_parentctx, _parentState);
					_localctx.sList = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_statements);
					setState(252);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(253);
					((StatementsContext)_localctx).s = statement();

					                  ((StatementsContext)_localctx).formatted_text =  ((StatementsContext)_localctx).sList.formatted_text + "\n" + ((StatementsContext)_localctx).s.formatted_text;
					                  log_rule_to_file("statements : statements statement", ((StatementsContext)_localctx).s->stop->getLine());
					                  writeIntoparserLogFile(_localctx.formatted_text + "\n");
					              
					}
					} 
				}
				setState(260);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public std::string formatted_text;
		public Token kw;
		public Expression_statementContext e1;
		public Expression_statementContext e2;
		public ExpressionContext e3;
		public StatementContext s;
		public ExpressionContext e;
		public StatementContext s1;
		public Token kw2;
		public StatementContext s2;
		public Token id;
		public Token sm;
		public Var_declarationContext v;
		public Expression_statementContext es;
		public Compound_statementContext c;
		public TerminalNode LPAREN() { return getToken(C2105028Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(C2105028Parser.RPAREN, 0); }
		public TerminalNode FOR() { return getToken(C2105028Parser.FOR, 0); }
		public List<Expression_statementContext> expression_statement() {
			return getRuleContexts(Expression_statementContext.class);
		}
		public Expression_statementContext expression_statement(int i) {
			return getRuleContext(Expression_statementContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode IF() { return getToken(C2105028Parser.IF, 0); }
		public TerminalNode ELSE() { return getToken(C2105028Parser.ELSE, 0); }
		public TerminalNode WHILE() { return getToken(C2105028Parser.WHILE, 0); }
		public TerminalNode PRINTLN() { return getToken(C2105028Parser.PRINTLN, 0); }
		public TerminalNode ID() { return getToken(C2105028Parser.ID, 0); }
		public TerminalNode SEMICOLON() { return getToken(C2105028Parser.SEMICOLON, 0); }
		public TerminalNode RETURN() { return getToken(C2105028Parser.RETURN, 0); }
		public Var_declarationContext var_declaration() {
			return getRuleContext(Var_declarationContext.class,0);
		}
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Assignment_error_statementContext assignment_error_statement() {
			return getRuleContext(Assignment_error_statementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_statement);
		try {
			setState(314);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(261);
				((StatementContext)_localctx).kw = match(FOR);
				setState(262);
				match(LPAREN);
				setState(263);
				((StatementContext)_localctx).e1 = expression_statement();
				setState(264);
				((StatementContext)_localctx).e2 = expression_statement();
				setState(265);
				((StatementContext)_localctx).e3 = expression();
				setState(266);
				match(RPAREN);
				setState(267);
				((StatementContext)_localctx).s = statement();

				        ((StatementContext)_localctx).formatted_text =  ((StatementContext)_localctx).kw->getText() + "(" + ((StatementContext)_localctx).e1.formatted_text + ((StatementContext)_localctx).e2.formatted_text + ((StatementContext)_localctx).e3.formatted_text + ")" + ((StatementContext)_localctx).s.formatted_text;
				        log_rule_to_file("statement : FOR LPAREN expression_statement expression_statement expression RPAREN statement", ((StatementContext)_localctx).kw->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(270);
				((StatementContext)_localctx).kw = match(IF);
				setState(271);
				match(LPAREN);
				setState(272);
				((StatementContext)_localctx).e = expression();
				setState(273);
				match(RPAREN);
				setState(274);
				((StatementContext)_localctx).s = statement();

				        ((StatementContext)_localctx).formatted_text =  ((StatementContext)_localctx).kw->getText() + "(" + ((StatementContext)_localctx).e.formatted_text + ")" + ((StatementContext)_localctx).s.formatted_text;
				        log_rule_to_file("statement : IF LPAREN expression RPAREN statement", ((StatementContext)_localctx).kw->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(277);
				((StatementContext)_localctx).kw = match(IF);
				setState(278);
				match(LPAREN);
				setState(279);
				((StatementContext)_localctx).e = expression();
				setState(280);
				match(RPAREN);
				setState(281);
				((StatementContext)_localctx).s1 = statement();
				setState(282);
				((StatementContext)_localctx).kw2 = match(ELSE);
				setState(283);
				((StatementContext)_localctx).s2 = statement();

				        ((StatementContext)_localctx).formatted_text =  ((StatementContext)_localctx).kw->getText() + "(" + ((StatementContext)_localctx).e.formatted_text + ")" + ((StatementContext)_localctx).s1.formatted_text+ ((StatementContext)_localctx).kw2->getText() + " " + ((StatementContext)_localctx).s2.formatted_text;
				        log_rule_to_file("statement : IF LPAREN expression RPAREN statement ELSE statement", ((StatementContext)_localctx).kw->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(286);
				((StatementContext)_localctx).kw = match(WHILE);
				setState(287);
				match(LPAREN);
				setState(288);
				((StatementContext)_localctx).e = expression();
				setState(289);
				match(RPAREN);
				setState(290);
				((StatementContext)_localctx).s = statement();

				        ((StatementContext)_localctx).formatted_text =  ((StatementContext)_localctx).kw->getText() + "(" + ((StatementContext)_localctx).e.formatted_text + ")" + ((StatementContext)_localctx).s.formatted_text;
				        log_rule_to_file("statement : WHILE LPAREN expression RPAREN statement", ((StatementContext)_localctx).kw->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(293);
				((StatementContext)_localctx).kw = match(PRINTLN);
				setState(294);
				match(LPAREN);
				setState(295);
				((StatementContext)_localctx).id = match(ID);
				setState(296);
				match(RPAREN);
				setState(297);
				((StatementContext)_localctx).sm = match(SEMICOLON);

				        symbol_info *info = st.lookup(((StatementContext)_localctx).id->getText());
				        if (info == nullptr) {
				            writeIntoErrorFile(
				                std::string("Error at line ") + std::to_string(((StatementContext)_localctx).id->getLine()) +
				                ": Undeclared variable " + ((StatementContext)_localctx).id->getText() + "\n"
				            );
				            writeIntoparserLogFile(
				                std::string("Error at line ") + std::to_string(((StatementContext)_localctx).id->getLine()) +
				                ": Undeclared variable " + ((StatementContext)_localctx).id->getText() + "\n"
				            );
				            syntaxErrorCount++;
				        }
				        ((StatementContext)_localctx).formatted_text =  ((StatementContext)_localctx).kw->getText() + "(" + ((StatementContext)_localctx).id->getText() + ")" + ((StatementContext)_localctx).sm->getText();
				        log_rule_to_file("statement : PRINTLN LPAREN ID RPAREN SEMICOLON", ((StatementContext)_localctx).kw->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(299);
				((StatementContext)_localctx).kw = match(RETURN);
				setState(300);
				((StatementContext)_localctx).e = expression();
				setState(301);
				((StatementContext)_localctx).sm = match(SEMICOLON);

				        ((StatementContext)_localctx).formatted_text =  ((StatementContext)_localctx).kw->getText() + " " + ((StatementContext)_localctx).e.formatted_text + ((StatementContext)_localctx).sm->getText();
				        log_rule_to_file("statement : RETURN expression SEMICOLON", ((StatementContext)_localctx).kw->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(304);
				((StatementContext)_localctx).v = var_declaration();

				        ((StatementContext)_localctx).formatted_text =  ((StatementContext)_localctx).v.formatted_text;
				        log_rule_to_file("statement : var_declaration", ((StatementContext)_localctx).v->stop->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(307);
				((StatementContext)_localctx).es = expression_statement();

				        ((StatementContext)_localctx).formatted_text =  ((StatementContext)_localctx).es.formatted_text;
				        log_rule_to_file("statement : expression_statement", ((StatementContext)_localctx).es->stop->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(310);
				((StatementContext)_localctx).c = compound_statement();

				        ((StatementContext)_localctx).formatted_text =  ((StatementContext)_localctx).c.formatted_text;
				        writeIntoparserLogFile(st.get_all_scopes_as_string());
				        log_rule_to_file("statement : compound_statement", ((StatementContext)_localctx).c->stop->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        st.exit_scope();
				    
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(313);
				assignment_error_statement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Assignment_error_statementContext extends ParserRuleContext {
		public std::string error_name;
		public ParserRuleContext * ctx;
		public Token id;
		public TerminalNode ASSIGNOP() { return getToken(C2105028Parser.ASSIGNOP, 0); }
		public TerminalNode SEMICOLON() { return getToken(C2105028Parser.SEMICOLON, 0); }
		public TerminalNode ID() { return getToken(C2105028Parser.ID, 0); }
		public Assignment_error_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment_error_statement; }
	}

	public final Assignment_error_statementContext assignment_error_statement() throws RecognitionException {
		Assignment_error_statementContext _localctx = new Assignment_error_statementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_assignment_error_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			((Assignment_error_statementContext)_localctx).id = match(ID);
			setState(317);
			match(ASSIGNOP);
			setState(318);
			match(SEMICOLON);

			        ((Assignment_error_statementContext)_localctx).error_name =  "syntax error: missing expression after assignment operator '='";
			        ((Assignment_error_statementContext)_localctx).ctx =  _localctx; 
			        
			        writeIntoErrorFile("Error at line " + std::to_string(((Assignment_error_statementContext)_localctx).id->getLine()) + ": " + _localctx.error_name + "\n");
			        syntaxErrorCount++;
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression_statementContext extends ParserRuleContext {
		public std::string formatted_text;
		public Token sm;
		public ExpressionContext e;
		public TerminalNode SEMICOLON() { return getToken(C2105028Parser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Expression_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_statement; }
	}

	public final Expression_statementContext expression_statement() throws RecognitionException {
		Expression_statementContext _localctx = new Expression_statementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_expression_statement);
		try {
			setState(327);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMICOLON:
				enterOuterAlt(_localctx, 1);
				{
				setState(321);
				((Expression_statementContext)_localctx).sm = match(SEMICOLON);

				        ((Expression_statementContext)_localctx).formatted_text =  ((Expression_statementContext)_localctx).sm->getText();
				        log_rule_to_file("expression_statement : SEMICOLON", ((Expression_statementContext)_localctx).sm->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case LPAREN:
			case ADDOP:
			case MULOP:
			case INCOP:
			case DECOP:
			case NOT:
			case RELOP:
			case ASSIGNOP:
			case ID:
			case CONST_INT:
			case CONST_FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(323);
				((Expression_statementContext)_localctx).e = expression();
				setState(324);
				((Expression_statementContext)_localctx).sm = match(SEMICOLON);

				        ((Expression_statementContext)_localctx).formatted_text =  ((Expression_statementContext)_localctx).e.formatted_text + ((Expression_statementContext)_localctx).sm->getText();
				        log_rule_to_file("expression_statement : expression SEMICOLON", ((Expression_statementContext)_localctx).e->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableContext extends ParserRuleContext {
		public std::string formatted_text;
		public std::string variable_name;
		public Token id;
		public ExpressionContext e;
		public TerminalNode ID() { return getToken(C2105028Parser.ID, 0); }
		public TerminalNode LTHIRD() { return getToken(C2105028Parser.LTHIRD, 0); }
		public TerminalNode RTHIRD() { return getToken(C2105028Parser.RTHIRD, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_variable);
		try {
			setState(337);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(329);
				((VariableContext)_localctx).id = match(ID);

				        ((VariableContext)_localctx).formatted_text =  ((VariableContext)_localctx).id->getText();
				        ((VariableContext)_localctx).variable_name =  ((VariableContext)_localctx).id->getText();
				        log_rule_to_file("variable : ID", ((VariableContext)_localctx).id->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(331);
				((VariableContext)_localctx).id = match(ID);
				setState(332);
				match(LTHIRD);
				setState(333);
				((VariableContext)_localctx).e = expression();
				setState(334);
				match(RTHIRD);

				        ((VariableContext)_localctx).formatted_text =  ((VariableContext)_localctx).id->getText() + "[" + ((VariableContext)_localctx).e.formatted_text + "]";
				        ((VariableContext)_localctx).variable_name =  ((VariableContext)_localctx).id->getText();
				        if(((VariableContext)_localctx).e.formatted_text.find('.') != std::string::npos)
				        {
				            log_rule_to_file("variable : ID LTHIRD expression RTHIRD", ((VariableContext)_localctx).e->start->getLine());
				            writeIntoErrorFile(
				                std::string("Error at line ") + std::to_string(((VariableContext)_localctx).e->start->getLine()) +
				                ": Expression inside third brackets not an integer\n");
				            writeIntoparserLogFile(std::string("Error at line ") + std::to_string(((VariableContext)_localctx).e->start->getLine()) +
				                ": Expression inside third brackets not an integer\n");
				            writeIntoparserLogFile(((VariableContext)_localctx).id->getText() + "[" + ((VariableContext)_localctx).e.formatted_text + "]\n");
				            syntaxErrorCount++;
				        }
				        else
				        {
				            log_rule_to_file("variable : ID LTHIRD expression RTHIRD", ((VariableContext)_localctx).id->getLine());
				            writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        }

				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public std::string formatted_text;
		public Logic_expressionContext l;
		public VariableContext v;
		public Token op;
		public Logic_expressionContext logic_expression() {
			return getRuleContext(Logic_expressionContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode ASSIGNOP() { return getToken(C2105028Parser.ASSIGNOP, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_expression);
		try {
			setState(347);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(339);
				((ExpressionContext)_localctx).l = logic_expression();

				        ((ExpressionContext)_localctx).formatted_text =  ((ExpressionContext)_localctx).l.formatted_text;
				        log_rule_to_file("expression : logic_expression", ((ExpressionContext)_localctx).l->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(342);
				((ExpressionContext)_localctx).v = variable();
				setState(343);
				((ExpressionContext)_localctx).op = match(ASSIGNOP);
				setState(344);
				((ExpressionContext)_localctx).l = logic_expression();

				        ((ExpressionContext)_localctx).formatted_text =  ((ExpressionContext)_localctx).v.formatted_text + ((ExpressionContext)_localctx).op->getText() + ((ExpressionContext)_localctx).l.formatted_text;
				        bool success = true; 
				        std::string lhs_name = ((ExpressionContext)_localctx).v.formatted_text.substr(0, ((ExpressionContext)_localctx).v.formatted_text.find('['));
				        symbol_info* lhs_info = st.lookup(lhs_name);
				        if (lhs_info == nullptr) {
				            success = false;
				            writeIntoErrorFile("Error at line " + std::to_string(((ExpressionContext)_localctx).v->start->getLine()) + ": Undeclared variable " + lhs_name + "\n");
				            writeIntoparserLogFile("Error at line " + std::to_string(((ExpressionContext)_localctx).v->start->getLine()) + ": Undeclared variable " + lhs_name + "\n");
				            syntaxErrorCount++;
				        } 
				        else {
				            std::string lhs_type = lhs_info->get_type();
				            bool lhs_is_array = lhs_info->is_array();
				            bool lhs_is_indexed = (((ExpressionContext)_localctx).v.formatted_text.find('[') != std::string::npos);

				            std::string rhs_type = "int";
				            
				            std::string func_name_check = ((ExpressionContext)_localctx).l.formatted_text.substr(0, ((ExpressionContext)_localctx).l.formatted_text.find('('));
				            symbol_info* func_info = st.lookup(func_name_check);
				            if (func_info != nullptr && func_info->is_function()) {
				                std::string return_type = func_info->get_return_type();
				                if (return_type == "void") {
				                    success = false;
				                    writeIntoErrorFile("Error at line " + std::to_string(((ExpressionContext)_localctx).l->start->getLine()) + ": Void function used in expression\n");
				                    syntaxErrorCount++;
				                }
				                rhs_type = return_type;
				            }
				            else if (((ExpressionContext)_localctx).l.formatted_text.find('.') != std::string::npos && ((ExpressionContext)_localctx).l.formatted_text.find('%') == std::string::npos) {
				                rhs_type = "float";
				            }
				            else {
				                symbol_info* rhs_var_info = st.lookup(((ExpressionContext)_localctx).l.formatted_text);
				                if (rhs_var_info != nullptr) {
				                    rhs_type = rhs_var_info->get_type();
				                }
				            }
				            if (lhs_is_array && !lhs_is_indexed) {
				                success = false;
				                writeIntoErrorFile("Error at line " + std::to_string(((ExpressionContext)_localctx).v->start->getLine()) + ": Type mismatch, " + lhs_name + " is an array\n");
				                writeIntoparserLogFile("Error at line " + std::to_string(((ExpressionContext)_localctx).v->start->getLine()) + ": Type mismatch, " + lhs_name + " is an array\n");
				                writeIntoparserLogFile(_localctx.formatted_text + "\n");
				                syntaxErrorCount++;
				            }
				            else if (!lhs_is_array && lhs_is_indexed) {
				                success = false;
				                writeIntoErrorFile("Error at line " + std::to_string(((ExpressionContext)_localctx).v->start->getLine()) + ": " + lhs_name + " not an array\n");
				                syntaxErrorCount++;
				            }
				            else if (lhs_type == "int" && rhs_type == "float") {
				                success = false;
				                writeIntoErrorFile("Error at line " + std::to_string(((ExpressionContext)_localctx).v->start->getLine()) + ": Type Mismatch\n");
				                writeIntoparserLogFile("Error at line " + std::to_string(((ExpressionContext)_localctx).v->start->getLine()) + ": Type Mismatch\n");
				                writeIntoparserLogFile(_localctx.formatted_text + "\n");
				                syntaxErrorCount++;
				            }
				        }

				        log_rule_to_file("expression : variable ASSIGNOP logic_expression", ((ExpressionContext)_localctx).v->start->getLine());
				        if (success) {
				            writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        } else {
				            writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        }
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Logic_expressionContext extends ParserRuleContext {
		public std::string formatted_text;
		public Rel_expressionContext r;
		public Rel_expressionContext l;
		public Token op;
		public List<Rel_expressionContext> rel_expression() {
			return getRuleContexts(Rel_expressionContext.class);
		}
		public Rel_expressionContext rel_expression(int i) {
			return getRuleContext(Rel_expressionContext.class,i);
		}
		public TerminalNode LOGICOP() { return getToken(C2105028Parser.LOGICOP, 0); }
		public Logic_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic_expression; }
	}

	public final Logic_expressionContext logic_expression() throws RecognitionException {
		Logic_expressionContext _localctx = new Logic_expressionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_logic_expression);
		try {
			setState(357);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(349);
				((Logic_expressionContext)_localctx).r = rel_expression();

				        ((Logic_expressionContext)_localctx).formatted_text =  ((Logic_expressionContext)_localctx).r.formatted_text;
				        log_rule_to_file("logic_expression : rel_expression", ((Logic_expressionContext)_localctx).r->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(352);
				((Logic_expressionContext)_localctx).l = rel_expression();
				setState(353);
				((Logic_expressionContext)_localctx).op = match(LOGICOP);
				setState(354);
				((Logic_expressionContext)_localctx).r = rel_expression();

				        ((Logic_expressionContext)_localctx).formatted_text =  ((Logic_expressionContext)_localctx).l.formatted_text + ((Logic_expressionContext)_localctx).op->getText() + ((Logic_expressionContext)_localctx).r.formatted_text;
				        log_rule_to_file("logic_expression : rel_expression LOGICOP rel_expression", ((Logic_expressionContext)_localctx).l->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Rel_expressionContext extends ParserRuleContext {
		public std::string formatted_text;
		public Simple_expressionContext s;
		public Simple_expressionContext s1;
		public Token op;
		public Simple_expressionContext s2;
		public List<Simple_expressionContext> simple_expression() {
			return getRuleContexts(Simple_expressionContext.class);
		}
		public Simple_expressionContext simple_expression(int i) {
			return getRuleContext(Simple_expressionContext.class,i);
		}
		public TerminalNode RELOP() { return getToken(C2105028Parser.RELOP, 0); }
		public Rel_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rel_expression; }
	}

	public final Rel_expressionContext rel_expression() throws RecognitionException {
		Rel_expressionContext _localctx = new Rel_expressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_rel_expression);
		try {
			setState(367);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(359);
				((Rel_expressionContext)_localctx).s = simple_expression(0);

				        ((Rel_expressionContext)_localctx).formatted_text =  ((Rel_expressionContext)_localctx).s.formatted_text;
				        log_rule_to_file("rel_expression : simple_expression", ((Rel_expressionContext)_localctx).s->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(362);
				((Rel_expressionContext)_localctx).s1 = simple_expression(0);
				setState(363);
				((Rel_expressionContext)_localctx).op = match(RELOP);
				setState(364);
				((Rel_expressionContext)_localctx).s2 = simple_expression(0);

				        ((Rel_expressionContext)_localctx).formatted_text =  ((Rel_expressionContext)_localctx).s1.formatted_text + ((Rel_expressionContext)_localctx).op->getText() + ((Rel_expressionContext)_localctx).s2.formatted_text;
				        log_rule_to_file("rel_expression : simple_expression RELOP simple_expression", ((Rel_expressionContext)_localctx).s1->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Simple_expressionContext extends ParserRuleContext {
		public std::string formatted_text;
		public Simple_expressionContext s;
		public TermContext t;
		public Token op;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public Simple_expressionContext simple_expression() {
			return getRuleContext(Simple_expressionContext.class,0);
		}
		public TerminalNode ADDOP() { return getToken(C2105028Parser.ADDOP, 0); }
		public Simple_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple_expression; }
	}

	public final Simple_expressionContext simple_expression() throws RecognitionException {
		return simple_expression(0);
	}

	private Simple_expressionContext simple_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Simple_expressionContext _localctx = new Simple_expressionContext(_ctx, _parentState);
		Simple_expressionContext _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_simple_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(370);
			((Simple_expressionContext)_localctx).t = term(0);

			        ((Simple_expressionContext)_localctx).formatted_text =  ((Simple_expressionContext)_localctx).t.formatted_text;
			        log_rule_to_file("simple_expression : term", ((Simple_expressionContext)_localctx).t->start->getLine());
			        writeIntoparserLogFile(_localctx.formatted_text + "\n");
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(380);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Simple_expressionContext(_parentctx, _parentState);
					_localctx.s = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_simple_expression);
					setState(373);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(374);
					((Simple_expressionContext)_localctx).op = match(ADDOP);
					setState(375);
					((Simple_expressionContext)_localctx).t = term(0);

					                  ((Simple_expressionContext)_localctx).formatted_text =  ((Simple_expressionContext)_localctx).s.formatted_text + ((Simple_expressionContext)_localctx).op->getText() + ((Simple_expressionContext)_localctx).t.formatted_text;
					                  log_rule_to_file("simple_expression : simple_expression ADDOP term", ((Simple_expressionContext)_localctx).s->start->getLine());
					                  writeIntoparserLogFile(_localctx.formatted_text + "\n");
					              
					}
					} 
				}
				setState(382);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public std::string formatted_text;
		public TermContext t;
		public Unary_expressionContext u;
		public Token op;
		public Unary_expressionContext unary_expression() {
			return getRuleContext(Unary_expressionContext.class,0);
		}
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TerminalNode MULOP() { return getToken(C2105028Parser.MULOP, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	}

	public final TermContext term() throws RecognitionException {
		return term(0);
	}

	private TermContext term(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TermContext _localctx = new TermContext(_ctx, _parentState);
		TermContext _prevctx = _localctx;
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_term, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(384);
			((TermContext)_localctx).u = unary_expression();

			        ((TermContext)_localctx).formatted_text =  ((TermContext)_localctx).u.formatted_text;
			        log_rule_to_file("term : unary_expression", ((TermContext)_localctx).u->start->getLine());
			        writeIntoparserLogFile(_localctx.formatted_text + "\n");
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(394);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TermContext(_parentctx, _parentState);
					_localctx.t = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_term);
					setState(387);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(388);
					((TermContext)_localctx).op = match(MULOP);
					setState(389);
					((TermContext)_localctx).u = unary_expression();

					                  ((TermContext)_localctx).formatted_text =  ((TermContext)_localctx).t.formatted_text  + ((TermContext)_localctx).op->getText() + ((TermContext)_localctx).u.formatted_text;
					                  log_rule_to_file("term : term MULOP unary_expression", ((TermContext)_localctx).t->start->getLine());
					                  bool success = true;
					                  bool u_is_void_func = false;
					                  std::string u_text = ((TermContext)_localctx).u.formatted_text;
					                  size_t paren_pos = u_text.find('(');
					                  if (paren_pos != std::string::npos && paren_pos > 0) {
					                      std::string func_name_check = u_text.substr(0, paren_pos);
					                      symbol_info* func_info = st.lookup(func_name_check);
					                      if (func_info && func_info->is_function() && func_info->get_return_type() == "void") {
					                          u_is_void_func = true;
					                      }
					                  }
					                  bool t_is_void_func = false;
					                  std::string t_text = ((TermContext)_localctx).t.formatted_text;
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
					                      writeIntoErrorFile("Error at line " + std::to_string(((TermContext)_localctx).op->getLine()) + ": Void function used in expression\n");
					                      writeIntoparserLogFile("Error at line " + std::to_string(((TermContext)_localctx).op->getLine()) + ": Void function used in expression\n");
					                      writeIntoparserLogFile(_localctx.formatted_text + "\n");
					                      syntaxErrorCount++;
					                  }
					                  else if (((TermContext)_localctx).op->getText() == "%") {
					                      if (((TermContext)_localctx).t.formatted_text.find('.') != std::string::npos || ((TermContext)_localctx).u.formatted_text.find('.') != std::string::npos) {
					                          success = false;
					                          writeIntoErrorFile("Error at line " + std::to_string(((TermContext)_localctx).op->getLine()) + ": Non-Integer operand on modulus operator\n");
					                          writeIntoparserLogFile("Error at line " + std::to_string(((TermContext)_localctx).op->getLine()) + ": Non-Integer operand on modulus operator\n");
					                          writeIntoparserLogFile(_localctx.formatted_text + "\n");
					                          syntaxErrorCount++;
					                      }
					                      if (((TermContext)_localctx).u.formatted_text == "0") {
					                          success = false;
					                          writeIntoErrorFile("Error at line " + std::to_string(((TermContext)_localctx).op->getLine()) + ": Modulus by Zero\n");
					                          writeIntoparserLogFile("Error at line " + std::to_string(((TermContext)_localctx).op->getLine()) + ": Modulus by Zero\n");
					                          writeIntoparserLogFile(_localctx.formatted_text + "\n");
					                          syntaxErrorCount++;
					                      }
					                  }

					                  if (success) {
					                      writeIntoparserLogFile(_localctx.formatted_text + "\n\n");
					                  }
					              
					}
					} 
				}
				setState(396);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Unary_expressionContext extends ParserRuleContext {
		public std::string formatted_text;
		public Token op;
		public Unary_expressionContext u;
		public FactorContext f;
		public TerminalNode ADDOP() { return getToken(C2105028Parser.ADDOP, 0); }
		public Unary_expressionContext unary_expression() {
			return getRuleContext(Unary_expressionContext.class,0);
		}
		public TerminalNode NOT() { return getToken(C2105028Parser.NOT, 0); }
		public FactorContext factor() {
			return getRuleContext(FactorContext.class,0);
		}
		public Unary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_expression; }
	}

	public final Unary_expressionContext unary_expression() throws RecognitionException {
		Unary_expressionContext _localctx = new Unary_expressionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_unary_expression);
		try {
			setState(408);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(397);
				((Unary_expressionContext)_localctx).op = match(ADDOP);
				setState(398);
				((Unary_expressionContext)_localctx).u = unary_expression();

				        ((Unary_expressionContext)_localctx).formatted_text =  ((Unary_expressionContext)_localctx).op->getText() + ((Unary_expressionContext)_localctx).u.formatted_text;
				        log_rule_to_file("unary_expression : ADDOP unary_expression", ((Unary_expressionContext)_localctx).op->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(401);
				((Unary_expressionContext)_localctx).op = match(NOT);
				setState(402);
				((Unary_expressionContext)_localctx).u = unary_expression();

				        ((Unary_expressionContext)_localctx).formatted_text =  ((Unary_expressionContext)_localctx).op->getText() + ((Unary_expressionContext)_localctx).u.formatted_text;
				        log_rule_to_file("unary_expression : NOT unary_expression", ((Unary_expressionContext)_localctx).op->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(405);
				((Unary_expressionContext)_localctx).f = factor();

				        ((Unary_expressionContext)_localctx).formatted_text =  ((Unary_expressionContext)_localctx).f.formatted_text;
				        log_rule_to_file("unary_expression : factor", ((Unary_expressionContext)_localctx).f->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FactorContext extends ParserRuleContext {
		public std::string formatted_text;
		public VariableContext v;
		public Token id;
		public Argument_listContext al;
		public ExpressionContext e;
		public Token c;
		public Token op;
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(C2105028Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(C2105028Parser.RPAREN, 0); }
		public TerminalNode ID() { return getToken(C2105028Parser.ID, 0); }
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode CONST_INT() { return getToken(C2105028Parser.CONST_INT, 0); }
		public TerminalNode CONST_FLOAT() { return getToken(C2105028Parser.CONST_FLOAT, 0); }
		public TerminalNode INCOP() { return getToken(C2105028Parser.INCOP, 0); }
		public TerminalNode DECOP() { return getToken(C2105028Parser.DECOP, 0); }
		public Factor_errContext factor_err() {
			return getRuleContext(Factor_errContext.class,0);
		}
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_factor);
		try {
			setState(438);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(410);
				((FactorContext)_localctx).v = variable();

				        ((FactorContext)_localctx).formatted_text =  ((FactorContext)_localctx).v.formatted_text;
				        log_rule_to_file("factor : variable", ((FactorContext)_localctx).v->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        symbol_info *info = st.lookup(((FactorContext)_localctx).v.formatted_text);
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(413);
				((FactorContext)_localctx).id = match(ID);
				setState(414);
				match(LPAREN);
				setState(416);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
				case 1:
					{
					setState(415);
					((FactorContext)_localctx).al = argument_list();
					}
					break;
				}
				setState(418);
				match(RPAREN);

				        std::string arg_text = (((FactorContext)_localctx).al != nullptr) ? ((FactorContext)_localctx).al.formatted_text : "";
				        ((FactorContext)_localctx).formatted_text =  ((FactorContext)_localctx).id->getText() + "(" + arg_text + ")";
				        log_rule_to_file("factor : ID LPAREN argument_list RPAREN", ((FactorContext)_localctx).id->getLine());

				        bool success = true;
				        symbol_info* func_info = st.lookup(((FactorContext)_localctx).id->getText());

				        if (func_info == nullptr || !func_info->is_function()) {
				            success = false;
				            writeIntoErrorFile("Error at line " + std::to_string(((FactorContext)_localctx).id->getLine()) + ": Undefined function " + ((FactorContext)_localctx).id->getText() + "\n");
				            writeIntoparserLogFile("Error at line " + std::to_string(((FactorContext)_localctx).id->getLine()) + ": Undefined function " + ((FactorContext)_localctx).id->getText() + "\n");
				            writeIntoparserLogFile(_localctx.formatted_text + "\n");
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
				                writeIntoErrorFile("Error at line " + std::to_string(((FactorContext)_localctx).id->getLine()) + ": Total number of arguments mismatch with declaration in function " + ((FactorContext)_localctx).id->getText() + "\n");
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
				                        writeIntoErrorFile("Error at line " + std::to_string(((FactorContext)_localctx).id->getLine()) + ": Type mismatch, argument " + std::to_string(i+1) + " is an array\n");
				                        writeIntoparserLogFile("Error at line " + std::to_string(((FactorContext)_localctx).id->getLine()) + ": Type mismatch, argument " + std::to_string(i+1) + " is an array\n");
				                        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				                    } else {
				                        writeIntoErrorFile("Error at line " + std::to_string(((FactorContext)_localctx).id->getLine()) + ": " + std::to_string(i+1) + "th argument mismatch in function " + ((FactorContext)_localctx).id->getText() + "\n");
				                        writeIntoparserLogFile("Error at line " + std::to_string(((FactorContext)_localctx).id->getLine()) + ": " + std::to_string(i+1) + "th argument mismatch in function " + ((FactorContext)_localctx).id->getText() + "\n");
				                        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				                    }
				                    
				                    syntaxErrorCount++;
				                    break;
				                }
				            }
				        }
				        
				        if(success) {
				            writeIntoparserLogFile(_localctx.formatted_text + "\n\n");
				        }
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(420);
				match(LPAREN);
				setState(421);
				((FactorContext)_localctx).e = expression();
				setState(422);
				match(RPAREN);

				        ((FactorContext)_localctx).formatted_text =  "(" + ((FactorContext)_localctx).e.formatted_text + ")";
				        log_rule_to_file("factor : LPAREN expression RPAREN", ((FactorContext)_localctx).e->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(425);
				((FactorContext)_localctx).c = match(CONST_INT);

				        ((FactorContext)_localctx).formatted_text =  ((FactorContext)_localctx).c->getText();
				        log_rule_to_file("factor : CONST_INT", ((FactorContext)_localctx).c->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        data_type = "int";
				    
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(427);
				((FactorContext)_localctx).c = match(CONST_FLOAT);

				        ((FactorContext)_localctx).formatted_text =  ((FactorContext)_localctx).c->getText();
				        log_rule_to_file("factor : CONST_FLOAT", ((FactorContext)_localctx).c->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				        data_type = "float";
				    
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(429);
				((FactorContext)_localctx).v = variable();
				setState(430);
				((FactorContext)_localctx).op = match(INCOP);

				        ((FactorContext)_localctx).formatted_text =  ((FactorContext)_localctx).v.formatted_text + ((FactorContext)_localctx).op->getText();
				        log_rule_to_file("factor : variable INCOP", ((FactorContext)_localctx).v->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(433);
				((FactorContext)_localctx).v = variable();
				setState(434);
				((FactorContext)_localctx).op = match(DECOP);

				        ((FactorContext)_localctx).formatted_text =  ((FactorContext)_localctx).v.formatted_text + ((FactorContext)_localctx).op->getText();
				        log_rule_to_file("factor : variable DECOP", ((FactorContext)_localctx).v->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(437);
				factor_err();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Factor_errContext extends ParserRuleContext {
		public std::string error_name;
		public ParserRuleContext * ctx;
		public Token op;
		public TerminalNode ASSIGNOP() { return getToken(C2105028Parser.ASSIGNOP, 0); }
		public TerminalNode ADDOP() { return getToken(C2105028Parser.ADDOP, 0); }
		public TerminalNode MULOP() { return getToken(C2105028Parser.MULOP, 0); }
		public TerminalNode RELOP() { return getToken(C2105028Parser.RELOP, 0); }
		public TerminalNode INCOP() { return getToken(C2105028Parser.INCOP, 0); }
		public TerminalNode DECOP() { return getToken(C2105028Parser.DECOP, 0); }
		public Factor_errContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor_err; }
	}

	public final Factor_errContext factor_err() throws RecognitionException {
		Factor_errContext _localctx = new Factor_errContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_factor_err);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(440);
			((Factor_errContext)_localctx).op = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 23420993536L) != 0)) ) {
				((Factor_errContext)_localctx).op = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}

			        std::string op_string = "";
			        if(((Factor_errContext)_localctx).op->getText() == "=") {
			            op_string = "ASSIGNOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == "+") {
			            op_string = "ADDOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == "-") {
			            op_string = "ADDOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == "*") {
			            op_string = "MULOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == "/") {
			            op_string = "MULOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == "%") {
			            op_string = "MULOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == "==") {
			            op_string = "RELOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == "!=") {
			            op_string = "RELOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == "<") {
			            op_string = "RELOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == "<=") {
			            op_string = "RELOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == ">") {
			            op_string = "RELOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == ">=") {
			            op_string = "RELOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == "++") {
			            op_string = "INCOP";
			        } else if (((Factor_errContext)_localctx).op->getText() == "--") {
			            op_string = "DECOP";
			        }
			        ((Factor_errContext)_localctx).error_name =  "syntax error: unexpected " + op_string + "\n";
			        ((Factor_errContext)_localctx).ctx =  _localctx;
			        
			        writeIntoErrorFile("Error at line " + std::to_string(((Factor_errContext)_localctx).op->getLine()) + ": " + _localctx.error_name + "\n");
			        syntaxErrorCount++;
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Argument_listContext extends ParserRuleContext {
		public std::string formatted_text;
		public ArgumentsContext a;
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public Argument_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument_list; }
	}

	public final Argument_listContext argument_list() throws RecognitionException {
		Argument_listContext _localctx = new Argument_listContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_argument_list);
		try {
			setState(447);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
			case ADDOP:
			case MULOP:
			case INCOP:
			case DECOP:
			case NOT:
			case RELOP:
			case ASSIGNOP:
			case ID:
			case CONST_INT:
			case CONST_FLOAT:
				enterOuterAlt(_localctx, 1);
				{
				setState(443);
				((Argument_listContext)_localctx).a = arguments(0);

				        ((Argument_listContext)_localctx).formatted_text =  ((Argument_listContext)_localctx).a.formatted_text;
				        log_rule_to_file("argument_list : arguments", ((Argument_listContext)_localctx).a->start->getLine());
				        writeIntoparserLogFile(_localctx.formatted_text + "\n");
				    
				}
				break;
			case RPAREN:
				enterOuterAlt(_localctx, 2);
				{

				        ((Argument_listContext)_localctx).formatted_text =  "";
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentsContext extends ParserRuleContext {
		public std::string formatted_text;
		public ArgumentsContext a;
		public Logic_expressionContext l;
		public Logic_expressionContext logic_expression() {
			return getRuleContext(Logic_expressionContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(C2105028Parser.COMMA, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		return arguments(0);
	}

	private ArgumentsContext arguments(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, _parentState);
		ArgumentsContext _prevctx = _localctx;
		int _startState = 54;
		enterRecursionRule(_localctx, 54, RULE_arguments, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(450);
			((ArgumentsContext)_localctx).l = logic_expression();

			        ((ArgumentsContext)_localctx).formatted_text =  ((ArgumentsContext)_localctx).l.formatted_text;
			        log_rule_to_file("arguments : logic_expression", ((ArgumentsContext)_localctx).l->start->getLine());
			        writeIntoparserLogFile(_localctx.formatted_text + "\n");
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(460);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArgumentsContext(_parentctx, _parentState);
					_localctx.a = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_arguments);
					setState(453);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(454);
					match(COMMA);
					setState(455);
					((ArgumentsContext)_localctx).l = logic_expression();

					                  ((ArgumentsContext)_localctx).formatted_text =  ((ArgumentsContext)_localctx).a.formatted_text + "," + ((ArgumentsContext)_localctx).l.formatted_text;
					                  log_rule_to_file("arguments : arguments COMMA logic_expression", ((ArgumentsContext)_localctx).a->start->getLine());
					                  writeIntoparserLogFile(_localctx.formatted_text + "\n");
					              
					}
					} 
				}
				setState(462);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return program_sempred((ProgramContext)_localctx, predIndex);
		case 5:
			return parameter_list_sempred((Parameter_listContext)_localctx, predIndex);
		case 11:
			return declaration_list_sempred((Declaration_listContext)_localctx, predIndex);
		case 13:
			return statements_sempred((StatementsContext)_localctx, predIndex);
		case 21:
			return simple_expression_sempred((Simple_expressionContext)_localctx, predIndex);
		case 22:
			return term_sempred((TermContext)_localctx, predIndex);
		case 27:
			return arguments_sempred((ArgumentsContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean program_sempred(ProgramContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean parameter_list_sempred(Parameter_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean declaration_list_sempred(Declaration_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 5);
		case 3:
			return precpred(_ctx, 4);
		}
		return true;
	}
	private boolean statements_sempred(StatementsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean simple_expression_sempred(Simple_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean term_sempred(TermContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean arguments_sempred(ArgumentsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001&\u01d0\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001"+
		"D\b\u0001\n\u0001\f\u0001G\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002R\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003"+
		"i\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0003\u0004~\b\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0003\u0005\u0088\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0005\u0005\u0090\b\u0005\n\u0005\f\u0005\u0093"+
		"\t\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0003\u0006\u00a1\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u00ab"+
		"\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\b\u00b7\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00c5"+
		"\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003"+
		"\u000b\u00d2\b\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0005\u000b\u00df\b\u000b\n\u000b\f\u000b\u00e2\t\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0003\f\u00f7\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0005\r\u0101\b\r\n\r\f\r\u0104\t\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u013b\b\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u0148\b\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0003\u0011\u0152\b\u0011\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0003\u0012\u015c\b\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u0166\b\u0013"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0003\u0014\u0170\b\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0005\u0015\u017b\b\u0015\n\u0015\f\u0015\u017e\t\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0005\u0016\u0189\b\u0016\n\u0016\f\u0016"+
		"\u018c\t\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0003\u0017\u0199\b\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0003\u0018\u01a1\b\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0003\u0018\u01b7\b\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u01c0\b\u001a\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0005\u001b\u01cb\b\u001b\n\u001b\f\u001b\u01ce"+
		"\t\u001b\u0001\u001b\u0000\u0007\u0002\n\u0016\u001a*,6\u001c\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e"+
		" \"$&(*,.0246\u0000\u0002\u0002\u0000\u001a\u001a\u001c\u001c\u0004\u0000"+
		"\u001a\u001a\u001c\u001e  \"\"\u01e4\u00008\u0001\u0000\u0000\u0000\u0002"+
		";\u0001\u0000\u0000\u0000\u0004Q\u0001\u0000\u0000\u0000\u0006h\u0001"+
		"\u0000\u0000\u0000\b}\u0001\u0000\u0000\u0000\n\u0087\u0001\u0000\u0000"+
		"\u0000\f\u00a0\u0001\u0000\u0000\u0000\u000e\u00aa\u0001\u0000\u0000\u0000"+
		"\u0010\u00b6\u0001\u0000\u0000\u0000\u0012\u00b8\u0001\u0000\u0000\u0000"+
		"\u0014\u00c4\u0001\u0000\u0000\u0000\u0016\u00d1\u0001\u0000\u0000\u0000"+
		"\u0018\u00f6\u0001\u0000\u0000\u0000\u001a\u00f8\u0001\u0000\u0000\u0000"+
		"\u001c\u013a\u0001\u0000\u0000\u0000\u001e\u013c\u0001\u0000\u0000\u0000"+
		" \u0147\u0001\u0000\u0000\u0000\"\u0151\u0001\u0000\u0000\u0000$\u015b"+
		"\u0001\u0000\u0000\u0000&\u0165\u0001\u0000\u0000\u0000(\u016f\u0001\u0000"+
		"\u0000\u0000*\u0171\u0001\u0000\u0000\u0000,\u017f\u0001\u0000\u0000\u0000"+
		".\u0198\u0001\u0000\u0000\u00000\u01b6\u0001\u0000\u0000\u00002\u01b8"+
		"\u0001\u0000\u0000\u00004\u01bf\u0001\u0000\u0000\u00006\u01c1\u0001\u0000"+
		"\u0000\u000089\u0003\u0002\u0001\u00009:\u0006\u0000\uffff\uffff\u0000"+
		":\u0001\u0001\u0000\u0000\u0000;<\u0006\u0001\uffff\uffff\u0000<=\u0003"+
		"\u0004\u0002\u0000=>\u0006\u0001\uffff\uffff\u0000>E\u0001\u0000\u0000"+
		"\u0000?@\n\u0002\u0000\u0000@A\u0003\u0004\u0002\u0000AB\u0006\u0001\uffff"+
		"\uffff\u0000BD\u0001\u0000\u0000\u0000C?\u0001\u0000\u0000\u0000DG\u0001"+
		"\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000EF\u0001\u0000\u0000\u0000"+
		"F\u0003\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000HI\u0003\u0012"+
		"\t\u0000IJ\u0006\u0002\uffff\uffff\u0000JR\u0001\u0000\u0000\u0000KL\u0003"+
		"\u0006\u0003\u0000LM\u0006\u0002\uffff\uffff\u0000MR\u0001\u0000\u0000"+
		"\u0000NO\u0003\b\u0004\u0000OP\u0006\u0002\uffff\uffff\u0000PR\u0001\u0000"+
		"\u0000\u0000QH\u0001\u0000\u0000\u0000QK\u0001\u0000\u0000\u0000QN\u0001"+
		"\u0000\u0000\u0000R\u0005\u0001\u0000\u0000\u0000ST\u0003\u0014\n\u0000"+
		"TU\u0006\u0003\uffff\uffff\u0000UV\u0005#\u0000\u0000VW\u0006\u0003\uffff"+
		"\uffff\u0000WX\u0005\u0012\u0000\u0000XY\u0006\u0003\uffff\uffff\u0000"+
		"YZ\u0003\n\u0005\u0000Z[\u0005\u0013\u0000\u0000[\\\u0006\u0003\uffff"+
		"\uffff\u0000\\]\u0005\u0018\u0000\u0000]^\u0006\u0003\uffff\uffff\u0000"+
		"^i\u0001\u0000\u0000\u0000_`\u0003\u0014\n\u0000`a\u0006\u0003\uffff\uffff"+
		"\u0000ab\u0005#\u0000\u0000bc\u0006\u0003\uffff\uffff\u0000cd\u0005\u0012"+
		"\u0000\u0000de\u0005\u0013\u0000\u0000ef\u0005\u0018\u0000\u0000fg\u0006"+
		"\u0003\uffff\uffff\u0000gi\u0001\u0000\u0000\u0000hS\u0001\u0000\u0000"+
		"\u0000h_\u0001\u0000\u0000\u0000i\u0007\u0001\u0000\u0000\u0000jk\u0003"+
		"\u0014\n\u0000kl\u0005#\u0000\u0000lm\u0006\u0004\uffff\uffff\u0000mn"+
		"\u0005\u0012\u0000\u0000no\u0006\u0004\uffff\uffff\u0000op\u0003\n\u0005"+
		"\u0000pq\u0005\u0013\u0000\u0000qr\u0003\u000e\u0007\u0000rs\u0006\u0004"+
		"\uffff\uffff\u0000s~\u0001\u0000\u0000\u0000tu\u0003\u0014\n\u0000uv\u0005"+
		"#\u0000\u0000vw\u0006\u0004\uffff\uffff\u0000wx\u0005\u0012\u0000\u0000"+
		"xy\u0005\u0013\u0000\u0000yz\u0006\u0004\uffff\uffff\u0000z{\u0003\u000e"+
		"\u0007\u0000{|\u0006\u0004\uffff\uffff\u0000|~\u0001\u0000\u0000\u0000"+
		"}j\u0001\u0000\u0000\u0000}t\u0001\u0000\u0000\u0000~\t\u0001\u0000\u0000"+
		"\u0000\u007f\u0080\u0006\u0005\uffff\uffff\u0000\u0080\u0081\u0003\u0014"+
		"\n\u0000\u0081\u0082\u0005#\u0000\u0000\u0082\u0083\u0006\u0005\uffff"+
		"\uffff\u0000\u0083\u0088\u0001\u0000\u0000\u0000\u0084\u0085\u0003\f\u0006"+
		"\u0000\u0085\u0086\u0006\u0005\uffff\uffff\u0000\u0086\u0088\u0001\u0000"+
		"\u0000\u0000\u0087\u007f\u0001\u0000\u0000\u0000\u0087\u0084\u0001\u0000"+
		"\u0000\u0000\u0088\u0091\u0001\u0000\u0000\u0000\u0089\u008a\n\u0003\u0000"+
		"\u0000\u008a\u008b\u0005\u0019\u0000\u0000\u008b\u008c\u0003\u0014\n\u0000"+
		"\u008c\u008d\u0005#\u0000\u0000\u008d\u008e\u0006\u0005\uffff\uffff\u0000"+
		"\u008e\u0090\u0001\u0000\u0000\u0000\u008f\u0089\u0001\u0000\u0000\u0000"+
		"\u0090\u0093\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000"+
		"\u0091\u0092\u0001\u0000\u0000\u0000\u0092\u000b\u0001\u0000\u0000\u0000"+
		"\u0093\u0091\u0001\u0000\u0000\u0000\u0094\u0095\u0003\u0014\n\u0000\u0095"+
		"\u0096\u0005\u001a\u0000\u0000\u0096\u0097\u0005#\u0000\u0000\u0097\u0098"+
		"\u0006\u0006\uffff\uffff\u0000\u0098\u00a1\u0001\u0000\u0000\u0000\u0099"+
		"\u009a\u0003\u0014\n\u0000\u009a\u009b\u0005\u001a\u0000\u0000\u009b\u009c"+
		"\u0006\u0006\uffff\uffff\u0000\u009c\u00a1\u0001\u0000\u0000\u0000\u009d"+
		"\u009e\u0003\u0014\n\u0000\u009e\u009f\u0006\u0006\uffff\uffff\u0000\u009f"+
		"\u00a1\u0001\u0000\u0000\u0000\u00a0\u0094\u0001\u0000\u0000\u0000\u00a0"+
		"\u0099\u0001\u0000\u0000\u0000\u00a0\u009d\u0001\u0000\u0000\u0000\u00a1"+
		"\r\u0001\u0000\u0000\u0000\u00a2\u00a3\u0005\u0014\u0000\u0000\u00a3\u00a4"+
		"\u0003\u001a\r\u0000\u00a4\u00a5\u0005\u0015\u0000\u0000\u00a5\u00a6\u0006"+
		"\u0007\uffff\uffff\u0000\u00a6\u00ab\u0001\u0000\u0000\u0000\u00a7\u00a8"+
		"\u0005\u0014\u0000\u0000\u00a8\u00a9\u0005\u0015\u0000\u0000\u00a9\u00ab"+
		"\u0006\u0007\uffff\uffff\u0000\u00aa\u00a2\u0001\u0000\u0000\u0000\u00aa"+
		"\u00a7\u0001\u0000\u0000\u0000\u00ab\u000f\u0001\u0000\u0000\u0000\u00ac"+
		"\u00ad\u0005\u0014\u0000\u0000\u00ad\u00ae\u0006\b\uffff\uffff\u0000\u00ae"+
		"\u00af\u0003\u001a\r\u0000\u00af\u00b0\u0005\u0015\u0000\u0000\u00b0\u00b1"+
		"\u0006\b\uffff\uffff\u0000\u00b1\u00b7\u0001\u0000\u0000\u0000\u00b2\u00b3"+
		"\u0005\u0014\u0000\u0000\u00b3\u00b4\u0006\b\uffff\uffff\u0000\u00b4\u00b5"+
		"\u0005\u0015\u0000\u0000\u00b5\u00b7\u0006\b\uffff\uffff\u0000\u00b6\u00ac"+
		"\u0001\u0000\u0000\u0000\u00b6\u00b2\u0001\u0000\u0000\u0000\u00b7\u0011"+
		"\u0001\u0000\u0000\u0000\u00b8\u00b9\u0003\u0014\n\u0000\u00b9\u00ba\u0006"+
		"\t\uffff\uffff\u0000\u00ba\u00bb\u0003\u0016\u000b\u0000\u00bb\u00bc\u0005"+
		"\u0018\u0000\u0000\u00bc\u00bd\u0006\t\uffff\uffff\u0000\u00bd\u0013\u0001"+
		"\u0000\u0000\u0000\u00be\u00bf\u0005\r\u0000\u0000\u00bf\u00c5\u0006\n"+
		"\uffff\uffff\u0000\u00c0\u00c1\u0005\u000e\u0000\u0000\u00c1\u00c5\u0006"+
		"\n\uffff\uffff\u0000\u00c2\u00c3\u0005\u000f\u0000\u0000\u00c3\u00c5\u0006"+
		"\n\uffff\uffff\u0000\u00c4\u00be\u0001\u0000\u0000\u0000\u00c4\u00c0\u0001"+
		"\u0000\u0000\u0000\u00c4\u00c2\u0001\u0000\u0000\u0000\u00c5\u0015\u0001"+
		"\u0000\u0000\u0000\u00c6\u00c7\u0006\u000b\uffff\uffff\u0000\u00c7\u00c8"+
		"\u0005#\u0000\u0000\u00c8\u00d2\u0006\u000b\uffff\uffff\u0000\u00c9\u00ca"+
		"\u0005#\u0000\u0000\u00ca\u00cb\u0005\u0016\u0000\u0000\u00cb\u00cc\u0005"+
		"$\u0000\u0000\u00cc\u00cd\u0005\u0017\u0000\u0000\u00cd\u00d2\u0006\u000b"+
		"\uffff\uffff\u0000\u00ce\u00cf\u0003\u0018\f\u0000\u00cf\u00d0\u0006\u000b"+
		"\uffff\uffff\u0000\u00d0\u00d2\u0001\u0000\u0000\u0000\u00d1\u00c6\u0001"+
		"\u0000\u0000\u0000\u00d1\u00c9\u0001\u0000\u0000\u0000\u00d1\u00ce\u0001"+
		"\u0000\u0000\u0000\u00d2\u00e0\u0001\u0000\u0000\u0000\u00d3\u00d4\n\u0005"+
		"\u0000\u0000\u00d4\u00d5\u0005\u0019\u0000\u0000\u00d5\u00d6\u0005#\u0000"+
		"\u0000\u00d6\u00df\u0006\u000b\uffff\uffff\u0000\u00d7\u00d8\n\u0004\u0000"+
		"\u0000\u00d8\u00d9\u0005\u0019\u0000\u0000\u00d9\u00da\u0005#\u0000\u0000"+
		"\u00da\u00db\u0005\u0016\u0000\u0000\u00db\u00dc\u0005$\u0000\u0000\u00dc"+
		"\u00dd\u0005\u0017\u0000\u0000\u00dd\u00df\u0006\u000b\uffff\uffff\u0000"+
		"\u00de\u00d3\u0001\u0000\u0000\u0000\u00de\u00d7\u0001\u0000\u0000\u0000"+
		"\u00df\u00e2\u0001\u0000\u0000\u0000\u00e0\u00de\u0001\u0000\u0000\u0000"+
		"\u00e0\u00e1\u0001\u0000\u0000\u0000\u00e1\u0017\u0001\u0000\u0000\u0000"+
		"\u00e2\u00e0\u0001\u0000\u0000\u0000\u00e3\u00e4\u0005#\u0000\u0000\u00e4"+
		"\u00e5\u0007\u0000\u0000\u0000\u00e5\u00e6\u0005#\u0000\u0000\u00e6\u00f7"+
		"\u0006\f\uffff\uffff\u0000\u00e7\u00e8\u0005#\u0000\u0000\u00e8\u00e9"+
		"\u0005#\u0000\u0000\u00e9\u00f7\u0006\f\uffff\uffff\u0000\u00ea\u00eb"+
		"\u0005#\u0000\u0000\u00eb\u00ec\u0005\u0016\u0000\u0000\u00ec\u00ed\u0005"+
		"$\u0000\u0000\u00ed\u00ee\u0005\u0017\u0000\u0000\u00ee\u00ef\u0005#\u0000"+
		"\u0000\u00ef\u00f7\u0006\f\uffff\uffff\u0000\u00f0\u00f1\u0005#\u0000"+
		"\u0000\u00f1\u00f2\u0005\u0016\u0000\u0000\u00f2\u00f3\u0003$\u0012\u0000"+
		"\u00f3\u00f4\u0005\u0017\u0000\u0000\u00f4\u00f5\u0006\f\uffff\uffff\u0000"+
		"\u00f5\u00f7\u0001\u0000\u0000\u0000\u00f6\u00e3\u0001\u0000\u0000\u0000"+
		"\u00f6\u00e7\u0001\u0000\u0000\u0000\u00f6\u00ea\u0001\u0000\u0000\u0000"+
		"\u00f6\u00f0\u0001\u0000\u0000\u0000\u00f7\u0019\u0001\u0000\u0000\u0000"+
		"\u00f8\u00f9\u0006\r\uffff\uffff\u0000\u00f9\u00fa\u0003\u001c\u000e\u0000"+
		"\u00fa\u00fb\u0006\r\uffff\uffff\u0000\u00fb\u0102\u0001\u0000\u0000\u0000"+
		"\u00fc\u00fd\n\u0001\u0000\u0000\u00fd\u00fe\u0003\u001c\u000e\u0000\u00fe"+
		"\u00ff\u0006\r\uffff\uffff\u0000\u00ff\u0101\u0001\u0000\u0000\u0000\u0100"+
		"\u00fc\u0001\u0000\u0000\u0000\u0101\u0104\u0001\u0000\u0000\u0000\u0102"+
		"\u0100\u0001\u0000\u0000\u0000\u0102\u0103\u0001\u0000\u0000\u0000\u0103"+
		"\u001b\u0001\u0000\u0000\u0000\u0104\u0102\u0001\u0000\u0000\u0000\u0105"+
		"\u0106\u0005\t\u0000\u0000\u0106\u0107\u0005\u0012\u0000\u0000\u0107\u0108"+
		"\u0003 \u0010\u0000\u0108\u0109\u0003 \u0010\u0000\u0109\u010a\u0003$"+
		"\u0012\u0000\u010a\u010b\u0005\u0013\u0000\u0000\u010b\u010c\u0003\u001c"+
		"\u000e\u0000\u010c\u010d\u0006\u000e\uffff\uffff\u0000\u010d\u013b\u0001"+
		"\u0000\u0000\u0000\u010e\u010f\u0005\u0005\u0000\u0000\u010f\u0110\u0005"+
		"\u0012\u0000\u0000\u0110\u0111\u0003$\u0012\u0000\u0111\u0112\u0005\u0013"+
		"\u0000\u0000\u0112\u0113\u0003\u001c\u000e\u0000\u0113\u0114\u0006\u000e"+
		"\uffff\uffff\u0000\u0114\u013b\u0001\u0000\u0000\u0000\u0115\u0116\u0005"+
		"\u0005\u0000\u0000\u0116\u0117\u0005\u0012\u0000\u0000\u0117\u0118\u0003"+
		"$\u0012\u0000\u0118\u0119\u0005\u0013\u0000\u0000\u0119\u011a\u0003\u001c"+
		"\u000e\u0000\u011a\u011b\u0005\u0006\u0000\u0000\u011b\u011c\u0003\u001c"+
		"\u000e\u0000\u011c\u011d\u0006\u000e\uffff\uffff\u0000\u011d\u013b\u0001"+
		"\u0000\u0000\u0000\u011e\u011f\u0005\n\u0000\u0000\u011f\u0120\u0005\u0012"+
		"\u0000\u0000\u0120\u0121\u0003$\u0012\u0000\u0121\u0122\u0005\u0013\u0000"+
		"\u0000\u0122\u0123\u0003\u001c\u000e\u0000\u0123\u0124\u0006\u000e\uffff"+
		"\uffff\u0000\u0124\u013b\u0001\u0000\u0000\u0000\u0125\u0126\u0005\u000b"+
		"\u0000\u0000\u0126\u0127\u0005\u0012\u0000\u0000\u0127\u0128\u0005#\u0000"+
		"\u0000\u0128\u0129\u0005\u0013\u0000\u0000\u0129\u012a\u0005\u0018\u0000"+
		"\u0000\u012a\u013b\u0006\u000e\uffff\uffff\u0000\u012b\u012c\u0005\f\u0000"+
		"\u0000\u012c\u012d\u0003$\u0012\u0000\u012d\u012e\u0005\u0018\u0000\u0000"+
		"\u012e\u012f\u0006\u000e\uffff\uffff\u0000\u012f\u013b\u0001\u0000\u0000"+
		"\u0000\u0130\u0131\u0003\u0012\t\u0000\u0131\u0132\u0006\u000e\uffff\uffff"+
		"\u0000\u0132\u013b\u0001\u0000\u0000\u0000\u0133\u0134\u0003 \u0010\u0000"+
		"\u0134\u0135\u0006\u000e\uffff\uffff\u0000\u0135\u013b\u0001\u0000\u0000"+
		"\u0000\u0136\u0137\u0003\u0010\b\u0000\u0137\u0138\u0006\u000e\uffff\uffff"+
		"\u0000\u0138\u013b\u0001\u0000\u0000\u0000\u0139\u013b\u0003\u001e\u000f"+
		"\u0000\u013a\u0105\u0001\u0000\u0000\u0000\u013a\u010e\u0001\u0000\u0000"+
		"\u0000\u013a\u0115\u0001\u0000\u0000\u0000\u013a\u011e\u0001\u0000\u0000"+
		"\u0000\u013a\u0125\u0001\u0000\u0000\u0000\u013a\u012b\u0001\u0000\u0000"+
		"\u0000\u013a\u0130\u0001\u0000\u0000\u0000\u013a\u0133\u0001\u0000\u0000"+
		"\u0000\u013a\u0136\u0001\u0000\u0000\u0000\u013a\u0139\u0001\u0000\u0000"+
		"\u0000\u013b\u001d\u0001\u0000\u0000\u0000\u013c\u013d\u0005#\u0000\u0000"+
		"\u013d\u013e\u0005\"\u0000\u0000\u013e\u013f\u0005\u0018\u0000\u0000\u013f"+
		"\u0140\u0006\u000f\uffff\uffff\u0000\u0140\u001f\u0001\u0000\u0000\u0000"+
		"\u0141\u0142\u0005\u0018\u0000\u0000\u0142\u0148\u0006\u0010\uffff\uffff"+
		"\u0000\u0143\u0144\u0003$\u0012\u0000\u0144\u0145\u0005\u0018\u0000\u0000"+
		"\u0145\u0146\u0006\u0010\uffff\uffff\u0000\u0146\u0148\u0001\u0000\u0000"+
		"\u0000\u0147\u0141\u0001\u0000\u0000\u0000\u0147\u0143\u0001\u0000\u0000"+
		"\u0000\u0148!\u0001\u0000\u0000\u0000\u0149\u014a\u0005#\u0000\u0000\u014a"+
		"\u0152\u0006\u0011\uffff\uffff\u0000\u014b\u014c\u0005#\u0000\u0000\u014c"+
		"\u014d\u0005\u0016\u0000\u0000\u014d\u014e\u0003$\u0012\u0000\u014e\u014f"+
		"\u0005\u0017\u0000\u0000\u014f\u0150\u0006\u0011\uffff\uffff\u0000\u0150"+
		"\u0152\u0001\u0000\u0000\u0000\u0151\u0149\u0001\u0000\u0000\u0000\u0151"+
		"\u014b\u0001\u0000\u0000\u0000\u0152#\u0001\u0000\u0000\u0000\u0153\u0154"+
		"\u0003&\u0013\u0000\u0154\u0155\u0006\u0012\uffff\uffff\u0000\u0155\u015c"+
		"\u0001\u0000\u0000\u0000\u0156\u0157\u0003\"\u0011\u0000\u0157\u0158\u0005"+
		"\"\u0000\u0000\u0158\u0159\u0003&\u0013\u0000\u0159\u015a\u0006\u0012"+
		"\uffff\uffff\u0000\u015a\u015c\u0001\u0000\u0000\u0000\u015b\u0153\u0001"+
		"\u0000\u0000\u0000\u015b\u0156\u0001\u0000\u0000\u0000\u015c%\u0001\u0000"+
		"\u0000\u0000\u015d\u015e\u0003(\u0014\u0000\u015e\u015f\u0006\u0013\uffff"+
		"\uffff\u0000\u015f\u0166\u0001\u0000\u0000\u0000\u0160\u0161\u0003(\u0014"+
		"\u0000\u0161\u0162\u0005!\u0000\u0000\u0162\u0163\u0003(\u0014\u0000\u0163"+
		"\u0164\u0006\u0013\uffff\uffff\u0000\u0164\u0166\u0001\u0000\u0000\u0000"+
		"\u0165\u015d\u0001\u0000\u0000\u0000\u0165\u0160\u0001\u0000\u0000\u0000"+
		"\u0166\'\u0001\u0000\u0000\u0000\u0167\u0168\u0003*\u0015\u0000\u0168"+
		"\u0169\u0006\u0014\uffff\uffff\u0000\u0169\u0170\u0001\u0000\u0000\u0000"+
		"\u016a\u016b\u0003*\u0015\u0000\u016b\u016c\u0005 \u0000\u0000\u016c\u016d"+
		"\u0003*\u0015\u0000\u016d\u016e\u0006\u0014\uffff\uffff\u0000\u016e\u0170"+
		"\u0001\u0000\u0000\u0000\u016f\u0167\u0001\u0000\u0000\u0000\u016f\u016a"+
		"\u0001\u0000\u0000\u0000\u0170)\u0001\u0000\u0000\u0000\u0171\u0172\u0006"+
		"\u0015\uffff\uffff\u0000\u0172\u0173\u0003,\u0016\u0000\u0173\u0174\u0006"+
		"\u0015\uffff\uffff\u0000\u0174\u017c\u0001\u0000\u0000\u0000\u0175\u0176"+
		"\n\u0001\u0000\u0000\u0176\u0177\u0005\u001a\u0000\u0000\u0177\u0178\u0003"+
		",\u0016\u0000\u0178\u0179\u0006\u0015\uffff\uffff\u0000\u0179\u017b\u0001"+
		"\u0000\u0000\u0000\u017a\u0175\u0001\u0000\u0000\u0000\u017b\u017e\u0001"+
		"\u0000\u0000\u0000\u017c\u017a\u0001\u0000\u0000\u0000\u017c\u017d\u0001"+
		"\u0000\u0000\u0000\u017d+\u0001\u0000\u0000\u0000\u017e\u017c\u0001\u0000"+
		"\u0000\u0000\u017f\u0180\u0006\u0016\uffff\uffff\u0000\u0180\u0181\u0003"+
		".\u0017\u0000\u0181\u0182\u0006\u0016\uffff\uffff\u0000\u0182\u018a\u0001"+
		"\u0000\u0000\u0000\u0183\u0184\n\u0001\u0000\u0000\u0184\u0185\u0005\u001c"+
		"\u0000\u0000\u0185\u0186\u0003.\u0017\u0000\u0186\u0187\u0006\u0016\uffff"+
		"\uffff\u0000\u0187\u0189\u0001\u0000\u0000\u0000\u0188\u0183\u0001\u0000"+
		"\u0000\u0000\u0189\u018c\u0001\u0000\u0000\u0000\u018a\u0188\u0001\u0000"+
		"\u0000\u0000\u018a\u018b\u0001\u0000\u0000\u0000\u018b-\u0001\u0000\u0000"+
		"\u0000\u018c\u018a\u0001\u0000\u0000\u0000\u018d\u018e\u0005\u001a\u0000"+
		"\u0000\u018e\u018f\u0003.\u0017\u0000\u018f\u0190\u0006\u0017\uffff\uffff"+
		"\u0000\u0190\u0199\u0001\u0000\u0000\u0000\u0191\u0192\u0005\u001f\u0000"+
		"\u0000\u0192\u0193\u0003.\u0017\u0000\u0193\u0194\u0006\u0017\uffff\uffff"+
		"\u0000\u0194\u0199\u0001\u0000\u0000\u0000\u0195\u0196\u00030\u0018\u0000"+
		"\u0196\u0197\u0006\u0017\uffff\uffff\u0000\u0197\u0199\u0001\u0000\u0000"+
		"\u0000\u0198\u018d\u0001\u0000\u0000\u0000\u0198\u0191\u0001\u0000\u0000"+
		"\u0000\u0198\u0195\u0001\u0000\u0000\u0000\u0199/\u0001\u0000\u0000\u0000"+
		"\u019a\u019b\u0003\"\u0011\u0000\u019b\u019c\u0006\u0018\uffff\uffff\u0000"+
		"\u019c\u01b7\u0001\u0000\u0000\u0000\u019d\u019e\u0005#\u0000\u0000\u019e"+
		"\u01a0\u0005\u0012\u0000\u0000\u019f\u01a1\u00034\u001a\u0000\u01a0\u019f"+
		"\u0001\u0000\u0000\u0000\u01a0\u01a1\u0001\u0000\u0000\u0000\u01a1\u01a2"+
		"\u0001\u0000\u0000\u0000\u01a2\u01a3\u0005\u0013\u0000\u0000\u01a3\u01b7"+
		"\u0006\u0018\uffff\uffff\u0000\u01a4\u01a5\u0005\u0012\u0000\u0000\u01a5"+
		"\u01a6\u0003$\u0012\u0000\u01a6\u01a7\u0005\u0013\u0000\u0000\u01a7\u01a8"+
		"\u0006\u0018\uffff\uffff\u0000\u01a8\u01b7\u0001\u0000\u0000\u0000\u01a9"+
		"\u01aa\u0005$\u0000\u0000\u01aa\u01b7\u0006\u0018\uffff\uffff\u0000\u01ab"+
		"\u01ac\u0005%\u0000\u0000\u01ac\u01b7\u0006\u0018\uffff\uffff\u0000\u01ad"+
		"\u01ae\u0003\"\u0011\u0000\u01ae\u01af\u0005\u001d\u0000\u0000\u01af\u01b0"+
		"\u0006\u0018\uffff\uffff\u0000\u01b0\u01b7\u0001\u0000\u0000\u0000\u01b1"+
		"\u01b2\u0003\"\u0011\u0000\u01b2\u01b3\u0005\u001e\u0000\u0000\u01b3\u01b4"+
		"\u0006\u0018\uffff\uffff\u0000\u01b4\u01b7\u0001\u0000\u0000\u0000\u01b5"+
		"\u01b7\u00032\u0019\u0000\u01b6\u019a\u0001\u0000\u0000\u0000\u01b6\u019d"+
		"\u0001\u0000\u0000\u0000\u01b6\u01a4\u0001\u0000\u0000\u0000\u01b6\u01a9"+
		"\u0001\u0000\u0000\u0000\u01b6\u01ab\u0001\u0000\u0000\u0000\u01b6\u01ad"+
		"\u0001\u0000\u0000\u0000\u01b6\u01b1\u0001\u0000\u0000\u0000\u01b6\u01b5"+
		"\u0001\u0000\u0000\u0000\u01b71\u0001\u0000\u0000\u0000\u01b8\u01b9\u0007"+
		"\u0001\u0000\u0000\u01b9\u01ba\u0006\u0019\uffff\uffff\u0000\u01ba3\u0001"+
		"\u0000\u0000\u0000\u01bb\u01bc\u00036\u001b\u0000\u01bc\u01bd\u0006\u001a"+
		"\uffff\uffff\u0000\u01bd\u01c0\u0001\u0000\u0000\u0000\u01be\u01c0\u0006"+
		"\u001a\uffff\uffff\u0000\u01bf\u01bb\u0001\u0000\u0000\u0000\u01bf\u01be"+
		"\u0001\u0000\u0000\u0000\u01c05\u0001\u0000\u0000\u0000\u01c1\u01c2\u0006"+
		"\u001b\uffff\uffff\u0000\u01c2\u01c3\u0003&\u0013\u0000\u01c3\u01c4\u0006"+
		"\u001b\uffff\uffff\u0000\u01c4\u01cc\u0001\u0000\u0000\u0000\u01c5\u01c6"+
		"\n\u0002\u0000\u0000\u01c6\u01c7\u0005\u0019\u0000\u0000\u01c7\u01c8\u0003"+
		"&\u0013\u0000\u01c8\u01c9\u0006\u001b\uffff\uffff\u0000\u01c9\u01cb\u0001"+
		"\u0000\u0000\u0000\u01ca\u01c5\u0001\u0000\u0000\u0000\u01cb\u01ce\u0001"+
		"\u0000\u0000\u0000\u01cc\u01ca\u0001\u0000\u0000\u0000\u01cc\u01cd\u0001"+
		"\u0000\u0000\u0000\u01cd7\u0001\u0000\u0000\u0000\u01ce\u01cc\u0001\u0000"+
		"\u0000\u0000\u001cEQh}\u0087\u0091\u00a0\u00aa\u00b6\u00c4\u00d1\u00de"+
		"\u00e0\u00f6\u0102\u013a\u0147\u0151\u015b\u0165\u016f\u017c\u018a\u0198"+
		"\u01a0\u01b6\u01bf\u01cc";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}