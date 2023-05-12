package practice.leetcode.easy.dp;

/**
 * You are given an integer array cost where cost[i] is the cost of ith step on a staircase. Once
 * you pay the cost, you can either climb one or two steps.
 * 
 * You can either start from the step with index 0, or the step with index 1.
 * 
 * Return the minimum cost to reach the top of the floor.
 * 
 * Example 1:
 * Input: cost = [10,15,20]
 * Output: 15
 * Explanation: You will start at index 1.
 * - Pay 15 and climb two steps to reach the top.
 * The total cost is 15.
 * 
 * Example 2:
 * Input: cost = [1,100,1,1,1,100,1,1,100,1]
 * Output: 6
 * Explanation: You will start at index 0.
 * - Pay 1 and climb two steps to reach index 2.
 * - Pay 1 and climb two steps to reach index 4.
 * - Pay 1 and climb two steps to reach index 6.
 * - Pay 1 and climb one step to reach index 7.
 * - Pay 1 and climb two steps to reach index 9.
 * - Pay 1 and climb one step to reach the top.
 * The total cost is 6.
 * 
 * Constraints:
 * 
 * 2 <= cost.length <= 1000
 * 0 <= cost[i] <= 999
 */
public class MinCostClimbingStairs_746 {
    private int[] memo; // memo[i] = chi phí tối thiểu để đi tới bậc i

    public int minCostClimbingStairs(int[] a) {
        int ans;
        // Step 1: recursion top down nhưng bị timeout
        // Vì tại mỗi vị trí có thể bước tiếp 1 hoặc 2 bậc, tại 2 bậc cuối cùng đều có có bước tới đỉnh
        // luôn. Do đỉnh là vị trí nằm ngoài cầu thang (ngoài index của a), nêu ta có thể coi nó là bậc
        // a[a.length], và coi như bậc này ko tốn phí
        // ans = minCostClimbingStairs_recursion(a, a.length);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // memo = new int[a.length + 1];
        // for (int i = 0; i < memo.length; i++) {
        // memo[i] = -1;
        // }
        // ans = minCostClimbingStairs_DP_topDown(a, a.length);
        // Utils.printArray(memo);

        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        // memo = new int[a.length + 1];
        // ans = minCostClimbingStairs_DP_bottomUp_memo(a);

        // Step 4: tối ưu hơn nữa (tối ưu memory) bằng việc dùng DP bottom up without memo
        ans = minCostClimbingStairs_DP_bottomUp_noMemo(a);

        return ans;
    }

    /**
     * Giống bài {@link practice.leetcode.easy.ClimbingStairs_70}: tại bậc thứ i, ta có 2 solution để đi
     * tới nó là từ bậc i-1 bước lên 1 bậc, hoặc từ bậc i-2 xong bước lên 2 bậc. Do đó chi phí tối thiểu
     * để leo tới bậc i = a[i] + minCost[i-1] hoặc a[i] + minCost[i-2]
     * Đó chính là ý tưởng đệ quy
     * 
     * Result:
     * Time Limit Exceeded 259 / 283 testcases passed
     */
    public int minCostClimbingStairs_recursion(int[] a, int pos) {
        if (pos <= 1) {
            return a[pos];
        }
        return (pos == a.length ? 0 : a[pos]) + Math.min(
                minCostClimbingStairs_recursion(a, pos - 1),
                minCostClimbingStairs_recursion(a, pos - 2));
    }

    /**
     * Result:
     * Runtime 1 ms Beats 64.38%
     * Memory 42 MB Beats 85.31%
     */
    public int minCostClimbingStairs_DP_topDown(int[] a, int pos) {
        if (pos <= 1) {
            memo[pos] = a[pos];
        } else if (memo[pos] == -1) {
            memo[pos] = (pos == a.length ? 0 : a[pos]) + Math.min(
                    minCostClimbingStairs_DP_topDown(a, pos - 1),
                    minCostClimbingStairs_DP_topDown(a, pos - 2));
        }
        return memo[pos];
    }

    /**
     * Note: length của memo hơn length của a 1 đơn vị
     * Result: ko nhanh hơn minCostClimbingStairs_DP_topDown là mấy
     */
    public int minCostClimbingStairs_DP_bottomUp_memo(int[] a) {
        if (a.length == 2)
            return Math.min(a[0], a[1]);
        memo[0] = a[0];
        memo[1] = a[1];
        for (int i = 2; i < memo.length; i++) {
            memo[i] = (i == a.length ? 0 : a[i]) + Math.min(memo[i - 1], memo[i - 2]);
        }
        return memo[a.length];
    }

    /**
     * Runtime 0 ms Beats 100%
     * Memory 42.3 MB Beats 65.46%
     * 
     * Nhanh hơn dùng memo rồi đếy!
     */
    public int minCostClimbingStairs_DP_bottomUp_noMemo(int[] a) {
        if (a.length == 2)
            return Math.min(a[0], a[1]);
        int m0 = a[0], m1 = a[1], res = 0;
        for (int i = 2; i <= a.length; i++) {
            res = (i == a.length ? 0 : a[i]) + Math.min(m0, m1);
            m0 = m1;
            m1 = res;
        }
        return res;
    }

    public static void main(String[] args) {
        MinCostClimbingStairs_746 app = new MinCostClimbingStairs_746();
        System.out.println(app.minCostClimbingStairs(new int[] {10, 15, 20})); // 15
        System.out.println(app.minCostClimbingStairs(new int[] {1, 100, 1, 1, 1, 100, 1, 1, 100, 1})); // 6
    }
}
