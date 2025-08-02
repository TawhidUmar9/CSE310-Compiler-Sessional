antlr4 -Dlanguage=Cpp C2105028Lexer.g4
antlr4 -Dlanguage=Cpp C2105028Parser.g4
g++ -std=c++17 -w -I/usr/include/antlr4-runtime -c C2105028Lexer.cpp C2105028Parser.cpp Ctester.cpp
g++ -std=c++17 -w C2105028Lexer.o C2105028Parser.o Ctester.o -L/usr/lib64 -lantlr4-runtime -o Ctester.out -pthread
# LD_LIBRARY_PATH=/usr/local/lib
./Ctester.out $1

shopt -s extglob

# Loop through all files that do NOT match *.sh, *.g4, or Ctester.cpp
for file in !(*.sh|*.g4|Ctester.cpp|*.hpp); do
    # Only delete if it's a regular file
    if [[ -f "$file" ]]; then
        rm -f "$file"
    fi
done

# Remove the 'output' directory if it exists
# rm -rf output
