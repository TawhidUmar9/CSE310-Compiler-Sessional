target_file=$1
flex test.l
g++ lex.yy.c
./a.out $target_file
rm lex.yy.c
rm a.out