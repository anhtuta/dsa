package practice.leetcode;

public class PascalTriangle {
    public static int pascalTriangle(int row, int col) {
        // Các node ở 2 cạnh trái phải luôn = 1
        if (col == 1 || col == row)
            return 1;
        return pascalTriangle(row - 1, col - 1) + pascalTriangle(row - 1, col);
    }

    public static void main(String[] args) {
        System.out.println(pascalTriangle(5, 3)); // 6
        System.out.println("\n==============\n");

        int size = 10;
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (i < j)
                    break;
                System.out.printf("%2d ", pascalTriangle(i, j));
            }
            System.out.println();
        }
    }
}
