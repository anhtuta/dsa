package practice.codility.lessons.l4;

/**
 * A non-empty array A consisting of N integers is given.
 * 
 * A permutation is a sequence containing each element from 1 to N once, and only once.
 * 
 * For example, array A such that:
 * 
 * A[0] = 4
 * A[1] = 1
 * A[2] = 3
 * A[3] = 2
 * is a permutation, but array A such that:
 * 
 * A[0] = 4
 * A[1] = 1
 * A[2] = 3
 * is not a permutation, because value 2 is missing.
 * 
 * The goal is to check whether array A is a permutation.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given an array A, returns 1 if array A is a permutation and 0 if it is not.
 * 
 * For example, given array A such that:
 * 
 * A[0] = 4
 * A[1] = 1
 * A[2] = 3
 * A[3] = 2
 * the function should return 1.
 * 
 * Given array A such that:
 * 
 * A[0] = 4
 * A[1] = 1
 * A[2] = 3
 * the function should return 0.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N is an integer within the range [1..100,000];
 * - each element of array A is an integer within the range [1..1,000,000,000].
 */
public class PermCheck {
    /**
     * Nếu là permutation thì mảng a phải bao gồm các số <= n, với n = a.length,
     * do đó có thể dùng cntArr để đếm được. Và mảng cntArr bắt buộc chỉ gồm các phần tử = 1
     * (tức là mọi có phần tử trong mảng a chỉ được xuất hiện 1 lần)
     */
    public int solution(int[] a) {
        int len = a.length;
        int[] cntArr = new int[len + 1];
        for (int i = 0; i < len; i++) {
            if (a[i] > len)
                return 0;
            cntArr[a[i]]++;
        }
        for (int i = 1; i <= len; i++) {
            if (cntArr[i] != 1)
                return 0;
        }
        return 1;
    }

    public static void main(String[] args) {
        int[] a = {4, 1, 3, 2};
        System.out.println(new PermCheck().solution(a));
    }
}
