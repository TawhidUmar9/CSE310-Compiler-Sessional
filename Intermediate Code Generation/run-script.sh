antlr4 -Dlanguage=Cpp C2105028Lexer.g4
antlr4 -Dlanguage=Cpp C2105028Parser.g4
g++ -std=c++17 -w -I/usr/include/antlr4-runtime -c C2105028Lexer.cpp C2105028Parser.cpp main.cpp
g++ -std=c++17 -w C2105028Lexer.o C2105028Parser.o main.o -L/usr/lib64 -lantlr4-runtime -o main.out -pthread
# LD_LIBRARY_PATH=/usr/local/lib
./main.out $1

shopt -s extglob

# Loop through all files that do NOT match *.sh, *.g4, or main.cpp
for file in !(*.sh|*.g4|main.cpp|*.hpp|*.asm); do
    # Only delete if it's a regular file
    if [[ -f "$file" ]]; then
        rm -f "$file"
    fi
done

# Remove the 'output' directory if it exists
# rm -rf output