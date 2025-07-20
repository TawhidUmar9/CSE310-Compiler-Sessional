.MODEL SMALL
.STACK 100H
.DATA
	CR EQU 0DH
	LF EQU 0AH
number DB 6 DUP('$') ;
    a DW ?
    b DW ?
    c DW ?
.CODE

func_a PROC
    PUSH BP
    MOV BP, SP
    MOV AX, 7
    MOV a, AX
    MOV SP, BP
    POP BP
    RET
func_a ENDP

foo PROC
    PUSH BP
    MOV BP, SP
    MOV AX, WORD PTR [BP+4]
    PUSH AX
    MOV AX, 3
    POP BX
    ADD AX, BX ; AX = RHS + LHS
    MOV WORD PTR [BP+4], AX
    MOV AX, WORD PTR [BP+4]
    MOV SP, BP
    POP BP
    RET
foo ENDP

bar PROC
    PUSH BP
    MOV BP, SP
    MOV AX, 4
    PUSH AX
    MOV AX, WORD PTR [BP+4]
    POP BX
    IMUL BX  ; AX = AX * BX
    PUSH AX
    MOV AX, 2
    PUSH AX
    MOV AX, WORD PTR [BP+6]
    POP BX
    IMUL BX  ; AX = AX * BX
    POP BX
    ADD AX, BX ; AX = RHS + LHS
    MOV c, AX
    MOV AX, c
    MOV SP, BP
    POP BP
    RET
bar ENDP

main PROC
    MOV AX, @DATA
    MOV DS, AX
    PUSH BP
    MOV BP, SP
    SUB SP, 2 ; i at [BP-2]
    SUB SP, 2 ; j at [BP-4]
    SUB SP, 2 ; k at [BP-6]
    SUB SP, 2 ; l at [BP-8]
    MOV AX, 5
    MOV WORD PTR [BP-2], AX
    MOV AX, 6
    MOV WORD PTR [BP-4], AX
    CALL func_a
    MOV AX, a
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    CALL foo
    ADD SP, 2
    MOV WORD PTR [BP-6], AX
    MOV AX, WORD PTR [BP-6]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-2]
    MOV AX, WORD PTR [BP-4]
    PUSH AX
    PUSH AX
    CALL bar
    ADD SP, 4
    MOV WORD PTR [BP-8], AX
    MOV AX, WORD PTR [BP-8]
    CALL print_output
    CALL new_line
    MOV AX, 6
    PUSH AX
    MOV AX, WORD PTR [BP-4]
    PUSH AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    CALL bar
    ADD SP, 4
    POP BX
    IMUL BX  ; AX = AX * BX
    PUSH AX
    MOV AX, 2
    POP BX
    ADD AX, BX ; AX = RHS + LHS
    PUSH AX
    MOV AX, 3
    PUSH AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    CALL foo
    ADD SP, 2
    POP BX
    IMUL BX  ; AX = AX * BX
    POP BX
    SUB BX, AX
    MOV AX, BX ; Move result back to AX
    MOV WORD PTR [BP-4], AX
    MOV AX, WORD PTR [BP-4]
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
