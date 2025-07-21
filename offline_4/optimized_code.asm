.MODEL SMALL
.STACK 100H
.DATA
	CR EQU 0DH
	LF EQU 0AH
number DB 6 DUP('$') ;
    i DW ?
    j DW ?
.CODE

main PROC
    MOV AX, @DATA
    MOV DS, AX
    PUSH BP
    MOV BP, SP
    SUB SP, 2 ; k at [BP-2]
    SUB SP, 2 ; ll at [BP-4]
    SUB SP, 2 ; m at [BP-6]
    SUB SP, 2 ; n at [BP-8]
    SUB SP, 2 ; o at [BP-10]
    SUB SP, 2 ; p at [BP-12]
    MOV AX, 1
    MOV i, AX
    MOV AX, i
    CALL print_output
    CALL new_line
    MOV AX, 5
    PUSH AX
    MOV AX, 8
    POP BX
    ADD AX, BX
    MOV j, AX
    MOV AX, j
    CALL print_output
    CALL new_line
    MOV AX, i
    PUSH AX
    MOV AX, 2
    PUSH AX
    MOV AX, j
    POP BX
    IMUL BX
    POP BX
    ADD AX, BX
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 9
    POP BX
    XCHG AX, BX
    CWD
    IDIV BX
    MOV AX, DX
    MOV WORD PTR [BP-6], AX
    MOV AX, WORD PTR [BP-6]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-6]
    PUSH AX
    MOV AX, WORD PTR [BP-4]
    POP BX
    CMP BX, AX
    JLE L0
    MOV AX, 0
    JMP L1
L0:
    MOV AX, 1
L1:
    MOV WORD PTR [BP-8], AX
    MOV AX, WORD PTR [BP-8]
    CALL print_output
    CALL new_line
    MOV AX, i
    PUSH AX
    MOV AX, j
    POP BX
    CMP BX, AX
    JNE L2
    MOV AX, 0
    JMP L3
L2:
    MOV AX, 1
L3:
    MOV WORD PTR [BP-10], AX
    MOV AX, WORD PTR [BP-10]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-8]
    PUSH AX
    MOV AX, WORD PTR [BP-10]
    POP BX
    OR AX, BX
    MOV WORD PTR [BP-12], AX
    MOV AX, WORD PTR [BP-12]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-8]
    PUSH AX
    MOV AX, WORD PTR [BP-10]
    POP BX
    AND AX, BX
    MOV WORD PTR [BP-12], AX
    MOV AX, WORD PTR [BP-12]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-12]
    PUSH AX
    INC AX
    MOV WORD PTR [BP-12], AX
    POP AX
    MOV AX, WORD PTR [BP-12]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-12]
    NEG AX
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    CALL print_output
    CALL new_line
    MOV AX, 0
    MOV SP, BP
    POP BP
    MOV AH, 4CH
    INT 21H
main ENDP
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

END MAIN
