package practice.codility.lessons.l2;

import java.util.Arrays;

/**
 * A non-empty array A consisting of N integers is given. The array contains an odd number of
 * elements, and each element of the array can be paired with another element that has the same
 * value, except for one element that is left unpaired.
 * 
 * For example, in array A such that:
 * 
 * A[0] = 9 A[1] = 3 A[2] = 9
 * A[3] = 3 A[4] = 9 A[5] = 7
 * A[6] = 9
 * 
 * - the elements at indexes 0 and 2 have value 9,
 * - the elements at indexes 1 and 3 have value 3,
 * - the elements at indexes 4 and 6 have value 9,
 * - the element at index 5 has value 7 and is unpaired.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given an array A consisting of N integers fulfilling the above conditions, returns the
 * value of the unpaired element.
 * 
 * For example, given array A such that:
 * 
 * A[0] = 9 A[1] = 3 A[2] = 9
 * A[3] = 3 A[4] = 9 A[5] = 7
 * A[6] = 9
 * the function should return 7, as explained in the example above.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N is an odd integer within the range [1..1,000,000];
 * - each element of array A is an integer within the range [1..1,000,000,000];
 * - all but one of the values in A occur an even number of times.
 */
public class OddOccurrencesInArray {
    /**
     * Dùng counting array, nhưng phần tử lớn nhất = 1 tỷ => mảng cntArr cũng phải có size = 1 tỷ
     * 
     * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
     * at Solution.solution(Solution.java:9)
     * at Exec.run(exec.java:46)
     * at Exec.main(exec.java:35)
     */
    public int solution_outOfMem(int[] a) {
        byte[] cntArr = new byte[1_000_000_000];
        for (int i = 0; i < a.length; i++) {
            cntArr[a[i]]++;
        }
        for (int i = 0; i < cntArr.length; i++) {
            if (cntArr[i] % 2 == 1)
                return i;
        }
        return 0;
    }

    /**
     * Dùng 2 vòng for lồng nhau và check từng phần tử với phần còn lại của mảng
     * => O(n^2)
     * 
     * Chắc chắn timeout, vì a[] có thể chứa 1 triệu phần tử,
     */
    public int solution_timeout(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > 0 && a[i] == a[j]) {
                    a[i] = 0;
                    a[j] = 0;
                    break;
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] > 0)
                return a[i];
        }
        return 0;
    }

    /**
     * Sort trước xong duyệt để tìm phần tử chỉ xuất hiện 1 lần
     * => O(n*log(n))
     */
    public int solution(int[] a) {
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            if (i == a.length - 1 || a[i] != a[i + 1]) {
                return a[i];
            } else {
                i++;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] a = {9, 3, 9, 3, 9, 7, 9};
        System.out.println(new OddOccurrencesInArray().solution(a));
        int[] b = {9, 3, 9, 3, 9, 11, 9};
        System.out.println(new OddOccurrencesInArray().solution(b));
    }
}
