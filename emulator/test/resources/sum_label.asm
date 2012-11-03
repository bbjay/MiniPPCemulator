LWDD R0 #500
SWDD R0 #504
loop_start:			; label declaration
LWDD R0 #500		; another comment
INC
SWDD R0 #500
LWDD R1 #504
ADD R1
SWDD R0 #504
LWDD R0 #500
NOT
INC
LWDD R1 #502
ADD R1
BNZD loop_start		; jump to label declaration
END
array:
1
2
3