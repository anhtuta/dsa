package practice.codility.lessons.l15;

import java.util.Arrays;

/**
 * A non-empty array A consisting of N numbers is given. The array is sorted in non-decreasing
 * order. The absolute distinct count of this array is the number of distinct absolute values among
 * the elements of the array.
 * 
 * For example, consider array A such that:
 * 
 * A[0] = -5
 * A[1] = -3
 * A[2] = -1
 * A[3] = 0
 * A[4] = 3
 * A[5] = 6
 * The absolute distinct count of this array is 5, because there are 5 distinct absolute values
 * among the elements of this array, namely 0, 1, 3, 5 and 6.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given a non-empty array A consisting of N numbers, returns absolute distinct count of array
 * A.
 * 
 * For example, given array A such that:
 * 
 * A[0] = -5
 * A[1] = -3
 * A[2] = -1
 * A[3] = 0
 * A[4] = 3
 * A[5] = 6
 * the function should return 5, as explained above.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N is an integer within the range [1..100,000];
 * - each element of array A is an integer within the range [−2,147,483,648..2,147,483,647];
 * - array A is sorted in non-decreasing order.
 * 
 * =====
 * absolute value: giá trị tuyệt đối, chính là hàm Math.abs đó
 * Bài này yêu cầu tìm số lượng các abs khác nhau (distinct absolute values), ex:
 * abs([-5, -3, -1, 0, 3, 6]) => [5, 3, 1, 0, 3, 6]
 * => có 5 giá trị khác nhau là 5, 3, 1, 0, 6
 * => cần phải return 5
 */
public class AbsDistinct {
    /**
     * Solution này chả liên quan đến Caterpillar Method. Cách làm rất đơn giản:
     * - Tạo 1 dãy mới = abs từ dãy input
     * - Sort dãy mới và đếm số lượng các số distinct
     */
    public int solution(int[] a) {
        int[] b = new int[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = Math.abs(a[i]);
        }

        Arrays.sort(b);
        int countDistinct = 1;
        int curr = b[0];
        int start = 1;
        while (start < b.length) {
            if (b[start] != curr) {
                curr = b[start];
                countDistinct++;
            }
            start++;
        }
        return countDistinct;
    }

    public static void main(String[] args) {
        System.out.println(new AbsDistinct().solution(new int[] {-5, -3, -1, 0, 3, 6}));
    }
}
