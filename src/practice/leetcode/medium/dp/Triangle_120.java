package practice.leetcode.medium.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/triangle/
 */
public class Triangle_120 {
    private int memo[][];

    /**
     * Giống hệt bài {@link MinimumFallingPathSum_931}, chỉ khác 1 chút là grid có hình tam giác. Do đó,
     * từ 1 cell (i,j) ta chỉ có thể đi xuống 2 ô phía dưới là:
     * - Ô ngay dưới chân nó: (i+1,j)
     * - Ô bên PHẢI ô dưới chân nó: (i+1, j+1)
     * 
     * Do đó, ở ô hiện tại (i,j), có 2 cách để đi đến nó là:
     * - Ô ngay trên nó: (i-1,j)
     * - Ô bên TRÁI ô ngay trên nó: (i-1, j-1)
     * 
     * minSum đi đến ô (i,j) chính là min của minSum đi đến 2 ô đó + giá trị tại ô (i,j)
     * 
     * Result:
     * Runtime 3 ms Beats 69.2%
     * Memory 42 MB Beats 89.65%
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int m = triangle.size(), n;
        memo = new int[m][m];
        int minSum = Integer.MAX_VALUE;

        // tìm minSum cho tất cả các cell[i][j], là sum tối thiểu từ hàng đầu đi tới ô đó
        for (int i = 0; i < m; i++) {
            n = triangle.get(i).size();
            for (int j = 0; j < n; j++) {
                if (i == 0)
                    memo[i][j] = triangle.get(i).get(j);
                else {
                    int aboveLeft = j == 0 ? Integer.MAX_VALUE : memo[i - 1][j - 1];
                    int aboveUp = j == n - 1 ? Integer.MAX_VALUE : memo[i - 1][j];
                    memo[i][j] = triangle.get(i).get(j) + min(aboveLeft, aboveUp);
                }
                System.out.printf("memo[%d][%d] = %d%n", i, j, memo[i][j]);
            }
            System.out.println();
        }

        // sau đó return minSum bé nhất trong các ô ở hàng cuối cùng (chính là memo[m-1] bé nhất)
        for (int i = 0; i < triangle.get(m - 1).size(); i++) {
            minSum = min(minSum, memo[m - 1][i]);
        }
        return minSum;
    }

    private int min(int a, int b) {
        return a < b ? a : b;
    }

    public static void main(String[] args) {
        Triangle_120 app = new Triangle_120();
        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(Arrays.asList(2));
        triangle.add(Arrays.asList(3, 4));
        triangle.add(Arrays.asList(6, 5, 7));
        triangle.add(Arrays.asList(4, 1, 8, 3));
        System.out.println(app.minimumTotal(triangle)); // 11
    }
}
