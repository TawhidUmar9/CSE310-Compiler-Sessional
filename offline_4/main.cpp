#pragma once
#include <iostream>
#include <fstream>
#include <string>
#include "antlr4-runtime.h"
#include "2105028_symbol_table.hpp"
#include "C2105028Lexer.h"
#include "C2105028Parser.h"

using namespace antlr4;
using namespace std;

ofstream parserLogFile; // global output stream
ofstream errorFile;     // global error stream
ofstream lexLogFile;    // global lexer log stream
ofstream assemblyFile;

int syntaxErrorCount;
symbol_table st(7, 0);
std::string var_type = "";
std::string return_type = "void";
bool in_print = false;
std::string data_type = "";
bool code_segment_started = false;
int current_local_offset = 0;
int label_count = 0;

int main(int argc, const char *argv[])
{
    if (argc < 2)
    {
        cerr << "Usage: " << argv[0] << " <input_file>" << endl;
        return 1;
    }

    // ---- Input File ----
    ifstream inputFile(argv[1]);
    if (!inputFile.is_open())
    {
        cerr << "Error opening input file: " << argv[1] << endl;
        return 1;
    }

    string outputDirectory = "output/";
    string parserLogFileName = outputDirectory + "parserLog.txt";
    string errorFileName = outputDirectory + "errorLog.txt";
    string lexLogFileName = outputDirectory + "lexerLog.txt";

    // create output directory if it doesn't exist
    system(("mkdir -p " + outputDirectory).c_str());

    // ---- Output Files ----
    assemblyFile.open("code.asm");
    assemblyFile << ".MODEL SMALL\n.STACK 100H\n.DATA\n";
    assemblyFile << "\tCR EQU 0DH\n\tLF EQU 0AH\n";
    parserLogFile.open(parserLogFileName);
    if (!parserLogFile.is_open())
    {
        cerr << "Error opening parser log file: " << parserLogFileName << endl;
        return 1;
    }

    errorFile.open(errorFileName);
    if (!errorFile.is_open())
    {
        cerr << "Error opening error log file: " << errorFileName << endl;
        return 1;
    }

    lexLogFile.open(lexLogFileName);
    if (!lexLogFile.is_open())
    {
        cerr << "Error opening lexer log file: " << lexLogFileName << endl;
        return 1;
    }

    // ---- Parsing Flow ----
    ANTLRInputStream input(inputFile);
    C2105028Lexer lexer(&input);
    CommonTokenStream tokens(&lexer);
    C2105028Parser parser(&tokens);

    // this is necessary to avoid the default error listener and use our custom error handling
    parser.removeErrorListeners();

    // start parsing at the 'start' rule
    parser.start();
    cout << "Parsing completed. Check the output files for details." << endl;
    return 0;
}