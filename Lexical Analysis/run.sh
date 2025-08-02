target_file=$1
flex 2105028.l
g++ -o 2105028.out lex.yy.c
./2105028.out $target_file
rm lex.yy.c
rm 2105028.out