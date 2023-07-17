package practice.leetcode.medium.math;

import util.Utils;

/**
 * https://leetcode.com/problems/rotate-array/
 * 
 * Could you do it in-place with O(1) extra space?
 * 
 * Ex1:
 * Input: nums = [1,2,3,4,5,6,7], k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 * 
 * Ex2:
 * Input: nums = [1,2,3,4,5,6,7], k = 6
 * Output: [2,3,4,5,6,7,1]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 * rotate 4 steps to the right: [4,5,6,7,1,2,3]
 * rotate 5 steps to the right: [3,4,5,6,7,1,2]
 * rotate 6 steps to the right: [2,3,4,5,6,7,1]
 * 
 * => Review: rotate to right 6 steps = rotate to left 7-6 = 1 steps
 * 
 * Chú ý: trong tất cả cách giải, do input 0 <= k <= 10^5, tức là k có thể lớn
 * hơn n, do đó phải chia module k = k % n, nếu ko sẽ lỗi ArrayIndexOutOfBound
 */
public class RotateArray_189 {
    /**
     * Use k temp variables to store k last elements, then move the beginning part
     * of the array to the end, then set beginning part equal to k temp variables
     * 
     * => Cách này cần tối đa n biến temp, với n = a.length
     * 
     * Runtime 1 ms Beats 50.74%
     * Memory 56 MB Beats 51.17%
     */
    public void rotate_extraSpace_On(int[] a, int k) {
        k = k % a.length; // This is really important
        rotateRight(a, k);
    }

    /**
     * Tối ưu hơn 1 chút: nếu k > n/2 thì ta sẽ dịch trái thay vì phải, như vậy mảng
     * temp sẽ ít hơn, tức là đỡ tốn bộ nhớ hơn
     * 
     * => Cách này cần tối đa n/2 biến temp, với n = a.length
     * 
     * Runtime 1 ms Beats 50.74%
     * Memory 55.8 MB Beats 67.16%
     * 
     * Hmm, NOT optimized significantly
     */
    public void rotate_extraSpace_OHalfN(int[] a, int k) {
        int n = a.length;
        k = k % a.length; // This is really important
        if (k <= n / 2) {
            rotateRight(a, k);
        } else {
            rotateLeft(a, n - k);
        }
    }

    private void rotateRight(int[] a, int k) {
        int n = a.length;
        int[] temp = new int[k];
        for (int i = 0; i < k; i++) {
            temp[i] = a[n - k + i];
        }
        // Utils.printArray(temp);
        for (int i = n - 1; i >= k; i--) {
            a[i] = a[i - k];
        }
        for (int i = 0; i < k; i++) {
            a[i] = temp[i];
        }
    }

    private void rotateLeft(int[] a, int k) {
        int n = a.length;
        int[] temp = new int[k];
        for (int i = 0; i < k; i++) {
            temp[i] = a[i];
        }
        // Utils.printArray(temp);
        for (int i = 0; i < n - k; i++) {
            a[i] = a[i + k];
        }
        int j = 0;
        for (int i = n - k; i < n; i++) {
            a[i] = temp[j++];
        }
    }

    /**
     * Idea: Let't consider this example: a[] = [1234567] ,k=3
     * 1. First reverse the numbers from index 0 to n-k
     * -> 4321 567
     * 2. Reverse the k elements from the last
     * -> 4321 765
     * 3. Now reverse the whole nums
     * -> 5671234 Done Answer is here !!!!
     * 
     * Runtime 0 ms Beats 100%
     * Memory 56.2 MB Beats 41.48%
     * 
     * Nhanh hơn hẳn rồi đấy, nhưng memory vẫn vậy, chắc do leetcode nó thế
     * 
     * Ref:
     * https://leetcode.com/problems/rotate-array/solutions/3506340/beats-100-3-line-solution-fully-most-optimised-code/
     */
    public void rotate(int[] a, int k) {
        int n = a.length;
        k = k % a.length; // This is really important
        rotateArray(a, 0, n - k - 1);
        rotateArray(a, n - k, n - 1);
        rotateArray(a, 0, n - 1);
    }

    private void rotateArray(int[] a, int start, int end) {
        int temp;
        while (start < end) {
            temp = a[start];
            a[start] = a[end];
            a[end] = temp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        RotateArray_189 app = new RotateArray_189();
        int[] a = new int[] { 1, 2, 3, 4, 5, 6, 7 };
        app.rotate(a, 3);
        Utils.printArray(a); // [5 6 7 1 2 3 4]
        a = new int[] { 1, 2, 3, 4, 5, 6, 7 };
        app.rotate(a, 5);
        Utils.printArray(a); // [3 4 5 6 7 1 2]
    }
}
