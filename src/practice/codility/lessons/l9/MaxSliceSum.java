package practice.codility.lessons.l9;

/**
 * A non-empty array A consisting of N integers is given. A pair of integers (P, Q),
 * such that 0 ≤ P ≤ Q < N, is called a slice of array A.
 * The sum of a slice (P, Q) is the total of A[P] + A[P+1] + ... + A[Q].
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given an array A consisting of N integers, returns the maximum sum of any slice of A.
 * 
 * For example, given array A such that:
 * 
 * A[0] = 3
 * A[1] = 2
 * A[2] = -6
 * A[3] = 4
 * A[4] = 0
 * the function should return 5 because:
 * 
 * (3, 4) is a slice of A that has sum 4,
 * (2, 2) is a slice of A that has sum −6,
 * (0, 1) is a slice of A that has sum 5,
 * no other slice of A has sum greater than (0, 1).
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N is an integer within the range [1..1,000,000];
 * - each element of array A is an integer within the range [−1,000,000..1,000,000];
 * - the result will be an integer within the range [−2,147,483,648..2,147,483,647].
 * 
 * =====
 * Bài này chính là bài Heaviest_Subsequence trong sách CTDL&GT của thầy Nghĩa, dùng DP để giải
 */
public class MaxSliceSum {
    public int solution(int[] a) {
        // kq của bài toán, ban đầu khởi tạo nó = phần tử đầu tiên
        int smax = a[0];

        // max sum của slice kết thúc tại a[i], ban đầu khởi tạo nó = max sum của slice kết thúc tại a[0]
        int mEH = a[0];

        for (int i = 1; i < a.length; i++) {
            mEH = mEH > 0 ? mEH + a[i] : a[i];
            if (mEH > smax)
                smax = mEH;
        }

        return smax;
    }

    public static void main(String[] args) {
        System.out.println(new MaxSliceSum().solution(new int[] {3, 2, -6, 4, 0}));
    }
}
