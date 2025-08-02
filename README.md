# 🛠️ CSE310 Compiler

A full compiler for a subset of the C programming language, built as part of the **CSE310: Compiler Design** course. This project implements the complete compilation pipeline—from tokenizing to code generation—with a strong focus on **educational clarity** and **modular design**.

---

## ✨ Features

- **Symbol Table** implemented in modern C++
- **Lexical Analysis** using Flex (yyFlex)
- **Parsing** with ANTLR4 (C++ implementation)
- **Intermediate Code Generation (ICG)** using ANTLR visitors
- **Final Code Output** in x86 Assembly
- Supports generation of `.asm` files executable in emulators (e.g., **EMU8086**, **DOSBox**)


## 🚀 Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/cse310-compiler.git
   cd cse310-compiler

    Set up ANTLR4 (C++ target):

        Follow this guide to install ANTLR4 in a virtual environment.

    Activate the virtual environment:

source antlr-venv/bin/activate

Put your C-like source code inside ICG/input/ folder, for example:

ICG/input/input.txt

Run the compiler:

./run-script.sh input/input.txt

Check output:
The generated x86 Assembly will be located at:

ICG optimized_code.asm
---

🧪 Example
✅ Input (C-like Source Code)

int main() {
    int a = 5;
    int b = 3;
    int c = a + b;
    println(c);
}

🧾 Output (.asm - x86 Assembly)
```
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
    lea si, number
    mov bx, 10
    add si, 4
    cmp ax, 0
    jnge negate
print:
    xor dx, dx
    div bx
    mov [si], dl
    add [si], '0'
    dec si
    cmp ax, 0
    jne print
    inc si
    lea dx, si
    mov ah, 9
    int 21h
    pop si
    pop dx
    pop cx
    pop bx
    pop ax
    ret
negate:
    push ax
    mov ah, 2
    mov dl, '-'
    int 21h
    pop ax
    neg ax
    jmp print
print_output endp

new_line proc
    push ax
    push dx
    mov ah, 2
    mov dl, 0Dh
    int 21h
    mov ah, 2
    mov dl, 0Ah
    int 21h
    pop dx
    pop ax
    ret
new_line endp

END main
```
📚 What I Learned

Implementing a multi-stage compiler with real tools (Flex, ANTLR, x86)
- Designing grammar rules and resolving ambiguities
- Managing memory and scopes using custom symbol tables
- Generating working 8086 assembly code for real emulators
- Writing automation scripts to streamline the toolchain

🙏 Acknowledgements

This project would not have been possible without the knowledge, support, and guidance of the following individuals and communities:

- Nafis Tahmid, Ahmed Rumi, and Anwarul Bashir Shuaib — for their outstanding mentorship, lectures, and continued encouragement throughout the CSE310: Compiler Design course.
- The creators of Flex (Fast Lexical Analyzer Generator) — for providing an efficient tool to build our lexer with minimal hassle.
- The ANTLR (Another Tool for Language Recognition) community — for making grammar-driven parsing intuitive, powerful, and accessible.
- To all of my peers who offered feedback, testing support, and discussions during development.
- I'm sincerely grateful for your contributions to this learning journey.
