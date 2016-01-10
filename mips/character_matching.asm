.data

buf:    .word 0:1024
suc:    .asciiz "\r\nSuccess!Location: "
fai:    .asciiz "\r\nFail!\r\n"
endl:   .asciiz "\r\n"
 
    .text
    .globl main
main:   
#input a target string
inputstr:
    la $a0, buf
    li $a1, 1000
    li $v0, 8
    syscall
#input a character
inputchar:
    li $v0, 12
    syscall
    addi $s0, $v0, 0 #s0 stores the character
 
judgeend:
    li $t0, '?'
    beq $t0, $s0, exit
     
search:
    or $t0, $0, $0 #t0 works as incremental counter
    la $t1, buf #t1 works as header of the buffer
    li $s1, '\0'
     
loop:   
    add $t2, $t1, $t0
    lbu $t3, ($t2) 
    beq $t3, $s1, failed 
    beq $t3, $s0, success 
    addi $t0, $t0, 1
    j loop
     
success: 
    la $a0, suc
    li $v0, 4
    syscall
    addi $a0, $t0, 1
    li $v0, 1
    syscall
    la $a0, endl
    li $v0, 4
    syscall
    j inputchar
     
failed: 
    la $a0, fai
    li $v0, 4
    syscall
    j inputchar
     
exit:
    li $v0, 10
    syscall
