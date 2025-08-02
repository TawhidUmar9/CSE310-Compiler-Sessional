🛠️ CSE310 Compiler

A full compiler for a subset of the C programming language, built as part of the CSE310: Compiler Design course. This project implements the complete compilation pipeline—from tokenizing to code generation—with a strong focus on educational clarity and modular design.
Features

    Symbol Table implemented in modern C++
    Lexical Analysis using Flex (yyFlex)
    Parsing with ANTLR4 (C++ implementation)
    Intermediate Code Generation (ICG) using ANTLR visitors
    Final Code Output in x86 Assembly
    Supports generation of .asm files executable in emulators (e.g. EMU8086 / DOSBox)

Compilation Pipeline

Input C (subset)
     │
     ▼
Lexical Analyzer (Flex)
     │
     ▼
   Tokens
     │
     ▼
Parser (ANTLR4)
     │
     ▼
Abstract Syntax Tree (AST)
     │
     ▼
Symbol Table Construction (C++)
     │
     ▼
Semantic Analysis & ICG (ANTLR Visitor)
     │
     ▼
x86 Assembly Output (.asm)

Getting Started

    Clone the repository
    Put your input file inside ICG/input
    Follow this pdf to setup antlr4 inside a venv
    Activate the venv
    Compile using

bash run-script.sh input/input.c

    The output assembly is in ICG/output/code.asm
    This assembly can be run in EMU8086
    Clear the output and other files using

bash clean-script.sh

Example

Here's a simple C-like program compiled using our compiler:
Input (C-like Source Code)

int main() {
    int a = 5;
    int b = 3;
    int c = a + b;
    println(c);
}

Output (x8086 Assembly)

.MODEL SMALL
.STACK 1000H
.DATA
    number DB "00000$"
.CODE
main PROC
    MOV AX, @DATA
    MOV DS, AX
    PUSH BP
    MOV BP, SP
    SUB SP, 2
    MOV AX, 5
    PUSH AX
    SUB SP, 2
    MOV AX, 3
    PUSH AX
    SUB SP, 2
    MOV AX, [BP - 2]
    PUSH AX
    MOV AX, [BP - 4]
    PUSH AX
    POP BX
    POP AX
    ADD AX, BX
    PUSH AX
    MOV AX, [BP - 6]
    CALL print_output
    CALL new_line
:
    ADD SP, 6
    POP BP
    MOV AX, 4CH
    INT 21H
main ENDP

print_output proc
    push ax
    push bx
    push cx
    push dx
    push si
    lea si,number
    mov bx,10
    add si,4
    cmp ax,0
    jnge negate
print:
    xor dx,dx
    div bx
    mov [si],dl
    add [si],'0'
    dec si
    cmp ax,0
    jne print
    inc si
    lea dx,si
    mov ah,9
    int 21h
    pop si
    pop dx
    pop cx
    pop bx
    pop ax
    ret
negate:
    push ax
    mov ah,2
    mov dl,'-'
    int 21h
    pop ax
    neg ax
    jmp print
print_output endp

new_line proc
    push ax
    push dx
    mov ah,2
    mov dl,0Dh
    int 21h
    mov ah,2
    mov dl,0Ah
    int 21h
    pop dx
    pop ax
    ret
new_line endp

END main

What I Learned

    Implementing a multi-stage compiler with real tools (Flex, ANTLR, x86)
    Designing grammar rules and resolving ambiguities
    Managing memory and scopes using custom symbol tables
    Generating working 8086 assembly code for real emulators
    Writing automation scripts to streamline the toolchain

Acknowledgements

This project would not have been possible without the knowledge, support, and guidance of the following individuals and communities:

    Nafis Tahmid, Ahmed Rumi, and Anwarul Bashir Shuaib — for their outstanding mentorship, lectures, and continued encouragement throughout the CSE310: Compiler Design course.
    The creators of Flex (Fast Lexical Analyzer Generator) — for providing an efficient tool to build our lexer with minimal hassle.
    The ANTLR (Another Tool for Language Recognition) community — for making grammar-driven parsing intuitive, powerful, and accessible.
    To all of my peers who offered feedback, testing support, and discussions during development.

I'm sincerely grateful for your contributions to this learning journey.
