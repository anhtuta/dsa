package practice.codility;

import java.util.Arrays;

/**
 * https://app.codility.com/c/run/demoC74CD4-749/
 * 
 * This is a demo task.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given an array A of N integers, returns the smallest positive integer (greater than 0) that
 * does not occur in A.
 * 
 * For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
 * 
 * Given A = [1, 2, 3], the function should return 4.
 * 
 * Given A = [−1, −3], the function should return 1.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N is an integer within the range [1..100,000];
 * - each element of array A is an integer within the range [−1,000,000..1,000,000].
 */
public class MissingInteger {
    /**
     * Example test cases
     * Passed 3 out of 3
     * 
     * Correctness test cases
     * Passed 5 out of 5
     * 
     * Performance test cases
     * Passed 4 out of 4
     * 
     * Total score: 100%
     * 
     * Cách làm: sort xong duyệt từ đầu và tìm số dương bé nhất ko có trong dãy.
     * Cứ check kiểu vét cạn thôi, chú ý mấy case đặc biệt như trong hàm main
     */
    public int solution(int[] a) {
        Arrays.sort(a);
        boolean isCheckFirstPositive = false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] <= 0)
                continue;
            if (!isCheckFirstPositive) {
                isCheckFirstPositive = true;
                if (a[i] > 1)
                    return 1;
            }
            if (i < a.length - 1 && (a[i] == a[i + 1] || a[i] + 1 == a[i + 1]))
                continue;
            return a[i] + 1;
        }
        return a[a.length - 1] >= 0 ? a[a.length - 1] + 1 : 1;
    }

    public static void main(String[] args) {
        System.out.println(new MissingInteger().solution(new int[] {1, 3, 6, 4, 1, 2})); // 5
        System.out.println(new MissingInteger().solution(new int[] {-1, -3})); // 1
        System.out.println(new MissingInteger().solution(new int[] {1, 2, 3, 4, 5})); // 6
        System.out.println(new MissingInteger().solution(new int[] {1, 2, 3, 4, 6})); // 5
        System.out.println(new MissingInteger().solution(new int[] {1, 2, 3, 4, 5, 5, 5})); // 6
        System.out.println(new MissingInteger().solution(new int[] {2, 3, 4, 5})); // 1
        System.out.println(new MissingInteger().solution(new int[] {4, 5, 6, 8, 9, 0})); // 1
        System.out.println(new MissingInteger().solution(new int[] {0, 0, 0})); // 1
        System.out.println(new MissingInteger().solution(new int[] {9, 9, 9})); // 1
        System.out.println(new MissingInteger().solution(new int[] {1, 1, 1})); // 2
        System.out.println(new MissingInteger().solution(new int[] {1})); // 2
        System.out.println(new MissingInteger().solution(new int[] {0})); // 1
        System.out.println(new MissingInteger().solution(new int[] {-1})); // 1
        System.out.println(new MissingInteger().solution(new int[] {10})); // 1
    }
}
