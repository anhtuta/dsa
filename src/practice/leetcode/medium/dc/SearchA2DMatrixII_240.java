package practice.leetcode.medium.dc;

public class SearchA2DMatrixII_240 {
    public boolean searchMatrix(int[][] matrix, int target) {
        // return searchMatrix_dc(matrix, 0, 0, matrix.length - 1, matrix[0].length - 1, target);
        return searchMatrix_twoPointers(matrix, target);
    }

    /**
     * Idea: Divide and conquer and binary search. Firstly, we divide the matrix into 4 zones
     * zone 1 ---- zone 2
     * * * * * | * * * * *
     * * * * * | * * * * *
     * * * * * | * * * * *
     * * * * * | * * * * *
     * -------------------
     * * * * * | * * * * *
     * * * * * | * * * * *
     * * * * * | * * * * *
     * * * * * | * * * * *
     * zone 3 ---- zone 4
     * 
     * Sau đó làm giống như binary search trên mảng 1D
     * 
     * Xem 2 ảnh images/240_SearchA2DMatrixII-searching-5.png,
     * images/240_SearchA2DMatrixII-searching-20.png
     * 
     * Bài này ko khó, ý tưởng có thể nhìn thấy luôn, chỉ phức tạp ở chỗ tìm vị trí bắt đầu và kết thúc
     * của matrix con muốn duyệt
     * 
     * Ref:
     * https://leetcode.com/problems/search-a-2d-matrix-ii/solutions/66147/java-an-easy-to-understand-divide-and-conquer-method/
     * 
     * Result:
     * Runtime 24 ms Beats 5.34%
     * Memory 48.3 MB Beats 53.9%
     * 
     * Time: O((m*n)log4(3)), với size của matrix là m * n
     * 
     * Tại sao lại quá chậm như vậy???
     */
    public boolean searchMatrix_dc(int[][] matrix, int startRow, int startCol, int endRow, int endCol, int target) {
        // System.out.printf("Searching from (%d, %d) to (%d, %d)%n", startRow, startCol, endRow, endCol);
        if (startRow > endRow || startCol > endCol)
            return false;

        int midRow = startRow + (endRow - startRow) / 2;
        int midCol = startCol + (endCol - startCol) / 2;

        if (matrix[midRow][midCol] == target) {
            System.out.printf("Found target at matrix[%d][%d]%n", midRow, midCol);
            return true;
        } else if (matrix[midRow][midCol] < target) { // discard zone 1
            return searchMatrix_dc(matrix, startRow, midCol + 1, midRow, endCol, target) || // zone 2
                    searchMatrix_dc(matrix, midRow + 1, startCol, endRow, midCol, target) || // zone 3
                    searchMatrix_dc(matrix, midRow + 1, midCol + 1, endRow, endCol, target); // zone 4
        } else { // discard zone 4
            return searchMatrix_dc(matrix, startRow, startCol, midRow - 1, midCol - 1, target) || // zone 1
                    searchMatrix_dc(matrix, startRow, midCol, midRow - 1, endCol, target) || // zone 2
                    searchMatrix_dc(matrix, midRow, startCol, endRow, midCol - 1, target); // zone 3
        }
    }

    /**
     * Idea: dùng 2 con trỏ đại diện cho 1 cell để duyệt, bắt đầu duyệt từ cell top right
     * - Nếu cell hiện tại < target: target ko thể ở trên toàn bộ row này, vì row này sort tăng dần, nên
     * cell hiện tại là giá trị LỚN nhất rồi => cần chuyển sang row tiếp theo: row++
     * - Nếu cell hiện tại > target: target ko thể ở trên toàn bộ col này, vì col này sort tăng dần, nên
     * cell hiện tại là giá trị BÉ nhất rồi => cần chuyển sang col tiếp theo: col--
     * 
     * Tại sao lại dùng 2 con trỏ duyệt từ cell top right? Giống như 2 con trỏ trên mảng 1 chiều, sau
     * khi check ta sẽ tăng hoặc giảm 1 con trỏ, nếu start cả 2 con trỏ từ đầu, thì biết tăng hay giảm
     * con trỏ nào? Tương tự với bài này, nếu start tại ô (0, 0) hoặc (m-1,n-1), thì sau đó sẽ duyệt tới
     * ô tiếp theo nào? Vì ô (0, 0) nhảy sang ô nào tiếp theo cũng như nhau: đều là ô lớn hơn nó
     * Nếu bắt đầu duyệt từ ô (0, m-1), thì 2 ô cạnh nó sẽ là 1 ô lớn hơn 1 ô nhỏ hơn, do đó ta có 2
     * case để duyệt tiếp
     * 
     * Ref:
     * https://leetcode.com/problems/search-a-2d-matrix-ii/solutions/66140/my-concise-o-m-n-java-solution/
     * 
     * Result:
     * Runtime 4 ms Beats 100%
     * Memory 48.2 MB Beats 66.13%
     * 
     * Time: O(m+n)
     */
    public boolean searchMatrix_twoPointers(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length - 1;
        while (row <= matrix.length - 1 && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                row++;
            } else {
                col--;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SearchA2DMatrixII_240 app = new SearchA2DMatrixII_240();
        int[][] matrix = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        System.out.println(app.searchMatrix(matrix, 0)); // false
        System.out.println(app.searchMatrix(matrix, 5)); // true
        System.out.println(app.searchMatrix(matrix, 20)); // false
        System.out.println(app.searchMatrix(matrix, 26)); // true
    }
}
