package practice.leetcode.easy;

import util.Utils;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 */
public class RemoveDuplicatesFromSortedArray_26 {
    /**
     * Idea: pattern two pointers: dùng 2 con trỏ left và right,
     * - left dùng để gán các unique elements vào mảng input
     * - right dùng để check duplicate
     * Mặc dù 2 con trỏ đều start từ đầu dãy, nhưng đây ko phải là sliding window, bởi vì ta ko làm gì
     * với cái subarray (window) trong khoảng [left...right]
     */
    public int removeDuplicates(int[] a) {
        int left = 0, right = 1;
        int cnt = 1;
        while (right < a.length) {
            if (a[right] != a[left]) {
                cnt++;
                a[++left] = a[right];
            }
            right++;
        }
        return cnt;
    }

    public static void main(String[] args) {
        RemoveDuplicatesFromSortedArray_26 app = new RemoveDuplicatesFromSortedArray_26();
        int[] arr = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 9, 9, 9, 10};
        int cnt = app.removeDuplicates(arr);
        System.out.println(cnt); // 10
        Utils.printArray(arr, 0, cnt - 1); // [1 2 3 4 5 6 7 8 9 10]
    }
}
