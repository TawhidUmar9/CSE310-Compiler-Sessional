.MODEL SMALL
.STACK 1000H

.DATA
	CR EQU 0DH
	LF EQU 0AH
	NEWLINE DB CR, LF, '$'
	number DB "00000$"
	i DW ?
	j DW ?

.CODE

; --- Helper procedure to print a number in AX ---
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
print_output proc  ;print what is in ax
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
	PUSH BP
	MOV BP, SP
	MOV AX, 1
	; i = AX
	MOV i, AX
	; println(i)
	MOV AX, i
	CALL print_output
	CALL new_line
	MOV AX, 5
	PUSH AX
	MOV AX, 8
	POP BX
	ADD AX, BX
	; j = AX
	MOV j, AX
	; println(j)
	MOV AX, j
	CALL print_output
	CALL new_line
	MOV AX, i
	PUSH AX
	MOV AX, 2
	PUSH AX
	MOV AX, j
	POP BX
	XCHG AX, BX
	IMUL BX
	POP BX
	ADD AX, BX
	; k = AX
	MOV [BP-2], AX
	; println(k)
	MOV AX, [BP-2]
	CALL print_output
	CALL new_line
	MOV AX, [BP-2]
	PUSH AX
	MOV AX, 9
	POP BX
	XCHG AX, BX
	CWD
	IDIV BX
	MOV AX, DX
	; m = AX
	MOV [BP-6], AX
	; println(m)
	MOV AX, [BP-6]
	CALL print_output
	CALL new_line
	MOV AX, [BP-6]
	PUSH AX
	MOV AX, [BP-4]
	POP BX
	CMP BX, AX
	JLE L0
	MOV AX, 0
	JMP L1
L0:
	MOV AX, 1
L1:
	; n = AX
	MOV [BP-8], AX
	; println(n)
	MOV AX, [BP-8]
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
	; o = AX
	MOV [BP-10], AX
	; println(o)
	MOV AX, [BP-10]
	CALL print_output
	CALL new_line
	MOV AX, [BP-8]
	PUSH AX
	MOV AX, [BP-10]
	POP BX
	OR AX, BX
	; p = AX
	MOV [BP-12], AX
	; println(p)
	MOV AX, [BP-12]
	CALL print_output
	CALL new_line
	MOV AX, [BP-8]
	PUSH AX
	MOV AX, [BP-10]
	POP BX
	AND AX, BX
	; p = AX
	MOV [BP-12], AX
	; println(p)
	MOV AX, [BP-12]
	CALL print_output
	CALL new_line
	INC WORD PTR [BP-12]
	MOV AX, [BP-12]
	; println(p)
	MOV AX, [BP-12]
	CALL print_output
	CALL new_line
	MOV AX, [BP-12]
	NEG AX
	; k = AX
	MOV [BP-2], AX
	; println(k)
	MOV AX, [BP-2]
	CALL print_output
	CALL new_line
	MOV AX, 0
	ADD SP, 12
	POP BP

END main
