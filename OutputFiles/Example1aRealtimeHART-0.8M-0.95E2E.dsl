WARP program for graph Example1A
Scheduler Name: RealtimeHART
M: 0.8
E2E: 0.95
nChannels: 16
Time Slot	A	B	C
0	push(F0: A -> B, #3)	wait(#3)	sleep
1	push(F0: A -> B, #4)	wait(#4)	sleep
2	push(F0: A -> B, #5)	wait(#5)	sleep
3	sleep	push(F0: B -> C, #13)	wait(#13)
4	sleep	push(F0: B -> C, #14)	wait(#14)
5	sleep	push(F0: B -> C, #15)	wait(#15)
6	sleep	wait(#10)	push(F1: C -> B, #10)
7	sleep	wait(#11)	push(F1: C -> B, #11)
8	sleep	wait(#12)	push(F1: C -> B, #12)
9	wait(#3)	push(F1: B -> A, #3)	sleep
10	push(F0: A -> B, #6)	wait(#6)	sleep
11	push(F0: A -> B, #7)	wait(#7)	sleep
12	push(F0: A -> B, #8)	wait(#8)	sleep
13	sleep	push(F0: B -> C, #0)	wait(#0)
14	sleep	push(F0: B -> C, #1)	wait(#1)
15	sleep	push(F0: B -> C, #2)	wait(#2)
16	wait(#4)	push(F1: B -> A, #4)	sleep
17	wait(#5)	push(F1: B -> A, #5)	sleep
18	sleep	sleep	sleep
19	sleep	sleep	sleep
// All flows meet their deadlines
