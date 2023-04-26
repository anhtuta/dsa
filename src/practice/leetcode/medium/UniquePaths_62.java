package practice.leetcode.medium;

/**
 * https://leetcode.com/problems/unique-paths/
 * 
 * Bài này tương tự bài {@link HouseRobber_198}, nên đọc bài đó trước khi đọc bài này
 * 
 * What's next: {@link LongestPalindromicSubsequence_516}, cũng dùng DP với mảng 2 chiều
 */
public class UniquePaths_62 {
    private int[][] memo;

    public void initMemo(int m, int n) {
        memo = new int[m][n];
        for (int i = 0; i < memo.length; i++) {
            for (int j = 0; j < memo[0].length; j++) {
                memo[i][j] = -1;
            }
        }
    }

    public int uniquePaths(int m, int n) {
        int ans;

        // Step 1: recursion top down nhưng bị timeout
        // ans = uniquePaths_recursion(m, n, m - 1, n - 1);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // initMemo(m, n);
        // ans = uniquePaths_DP_topDown(m, n, m - 1, n - 1);

        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        memo = new int[m][n];
        ans = uniquePaths_DP_bottomUp_memo(m, n);

        // Step 4: tối ưu hơn nữa (tối ưu memory) bằng việc dùng DP bottom up without memo
        // Do bài này dùng mảng 2 chiều nên KHÔNG thể ko dùng memo được. Tại sao?
        //
        // Xét bài toán Fibonacci hoặc bài HouseRobber_198, ta có cách tính F(i) như sau:
        // F[i] = F[i-1] + F[i-2], tức là:
        // F[4] = F[3] + F[2], sau đó sẽ tính:
        // F[5] = F[4] + F[3]. Ta thấy ở F[5], cần 2 số ở step trước đó là F[4] và F[3].
        //
        // Nhưng với bài này, ta sẽ tính lần lượt như sau:
        // steps[4][7] = steps[3][7] + steps[4][[6], sau đó sẽ tính:
        // steps[4][8] = steps[3][8] + steps[4][[7].
        // Rõ ràng bước sau, 2 biến steps[3][8] và steps[4][[7] KHÔNG có ở bước trước đó,
        // thành ra KHÔNG thể chỉ dùng đúng 2 biến để tính toán được

        return ans;
    }

    /**
     * Idea: do tại 1 ô chỉ có thể đi theo 2 hướng (down, right), nên sẽ có 2 cách để đi đến ô thứ
     * (i, j):
     * - Từ ô (i-1, j) đi xuống
     * - Từ ô (i, j-1) đi sang phải
     * => steps(i, j) = step(i-1, j) + step(i, j-1)
     * => Bài này lại giống với Fibonacci :v
     * => Dùng recursion top down là dễ hiểu nhất
     * 
     * Result:
     * Time Limit Exceeded: 41 / 63 testcases passed
     * Time complexity: O(2^(m+n))
     */
    public int uniquePaths_recursion(int m, int n, int i, int j) {
        if (i < 0 || j < 0)
            return 0;
        if (i == 0 || j == 0)
            return 1;
        return uniquePaths_recursion(m, n, i - 1, j) + uniquePaths_recursion(m, n, i, j - 1);
    }

    /**
     * Để tối ưu uniquePaths_recursion, ta có thể dùng DP top down (Memoization): tạo bảng memo và lưu
     * trữ các giá trị steps(k1, k2), tới khi nào gọi lại thì return từ memo thay vì tính toán lại
     * 
     * Result:
     * Accepted. Runtime: 0 ms
     * Time complexity: O(m*n)
     */
    public int uniquePaths_DP_topDown(int m, int n, int i, int j) {
        if (i < 0 || j < 0)
            return 0;
        if (i == 0 || j == 0)
            return 1;
        if (memo[i][j] == -1) {
            memo[i][j] = uniquePaths_DP_topDown(m, n, i - 1, j) + uniquePaths_DP_topDown(m, n, i, j - 1);
        }
        return memo[i][j];
    }

    /**
     * Khử đệ quy bằng cách dùng DP bottom up + memo
     * Do 2 con trỏ i,j đều chạy từ 0,0 đến cuối mảng, và cũng dựa theo DP top down, ta thấy memo[i][j]
     * sẽ phụ thuộc vào những thằng sau:
     * - memo[i-1][j]
     * - memo[i][j-1]
     * Đây đều là những thằng đứng trước nó trong mảng 2 chiều, do đó việc tính toán khá đơn giản, chỉ
     * hơi khó hơn việc tính toán Fibonacci 1 xíu
     */
    public int uniquePaths_DP_bottomUp_memo(int m, int n) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0)
                    memo[i][j] = 1;
                else
                    memo[i][j] = memo[i - 1][j] + memo[i][j - 1];
            }
        }

        return memo[m - 1][n - 1];
    }

    public static void main(String[] args) {
        UniquePaths_62 app = new UniquePaths_62();
        System.out.println(app.uniquePaths(3, 2)); // 3
        System.out.println(app.uniquePaths(3, 7)); // 28
    }
}
