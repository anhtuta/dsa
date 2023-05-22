package practice.leetcode.hard.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/n-queens/
 * 
 * Bài toán N con hậu ngày trước đã được học, dùng backtracking
 */
public class NQueens_51 {
    // Bài này nếu dùng mảng 2 chiều [][] đại diện cho bàn cờ thì sẽ làm hơn thay vì mảng 1D
    private int[] rows; // Vị trí của Queen trên từng hàng, ex: [1, 3, 0, 2]

    private int n;

    private List<List<String>> res;

    public List<List<String>> solveNQueens(int n) {
        // Init
        this.n = n;
        rows = new int[n];
        for (int i = 0; i < n; i++) {
            rows[i] = -1;
        }
        res = new ArrayList<>(n);

        // Backtracking and update answer to res each time we find the solution
        backtracking(0); // Bắt đầu đặt Queen từ hàng đầu tiên, duyệt đến n-1 là xong
        return res;
    }

    /**
     * Runtime 4 ms Beats 55.72%
     * Memory 44.3 MB Beats 5.61%
     * 
     * Tại sao lại chậm hơn nhiều thế nhỉ
     */
    private void backtracking(int row) {
        // System.out.println("Row = " + row);
        for (int col = 0; col < n; col++) { // thử với từng col trên hàng row
            if (isValidCandidate(row, col)) {
                rows[row] = col; // Đặt Queen tại cột col của hàng row
                // Utils.printArray(rows);
                if (row == n - 1) {
                    buildSolution();
                } else {
                    backtracking(row + 1);
                }
                rows[row] = -1; // Bỏ Queen tại cột i của hàng row, để tiếp tục xét cột tiếp theo
            }
        }
    }

    /**
     * Check xem tại hàng thứ row, có đặt được Queen tại cột col hay ko, nếu được thì return true, sau
     * đó sẽ gán rows[row] = col
     */
    private boolean isValidCandidate(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (rows[i] == col)
                return false;
            if (Math.abs(rows[i] - col) == Math.abs(i - row))
                return false;
        }
        return true;
    }

    private void buildSolution() {
        List<String> eachRow = new ArrayList<>(n);
        StringBuilder builder;
        for (int i = 0; i < n; i++) {
            builder = new StringBuilder();
            for (int j = 0; j < n; j++) {
                builder.append(rows[j] == i ? "Q" : ".");
            }
            eachRow.add(builder.toString());
        }
        res.add(eachRow);
    }

    public static void main(String[] args) {
        NQueens_51 app = new NQueens_51();
        System.out.println(app.solveNQueens(4));
    }
}
