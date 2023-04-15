package practice.codility.exercises.e4;

import java.util.Arrays;
import util.Utils;

/**
 * Level: Medium
 * 
 * An array A consisting of N integers is given. An inversion is a pair of indexes (P, Q) such that
 * P < Q and A[Q] < A[P].
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that computes the number of inversions in A, or returns −1 if it exceeds 1,000,000,000.
 * 
 * For example, in the following array:
 * 
 * A[0] = -1
 * A[1] = 6
 * A[2] = 3
 * A[3] = 4
 * A[4] = 7
 * A[5] = 4
 * there are four inversions:
 * 
 * (1,2) (1,3) (1,5) (4,5)
 * so the function should return 4.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N is an integer within the range [0..100,000];
 * - each element of array A is an integer within the range [−2,147,483,648..2,147,483,647].
 */
public class ArrayInversionCount {
    /**
     * Cách đơn giản nhất là 2 vòng for xong duyệt tất cả các cặp và đếm.
     * Timeout, score = 63% (Correctness: 100%, Performance: 20%)
     */
    public int solution_timeout(int[] a) {
        int cnt = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    cnt++;
                    if (cnt > 1_000_000_000)
                        return -1;
                }
            }
        }

        return cnt;
    }

    private int binarySearchFirst(int[] a, int key) {
        int start = 0, end = a.length - 1;
        int mid;
        int res = -1;
        while (start <= end) {
            mid = (start + end) / 2;
            if (a[mid] > key) {
                end = mid - 1;;
            } else if (a[mid] < key) {
                start = mid + 1;
            } else {
                res = mid;
                end--;
            }
        }
        return res;
    }

    /**
     * Algorithm is quite easy : create a sorted copy of the list, then for each value of the first
     * list, binary search its index in the sorted one
     * 
     * Cách này SAI hoàn toàn, vì với dãy [5,4,3], số 4 sau khi sort vẫn ở vị trí cũ, do đó cách này sẽ
     * đếm rằng ko có cặp inversion nào đi với số 4, trong khi thực tế có (4,3)
     */
    public int solution_wrong(int[] a) {
        int[] b = Arrays.copyOf(a, a.length);
        Arrays.sort(b);
        int cnt = 0;
        int sortedIndex;
        for (int i = 0; i < a.length; i++) {
            // sortedIndex = Arrays.binarySearch(b, a[i]);
            sortedIndex = binarySearchFirst(b, a[i]);
            if (sortedIndex > i)
                cnt = cnt + (sortedIndex - i);
        }
        return cnt;
    }

    int res;

    /**
     * Dùng merge sort để đếm trong lúc merge 2 arrays
     */
    private void mergeSort(int[] a, int left, int right) {
        if (left >= right)
            return;
        int mid = (left + right) / 2;
        mergeSort(a, left, mid);
        mergeSort(a, mid + 1, right);
        mergeArrays(a, left, mid, right);
    }

    /**
     * Merge two sorted arrays: a[left ... mid] and a[mid+1 ... right]
     * 
     * Cách vừa merge vừa đếm như sau:
     * Giả sử cần merge array a[] = [1 2 8 9 6 7 11 12], 2 array con là [0 ... 3] và [4 ... 7].
     * Gọi 2 dãy này lần lượt là x1, x2 (x1 = [1 2 8 9], x2 = [6 7 11 12])
     * Index i cho x1, j cho x2 (i = 0 -> 3, j = 4 -> 7), mid = 3
     * 
     * Tại i = 2, j = 4, a[2] = 8 > a[4] = 6, do đó cặp này là inversion.
     * 
     * Quan trọng: cách đếm đủ inversion:
     * Nhưng do cả 2 dãy đều đã được sort, nên số 6 kia sẽ tạo các inversion với TẤT CẢ các số còn lại
     * bên trong dãy x1 KỂ TỪ SỐ 8 đó, do đó tổng inversion đối với số 6 sẽ là 2: (8, 6) và (9, 6)
     * => Tổng số inversion với số 6 là mid - i + 1
     * 
     * Ref: https://codesays.com/2014/solution-to-array-inversion-count-by-codility/
     */
    private void mergeArrays(int[] a, int left, int mid, int right) {
        System.out.printf("%nMerging 2 arrays: [%d ... %d] and [%d ... %d]%n", left, mid, mid + 1, right);
        Utils.printArray(a);
        int[] b = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        // merge two arrays and count the inversions
        while (i <= mid && j <= right) {
            if (a[i] <= a[j]) {
                b[k++] = a[i++];
            } else {
                System.out.printf("Found %d inversions with: a[%d] = %d%n", (mid - i + 1), j, a[j]);
                b[k++] = a[j++];
                this.res = this.res + (mid - i + 1);
                // Để tối ưu hơn, có thể stop luôn tại đây nếu this.res > 1_000_000_000
            }
        }
        while (i <= mid) {
            b[k++] = a[i++];
        }
        while (j <= right) {
            b[k++] = a[j++];
        }

        // copy b to a
        i = left;
        for (k = 0; k < b.length; k++) {
            a[i++] = b[k];
        }
    }

    public int solution(int[] a) {
        this.res = 0;
        mergeSort(a, 0, a.length - 1);
        return this.res > 1_000_000_000 ? -1 : this.res;
    }

    public static void main(String[] args) {
        System.out.println(new ArrayInversionCount().solution(new int[] {-1, 6, 3, 4, 7, 4})); // 4
        System.out.println(new ArrayInversionCount().solution(new int[] {-1, 3, 4, 4, 6, 7})); // 0
        System.out.println(new ArrayInversionCount().solution(new int[] {1, 1, 1})); // 0
        System.out.println(new ArrayInversionCount().solution(new int[] {9, 8})); // 1
        System.out.println(new ArrayInversionCount().solution(new int[] {9, 8, 7, 6})); // 6
        System.out.println(new ArrayInversionCount().solution(new int[] {1, 2, 9, 8, 7, 6, 11, 12})); // 6
        System.out.println(new ArrayInversionCount().solution(new int[] {5, 4, 3})); // 3
        System.out.println(new ArrayInversionCount().solution(new int[] {5, 4, 3, 2, 1})); // 10
        System.out.println(new ArrayInversionCount().solution(new int[] {8, 9, 6, 7})); // 4
    }
}
