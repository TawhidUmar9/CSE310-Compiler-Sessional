.MODEL SMALL
.STACK 100H
.DATA
	CR EQU 0DH
	LF EQU 0AH
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
    MOV AX, 5
    PUSH AX
    MOV AX, 8
    POP BX
    ADD AX, BX ; AX = RHS + LHS
    MOV j, AX
    MOV AX, i
    PUSH AX
    MOV AX, 2
    PUSH AX
    MOV AX, j
    POP BX
    IMUL BX  ; AX = AX * BX
    POP BX
    ADD AX, BX ; AX = RHS + LHS
    MOV WORD PTR [BP-2], AX
    MOV AX, WORD PTR [BP-2]
    PUSH AX
    MOV AX, 9
    POP BX
    XCHG AX, BX ; AX has LHS(BX), BX has RHS(AX)
    CWD         ; Extend sign of AX into DX for division
    IDIV BX     ; AX = DX:AX / BX
    MOV AX, DX ; For modulus, the result is the remainder in DX
    MOV WORD PTR [BP-6], AX
    MOV AX, WORD PTR [BP-6]
    PUSH AX
    MOV AX, WORD PTR [BP-4]
    POP BX
    CMP BX, AX ; Compare LHS (BX) with RHS (AX)
    JLE L0 ; Jump on true
    MOV AX, 0           ; False case
    JMP L1
L0:
    MOV AX, 1           ; True case
L1:
    MOV WORD PTR [BP-8], AX
    MOV AX, i
    PUSH AX
    MOV AX, j
    POP BX
    CMP BX, AX ; Compare LHS (BX) with RHS (AX)
    JNE L2 ; Jump on true
    MOV AX, 0           ; False case
    JMP L3
L2:
    MOV AX, 1           ; True case
L3:
    MOV WORD PTR [BP-10], AX
    MOV AX, WORD PTR [BP-8]
    PUSH AX
    MOV AX, WORD PTR [BP-10]
    POP BX
    OR AX, BX  ; AX = (LHS result) OR (RHS result)
    CMP AX, 0      ; Compare the result with 0
    SETNE AL       ; Set AL to 1 if the result is Not Equal to 0, else 0
    MOVZX AX, AL   ; Zero-extend AL into AX to clean the upper byte
    MOV WORD PTR [BP-12], AX
    MOV AX, WORD PTR [BP-8]
    PUSH AX
    MOV AX, WORD PTR [BP-10]
    POP BX
    AND AX, BX ; AX = (LHS result) AND (RHS result)
    CMP AX, 0      ; Compare the result with 0
    SETNE AL       ; Set AL to 1 if the result is Not Equal to 0, else 0
    MOVZX AX, AL   ; Zero-extend AL into AX to clean the upper byte
    MOV WORD PTR [BP-12], AX
    MOV AX, WORD PTR [BP-12]
    PUSH AX
    INC AX
    MOV WORD PTR [BP-12], AX
    POP AX
    MOV AX, WORD PTR [BP-12]
    NEG AX ; Negate the value in AX
    MOV WORD PTR [BP-2], AX
    MOV AX, 0
    MOV SP, BP
    POP BP
    MOV AH, 4CH
    INT 21H
main ENDP
