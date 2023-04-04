package practice.codility.lessons.l9;

/**
 * Level: Medium
 * 
 * A non-empty array A consisting of N integers is given.
 * 
 * A triplet (X, Y, Z), such that 0 ≤ X < Y < Z < N, is called a double slice.
 * 
 * The sum of double slice (X, Y, Z) is the total of:
 * A[X + 1] + A[X + 2] + ... + A[Y − 1] + A[Y + 1] + A[Y + 2] + ... + A[Z − 1].
 * (KHÔNG bao gồm các phần tử ở biên A[X], A[Y], A[Z])
 * 
 * For example, array A such that:
 * 
 * A[0] = 3
 * A[1] = 2
 * A[2] = 6
 * A[3] = -1
 * A[4] = 4
 * A[5] = 5
 * A[6] = -1
 * A[7] = 2
 * 
 * contains the following example double slices:
 * - double slice (0, 3, 6), sum is 2 + 6 + 4 + 5 = 17,
 * - double slice (0, 3, 7), sum is 2 + 6 + 4 + 5 − 1 = 16,
 * - double slice (3, 4, 5), sum is 0.
 * 
 * The goal is to find the maximal sum of any double slice.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given a non-empty array A consisting of N integers, returns the maximal sum of any double
 * slice.
 * 
 * For example, given:
 * 
 * A[0] = 3
 * A[1] = 2
 * A[2] = 6
 * A[3] = -1
 * A[4] = 4
 * A[5] = 5
 * A[6] = -1
 * A[7] = 2
 * the function should return 17, because no double slice of array A has a sum of greater than 17.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N is an integer within the range [3..100,000];
 * - each element of array A is an integer within the range [−10,000..10,000].
 * 
 * =====
 * Bài này khác bài MaxSliceSum ở chỗ:
 * - Cần tính max sum của 2 slice thay vì 1
 * - Tổng của 1 slice KHÔNG bao gồm 2 số ở biên
 * - 2 slice đó, phải LIỀN KỀ nhau
 */
public class MaxDoubleSliceSum {
    private int max(int a, int b, int c) {
        return Math.max(a, b > c ? b : c);
    }

    /**
     * Ref: https://rafal.io/posts/codility-max-double-slice-sum.html
     * 
     * Do 2 slice liền kề nhau nên cách làm cũng ko quá khó. Kí hiệu:
     * - k1[i] là slice lớn nhất KẾT THÚC tại i, bỏ k1[0]
     * - k2[i] là slice lớn nhất BẮT ĐẦU tại i, bỏ k2[n-1]
     * 
     * Việc tính mảng k1 giống như bài MaxSliceSum, còn k2 cũng tương tự nhưng duyệt từ cuối dãy. Việc
     * bỏ k1[0], k2[n-1] bởi vì slice trong bài này ko tính 2 số đầu cuối của slice. PHẢI BỎ NHÉ, nếu
     * tính toán cả 2 số này sẽ bị SAI
     * 
     * Note: KHÔNG cần dùng mEH nữa, vì mEH chính là 2 mảng k1[], k2[]
     * 
     * Duyệt thêm lần nữa, ký hiệu k[i] là max 2 slice tại i (chính là mảng [X, i, Z] như đề bài)
     * k[i] = max(k1[i-1] + k2[i+1], k1[i-1], k2[i+1])
     * 
     * Tại sao k[i] ko chỉ đơn thuần là = k1[i-1] + k2[i+1]?
     * Bởi vì ta tính thêm trường hợp 1 slice rỗng nữa:
     * - k[i] = k1[i-1] khi slice bên PHẢI rỗng, ex: [X,Y,Z] = [1,5,6]
     * - k[i] = k2[i+1] khi slice bên TRÁI rỗng, ex: [X,Y,Z] = [1,2,6]
     * 
     * Đây chính là mấu chốt và cũng là 2 case đặc biệt nhất của bài toán!
     */
    public int solution(int[] a) {
        int n = a.length;
        int[] k1 = new int[n];
        int[] k2 = new int[n];

        k1[1] = a[1];
        k2[n - 2] = a[n - 2];

        for (int i = 2; i < n; i++) {
            k1[i] = Math.max(k1[i - 1] + a[i], a[i]);
        }

        for (int i = n - 3; i >= 0; i--) {
            k2[i] = Math.max(k2[i + 1] + a[i], a[i]);
        }

        int max = Integer.MIN_VALUE;
        int currMax;

        // Chú ý: cần xét cả trường hợp 1 slice ko có phần tử nào, tức là [X,Y,Z] có 2 số là 2 số liên tiếp
        // nhau, ex: [1,2,6], do đó phải duyệt từ i = 1 -> n-2, nếu duyệt từ 2 -> n-3 là miss 2 case rồi
        for (int i = 1; i < n - 1; i++) {
            // max = Math.max(max, k1[i - 1] + k2[i + 1]);
            currMax = max(k1[i - 1] + k2[i + 1], k1[i - 1], k2[i + 1]);
            max = Math.max(max, currMax);
            // System.out.printf("i = %d, max = %d, k1[%d] = %d, k2[%d] = %d%n",
            // i, max, i - 1, k1[i - 1], i + 1, k2[i + 1]);
        }

        // Nếu max âm, thì 2 slice cần tìm sẽ là 3 số liên tiếp nhau (tức là ko có phần tử nào cả)
        return max > 0 ? max : 0;
    }

    public static void main(String[] args) {
        System.out.println(new MaxDoubleSliceSum().solution(new int[] {3, 2, 6, -1, 4, 5, -1, 2})); // 17
        System.out.println(new MaxDoubleSliceSum().solution(new int[] {5, 5, 5})); // 0
        System.out.println(new MaxDoubleSliceSum().solution(new int[] {-5, -5, -5})); // 0

        // [X,Y,Z] = [0,2,3]
        System.out.println(new MaxDoubleSliceSum().solution(new int[] {5, 17, 0, 3})); // 17

        // [X,Y,Z] = [0,2,3]
        System.out.println(new MaxDoubleSliceSum().solution(new int[] {0, 10, -5, -2, 0})); // 10
    }
}
