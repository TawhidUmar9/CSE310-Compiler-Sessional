#!/bin/bash

file=$1

if [[ $file == "input" ]]; then
    echo "Compiling input generator"
    g++ -o input_generator.exe 2105028_input_generator.cpp

elif [[ $file == "report" ]]; then 
    echo "Compiling report generator"
    g++ -o report_generator.exe 2105028_report_generator.cpp

elif [[ $file == "main" ]]; then
    echo "Compiling main program"
    g++ -o main.exe 2105028_main.cpp -fsanitize=address

elif [[ $file == "all" ]]; then
    echo "Compiling all files"
    g++ -o input_generator.exe 2105028_input_generator.cpp
    g++ -o report_generator.exe 2105028_report_generator.cpp
    g++ -o main.exe 2105028_main.cpp -fsanitize=address

elif [[ $file == "clean" ]]; then
    echo "Cleaning up"
    rm -f input_generator report_generator main

else
    echo "Invalid argument. Use 'input', 'report', 'main', 'all', or 'clean'."
fi
