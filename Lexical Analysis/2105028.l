%option noyywrap

%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "2105028_symbol_table.hpp"

#define MAX_STRING_LEN 10000
static char string_buffer[MAX_STRING_LEN];
static char comment_buffer[MAX_STRING_LEN];
static char character_buffer[MAX_STRING_LEN];
static int string_index;
int line_no = 1;
int comment_start_line = 1;
int comment_index=0;
static int character_index=0;
int error_count = 0;


FILE *logout;
FILE *tokenout;
symbol_table symbol_table(7,0);

%}



WHITESPACE [ \t\f\r\v]+ 
IDENTIFIER [a-zA-Z_][a-zA-Z0-9_]*
DIGIT   [0-9]
EXP     ([eE][+-]?{DIGIT}+)
NEWLINE \n

%x STRINGSTATE
%x SINGLECOMMENTSTATE
%x MULTICOMMENTSTATE
%x SINGLEQUOTESTATE

%%

{NEWLINE}                      { line_no++; }


\'                      { 
                            character_index = 0; 
                            BEGIN(SINGLEQUOTESTATE); 
                        }

<SINGLEQUOTESTATE>\' {
                            if (character_index == 0) {
                                character_buffer[character_index++] = '\0';
                                printf("Error at line no %d: Empty character constant error '%s'\n\n\n", line_no, character_buffer); 
                                                    error_count += 1; 
                            } else if (character_index > 1) {
                                character_buffer[character_index++] = '\0';
                                printf("Error at line no %d: Multi character constant error '%s'\n\n\n", line_no, character_buffer); 
                                                    error_count += 1; 
                            } else if( character_index ==1){
                                    character_buffer[character_index++] = '\0';
                                    char temp[5];
                                    temp[0] = '\'';

                                    if(character_buffer[0] == '\n') {
                                        temp[1]= '\\';
                                        temp[2] = 'n';
                                        temp[3] = '\'';
                                        temp[4] = '\0';
                                    }else if(character_buffer[0] == '\t') {
                                        temp[1]= '\\';
                                        temp[2] = 't';
                                        temp[3] = '\'';
                                        temp[4] = '\0';
                                    } else if(character_buffer[0] == '\a') {
                                        temp[1]= '\\';
                                        temp[2] = 'a';
                                        temp[3] = '\'';
                                        temp[4] = '\0';
                                    } else if(character_buffer[0] == '\b') {
                                        temp[1]= '\\';
                                        temp[2] = 'b';
                                        temp[3] = '\'';
                                        temp[4] = '\0';
                                    } else if(character_buffer[0] == '\f') {
                                        temp[1]= '\\';
                                        temp[2] = 'f';
                                        temp[3] = '\'';
                                        temp[4] = '\0';
                                    } else if(character_buffer[0] == '\r') {
                                        temp[1]= '\\';
                                        temp[2] = 'r';
                                        temp[3] = '\'';
                                        temp[4] = '\0';
                                    } else if(character_buffer[0] == '\v') {
                                        temp[1]= '\\';
                                        temp[2] = 'v';
                                        temp[3] = '\'';
                                        temp[4] = '\0';
                                    } else if(character_buffer[0] == '\\') {
                                        temp[1]= '\\';
                                        temp[2]= '\\'; 
                                        temp[3]= '\'';
                                        temp[4]= '\0'; 
                                    } else if(character_buffer[0]=='\"'){
                                        temp[1]= '\\';
                                        temp[2]= '\"';
                                        temp[3]= '\'';
                                        temp[4]= '\0';
                                    } else if(character_buffer[0]=='\0'){
                                        temp[1]= '\\';
                                        temp[2]= '0';
                                        temp[3]= '\'';
                                        temp[4]= '\0';
                                    }else{
                                        temp[1]= character_buffer[0];
                                        temp[2]= '\'';
                                        temp[3]= '\0'; 
                                    }
                                    fprintf(tokenout, "<CONST_CHAR, %s> ", character_buffer);
                                    fprintf(logout, "Line no %d: Token <CONST_CHAR> Lexeme %s found --> <CONST_CHAR, %s>\n", line_no, temp, character_buffer);
                                    auto temp1 = symbol_table.get_current_scope_table()->lookup(temp);
                                    if (temp1 == nullptr)
                                        symbol_table.insert(temp, "CONST_CHAR");
                                    else {
                                        std::pair<int, int> temp2 = symbol_table.get_position_of_symbol(temp);
                                        int table_index = symbol_table.get_current_scope_table()->get_id();
                                        printf("< %s : CONST_CHAR > already exists in ScopeTable# %d at position %d, %d\n", temp, table_index, temp2.second, temp2.first);
                                    }

                            }
                            BEGIN(INITIAL);
                        }


<SINGLEQUOTESTATE>\\\'  { character_buffer[character_index++] = '\''; }
<SINGLEQUOTESTATE>\\n   { character_buffer[character_index++] = '\n'; }
<SINGLEQUOTESTATE>\\t   { character_buffer[character_index++] = '\t'; }
<SINGLEQUOTESTATE>\\\\  { character_buffer[character_index++] = '\\'; }
<SINGLEQUOTESTATE>\\a   { character_buffer[character_index++] = '\a'; }
<SINGLEQUOTESTATE>\\f   { character_buffer[character_index++] = '\f'; }
<SINGLEQUOTESTATE>\\r   { character_buffer[character_index++] = '\r'; }
<SINGLEQUOTESTATE>\\b   { character_buffer[character_index++] = '\b'; }
<SINGLEQUOTESTATE>\\v   { character_buffer[character_index++] = '\v'; }
<SINGLEQUOTESTATE>\\0   { character_buffer[character_index++] = '\0'; }
<SINGLEQUOTESTATE>\\\"  { character_buffer[character_index++] = '\"'; }
<SINGLEQUOTESTATE>\n    {  
                            character_buffer[character_index++] = '\0';
                            printf("Error at line no %d: Unterminated character  '%s'\n\n\n", line_no, character_buffer); 
                            error_count += 1; 
                            line_no++;
                            BEGIN(INITIAL);
                        }
<SINGLEQUOTESTATE>.     { 
                            character_buffer[character_index++] = yytext[0];
                        }

\"                      { 
                                string_index = 0;
                                BEGIN(STRINGSTATE);
                            }
<STRINGSTATE>\"         { 
                                string_buffer[string_index] = '\0';
                                fprintf(tokenout, "<STRING, %s> ", string_buffer);
                                fprintf(logout, "Line no %d: Token <STRING> Lexeme \"%s\" found --> <STRING, %s>\n", line_no, string_buffer, string_buffer);
                                BEGIN(INITIAL);
                            }
<STRINGSTATE>\n         {   
                            string_buffer[string_index++] = '\0';
                            printf("Error at line no %d: Unterminated string  %s\n\n\n", line_no, string_buffer); 
                                                    error_count += 1; 
                            line_no++;
                            BEGIN(INITIAL);

}
<STRINGSTATE>\\\n       { line_no++;   
                            }

<STRINGSTATE>\\n        {       string_buffer[string_index++] = '\n'; line_no++; }
<STRINGSTATE>\\t        {       string_buffer[string_index++] = '\t'; }
<STRINGSTATE>\\a        {       string_buffer[string_index++] = '\a'; }
<STRINGSTATE>\\f        {       string_buffer[string_index++] = '\f'; }
<STRINGSTATE>\\r        {       string_buffer[string_index++] = '\r'; }
<STRINGSTATE>\\b        {       string_buffer[string_index++] = '\b'; }
<STRINGSTATE>\\v        {       string_buffer[string_index++] = '\v'; }
<STRINGSTATE>\\0        {       string_buffer[string_index++] = '\0'; }
<STRINGSTATE>\\\"       {       string_buffer[string_index++] = '\"'; }
<STRINGSTATE>\\\\       {       string_buffer[string_index++] = '\\'; }
<STRINGSTATE><<EOF>>    { 
                                printf("Error at line no %d: Unfinished string\n\n\n", line_no); 
                                error_count += 1;
                                return 0;
                            }
<STRINGSTATE>.          { 
                                if (string_index < MAX_STRING_LEN - 1) {
                                    string_buffer[string_index++] = yytext[0];
                                } else {
                                    printf("Error at line no %d: String too long\n\n\n", line_no);
                                    error_count += 1;
                                }
                            }

"//"                    {
                                comment_index = 0; 
                                BEGIN(SINGLECOMMENTSTATE);
                            }
<SINGLECOMMENTSTATE>\\\n       {       comment_buffer[comment_index++] = '\n';line_no++;}
<SINGLECOMMENTSTATE>\\t        {       comment_buffer[comment_index++] = '\t'; }
<SINGLECOMMENTSTATE>\\a        {       comment_buffer[comment_index++] = '\a'; }
<SINGLECOMMENTSTATE>\\f        {       comment_buffer[comment_index++] = '\f'; }
<SINGLECOMMENTSTATE>\\r        {       comment_buffer[comment_index++] = '\r'; }
<SINGLECOMMENTSTATE>\\b        {       comment_buffer[comment_index++] = '\b'; }
<SINGLECOMMENTSTATE>\\v        {       comment_buffer[comment_index++] = '\v'; }
<SINGLECOMMENTSTATE>\\0        {       comment_buffer[comment_index++] = '\0'; }
<SINGLECOMMENTSTATE>\\\"       {       comment_buffer[comment_index++] = '\"'; }
<SINGLECOMMENTSTATE>\\\\       {       comment_buffer[comment_index++] = '\\'; }
<SINGLECOMMENTSTATE>\n {
                                comment_buffer[comment_index] = '\0';
                                fprintf(logout, "Line no %d: Token <COMMENT> Lexeme %s found\n", line_no, comment_buffer);
                                line_no++; 
                                BEGIN(INITIAL);
                            }

<SINGLECOMMENTSTATE>. {
                                if (comment_index < MAX_STRING_LEN - 1) {
                                    comment_buffer[comment_index++] = yytext[0];
                                } else {
                                    printf("Line %d: Comment too long\n\n\n", line_no);
                                    error_count += 1;
                                }
                            }

<SINGLECOMMENTSTATE><<EOF>>     {
                                        printf("Error at line no %d: Unfinished comment\n\n\n", line_no);
                                        error_count += 1;
                                        return 0;
                                    }

"/*"                            { 
                                        comment_index = 0; 
                                        comment_start_line = line_no;
                                        BEGIN(MULTICOMMENTSTATE);
                                    }


<MULTICOMMENTSTATE>\*\/         {
    comment_buffer[comment_index] = '\0';
                                        fprintf(logout, "Line no %d: Token <COMMENT> Lexeme %s found\n", comment_start_line, comment_buffer);
                                        BEGIN(INITIAL);
                                    }
<MULTICOMMENTSTATE>\n          {      comment_buffer[comment_index++] = '\n'; line_no++;
                                            
                                    }
<MULTICOMMENTSTATE>\\\n       { line_no++;   }
<MULTICOMMENTSTATE>\\t        {       comment_buffer[comment_index++] = '\t'; }
<MULTICOMMENTSTATE>\\a        {       comment_buffer[comment_index++] = '\a'; }
<MULTICOMMENTSTATE>\\f        {       comment_buffer[comment_index++] = '\f'; }
<MULTICOMMENTSTATE>\\r        {       comment_buffer[comment_index++] = '\r'; }
<MULTICOMMENTSTATE>\\b        {       comment_buffer[comment_index++] = '\b'; }
<MULTICOMMENTSTATE>\\v        {       comment_buffer[comment_index++] = '\v'; }
<MULTICOMMENTSTATE>\\0        {       comment_buffer[comment_index++] = '\0'; }
<MULTICOMMENTSTATE>\\\"       {       comment_buffer[comment_index++] = '\"'; }
<MULTICOMMENTSTATE>\\\\       {       comment_buffer[comment_index++] = '\\'; }

<MULTICOMMENTSTATE>.            {
                                        if (comment_index < MAX_STRING_LEN - 1) {
                                            comment_buffer[comment_index++] = yytext[0];
                                        } else {
                                            printf("Error at line no %d: Comment too long\n\n\n", line_no);
                                            error_count++;
                                        }
                                    }

<MULTICOMMENTSTATE><<EOF>>      {
                                        printf("Error at line no %d: Unterminated comment %s \n\n\n", comment_start_line, comment_buffer);
                                        error_count++;
                                        return 0;
                                    }



"if"          { 
                    fprintf(tokenout, "<IF> ");
                    fprintf(logout, "Line no %d: Token <IF> Lexeme %s found\n\n", line_no, yytext);
                }

"for"         { 
                    fprintf(tokenout, "<FOR> ");
                    fprintf(logout, "Line no %d: Token <FOR> Lexeme %s found\n\n", line_no, yytext);
                }

"do"          { 
                    fprintf(tokenout, "<DO> ");
                    fprintf(logout, "Line no %d: Token <DO> Lexeme %s found\n\n", line_no, yytext);
                }

"while"       { 
                    fprintf(tokenout, "<WHILE> ");
                    fprintf(logout, "Line no %d: Token <WHILE> Lexeme %s found\n\n", line_no, yytext);
                }

"else"        { 
                    fprintf(tokenout, "<ELSE> ");
                    fprintf(logout, "Line no %d: Token <ELSE> Lexeme %s found\n\n", line_no, yytext);
                }

"return"      { 
                    fprintf(tokenout, "<RETURN> ");
                    fprintf(logout, "Line no %d: Token <RETURN> Lexeme %s found\n\n", line_no, yytext);
                }   
"break"       { 
                    fprintf(tokenout, "<BREAK> ");
                    fprintf(logout, "Line no %d: Token <BREAK> Lexeme %s found\n\n", line_no, yytext);
                }

"continue"    { 
                    fprintf(tokenout, "<CONTINUE> ");
                    fprintf(logout, "Line no %d: Token <CONTINUE> Lexeme %s found\n\n", line_no, yytext);
                }

"void"        { 
                    fprintf(tokenout, "<VOID> ");
                    fprintf(logout, "Line no %d: Token <VOID> Lexeme %s found\n\n", line_no, yytext);
                }

"switch"      {
                    fprintf(tokenout, "<SWITCH> ");
                    fprintf(logout, "Line no %d: Token <SWITCH> Lexeme %s found\n\n", line_no, yytext);
                }

"case"        { 
                    fprintf(tokenout, "<CASE> ");
                    fprintf(logout, "Line no %d: Token <CASE> Lexeme %s found\n\n", line_no, yytext);
                }

"default"     { 
                    fprintf(tokenout, "<DEFAULT> ");
                    fprintf(logout, "Line no %d: Token <DEFAULT> Lexeme %s found\n\n", line_no, yytext);
                }

"static"      {
                    fprintf(tokenout, "<STATIC> ");
                    fprintf(logout, "Line no %d: Token <STATIC> Lexeme %s found\n\n", line_no, yytext);
                }

"const"       { 
                    fprintf(tokenout, "<CONST> ");
                    fprintf(logout, "Line no %d: Token <CONST> Lexeme %s found\n\n", line_no, yytext);
                }

"volatile"    { 
                    fprintf(tokenout, "<VOLATILE> ");
                    fprintf(logout, "Line no %d: Token <VOLATILE> Lexeme %s found\n\n", line_no, yytext);
                }

"int"         { 
                    fprintf(tokenout, "<INT> ");
                    fprintf(logout, "Line no %d: Token <INT> Lexeme %s found\n\n", line_no, yytext);
                }

"char"        { 
                    fprintf(tokenout, "<CHAR> ");
                    fprintf(logout, "Line no %d: Token <CHAR> Lexeme %s found\n\n", line_no, yytext);
                }

"float"       {
                    fprintf(tokenout, "<FLOAT> ");
                    fprintf(logout, "Line no %d: Token <FLOAT> Lexeme %s found\n\n", line_no, yytext);
                }

"double"      { 
                    fprintf(tokenout, "<DOUBLE> ");
                    fprintf(logout, "Line no %d: Token <DOUBLE> Lexeme %s found\n\n", line_no, yytext);
                }

"short"       {
                    fprintf(tokenout, "<SHORT> ");
                    fprintf(logout, "Line no %d: Token <SHORT> Lexeme %s found\n\n", line_no, yytext);
                }

"long"        { 
                    fprintf(tokenout, "<LONG> ");
                    fprintf(logout, "Line no %d: Token <LONG> Lexeme %s found\n\n", line_no, yytext);
                }

"unsigned"    { 
                    fprintf(tokenout, "<UNSIGNED> ");
                    fprintf(logout, "Line no %d: Token <UNSIGNED> Lexeme %s found\n\n", line_no, yytext);
                }

"goto"        {
                    fprintf(tokenout, "<GOTO> ");
                    fprintf(logout, "Line no %d: Token <GOTO> Lexeme %s found\n\n", line_no, yytext);
                }


"+"           |
"-"           { 
                    fprintf(tokenout, "<ADDOP, %s> ", yytext);
                    fprintf(logout, "Line no %d: Token <ADDOP> Lexeme %s found\n\n", line_no, yytext);
                }
"*"           |
"/"           |
"%"           { 
                    fprintf(tokenout, "<MULOP, %s> ", yytext);
                    fprintf(logout, "Line no %d: Token <MULOP> Lexeme %s found\n\n", line_no, yytext);
                }
"++"          |
"--"          { 
                    fprintf(tokenout, "<INCOP, %s> ", yytext);
                    fprintf(logout, "Line no %d: Token <INCOP> Lexeme %s found\n\n", line_no, yytext);
                }
"<="          |
">="          |
"=="          |
"!="          |
"<"           |
">"           {
                    fprintf(tokenout, "<RELOP, %s> ", yytext);
                    fprintf(logout, "Line no %d: Token <RELOP> Lexeme %s found\n\n", line_no, yytext);
                }

"="           { 
                    fprintf(tokenout, "<ASSIGNOP, => ");
                    fprintf(logout, "Line no %d: Token <ASSIGNOP> Lexeme %s found\n\n", line_no, yytext);
                }

"&&"          |
"||"          { 
                    fprintf(tokenout, "<LOGICOP, %s> ", yytext);
                    fprintf(logout, "Line no %d: Token <LOGICOP> Lexeme %s found\n\n", line_no, yytext);
                }
"!"           {
                    fprintf(tokenout, "<NOT, !> ");
                    fprintf(logout, "Line no %d: Token <NOT> Lexeme %s found\n\n", line_no, yytext);
                }

"("           {     
                    fprintf(tokenout, "<LPAREN, (> ");
                    fprintf(logout, "Line no %d: Token <LPAREN> Lexeme %s found\n\n", line_no, yytext);
                }

")"           {     
                    fprintf(tokenout, "<RPAREN, )> ");
                    fprintf(logout, "Line no %d: Token <RPAREN> Lexeme %s found\n\n", line_no, yytext);
                }
"{"           {     
                    fprintf(tokenout, "<LCURL, {> ");
                    fprintf(logout, "Line no %d: Token <LCURL> Lexeme %s found\n\n", line_no, yytext);
                    if(!symbol_table.enter_scope())
                    {
                        printf("Error at line no %d: Error entering scope\n\n\n", line_no);
                        error_count += 1;
                    }
                }

"}"           {     
                    fprintf(tokenout, "<RCURL, }> ");
                    fprintf(logout, "Line no %d: Token <RCURL> Lexeme %s found\n\n", line_no, yytext);
                    if(!symbol_table.exit_scope())
                    {
                        printf("Error at line no %d: Error exiting scope\n\n\n", line_no);
                        error_count += 1;
                    }
                }
"["           {     
                    fprintf(tokenout, "<LTHIRD, [> ");
                    fprintf(logout, "Line no %d: Token <LTHIRD> Lexeme %s found\n\n", line_no, yytext);
                }
"]"           {     
                    fprintf(tokenout, "<RTHIRD, ]> ");
                    fprintf(logout, "Line no %d: Token <RTHIRD> Lexeme %s found\n\n", line_no, yytext);
                }
","           {     
                    fprintf(tokenout, "<COMMA, ,> ");
                    fprintf(logout, "Line no %d: Token <COMMA> Lexeme %s found\n\n", line_no, yytext);
                }
";"           {     
                    fprintf(tokenout, "<SEMICOLON, ;> ");
                    fprintf(logout, "Line no %d: Token <SEMICOLON> Lexeme %s found\n\n", line_no, yytext);
                }

{IDENTIFIER}                              { 
                                                    fprintf(tokenout, "<ID, %s> ", yytext);
                                                    fprintf(logout, "Line no %d: Token <ID> Lexeme %s found\n\n", line_no, yytext);

                                                    auto temp1 = symbol_table.get_current_scope_table()->lookup(yytext);
                                                    if (temp1 == nullptr)
                                                        symbol_table.insert(yytext, "ID");
                                                    else {
                                                        std::pair<int, int> temp = symbol_table.get_position_of_symbol(yytext);
                                                        int table_index = symbol_table.get_current_scope_table()->get_id();
                                                        printf("< %s : ID > already exists in ScopeTable# %d at position %d, %d\n", yytext, table_index, temp.second, temp.first);
                                                    }
                                                }

{DIGIT}+                           {
                                                    const char *num = yytext;
                                                    if (num[0]=='+' || num[0]=='-') num++;
                                                    fprintf(tokenout, "<CONST_INT, %s> ", num);
                                                    fprintf(logout,  "Line no %d: Token <CONST_INT> Lexeme %s found\n\n",
                                                                    line_no, num);
                                                    auto temp1 = symbol_table.get_current_scope_table()->lookup(num);
                                                    if (temp1 == nullptr)
                                                        symbol_table.insert(num, "CONST_INT");
                                                    else {
                                                        std::pair<int, int> temp = symbol_table.get_position_of_symbol(num);
                                                        int table_index = symbol_table.get_current_scope_table()->get_id();
                                                        printf("< %s : CONST_INT > already exists in ScopeTable# %d at position %d, %d\n", num, table_index, temp.second, temp.first);
                                                    }
                                                }

{DIGIT}+[.]?{DIGIT}+{EXP}?          {
                                                    const char *num = yytext;
                                                    if (num[0]=='+' || num[0]=='-') num++;
                                                    fprintf(tokenout, "<CONST_FLOAT, %s> ", num);
                                                    fprintf(logout,  "Line no %d: Token <CONST_FLOAT> Lexeme %s found\n\n",
                                                                    line_no, num);
                                                    auto temp1 = symbol_table.get_current_scope_table()->lookup(num);
                                                    if (temp1 == nullptr)
                                                        symbol_table.insert(num, "CONST_FLOAT");
                                                    else {
                                                        std::pair<int, int> temp = symbol_table.get_position_of_symbol(num);
                                                        int table_index = symbol_table.get_current_scope_table()->get_id();
                                                        printf("< %s : CONST_FLOAT > already exists in ScopeTable# %d at position %d, %d\n", yytext, table_index, temp.second, temp.first);
                                                    }
                                                }


[0-9]+\.[0-9]*\.[0-9.]*                     {       printf("Error at line no %d: Too many decimal points %s\n\n\n", line_no, yytext); 
                                                    error_count += 1; 
                                                }

([0-9]+|[0-9]*\.[0-9]+)[eE][+-]*[0-9\.]+   {       printf("Error at line no %d: Ill formed number %s\n\n\n", line_no, yytext); 
                                                    error_count += 1; 
                                                }
[0-9]+[a-zA-Z_]+[0-9a-zA-Z_]*                 {       printf("Error at line no %d: Invalid prefix on ID or invalid suffix on Number %s\n\n\n", line_no, yytext); 
                                                    error_count += 1; 
                                                }


{WHITESPACE}+               { }
.                      { 
                                                    printf("Error at line no %d: Unrecognized character %c\n\n\n", line_no, yytext[0]); 
                                                    error_count += 1; 
                                                }     

%%

int main(int argc,char *argv[]){
	
	if(argc!=2){
		printf("Please provide input file name and try again\n");
		return 0;
	}
	
	FILE *fin=fopen(argv[1],"r");
	if(fin==NULL){
		printf("Cannot open specified file\n");
		return 0;
	}
	
	logout= freopen("2105028_log.txt","w", stdout);
	tokenout= fopen("2105028_token.txt","w");

	yyin= fin;
	yylex();
	fclose(yyin);

    symbol_table.print_all_scopes();
    printf("Total lines: %d\n", line_no);
    printf("Total errors: %d\n", error_count);
	fclose(tokenout);
	fclose(logout);
	return 0;
}
