WARP program for graph Example1A
Scheduler Name: RateMonotonic
M: 0.8
E2E: 0.95
nChannels: 16
Time Slot	A	B	C
0	if has(F0) push(F0: A -> B, #7)	wait(#7)	sleep
1	wait(#11)	if has(F0) push(F0: B -> C, #11) else pull(F0: A -> B, #11)	wait(#11)
2	wait(#12)	if has(F0) push(F0: B -> C, #12) else pull(F0: A -> B, #12)	wait(#12)
3	sleep	if has(F0) push(F0: B -> C, #13)	wait(#13)
4	sleep	wait(#4)	if has(F1) push(F1: C -> B, #4)
5	wait(#1)	if has(F1) push(F1: B -> A, #1) else pull(F1: C -> B, #1)	wait(#1)
6	wait(#2)	if has(F1) push(F1: B -> A, #2) else pull(F1: C -> B, #2)	wait(#2)
7	wait(#3)	if has(F1) push(F1: B -> A, #3)	sleep
8	sleep	sleep	sleep
9	sleep	sleep	sleep
10	if has(F0) push(F0: A -> B, #10)	wait(#10)	sleep
11	wait(#14)	if has(F0) push(F0: B -> C, #14) else pull(F0: A -> B, #14)	wait(#14)
12	wait(#15)	if has(F0) push(F0: B -> C, #15) else pull(F0: A -> B, #15)	wait(#15)
13	sleep	if has(F0) push(F0: B -> C, #0)	wait(#0)
14	sleep	sleep	sleep
15	sleep	sleep	sleep
16	sleep	sleep	sleep
17	sleep	sleep	sleep
18	sleep	sleep	sleep
19	sleep	sleep	sleep
// All flows meet their deadlines
