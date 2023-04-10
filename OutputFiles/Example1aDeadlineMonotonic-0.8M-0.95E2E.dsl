WARP program for graph Example1A
Scheduler Name: DeadlineMonotonic
M: 0.8
E2E: 0.95
nChannels: 16
Time Slot	A	B	C
0	if has(F0) push(F0: A -> B, #13)	wait(#13)	sleep
1	wait(#4)	if has(F0) push(F0: B -> C, #4) else pull(F0: A -> B, #4)	wait(#4)
2	wait(#5)	if has(F0) push(F0: B -> C, #5) else pull(F0: A -> B, #5)	wait(#5)
3	sleep	if has(F0) push(F0: B -> C, #6)	wait(#6)
4	sleep	wait(#7)	if has(F1) push(F1: C -> B, #7)
5	wait(#10)	if has(F1) push(F1: B -> A, #10) else pull(F1: C -> B, #10)	wait(#10)
6	wait(#11)	if has(F1) push(F1: B -> A, #11) else pull(F1: C -> B, #11)	wait(#11)
7	wait(#12)	if has(F1) push(F1: B -> A, #12)	sleep
8	sleep	sleep	sleep
9	sleep	sleep	sleep
10	if has(F0) push(F0: A -> B, #0)	wait(#0)	sleep
11	wait(#7)	if has(F0) push(F0: B -> C, #7) else pull(F0: A -> B, #7)	wait(#7)
12	wait(#8)	if has(F0) push(F0: B -> C, #8) else pull(F0: A -> B, #8)	wait(#8)
13	sleep	if has(F0) push(F0: B -> C, #9)	wait(#9)
14	sleep	sleep	sleep
15	sleep	sleep	sleep
16	sleep	sleep	sleep
17	sleep	sleep	sleep
18	sleep	sleep	sleep
19	sleep	sleep	sleep
// All flows meet their deadlines
