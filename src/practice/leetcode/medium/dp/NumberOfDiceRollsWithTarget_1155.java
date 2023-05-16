package practice.leetcode.medium.dp;

/**
 * https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/
 * 
 * You have n dice, and each die has k faces numbered from 1 to k.
 * 
 * Given three integers n, k, and target, return the number of possible ways (out of the kn total
 * ways) to roll the dice, so the sum of the face-up numbers equals target. Since the answer may be
 * too large, return it modulo 10^9 + 7.
 * 
 * Example 1:
 * Input: n = 1, k = 6, target = 3
 * Output: 1
 * Explanation: You throw one die with 6 faces.
 * There is only one way to get a sum of 3.
 * 
 * Example 2:
 * Input: n = 2, k = 6, target = 7
 * Output: 6
 * Explanation: You throw two dice, each with 6 faces.
 * There are 6 ways to get a sum of 7: 1+6, 2+5, 3+4, 4+3, 5+2, 6+1.
 * 
 * Example 3:
 * Input: n = 30, k = 30, target = 500
 * Output: 222616187
 * Explanation: The answer must be returned modulo 10^9 + 7.
 * 
 * Constraints:
 * 
 * 1 <= n, k <= 30
 * 1 <= target <= 1000
 */
public class NumberOfDiceRollsWithTarget_1155 {
    private int[][][] memo;

    private int[][] memo2; // memo2[i][j] = numRollsToTarget(i, k, j)

    public int numRollsToTarget(int n, int k, int target) {
        int ans;

        // Step 1: recursion top down nhưng bị timeout
        // ans = recursion(n, k, target);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // memo = new int[n + 1][k + 1][target + 1];
        // for (int i = 0; i <= n; i++) {
        // for (int j = 0; j <= k; j++) {
        // for (int t = 0; t <= target; t++) {
        // memo[i][j][t] = -1;
        // }
        // }
        // }
        // ans = dp_topDown(n, k, target);

        // Step 2.2: tối ưu mảng memo
        memo2 = new int[n + 1][target + 1];
        for (int i = 0; i <= n; i++) {
            for (int t = 0; t <= target; t++) {
                memo2[i][t] = -1;
            }
        }
        ans = dp_topDown_optimize(n, k, target);

        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        // memo2 = new int[n + 1][target + 1];
        // ans = dp_bottomUp_memo(n, k, target);

        return ans;
    }

    /**
     * Recursion idea:
     * 
     * Hãy bắt đầu bằng ví dụ numRollsToTarget(5, 6, 18), viết gọi lại thành dp(5, 6, 18)
     * Ta có 5 xúc xắc 6 mặt, và tính số cách gieo sao cho tổng của 5 xúc xắc = 18
     * Xét 1 dice trong 5 dice đó, giả sử là con xúc xắc cuối cùng, ta có 6 case cho nó:
     * 
     * Case 1: The last die is a 1. The remaining 4 dice must sum to 18-1=17.
     * This can happen dp(4, 6, 17) ways.
     * Case 2: The last die is a 2. The remaining 4 dice must sum to 18-2=16.
     * This can happen dp(4, 6, 16) ways.
     * Case 3: The last die is a 3. The remaining 4 dice must sum to 18-3=15.
     * This can happen dp(4, 6, 15) ways.
     * Case 4: The last die is a 4. The remaining 4 dice must sum to 18-4=14.
     * This can happen dp(4, 6, 14) ways.
     * Case 5: The last die is a 5. The remaining 4 dice must sum to 18-5=13.
     * This can happen dp(4, 6, 13) ways.
     * Case 6: The last die is a 6. The remaining 4 dice must sum to 18-6=12.
     * This can happen dp(4, 6, 12) ways.
     * 
     * Hay ta có công thức tổng quát là:
     * dp(5, 6, 18) =
     * dp(4, 6, 17) + dp(4, 6, 16) + dp(4, 6, 15) + dp(4, 6, 14) + dp(4, 6, 13) + dp(4, 6, 12)
     * 
     * Tức là: dp(n, k, target) = ∑dp(n-1, k, target-i), với i = 1->k
     * 
     * Ref:
     * https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/solutions/355894/python-dp-with-memoization-explained/
     * 
     * Result: Time Limit Exceeded, 0 testcase passed
     */
    public int recursion(int n, int k, int target) {
        if (target <= 0)
            return 0;
        if (n == 1) {
            if (k >= target)
                return 1;
            else
                return 0;
        }
        int res = 0;
        for (int i = 1; i <= k; i++) {
            res = (res + recursion(n - 1, k, target - i)) % 1_000_000_007;
        }
        // System.out.printf("recursion(%d, %d, %d) = %d%n", n, k, target, res);
        return res;
    }

    /**
     * Result:
     * Runtime 22 ms Beats 56.6%
     * Memory 44.8 MB Beats 5.7%
     */
    public int dp_topDown(int n, int k, int target) {
        if (target <= 0)
            return 0;
        if (n == 1) {
            if (k >= target)
                return 1;
            else
                return 0;
        }
        if (memo[n][k][target] == -1) {
            int res = 0;
            for (int i = 1; i <= k; i++) {
                if (target <= i)
                    break;
                res = (res + dp_topDown(n - 1, k, target - i)) % 1_000_000_007;
            }
            memo[n][k][target] = res;
        }
        return memo[n][k][target];
    }

    /**
     * Rõ ràng là biến k luôn ko đổi trong suốt quá trình gọi đệ quy, nên mảng memo chỉ cần 2D là đủ
     * 
     * Result:
     * Runtime 15 ms Beats 84.36%
     * Memory 41.9 MB Beats 49.78%
     */
    public int dp_topDown_optimize(int n, int k, int target) {
        if (target <= 0)
            return 0;
        if (n == 1) { // base cases
            if (k >= target)
                return 1;
            else
                return 0;
        }
        if (memo2[n][target] == -1) {
            int res = 0;
            for (int i = 1; i <= k; i++) {
                if (target <= i)
                    break;
                res = (res + dp_topDown_optimize(n - 1, k, target - i)) % 1_000_000_007;
            }
            memo2[n][target] = res;
        }
        return memo2[n][target];
    }

    /**
     * Tốn hơn 1 buổi chiều để tìm ra công thức tính từ bottom up, phải dùng 3 vòng for lồng nhau, nghĩ
     * mãi mệt vc, kết quả thậm chí còn chậm hơn dp top down
     * 
     * dp(n, k, target) = ∑dp(n-1, k, target-i), với i = 1->k
     * if (t <= j)
     * memo[i][target] = memo[i-1][target-1] + memo[i-1][target-2] + ... + memo[i-1][target-j]
     * 
     * dp(2, 6, 7):
     * memo2[2][4] = memo2[1][3] + memo2[1][2] + memo2[1][1]
     * 
     * dp(5, 6, 18):
     * memo2[5][18] =
     * memo2[4][17] + memo2[4][16] + memo2[4][15] + memo2[4][14] + memo2[4][13] + memo2[4][12] =
     * memo2[4][target - 1] + memo2[4][target - 2] + memo2[4][target - 3] + memo2[4][target - 4] +
     * memo2[4][target - 5] + memo2[4][target - 6]
     * 
     * for (int j = 1; j <= k; j++)
     * memo2[5][18] += memo2[4][target-j]
     * 
     * Result:
     * Runtime 21 ms Beats 56.50%
     * Memory 42.1 MB Beats 22.58%
     */
    public int dp_bottomUp_memo(int n, int k, int target) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                if (i == 1) { // base cases
                    if (j <= target) {
                        memo2[i][j] = 1;
                    }
                    continue;
                }
                for (int t = 1; t <= target; t++) {
                    if (t <= j)
                        continue;
                    memo2[i][t] = (memo2[i][t] + memo2[i - 1][t - j]) % 1_000_000_007;
                    // System.out.printf("memo2[%d][%d] = %d%n", i, t, memo2[i][t]);
                }
            }
        }
        return memo2[n][target];
    }

    public static void main(String[] args) {
        NumberOfDiceRollsWithTarget_1155 app = new NumberOfDiceRollsWithTarget_1155();
        System.out.println(app.numRollsToTarget(1, 6, 3)); // 1
        System.out.println(app.numRollsToTarget(2, 6, 7)); // 6
        System.out.println(app.numRollsToTarget(5, 6, 18)); // 780
        System.out.println(app.numRollsToTarget(30, 30, 500)); // 222616187
    }
}
