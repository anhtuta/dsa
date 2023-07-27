package practice.leetcode.medium.twopointers;

import util.Utils;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
 * 
 * Extend of problem {@link practice.leetcode.easy.RemoveDuplicatesFromSortedArray_26}
 */
public class RemoveDuplicatesFromSortedArrayII_80 {
    /**
     * Idea: sử dụng two pointers, nhưng khác với bài RemoveDuplicatesFromSortedArray_26, biến cnt dùng
     * để đếm số lượng phần tử = a[right].
     * 
     * Bắt đầu duyệt từ đầu.
     * 
     * Con trỏ right đùng để check các phần tử = nhau, cứ duyệt tới khi phần tử tiếp theo khác phần
     * tử hiện tại thì dừng. Tại mỗi vòng duyệt, tăng biến cnt để đếm số lượng phần tử = nhau và
     * = a[right].
     * 
     * Con trỏ left dùng để giữ vị trí, sau khi right dừng lại:
     * - Nếu cnt >= 2: gán cho a[left], a[left+1] 2 phần tử bằng a[right] rồi chuyển left tới 2 vị trí
     * tiếp theo
     * - Nếu cnt = 1: gán cho a[left] phần tử bằng a[right] rồi chuyển left tới 1 vị trí tiếp theo
     * 
     * Runtime 0 ms Beats 100%
     * Memory 43.9 MB Beats 48.11%
     */
    public int removeDuplicates(int[] a) {
        int left = 0, right = 0;
        int cnt = 1; // total elements that equals to a[right] (include a[right])
        while (right < a.length) {
            if (right < a.length - 1 && a[right + 1] == a[right]) {
                cnt++;
                right++;
            } else {
                if (cnt > 1) {
                    a[left] = a[right];
                    a[left + 1] = a[right];
                    left += 2;
                } else {
                    a[left] = a[right];
                    left++;
                }
                cnt = 1;
                right++;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        RemoveDuplicatesFromSortedArrayII_80 app = new RemoveDuplicatesFromSortedArrayII_80();
        int[] arr = {1, 1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 5, 5, 6, 7, 7, 8, 8, 9, 9, 9, 9, 9, 10};
        int cnt = app.removeDuplicates(arr);
        System.out.println(cnt); // 18
        Utils.printArray(arr, 0, cnt - 1); // [1 1 2 2 3 3 4 4 5 5 6 7 7 8 8 9 9 10]

        int[] arr2 = new int[] {0, 0, 1, 1, 1, 1, 2, 3, 3};
        int cnt2 = app.removeDuplicates(arr2);
        System.out.println(cnt2); // 7
        Utils.printArray(arr2, 0, cnt2 - 1); // [0 0 1 1 2 3 3]
    }
}
