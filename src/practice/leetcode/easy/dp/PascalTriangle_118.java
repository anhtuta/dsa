package practice.leetcode.easy.dp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/pascals-triangle/
 */
public class PascalTriangle_118 {
    private int[][] memo;

    /**
     * Idea: recursion, but timeout. We will calculate every single position of the triangle with
     * following formula:
     * triangle(row, col) = triangle(row - 1, col - 1) + triangle(row - 1, col);
     * 
     * Time Limit Exceeded
     * 29 / 30 testcases passed
     */
    public List<List<Integer>> generate_recursion(int numRows) {
        List<List<Integer>> res = new ArrayList<>(numRows);
        for (int i = 1; i <= numRows; i++) {
            List<Integer> row = new LinkedList<>();
            for (int j = 1; j <= i; j++) {
                row.add(pascalTriangle(i, j));
            }
            res.add(row);
        }
        return res;
    }

    private int pascalTriangle(int row, int col) {
        // Các node ở 2 cạnh trái phải luôn = 1
        if (col == 1 || col == row)
            return 1;
        return pascalTriangle(row - 1, col - 1) + pascalTriangle(row - 1, col);
    }

    /**
     * Idea: dp top down + memo. Similar to fibonacci problem
     * 
     * Runtime 1 ms Beats 89.52%
     * Memory 41.4 MB Beats 24.91%
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>(numRows);
        memo = new int[numRows + 1][numRows + 1];
        for (int i = 1; i <= numRows; i++) {
            List<Integer> row = new LinkedList<>();
            for (int j = 1; j <= i; j++) {
                row.add(pascalTriangle2(i, j));
            }
            res.add(row);
        }
        return res;
    }

    private int pascalTriangle2(int row, int col) {
        // Các node ở 2 cạnh trái phải luôn = 1
        if (col == 1 || col == row)
            return 1;
        if (memo[row][col] == 0) {
            memo[row][col] = pascalTriangle2(row - 1, col - 1) + pascalTriangle2(row - 1, col);
        }
        return memo[row][col];
    }
}
