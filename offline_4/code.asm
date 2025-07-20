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
    NEG AX ; Negate the value in AX
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
    ADD AX, BX ; AX = RHS + LHS
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 0
    POP BX
    SUB BX, AX
    MOV AX, BX ; Move result back to AX
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 1
    POP BX
    IMUL BX  ; AX = AX * BX
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    CALL print_output
    CALL new_line
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 0
    POP BX
    CMP BX, AX ; Compare LHS (BX) with RHS (AX)
    JG L0 ; Jump on true
    MOV AX, 0           ; False case
    JMP L1
L0:
    MOV AX, 1           ; True case
L1:
    PUSH AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 10
    POP BX
    CMP BX, AX ; Compare LHS (BX) with RHS (AX)
    JL L2 ; Jump on true
    MOV AX, 0           ; False case
    JMP L3
L2:
    MOV AX, 1           ; True case
L3:
    POP BX
    AND AX, BX ; AX = (LHS result) AND (RHS result)
    PUSH AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 0
    POP BX
    CMP BX, AX ; Compare LHS (BX) with RHS (AX)
    JL L4 ; Jump on true
    MOV AX, 0           ; False case
    JMP L5
L4:
    MOV AX, 1           ; True case
L5:
    PUSH AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 10
    NEG AX ; Negate the value in AX
    POP BX
    CMP BX, AX ; Compare LHS (BX) with RHS (AX)
    JG L6 ; Jump on true
    MOV AX, 0           ; False case
    JMP L7
L6:
    MOV AX, 1           ; True case
L7:
    POP BX
    AND AX, BX ; AX = (LHS result) AND (RHS result)
    POP BX
    OR AX, BX  ; AX = (LHS result) OR (RHS result)
    CMP AX, 0 ; Check if the condition is false
    JE L8
    MOV AX, 100
    MOV WORD PTR [BP-2], AX
    JMP L9
L8:
    MOV AX, 200
    MOV WORD PTR [BP-2], AX
L9:
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

    print_output proc  ;prints what is in ax
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
    
END MAIN
