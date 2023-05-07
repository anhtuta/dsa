package practice.leetcode.medium;

import java.util.Arrays;
import util.Utils;

/**
 * https://leetcode.com/problems/next-permutation/
 * 
 * ===
 * Tóm tắt: 1 mảng a[] sẽ có các hoán vị = cách sắp xếp lại cách phần tử của chúng, ex: arr =
 * [1,2,3], the following are all the permutations of arr: [1,2,3], [1,3,2], [2, 1, 3], [2, 3, 1],
 * [3,1,2], [3,2,1].
 * 
 * Input là 1 hoán vị, hãy tìm hoán vị tiếp theo (theo thứ tự từ điển lexicographically)
 * 
 * Tuy bài này được gán tag là Two pointers, nhưng thực tế chỉ cần dùng công thức từ wikipedia là
 * xong
 */
public class NextPermutation_31 {
    private int n;

    /**
     * According to Wikipedia, a man named Narayana Pandita presented the following simple algorithm to
     * solve this problem in the 14th century.
     * (https://en.wikipedia.org/wiki/Permutation#Generation_in_lexicographic_order)
     * 
     * - (1) Find the largest index k such that nums[k] < nums[k + 1]. If no such index exists, just
     * reverse nums and done.
     * - (2) Find the largest index l > k such that nums[k] < nums[l].
     * - (3) Swap nums[k] and nums[l].
     * - (4) Reverse the sub-array nums[k + 1:].
     */
    public void nextPermutation(int[] a) {
        n = a.length;
        int k;
        // (1) Cách làm thiếu thông minh là duyệt từ đầu:
        // for (int i = 0; i < n - 1; i++) {
        // if (a[i] < a[i + 1])
        // k = i;
        // }
        // Duyệt luôn từ cuối xong break cho nhanh
        for (k = n - 2; k >= 0; k--) {
            if (a[k] < a[k + 1]) {
                break;
            }
        }

        if (k == -1) {
            // just reverse a[] and done.
            reverse(a, 0, n - 1);
            return;
        }

        // (2) After (1) step, k sẽ >= 0, giờ tìm số l lớn nhất mà a[l] > a[k]
        int l = n - 1;
        while (l >= k) {
            if (a[l] > a[k])
                break;
            l--;
        }

        swap(a, k, l); // (3)
        reverse(a, k + 1, n - 1); // (4)
    }

    // private int[] reverse(int[] a) {
    // return reverse(a, 0, a.length - 1);
    // }

    private int[] reverse(int[] a, int start, int end) {
        while (start < end) {
            swap(a, start++, end--);
        }
        return a;
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        NextPermutation_31 app = new NextPermutation_31();
        int[] a = {1, 2, 3, 4, 5};
        app.nextPermutation(a);
        System.out.println(Arrays.toString(a)); // [1, 2, 3, 5, 4]

        int[] a1 = {5, 4, 3, 2, 1};
        app.nextPermutation(a1);
        System.out.println(Arrays.toString(a1)); // [1, 2, 3, 4, 5]

        int[] a2 = {1, 1, 5};
        app.nextPermutation(a2);
        System.out.println(Arrays.toString(a2)); // [1, 5, 1]

        int[] a3 = {1, 2, 3};
        app.nextPermutation(a3);
        System.out.println(Arrays.toString(a3)); // [1, 3, 2]

        Utils.printArray(app.reverse(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8}, 0, 8));
        Utils.printArray(app.reverse(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 0, 9));
    }
}
