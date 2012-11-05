; Parameters:
; 500 A1 = Multiplicand
; 501 P2 = Multiplier
; Variables:
; 502 counter
; 503 P1
; 504 suffix P
; 505 S1

; Init
LWDD R0 #500		; load A1
NOT					; invert
INC					; akku = -A1 
SWDD R0 #505		; store in S1
LWDD R1 #501		; load P2
LWDD R0 zero
SWDD R0 #504		; suffixP = 0
LWDD R0 count
SWDD R0 #502		; set counter

loopstart:
LWDD R1 #501		; load P2
LWDD R2 #504		; load suffixP
LWDD R0 one
AND R1
ADD R2				; add suffixP
DEC
BNZD rightshift		; Case 3. + 4. (00 or 11)
LWDD R0 #504		; load suffixP
BZD case2			; if suffixP == 0 goto case2

case1:				; Case 1. 01
LWDD R0 #503		; load P1
LWDD R1 #500		; load A1
ADD R1
SWDD R0 #503		; P1=P1+A1
BD rightshift

case2:				; Case 2. 10
LWDD R0 #503		; load P1
LWDD R1 #505		; load S1
ADD R1   
SWDD R0 #503		; P1=P1+S1

rightshift:
LWDD R0 #503		; load P1
LWDD R1 one
AND R1				; if LSb==1, akku=1, else akku=0
BZD LogicShiftP2	; if LSb==1 do AritmeticShiftP2 else do LogicShiftP2

AritmeticShiftP2:
LWDD R0 #501		; load P2
LWDD R1 one
AND R1				; if LSb==1, akku==1, else akku==0
SWDD R0 #504		; suffixP = akku bzw. LSb
LWDD R0 #501		; load P2
SRA
LWDD R1 MSb
OR R1				; set the MSb to 1 (in case P2 was not negative)
SWDD R0 #501 
BD AritmeticShiftP1

LogicShiftP2:
LWDD R0 #501		; load P2
LWDD R1 one
AND R1				; if LSb==1, akku==1, else akku==0
SWDD R0 #504		; suffixP = akku bzw. LSb
LWDD R0 #501		; load P2
SRL
SWDD R0 #501 

AritmeticShiftP1:
LWDD R0 #503		; load P1
SRA 
SWDD R0 #503

LWDD R0 #502		; load counter
DEC
SWDD R0 #502		; store new counter
BNZD loopstart


LWDD R0 #503		; load P1
SWDD R0 #510
LWDD R0 #501		; load P2
SWDD R0 #511		; store P1P2 to #510#511
END

; Constants
zero:
0
one:
1
MSb:
-32768	; 0b10000000 00000000
count:
16