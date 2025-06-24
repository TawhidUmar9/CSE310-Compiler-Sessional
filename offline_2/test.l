%option noyywrap
%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <string>

#include <stack>
using namespace std;

int operator_count = 0;
int operand_count = 0;
char function_name[100];

int fparen_count = 0;
int cparen_count = 0;
int tparen_count = 0;

stack<string> s;

int current_state = 0;
int prev_state = 0;
%}

WHITESPACE [ \t\n]



%%




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
	

	yyin= fin;
	yylex();
	fclose(yyin);

    printf("Total operators: %d\n", operator_count);
    printf("Total operands: %d\n", operand_count);
	return 0;
}
