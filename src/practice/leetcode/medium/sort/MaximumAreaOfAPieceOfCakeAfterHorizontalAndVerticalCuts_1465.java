package practice.leetcode.medium.sort;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/maximum-area-of-a-piece-of-cake-after-horizontal-and-vertical-cuts/
 */
public class MaximumAreaOfAPieceOfCakeAfterHorizontalAndVerticalCuts_1465 {
    /**
     * Idea: sort xong tìm mảnh con có height, width lớn nhất là được.
     * 
     * Note: Nếu cắt thành 2 mảnh con có [h,w] = [5,1] và [3,4], thì đáp án ko phải là 12, mà phải xét
     * đến mảnh con to nhất là [5,4]. Bởi vì việc cắt là cắt thẳng từ đầu đến cuối, do đó chắc chắn sẽ
     * tạo dc mảnh con có size là [5,4], do đó đáp án là 20
     * 
     * Runtime 18 ms Beats 21%
     * Memory 54.2 MB Beats 73.50%
     */
    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);
        int i = 0, j = 0;
        int maxHorGap = horizontalCuts[0], maxVerGap = verticalCuts[0];

        // Using single while loop instead of 2, and two pointers, similar to merge sort
        while (i < horizontalCuts.length - 1 && j < verticalCuts.length - 1) {
            maxHorGap = Math.max(maxHorGap, horizontalCuts[i + 1] - horizontalCuts[i]);
            maxVerGap = Math.max(maxVerGap, verticalCuts[j + 1] - verticalCuts[j]);
            i++;
            j++;
        }

        // Continue traversing if any elements left
        while (i < horizontalCuts.length - 1) {
            maxHorGap = Math.max(maxHorGap, horizontalCuts[i + 1] - horizontalCuts[i]);
            i++;
        }

        // Continue traversing if any elements left
        while (j < verticalCuts.length - 1) {
            maxVerGap = Math.max(maxVerGap, verticalCuts[j + 1] - verticalCuts[j]);
            j++;
        }

        // We should calculate the gap of the last cut and the edge
        maxHorGap = Math.max(maxHorGap, h - horizontalCuts[i]);
        maxVerGap = Math.max(maxVerGap, w - verticalCuts[j]);

        // maxHorGap and maxVerGap could be too large (10^9), so multiply them together is NOT possible
        // return (maxHorGap * maxVerGap) % 1_000_000_007;

        return multiply(maxHorGap, maxVerGap, 1_000_000_007);
    }

    /**
     * https://www.geeksforgeeks.org/multiply-large-integers-under-large-modulo/
     * Note: param a and b MUST smaller than Integer.MAX_VALUE / 2
     */
    private int multiply(int a, int b, int module) {
        // Initialize result
        int res = 0;

        // Update a if it is more than
        // or equal to mod
        a %= module;

        while (b > 0) {

            // If b is odd, add a with result
            if ((b & 1) > 0) {
                res = (res + a) % module;
            }

            // Here we assume that doing 2*a doesn't cause overflow
            a = (2 * a) % module;

            b >>= 1; // b = b / 2
        }
        return res;
    }
}
