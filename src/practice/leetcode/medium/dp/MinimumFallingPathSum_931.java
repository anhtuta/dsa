package practice.leetcode.medium.dp;

public class MinimumFallingPathSum_931 {
    // memo[i][j] = minSum của ô a[i][j] = tổng bé nhất của falling path khi rơi từ hàng đầu tiên đến ô
    // a[i][j] (note: ở hàng đầu tiên, bắt đầu rơi từ ô nào ko quan trọng nhé)
    private int memo[][];

    private int m, n; // size of 2D array, m = total rows, n = total columns, thực ra đề cho m = n

    // Bởi vì đề bài cho tối đa mảng vuông 100x100, giá trị mỗi ô bé nhất là -100
    // Do đó nếu full các ô có giá trị -100 thì tổng của chúng cũng chỉ là -1_000_000
    private final int UNSET = -1_000_001;

    public int minFallingPathSum(int[][] matrix) {
        int ans;
        m = matrix.length;
        n = matrix[0].length;

        // Step 1: recursion top down nhưng bị timeout
        // ans = recursion(matrix);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // memo = new int[m][n];
        // for (int i = 0; i < m; i++) {
        // for (int j = 0; j < n; j++) {
        // memo[i][j] = UNSET;
        // }
        // }
        // ans = dp_topDown(matrix);

        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        memo = new int[m][n];
        ans = dp_bottomUp_memo(matrix);

        // Step 4: tối ưu hơn nữa (tối ưu memory) bằng việc dùng DP bottom up without memo
        // Khả năng bài này ko làm được theo cách này

        return ans;
    }

    /**
     * Idea: đệ quy top down: đáp án của bài toán sẽ là đáp án tại 1 trong n cell của hàng cuối cùng, do
     * đó ta sẽ duyệt hàng cuối cùng và tìm minSum của hàng đó bằng đệ quy.
     * 
     * Hiểu được ý tưởng đệ quy rồi thì implement và tối ưu bằng DP rất đơn giản
     * 
     * Result:
     * Time Limit Exceeded 38 / 49 testcases passed
     */
    public int recursion(int[][] a) {
        int minSum = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            minSum = min(minSum, recursion(a, m - 1, i));
        }
        return minSum;
    }

    /**
     * Tính minSum tại cell a[row][col]. Tại ô này sẽ có 3 ô trên nó có thể rơi xuống là
     * a[row-1][i-1], a[row-1][i], a[row-1][i+1], cần tìm minSum khi rơi từ hàng trên cùng xuống 3 ô đó,
     * rồi cộng với giá trị ô hiện tại là thu được kết quả
     */
    public int recursion(int[][] a, int row, int col) {
        // base case
        if (row == 0)
            return a[row][col];

        // boundary cases are (col == 0) and (col == n-1)
        int aboveLeft = col == 0 ? Integer.MAX_VALUE : recursion(a, row - 1, col - 1);
        int aboveUp = recursion(a, row - 1, col);
        int aboveRight = col == n - 1 ? Integer.MAX_VALUE : recursion(a, row - 1, col + 1);

        return a[row][col] + min(aboveLeft, aboveUp, aboveRight);
    }

    /**
     * Tối ưu đệ quy top down dùng memo
     * 
     * Result:
     * Runtime 2 ms Beats 79.18%
     * Memory 43.1 MB Beats 60.39%
     */
    public int dp_topDown(int[][] a) {
        int minSum = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            minSum = min(minSum, dp_topDown(a, m - 1, i));
        }
        return minSum;
    }

    /**
     * Clone từ method recursion, chỉ dùng thêm chỗ memo[row][col] mà thôi
     * Note again: nhớ rename lại các method khi gọi đệ quy nhé, đừng có gọi đệ quy vào recursion mà
     * phải gọi đệ quy vào chính method này
     */
    public int dp_topDown(int[][] a, int row, int col) {
        // base case
        if (row == 0)
            return a[row][col];

        if (memo[row][col] == UNSET) {
            int aboveLeft = col == 0 ? Integer.MAX_VALUE : dp_topDown(a, row - 1, col - 1);
            int aboveUp = dp_topDown(a, row - 1, col);
            int aboveRight = col == n - 1 ? Integer.MAX_VALUE : dp_topDown(a, row - 1, col + 1);

            memo[row][col] = a[row][col] + min(aboveLeft, aboveUp, aboveRight);
        }

        return memo[row][col];
    }

    /**
     * Idea: DP bottom up, khá đơn giản, ta sẽ tính minSum từ row 0 -> row n-1, bởi vì minSum tại ô
     * [i][j] chỉ phụ thuộc vào minSum của 3 ô ở hàng trước đó, do đó dùng 2 vòng for để tính toán từ
     * row 0 -> n-1 là có thể thực hiện được
     * 
     * Result:
     * 
     * Runtime 4 ms Beats 65.22%
     * Memory 42.8 MB Beats 93.19%
     * 
     * WTF??? Tại sao lại chậm hơn top down chứ???
     */
    public int dp_bottomUp_memo(int[][] a) {
        int minSum = Integer.MAX_VALUE;

        // tìm minSum cho tất cả các cell[i][j], là sum tối thiểu từ hàng đầu đi tới ô đó
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (row == 0)
                    memo[row][col] = a[row][col];
                else {
                    int aboveLeft = col == 0 ? Integer.MAX_VALUE : memo[row - 1][col - 1];
                    int aboveUp = memo[row - 1][col];
                    int aboveRight = col == n - 1 ? Integer.MAX_VALUE : memo[row - 1][col + 1];

                    memo[row][col] = a[row][col] + min(aboveLeft, aboveUp, aboveRight);
                }
            }
        }

        // sau đó return minSum bé nhất trong các ô ở hàng cuối cùng (chính là memo[m-1] bé nhất)
        for (int i = 0; i < n; i++) {
            minSum = min(minSum, memo[m - 1][i]);
        }
        return minSum;
    }

    /**
     * Loại bỏ các biến aboveLeft, aboveUp, aboveRight, nhưng time còn tệ hơn:
     * Runtime 7 ms Beats 16.66%
     */
    public int dp_bottomUp_memo2(int[][] a) {
        int minSum = Integer.MAX_VALUE;

        // tìm minSum cho tất cả các cell[i][j], là sum tối thiểu từ hàng đầu đi tới ô đó
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (row == 0)
                    memo[row][col] = a[row][col];
                else {
                    memo[row][col] = a[row][col] + memo[row - 1][col];
                    if (col > 0)
                        memo[row][col] = min(memo[row][col], a[row][col] + memo[row - 1][col - 1]);
                    if (col < n - 1)
                        memo[row][col] = min(memo[row][col], a[row][col] + memo[row - 1][col + 1]);
                }
            }
        }

        // sau đó return minSum bé nhất trong các ô ở hàng cuối cùng (chính là memo[m-1] bé nhất)
        for (int i = 0; i < n; i++) {
            minSum = min(minSum, memo[m - 1][i]);
        }
        return minSum;
    }

    private int min(int a, int b) {
        return a < b ? a : b;
    }

    private int min(int a, int b, int c) {
        return min(a, min(b, c));
    }

    public static void main(String[] args) {
        MinimumFallingPathSum_931 app = new MinimumFallingPathSum_931();
        System.out.println(app.minFallingPathSum(new int[][] {{2, 1, 3}, {6, 5, 4}, {7, 8, 9}})); // 13
    }
}
