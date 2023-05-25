package practice.leetcode;

/**
 * Cách duyệt toàn bộ các cell của 1 matrix chỉ với 1 vòng for
 */
public class MatrixTraversal {
    public static void main(String[] args) {
        System.out.println("Square matrix");
        final int MATRIX_SIZE = 4;
        for (int i = 0; i < MATRIX_SIZE * MATRIX_SIZE; i++) {
            System.out.printf("Moving to cell[%d][%d]%n", i / MATRIX_SIZE, i % MATRIX_SIZE);
        }

        System.out.println("\nRectangle matrix");
        final int MATRIX_HEIGHT = 3; // số lượng row
        final int MATRIX_WIDTH = 5; // số lượng column
        // Số lượng row KHÔNG cần dùng để tính toán vị trí cell, nó chỉ dùng để giới hạn vòng loop thôi
        for (int i = 0; i < MATRIX_HEIGHT * MATRIX_WIDTH; i++) {
            System.out.printf("Moving to cell[%d][%d]%n", i / MATRIX_WIDTH, i % MATRIX_WIDTH);
        }
    }
}
