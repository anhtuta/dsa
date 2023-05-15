package practice.leetcode.easy;

/**
 * Problem: https://leetcode.com/problems/climbing-stairs/
 * (Easy)
 * 
 * You are climbing a staircase. It takes n steps to reach the top.
 * 
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 * 
 * Example 1:
 * Input: n = 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 * 1. 1 step + 1 step
 * 2. 2 steps
 * 
 * Example 2:
 * Input: n = 3
 * Output: 3
 * Explanation: There are three ways to climb to the top.
 * 1. 1 step + 1 step + 1 step
 * 2. 1 step + 2 steps
 * 3. 2 steps + 1 step
 * 
 * Constraints:
 * 
 * 1 <= n <= 45
 * 
 * Here is my comment on leetcode:
 * Hi, here is my thinking after reading several comments:
 * Let's denote that S[n] is the number of solutions for a n steps stair. From the nth
 * stairs, let's walk downward, how many ways you could walk? Two, right? The first way is
 * using 1 step, and the second way is to use 2 steps, so now we can imagine, at the (n-1)th
 * stairs and at (n-2)th stairs, both of them, we could be able to climb up to our
 * destination (nth stairs), which means s[n] = s[n-1] + s[n-2]
 * 
 * Dịch: từ bậc n, ta có thể đi xuống 2 bậc đó là bậc n-1 và bậc n-2,
 * nghĩa là s[n] = s[n-1] + s[n-2]
 * 
 * Do đó bài này chính là bài Fibonacci, với F1 = 1, F2 = 2, F3 = F1 + F2,...
 * 
 * @author tatu
 * 
 */
public class ClimbingStairs_70 {
    public int climbStairs(int n) {
        if (n <= 3)
            return n;
        int f2 = 0, f1 = 3, f0 = 2;
        for (int i = 4; i <= n; i++) {
            f2 = f1 + f0;
            f0 = f1;
            f1 = f2;
        }
        return f2;
    }

    /**
     * DP cho beginner: bắt đầu từ đệ quy, nhưng bị timeout
     * Idea: tại bậc thứ i, sẽ có 2 cách để leo lên bậc này là từ bậc i-1 hoặc bậc i-2, do đó tổng số
     * cách để leo tới bậc i = steps[i] = steps[i-1] + steps[i-2]
     */
    public int climbStairs_recursion(int n) {
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    private static int[] memo = new int[46]; // n max = 46

    /**
     * Dùng DP top down + memo, accepted
     */
    public int climbStairs_dp_topDown(int n) {
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        if (memo[n] == 0) {
            memo[n] = climbStairs_dp_topDown(n - 1) + climbStairs_dp_topDown(n - 2);
        }
        return memo[n];
    }

    // Khử đệ quy = DP bottom up: xem lời giải đầu tiên
}
