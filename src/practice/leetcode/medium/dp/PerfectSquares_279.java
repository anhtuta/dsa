package practice.leetcode.medium.dp;

import java.util.Random;

/**
 * https://leetcode.com/problems/perfect-squares/
 * 
 * Given an integer n, return the least number of perfect square numbers that sum to n.
 * 
 * A perfect square is an integer that is the square of an integer; in other words, it is the
 * product of some integer with itself. For example, 1, 4, 9, and 16 are perfect squares while 3 and
 * 11 are not.
 * 
 * Example 1:
 * Input: n = 12
 * Output: 3
 * Explanation: 12 = 4 + 4 + 4.
 * 
 * Example 2:
 * Input: n = 13
 * Output: 2
 * Explanation: 13 = 4 + 9.
 * 
 * Constraints:
 * 
 * 1 <= n <= 10^4
 * 
 * =====
 * Tóm tắt: cho 1 số n, tìm số lượng nhỏ nhất các số chính phương mà có tổng = n
 * 
 * Bài này có thể giải được bằng DP, BFS hoặc Math
 * Ref:
 * https://leetcode.com/problems/perfect-squares/solutions/71488/summary-of-4-different-solutions-bfs-dp-static-dp-and-mathematics/
 * 
 * Trick: using static variables could run a little faster
 */
public class PerfectSquares_279 {
    private int[] memo; // memo[i] = số lượng tờ tiền tối thiểu để có thể tạo thành amount

    public int numSquares(int n) {
        // return numSquares_dp(n);
        return numSquares_math(n);
    }

    /**
     * Bài này giống hệt bài {@link CoinChange_322}. Mảng perfectSquares tương ứng với mảng coins, và
     * input n tương ứng với amount bên đó
     */
    public int numSquares_dp(int n) {
        int[] perfectSquares = new int[(int) Math.sqrt(n) + 1];
        for (int i = 0; i < perfectSquares.length; i++) {
            perfectSquares[i] = (i + 1) * (i + 1);
        }
        // Utils.printArray(perfectSquares);

        memo = new int[n + 1];
        for (int coin : perfectSquares) {
            if (coin > n)
                continue;
            memo[coin] = 1;
        }
        return dp_bottomUp_memo(perfectSquares, n);
    }

    /**
     * Clone từ bên {@link CoinChange_322}
     * 
     * Result:
     * Runtime 60 ms Beats 41.79%
     * Memory 41.9 MB Beats 66.89%
     * 
     * Bài này giải theo Math sẽ nhanh hơn
     */
    public int dp_bottomUp_memo(int[] coins, int amount) {
        if (amount == 0)
            return 0;

        int min;
        for (int money = 1; money <= amount; money++) {
            if (memo[money] == 0) {
                min = Integer.MAX_VALUE;
                // tìm min của các memo trước đó, là các memo[money - coin]
                for (int coin : coins) {
                    if (coin > money)
                        continue;
                    if (memo[money - coin] >= 0 && memo[money - coin] < min) {
                        min = memo[money - coin];
                    }
                }
                if (min == Integer.MAX_VALUE)
                    memo[money] = -1;
                else
                    memo[money] = min + 1;
            }
        }

        return memo[amount];
    }

    /**
     * Based on Lagrange's Four Square theorem, there are only 4 possible results: 1, 2, 3, 4.
     * Ref: xem ở đầu bài
     */
    public int numSquares_math(int n) {
        // If n is a perfect square, return 1.
        if (is_square(n)) {
            return 1;
        }

        // The result is 4 if and only if n can be written in the
        // form of 4^k*(8*m + 7). Please refer to
        // Legendre's three-square theorem.
        while ((n & 3) == 0) // n%4 == 0
        {
            n >>= 2;
        }
        if ((n & 7) == 7) // n%8 == 7
        {
            return 4;
        }

        // Check whether 2 is the result.
        int sqrt_n = (int) (Math.sqrt(n));
        for (int i = 1; i <= sqrt_n; i++) {
            if (is_square(n - i * i)) {
                return 2;
            }
        }

        return 3;
    }

    public boolean is_square(int n) {
        int sqrt_n = (int) (Math.sqrt(n));
        return (sqrt_n * sqrt_n == n);
    }

    public static void main(String[] args) {
        PerfectSquares_279 app = new PerfectSquares_279();
        System.out.println(app.numSquares(9999)); // 4

        Random rd = new Random();
        int n;
        for (int i = 0; i < 100; i++) {
            n = rd.nextInt(100);
            System.out.printf("n = %d, numSquares = %d%n", n, app.numSquares(n));
        }
    }
}
