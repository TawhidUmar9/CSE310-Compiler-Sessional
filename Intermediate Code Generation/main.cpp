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
ofstream optimizedAssemblyFile;

int syntaxErrorCount;
symbol_table st(7, 0);
string var_type = "";
string return_type = "void";
bool in_print = false;
string data_type = "";
bool code_segment_started = false;
int current_local_offset = 0;
int label_count = 0;
vector<int> label_stack;
stringstream code_buffer;
bool buffer_mode = false;
bool is_unreachable = false;

string trim(const string &str)
{
    const string whitespace = " \t";
    const auto str_begin = str.find_first_not_of(whitespace);
    if (str_begin == string::npos)
        return "";

    const auto str_end = str.find_last_not_of(whitespace);
    const auto str_range = str_end - str_begin + 1;

    return str.substr(str_begin, str_range);
}

void optimizer()
{
    ifstream asmFile("code.asm");
    vector<string> code_lines;
    string line;
    while (getline(asmFile, line))
    {
        code_lines.push_back(line);
    }
    asmFile.close();

    ofstream optimizedAssemblyFile("optimized_code.asm");
    size_t i = 0;
    while (i < code_lines.size())
    {
        if (i + 1 < code_lines.size())
        {
            string line1 = trim(code_lines[i]);
            string line2 = trim(code_lines[i + 1]);
            // (2)
            if (line1.rfind("PUSH ", 0) == 0 && line2.rfind("POP ", 0) == 0)
            {
                string reg1 = line1.substr(5);
                string reg2 = line2.substr(4);
                if (reg1 == reg2)
                {
                    i += 2;
                    continue;
                }
            }

            // (3)
            if (line1.rfind("MOV AX, ", 0) == 0 && line2.rfind(", AX", line2.length() - 4) != string::npos)
            {
                string var1 = line1.substr(8);
                string mov_part = line2.substr(0, line2.length() - 4);
                if (mov_part.rfind("MOV ", 0) == 0)
                {
                    string var2 = mov_part.substr(4);
                    if (var1 == var2)
                    {
                        i += 2;
                        continue;
                    }
                }
            }
            // (4)
            if (line1.rfind("MOV ", 0) == 0 && line2.rfind("MOV ", 0) == 0)
            {
                stringstream ss1(line1);
                string op1, reg1, comma1, val1;
                ss1 >> op1 >> reg1 >> comma1 >> val1;

                stringstream ss2(line2);
                string op2, reg2, comma2, val2;
                ss2 >> op2 >> reg2 >> comma2 >> val2;

                if (op1 == "MOV" && op2 == "MOV" && reg1 == reg2)
                {
                    i += 1; // Skip the first MOV, keep the second
                    continue;
                }
            }
        }
        string current_line = trim(code_lines[i]);

        // (3)
        if (current_line.rfind("ADD ", 0) == 0 && current_line.rfind(", 0", current_line.length() - 3) != string::npos)
        {
            i++;
            continue;
        }

        // (3)
        if (current_line.rfind("IMUL ", 0) == 0 || current_line.rfind("MUL ", 0) == 0)
        {
            stringstream ss(current_line);
            string op, reg, comma, val;
            ss >> op >> reg >> comma >> val;
            if (val == "1")
            {
                i++;
                continue;
            }
        }
        optimizedAssemblyFile << code_lines[i] << endl;
        i++;
    }

    optimizedAssemblyFile.close();
}

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

    assemblyFile.open("code.asm");
    assemblyFile << ".MODEL SMALL\n.STACK 100H\n.DATA\n";
    assemblyFile << "\tCR EQU 0DH\n\tLF EQU 0AH\n";
    assemblyFile << "number DB 6 DUP('$') ;\n";
    parserLogFile.open(parserLogFileName);
    optimizedAssemblyFile.open("optimized_code.asm");

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
    optimizer();
    cout << "Parsing completed. Check the output files for details." << endl;
    return 0;
}