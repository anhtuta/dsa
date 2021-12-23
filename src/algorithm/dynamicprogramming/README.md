## Quy hoạch động (Dynamic Programming - DP):
### **Those who cannot remember the past are condemned to repeat it** (Những người không thể nhớ quá khứ không thể thoái khỏi việc lặp lại nó)

That's what Dynamic Programming is about. To always remember answers to the sub-problems you've already solved

Ý tưởng cốt lõi của DP là **tránh lặp lại công việc** bằng cách **ghi nhớ các kết quả từng phần** (partial results) (khái niệm này được ứng dụng trong rất nhiều tình huống thực tế.)

### How should I explain dynamic programming to a 4-year-old?
Writes down "1+1+1+1+1+1+1+1 = " on a sheet of paper.
- "What's that equal to?"
- Counting "Eight!"

Writes down another "1+" on the left.
- "What about that?"
- "Nine! How'd you know it was nine so fast?"
- "You just added one more!"
- "So you didn't need to recount because you remembered there were eight! Dynamic Programming is just a fancy way to say remembering stuff to save time later!"

### Two major properties of Dynamic programming
To decide whether problem can be solved by applying Dynamic programming we check for two properties. If problem has these two properties then we can solve that problem using Dynamic programming.
- **Overlapping Sub-problems**:
	+ As the name suggests the sub-problems needs to be solved again and again.
	+ In recursion we solve those problems every time and in DP we solve these sub-problems only once and store it for future use.
- **Optimal Substructure**: If a problem can be solved by using the solutions of the sub problems.

### Dynamic Programming Approaches:
- **Top-Down** (Memoization: ghi nhớ):
	+ Chia bài toán lớn thành nhiều subproblem.
	+ Nếu subproblem đã được giải quyết (đã tồn tại trong lookup table), chỉ cần sử dụng lại câu trả lời
	+ Nếu không, giải subproblem và lưu trữ kết quả trong lookup table
- **Bottom-Up** (Tabulation: lập bảng): Bắt đầu tính toán kết quả cho subproblem. Sử dụng kết quả subproblem giải quyết một subproblem khác và cuối cùng giải quyết toàn bộ vấn đề

Fibonacci problem using Top-Down: starts breaking the problem. Like:
- If we want to compute Fibonacci(4), the top-down approach will do the following
- Fibonacci(4) -> Go and compute Fibonacci(3) and Fibonacci(2) and return the results.
- Fibonacci(3) -> Go and compute Fibonacci(2) and Fibonacci(1) and return the results.
- Fibonacci(2) -> Go and compute Fibonacci(1) and Fibonacci(0) and return the results.
- Finally, Fibonacci(1) will return 1 and Fibonacci(0) will return 0.

![top-down-fib](./top-down-fib.png)

Fibonacci problem using Bottom-Up:
- Find 0th member
- Find 1st member
- Calculate the 2nd member using 0th and 1st member
- Calculate the 3rd member using 1st and 2nd member
- By doing this we can easily find the nth member.

Another example of Top Down vs Bottom up:
- Top Down: I will be an amazing coder. How? I will work hard like crazy. How? I'll practice more and try to improve. How? I'll start taking part in contests. Then? I'll practicing. How? I'm going to learn programming.
- Bottom Up: I'm going to learn programming. Then, I will start practicing. Then, I will start taking part in contests. Then, I'll practice even more and try to improve. After working hard like crazy, I'll be an amazing coder.

### Ref
- https://www.hackerearth.com/practice/algorithms/dynamic-programming/introduction-to-dynamic-programming-1/tutorial/
- https://www.geeksforgeeks.org/overlapping-subproblems-property-in-dynamic-programming-dp-1/
- https://www.quora.com/How-should-I-explain-dynamic-programming-to-a-4-year-old/answer/Jonathan-Paulson
- https://www.log2base2.com/algorithms/dynamic-programming/dynamic-programming.html