package practice.codility.lessons.l14;

/**
 * Level: Medium
 * 
 * You are given integers K, M and a non-empty array A consisting of N integers. Every element of
 * the array is not greater than M.
 * 
 * You should divide this array into K blocks of consecutive elements (K khối phần tử liên tiếp).
 * The size of the block is any integer between 0 and N. Every element of the array should belong to
 * some block.
 * 
 * The sum of the block from X to Y equals A[X] + A[X + 1] + ... + A[Y]. The sum of empty block
 * equals 0.
 * 
 * The large sum is the maximal sum of any block.
 * 
 * For example, you are given integers K = 3, M = 5 and array A such that:
 * 
 * A[0] = 2
 * A[1] = 1
 * A[2] = 5
 * A[3] = 1
 * A[4] = 2
 * A[5] = 2
 * A[6] = 2
 * The array can be divided, for example, into the following blocks:
 * 
 * - [2, 1, 5, 1, 2, 2, 2], [], [] with a large sum of 15;
 * - [2], [1, 5, 1, 2], [2, 2] with a large sum of 9;
 * - [2, 1, 5], [], [1, 2, 2, 2] with a large sum of 8;
 * - [2, 1], [5, 1], [2, 2, 2] with a large sum of 6.
 * 
 * The goal is to minimize the large sum. In the above example, 6 is the minimal large sum.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int K, int M, int[] A); }
 * 
 * that, given integers K, M and a non-empty array A consisting of N integers, returns the minimal
 * large sum.
 * 
 * For example, given K = 3, M = 5 and array A such that:
 * 
 * A[0] = 2
 * A[1] = 1
 * A[2] = 5
 * A[3] = 1
 * A[4] = 2
 * A[5] = 2
 * A[6] = 2
 * the function should return 6, as explained above.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N and K are integers within the range [1..100,000];
 * - M is an integer within the range [0..10,000];
 * - each element of array A is an integer within the range [0..M].
 * 
 * =====
 * Số M ở đây để làm gì? Large sum chắc chắn phải >= M, tức là: M <= Large sum <= sum(A).
 * Nhưng đề bài lại nói rằng: mọi phần tử của array ko lớn hơn M, tức là a[i] <= M, tức là có thể
 * mọi a[i] đều nhỏ hơn M
 * => Éo cần dùng M nữa, ta sẽ tính toán lại M = max(a[]) = phần tử lớn nhất của dãy
 * 
 * Dùng binary search để check Large sum trong khoảng [M, sumA]. Với mỗi giá trị Large sum, check
 * xem có bao nhiêu blocks có thể được tạo thành:
 * - Nếu số lượng block < k: OK, vì ta có thể dùng các block empty. Nhưng cần giảm Large sum xuống
 * để tìm giá trị tối ưu hơn (Large sum nhỏ hơn)
 * - Nếu số lượng block = k: OK (dù ko dùng tới block empty). Tương tự, cũng cần giảm Large sum và
 * tiếp tục tìm giá trị tối ưu hơn
 * - Nếu số lượng block > k: cần tăng largeSum lên
 */
public class MinMaxDivision {
    /**
     * Check if how many none-empty blocks could be created with given largeSum.
     * Note: largeSum is greater than all of a[i], because largeSum is in range [m, sumA]
     */
    private int countBlocks(int[] a, int largeSum) {
        int blocks = 0;
        int sumOfEachBlock = 0;
        for (int i = 0; i < a.length; i++) {
            if (sumOfEachBlock + a[i] > largeSum) {
                // this block cannot contains a[i], a[i] must belong to a new block
                sumOfEachBlock = 0;
                blocks++;
            }
            sumOfEachBlock += a[i];
        }

        // last block: there's no more a[i] left for it to calculate and check
        // whether "sumOfEachBlock + a[i] > largeSum" or not, so we need to check it here
        if (sumOfEachBlock <= largeSum)
            blocks++;

        return blocks;
    }

    /**
     * @param m not needed! Not used
     */
    public int solution(int k, int m, int[] a) {
        int sumA = 0;
        int maxA = -1;
        for (int i = 0; i < a.length; i++) {
            sumA += a[i];
            if (maxA < a[i])
                maxA = a[i];
        }

        // start, end, mid là khoảng search cho largeSum, cần tìm largeSum bé nhất trong khoảng này
        int start = maxA, end = sumA;
        int mid, blocks;
        while (start <= end) {
            mid = (start + end) / 2;
            blocks = countBlocks(a, mid);
            if (blocks <= k) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return start;
    }

    public static void main(String[] args) {
        System.out.println(new MinMaxDivision().solution(3, 5, new int[] {2, 1, 5, 1, 2, 2, 2})); // 6
        System.out.println(new MinMaxDivision().solution(1, 1, new int[] {0})); // 0
        System.out.println(new MinMaxDivision().solution(1, 9, new int[] {0, 1, 0, 1, 0})); // 2
        System.out.println(new MinMaxDivision().solution(2, 3, new int[] {0, 1, 0, 1, 0})); // 1
    }
}
