package practice.leetcode.medium.dp;

/**
 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right,
 * which minimizes the sum of all numbers along its path.
 * 
 * Note: You can only move either down or right at any point in time.
 * 
 * Example 1:
 * Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
 * Output: 7
 * Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
 * 
 * Example 2:
 * Input: grid = [[1,2,3],[4,5,6]]
 * Output: 12
 * 
 * Constraints:
 * 
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 200
 * 0 <= grid[i][j] <= 100
 * 
 * ===
 * Bài này khá giống với bài {@link practice.leetcode.medium.UniquePaths_62}, cũng đi từ top left
 * xuống bottom down của map 2D
 * Bài này cũng giống bài {@link practice.leetcode.easy.dp.MinCostClimbingStairs_746}, sum nhỏ nhất
 * tại ô (i, j) = a[i][j] + sum nhỏ nhất tại 1 trong 2 ô trước đó (xem method recursion ở dưới)
 */
public class MinimumPathSum_64 {
    private int[][] memo; // memo[i][j] = sum bé nhất khi đi tới ô a[i][j]

    public void initMemo(int m, int n) {
        memo = new int[m][n];
        for (int i = 0; i < memo.length; i++) {
            for (int j = 0; j < memo[0].length; j++) {
                memo[i][j] = -1;
            }
        }
    }

    public int minPathSum(int[][] a) {
        int ans;
        // Step 1: recursion top down nhưng bị timeout
        // ans = minPathSum_recursion(a, a.length - 1, a[0].length - 1);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // initMemo(a.length, a[0].length);
        // ans = minPathSum_DP_topDown(a, a.length - 1, a[0].length - 1);

        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        memo = new int[a.length][a[0].length];
        ans = minPathSum_DP_bottomUp_memo(a);

        // Step 4: tối ưu hơn nữa (tối ưu memory) bằng việc dùng DP bottom up without memo
        // Khả năng bài này ko làm được theo cách này

        return ans;
    }

    /**
     * Idea: có 2 cách để đi được tới ô thứ (i, j)
     * - Từ ô (i-1, j) đi xuống
     * - Từ ô (i, j-1) đi sang phải
     * => sum nhỏ nhất tại ô (i, j) = a[i][j] + sum nhỏ nhất tại 1 trong 2 ô trước đó
     * 
     * Result:
     * Time Limit Exceeded 25 / 61 testcases passed
     */
    public int minPathSum_recursion(int[][] a, int i, int j) {
        if (i < 0 || j < 0)
            return Integer.MAX_VALUE; // Ko chọn những ô ở ngoài grid (index âm)
        if (i == 0 && j == 0)
            return a[i][j];
        int res = a[i][j] + Math.min(minPathSum_recursion(a, i - 1, j), minPathSum_recursion(a, i, j - 1));
        System.out.printf("i = %d, j = %d, res = %d%n", i, j, res);
        return res;
    }

    /**
     * Result:
     * Runtime 1 ms Beats 99.57%
     * Memory 45.1 MB Beats 77.22%
     * 
     * Các bài DP trên leetcode tối ưu tới đây là pass hết rồi
     */
    public int minPathSum_DP_topDown(int[][] a, int i, int j) {
        if (i < 0 || j < 0)
            return Integer.MAX_VALUE; // Ko chọn những ô ở ngoài grid (index âm)
        if (i == 0 && j == 0)
            return a[i][j];
        if (memo[i][j] < 0) {
            memo[i][j] = a[i][j] + Math.min(minPathSum_DP_topDown(a, i - 1, j), minPathSum_DP_topDown(a, i, j - 1));
        }

        return memo[i][j];
    }

    /**
     * Chuyển từ topDown sang bottom up. Từ DP_topDown ở trên ta thấy a[i][j] chỉ phụ thuộc vào
     * a[i-1][j] và a[i][j-1], tức là các phần tử trước nó trong mảng 2D a[][], do đó chỉ cần 2 vòng for
     * duyệt từ đầu là có thể tính được theo cách bottom up. Nhưng ta cần phải tính toán các a[0][j] và
     * a[i][0] trước. Dùng 2 biến sum_i0 và sum_j0 để làm việc đó. Khá giống với bài
     * {@link practice.leetcode.medium.UniquePaths_62} nhưng phức tạp hơn xíu
     * 
     * Result:
     * Runtime 3 ms Beats 27.59%
     * Memory 45.3 MB Beats 52.69%
     * 
     * Tại sao lại chậm hơn top down nhỉ? Có lẽ do dùng if else quá nhiều khi tính memo
     */
    public int minPathSum_DP_bottomUp_memo(int[][] a) {
        int m = a.length, n = a[0].length;
        int sum_i0 = a[0][0], sum_j0 = a[0][0];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    memo[i][j] = a[i][j];
                } else if (i == 0) { // j != 0
                    sum_i0 += a[i][j];
                    memo[i][j] = sum_i0;
                } else if (j == 0) { // i != 0
                    sum_j0 += a[i][j];
                    memo[i][j] = sum_j0;
                } else {
                    memo[i][j] = a[i][j] + Math.min(memo[i - 1][j], memo[i][j - 1]);
                }
                System.out.printf("memo[%d][%d] = %d%n", i, j, memo[i][j]);
            }
        }
        return memo[m - 1][n - 1];
    }

    public static void main(String[] args) {
        MinimumPathSum_64 app = new MinimumPathSum_64();
        System.out.println(app.minPathSum(new int[][] {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}})); // 7
    }
}
