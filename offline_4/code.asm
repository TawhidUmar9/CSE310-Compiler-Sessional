.MODEL SMALL
.STACK 100H
.DATA
	CR EQU 0DH
	LF EQU 0AH
number DB 6 DUP('$') ;
    w DW 10 DUP(?)
.CODE

main PROC
    MOV AX, @DATA
    MOV DS, AX
    PUSH BP
    MOV BP, SP
    SUB SP, 2 ; i at [BP-2]
    SUB SP, 20 ; Array x starts at [BP-22]
    MOV AX, 0
    PUSH AX
    MOV AX, 2
    NEG AX
    POP BX
    SHL BX, 1
    LEA SI, w
    ADD SI, BX
    MOV [SI], AX
    MOV AX, 0
    PUSH AX
    MOV AX, 0
    MOV BX, AX
    SHL BX, 1
    LEA SI, w
    ADD SI, BX
    MOV AX, [SI]
    POP BX
    SHL BX, 1
    LEA SI, [BP-22]
    ADD SI, BX
    MOV [SI], AX
    MOV AX, 0
    MOV BX, AX
    SHL BX, 1
    LEA SI, [BP-22]
    ADD SI, BX
    MOV AX, [SI]
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    CALL print_output
    CALL new_line
    MOV AX, 1
    PUSH AX
    MOV AX, 0
    MOV BX, AX
    SHL BX, 1
    LEA SI, w
    ADD SI, BX
    MOV AX, [SI]
    PUSH AX
    INC WORD PTR [SI]
    POP AX
    POP BX
    SHL BX, 1
    LEA SI, [BP-22]
    ADD SI, BX
    MOV [SI], AX
    MOV AX, 1
    MOV BX, AX
    SHL BX, 1
    LEA SI, [BP-22]
    ADD SI, BX
    MOV AX, [SI]
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    CALL print_output
    CALL new_line
    MOV AX, 0
    MOV BX, AX
    SHL BX, 1
    LEA SI, w
    ADD SI, BX
    MOV AX, [SI]
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 0
    POP BX
    ADD AX, BX
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 0
    POP BX
    SUB BX, AX
    MOV AX, BX
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 1
    POP BX
    IMUL BX
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 0
    POP BX
    CMP BX, AX
    JG L0
    MOV AX, 0
    JMP L1
L0:
    MOV AX, 1
L1:
    CMP AX, 1
    JE L2
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 10
    POP BX
    CMP BX, AX
    JL L3
    MOV AX, 0
    JMP L4
L3:
    MOV AX, 1
L4:
L2:
    CMP AX, 1
    JE L5
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 0
    POP BX
    CMP BX, AX
    JL L6
    MOV AX, 0
    JMP L7
L6:
    MOV AX, 1
L7:
    CMP AX, 1
    JE L8
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 10
    NEG AX
    POP BX
    CMP BX, AX
    JG L9
    MOV AX, 0
    JMP L10
L9:
    MOV AX, 1
L10:
L8:
L5:
    CMP AX, 0
    JE L11
    MOV AX, 100
    MOV WORD PTR [BP-2], AX
    JMP L12
L11:
    MOV AX, 200
    MOV WORD PTR [BP-2], AX
L12:
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
