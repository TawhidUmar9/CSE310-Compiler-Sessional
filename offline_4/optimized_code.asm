.MODEL SMALL
.STACK 100H
.DATA
	CR EQU 0DH
	LF EQU 0AH
number DB 6 DUP('$') ;
.CODE

f PROC
    PUSH BP
    MOV BP, SP
    SUB SP, 2 ; k at [BP-2]
    MOV AX, 5
    MOV WORD PTR [BP-2], AX
L0:
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 0
    POP BX
    CMP BX, AX
    JG L2
    MOV AX, 0
    JMP L3
L2:
    MOV AX, 1
L3:
    CMP AX, 0
    JE L1
    MOV AX, WORD PTR [BP+4]
    PUSH AX
    INC AX
    MOV WORD PTR [BP+4], AX
    POP AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    DEC AX
    MOV WORD PTR [BP-2], AX
    POP AX
    JMP L0
L1:
    MOV AX, 3
    PUSH AX
    MOV AX, WORD PTR [BP+4]
    POP BX
    IMUL BX
    PUSH AX
    MOV AX, 7
    POP BX
    SUB BX, AX
    MOV AX, BX
    MOV SP, BP
    POP BP
    RET
f ENDP

g PROC
    PUSH BP
    MOV BP, SP
    SUB SP, 2 ; x at [BP-2]
    SUB SP, 2 ; i at [BP-4]
    MOV AX, WORD PTR [BP+4]
    PUSH AX
    CALL f
    ADD SP, 2
    PUSH AX
    MOV AX, WORD PTR [BP+4]
    POP BX
    ADD AX, BX
    PUSH AX
    MOV AX, WORD PTR [BP+6]
    POP BX
    ADD AX, BX
    MOV WORD PTR [BP-2], AX
    MOV AX, 0
    MOV WORD PTR [BP-4], AX
L4:
    MOV AX, WORD PTR [BP-4]
    PUSH AX
    MOV AX, 7
    POP BX
    CMP BX, AX
    JL L6
    MOV AX, 0
    JMP L7
L6:
    MOV AX, 1
L7:
    CMP AX, 0
    JE L5
    MOV AX, WORD PTR [BP-4]
    PUSH AX
    MOV AX, 3
    POP BX
    XCHG AX, BX
    CWD
    IDIV BX
    MOV AX, DX
    PUSH AX
    MOV AX, 0
    POP BX
    CMP BX, AX
    JE L8
    MOV AX, 0
    JMP L9
L8:
    MOV AX, 1
L9:
    CMP AX, 0
    JE L10
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 5
    POP BX
    ADD AX, BX
    MOV WORD PTR [BP-2], AX
    JMP L11
L10:
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 1
    POP BX
    SUB BX, AX
    MOV AX, BX
    MOV WORD PTR [BP-2], AX
L11:
    MOV AX, WORD PTR [BP-4]
    PUSH AX
    INC AX
    MOV WORD PTR [BP-4], AX
    POP AX
    JMP L4
L5:
    MOV AX, WORD PTR [BP-2]
    MOV SP, BP
    POP BP
    RET
g ENDP

main PROC
    MOV AX, @DATA
    MOV DS, AX
    PUSH BP
    MOV BP, SP
    SUB SP, 2 ; a at [BP-2]
    SUB SP, 2 ; b at [BP-4]
    SUB SP, 2 ; i at [BP-6]
    MOV AX, 1
    MOV WORD PTR [BP-2], AX
    MOV AX, 2
    MOV WORD PTR [BP-4], AX
    MOV AX, WORD PTR [BP-4]
    PUSH AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    CALL g
    ADD SP, 4
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    CALL print_output
    CALL new_line
    MOV AX, 0
    MOV WORD PTR [BP-6], AX
L12:
    MOV AX, WORD PTR [BP-6]
    PUSH AX
    MOV AX, 4
    POP BX
    CMP BX, AX
    JL L14
    MOV AX, 0
    JMP L15
L14:
    MOV AX, 1
L15:
    CMP AX, 0
    JE L13
    MOV AX, 3
    MOV WORD PTR [BP-2], AX
L16:
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 0
    POP BX
    CMP BX, AX
    JG L18
    MOV AX, 0
    JMP L19
L18:
    MOV AX, 1
L19:
    CMP AX, 0
    JE L17
    MOV AX, WORD PTR [BP-4]
    PUSH AX
    INC AX
    MOV WORD PTR [BP-4], AX
    POP AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    DEC AX
    MOV WORD PTR [BP-2], AX
    POP AX
    JMP L16
L17:
    MOV AX, WORD PTR [BP-6]
    PUSH AX
    INC AX
    MOV WORD PTR [BP-6], AX
    POP AX
    JMP L12
L13:
    MOV AX, WORD PTR [BP-2]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-4]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-6]
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
