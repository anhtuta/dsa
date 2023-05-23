package practice.leetcode.medium.matrix;

/**
 * https://leetcode.com/problems/valid-sudoku/
 */
public class ValidSudoku_36 {
    /**
     * Dùng hàm có sẵn bên bài {@link practice.leetcode.hard.backtracking.SudokuSolver_37}
     * 
     * Time: O(n^3)
     * Runtime 1 ms Beats 100%
     * Memory 43.5 MB Beats 15.45%
     */
    public boolean isValidSudoku(char[][] board) {
        char c;
        // Duyệt tất cả các ô, sau đó check valid từng ô một
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == '.')
                    continue;

                // Đầu tiên clear value cho ô cần check bằng cách gán value cho nó = '.'
                c = board[i][j];
                board[i][j] = '.';

                // Sau đó check xem liệu có thể đặt giá trị c vào ô đó hay ko
                boolean isValid = isValidCandidate(board, i, j, c);

                // Restore lại giá trị ban đầu của ô đó
                board[i][j] = c;

                if (!isValid)
                    return false;
            }
        }
        return true;
    }

    /**
     * Check if character c could be placed at position [row][col]
     */
    private boolean isValidCandidate(char[][] board, int row, int col, char c) {
        for (int i = 0; i < board.length; i++) {
            // check row
            if (board[i][col] == c)
                return false;

            // check column
            if (board[row][i] == c)
                return false;

            // check 3x3 sub-boxes
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ValidSudoku_36 app = new ValidSudoku_36();
        char[][] board = new char[][] {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        System.out.println(app.isValidSudoku(board)); // true

        board[0][0] = '8';
        System.out.println(app.isValidSudoku(board)); // false
    }
}
