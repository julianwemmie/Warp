WARP program for graph Example1A
Scheduler Name: PosetRM
M: 0.8
E2E: 0.95
nChannels: 16
Time Slot	A	B	C
0	wait(#0)	if !has(F0: A -> B) pull(F0: A -> B, #0)	sleep
1	wait(#0)	if !has(F0: A -> B) pull(F0: A -> B, #0) else if !has(F0: A -> B) pull(F0: A -> B, #0)	sleep
2	wait(#0)	if !has(F0: A -> B) pull(F0: A -> B, #0) else if !has(F0: A -> B) pull(F0: A -> B, #0) else if has(F0: B -> C) push(F0: B -> C, #0)	wait(#0)
3	wait(#0)	if !has(F0: A -> B) pull(F0: A -> B, #0) else if has(F0: B -> C) push(F0: B -> C, #0) else if has(F0: B -> C) push(F0: B -> C, #0)	wait(#0)
4	sleep	if has(F0: B -> C) push(F0: B -> C, #0) else if has(F0: B -> C) push(F0: B -> C, #0)	wait(#0)
5	sleep	if has(F0: B -> C) push(F0: B -> C, #0)	wait(#0)
6	sleep	sleep	sleep
7	sleep	sleep	sleep
8	sleep	sleep	sleep
9	sleep	sleep	sleep
10	wait(#0)	if !has(F0: A -> B) pull(F0: A -> B, #0)	sleep
11	wait(#0)	if !has(F0: A -> B) pull(F0: A -> B, #0) else if !has(F0: A -> B) pull(F0: A -> B, #0)	sleep
12	wait(#0)	if !has(F0: A -> B) pull(F0: A -> B, #0) else if !has(F0: A -> B) pull(F0: A -> B, #0) else if has(F0: B -> C) push(F0: B -> C, #0)	wait(#0)
13	wait(#0)	if !has(F0: A -> B) pull(F0: A -> B, #0) else if has(F0: B -> C) push(F0: B -> C, #0) else if has(F0: B -> C) push(F0: B -> C, #0)	wait(#0)
14	sleep	if has(F0: B -> C) push(F0: B -> C, #0) else if has(F0: B -> C) push(F0: B -> C, #0)	wait(#0)
15	sleep	if has(F0: B -> C) push(F0: B -> C, #0)	wait(#0)
16	sleep	sleep	sleep
17	sleep	sleep	sleep
18	sleep	sleep	sleep
19	sleep	sleep	sleep
// All flows meet their deadlines
