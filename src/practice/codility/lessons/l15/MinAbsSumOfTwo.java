package practice.codility.lessons.l15;

import java.util.Arrays;

/**
 * Let A be a non-empty array consisting of N integers.
 * 
 * The abs sum of two for a pair of indices (P, Q) is the absolute value |A[P] + A[Q]|, for 0 ≤ P ≤
 * Q < N.
 * 
 * For example, the following array A:
 * 
 * A[0] = 1
 * A[1] = 4
 * A[2] = -3
 * has pairs of indices (0, 0), (0, 1), (0, 2), (1, 1), (1, 2), (2, 2).
 * The abs sum of two for the pair (0, 0) is A[0] + A[0] = |1 + 1| = 2.
 * The abs sum of two for the pair (0, 1) is A[0] + A[1] = |1 + 4| = 5.
 * The abs sum of two for the pair (0, 2) is A[0] + A[2] = |1 + (−3)| = 2.
 * The abs sum of two for the pair (1, 1) is A[1] + A[1] = |4 + 4| = 8.
 * The abs sum of two for the pair (1, 2) is A[1] + A[2] = |4 + (−3)| = 1.
 * The abs sum of two for the pair (2, 2) is A[2] + A[2] = |(−3) + (−3)| = 6.
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given a non-empty array A consisting of N integers, returns the minimal abs sum of two for
 * any pair of indices in this array.
 * 
 * For example, given the following array A:
 * 
 * A[0] = 1
 * A[1] = 4
 * A[2] = -3
 * the function should return 1, as explained above.
 * 
 * Given array A:
 * 
 * A[0] = -8
 * A[1] = 4
 * A[2] = 5
 * A[3] =-10
 * A[4] = 3
 * the function should return |(−8) + 5| = 3.
 * -10 -8 3 4 5
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N is an integer within the range [1..100,000];
 * - each element of array A is an integer within the range [−1,000,000,000..1,000,000,000].
 */
public class MinAbsSumOfTwo {
    /**
     * Cách đơn giản nhất là tính tổng abs của tất cả các cặp và so sánh để tìm ra tổng abs nhỏ nhất
     * O(n^2)
     * Score: 36%
     */
    public int solution_timeout(int[] a) {
        int minAbs = Integer.MAX_VALUE;
        int currAbs;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                currAbs = Math.abs(a[i] + a[j]);
                minAbs = Math.min(minAbs, currAbs);
            }
        }
        return minAbs;
    }

    /**
     * Ref: https://github.com/ZRonchy/Codility/blob/master/Lesson13/MinAbsSumOfTwo.java
     * Cách giải này đúng là phải dùng Caterpillar Method
     * O(n * log(n))
     */
    public int solution(int[] a) {
        Arrays.sort(a);
        int back = 0;
        int front = a.length - 1;
        int minAbs = Math.abs(a[back] + a[front]);
        int currAbs, currSum;
        while (back <= front) {
            currSum = a[back] + a[front];
            currAbs = Math.abs(currSum);
            minAbs = Math.min(minAbs, currAbs);
            if (currSum < 0)
                back++;
            else
                front--;
        }
        return minAbs;
    }

    public static void main(String[] args) {
        System.out.println(new MinAbsSumOfTwo().solution(new int[] {1, 4, -3})); // 1
        System.out.println(new MinAbsSumOfTwo().solution(new int[] {-8, 4, 5, -10, 3})); // 3
        System.out.println(new MinAbsSumOfTwo().solution(new int[] {-8, 4, 5, -10, 3, 8})); // 0
    }
}
