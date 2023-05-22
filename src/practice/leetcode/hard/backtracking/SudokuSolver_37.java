package practice.leetcode.hard.backtracking;

import util.Utils;

/**
 * https://leetcode.com/problems/sudoku-solver/
 */
public class SudokuSolver_37 {
    private static final char NOT_VISITED = '.';
    private static final int BOARD_SIZE = 9; // a square 9x9
    private boolean isFoundSolution;

    public void solveSudoku(char[][] board) {
        isFoundSolution = false;
        backtrack(board, 0, 0);
    }

    /**
     * Runtime 17 ms Beats 48.94%
     * Memory 39.9 MB Beats 85.25%
     * 
     * Sao lại chậm thế nhỉ?
     */
    private void backtrack(char[][] board, int row, int col) {
        if (row >= BOARD_SIZE) {
            isFoundSolution = true;
            return;
        }

        if (board[row][col] != NOT_VISITED) {
            // Ô này đã được điền số từ đề bài rồi
            // Go to next position and find candidate for it
            if (col < 8) {
                backtrack(board, row, col + 1);
            } else {
                backtrack(board, row + 1, 0);
            }
            return;
        }

        char c;
        for (int i = 1; i <= BOARD_SIZE; i++) {
            c = (char) (i + 48); // 48 là mã ASCII của số 0
            if (isValidCandidate(board, row, col, c)) {
                board[row][col] = c;

                // Go to next position and find candidate for it
                if (col < 8) {
                    backtrack(board, row, col + 1);
                } else {
                    backtrack(board, row + 1, 0);
                }

                // KHÔNG remove candidate để quay lại backtrack nếu đã tìm được đáp án
                if (!isFoundSolution) {
                    board[row][col] = NOT_VISITED;
                }
            }
        }
    }

    /**
     * Check if character c could be placed at position [row][col]
     */
    private boolean isValidCandidate(char[][] board, int row, int col, char c) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            // check row
            if (board[i][col] == c)
                return false;

            // check column
            if (board[row][i] == c)
                return false;

            // check 3x3 sub-boxes, công thức này tham khảo từ:
            // https://leetcode.com/problems/sudoku-solver/solutions/15752/straight-forward-java-solution-using-backtracking/
            // Note: KHÔNG phải check ô đó != '.' trước, bởi vì ta check ô hiện tại [row][col] trước khi set
            // value cho nó, mà ô này chắc chắn có value = '.', tức là nó đã != c rồi
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        char c = (char) 5 + 48;
        System.out.println(c);
        SudokuSolver_37 app = new SudokuSolver_37();

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
        app.solveSudoku(board);
        Utils.printArray(board);
    }
}
