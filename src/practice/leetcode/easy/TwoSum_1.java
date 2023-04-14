package practice.leetcode.easy;

import java.util.Arrays;
import practice.codility.Utils;

/**
 * https://leetcode.com/problems/two-sum/
 * 
 * Given an array of integers nums and an integer target, return indices of the two numbers such
 * that they add up to target.
 * 
 * You may assume that each input would have exactly one solution, and you may not use the same
 * element twice.
 * 
 * You can return the answer in any order.
 * 
 * Example 1:
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
 * 
 * Example 2:
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 * 
 * Example 3:
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 * 
 * Constraints:
 * 
 * 2 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 * Only one valid answer exists.
 */
public class TwoSum_1 {
    /**
     * Ý tưởng:
     * - Tạo 1 dãy b[] mới từ dãy ban đầu xong sort b[], sau đó duyệt trên b[].
     * - Dùng Two Pointers i,j duyệt từ 2 đầu của b[], check nếu tổng 2 số s = target thì đó là 2 số cần
     * tìm, sau đó search 2 số này ở mảng a[] để return index
     * - Do mảng b[] đã sort, nên nếu tổng s < target thì cần tăng s lên, tức là cần di chuyển con trỏ
     * bên trái là i (i++),
     * - Ngược lại, nếu tổng s < target, ta cần giảm s xuống, tức là phải di chuyển con trỏ bên phải là
     * j (j--)
     * 
     * Ref:
     * https://leetcode.com/explore/interview/card/leetcodes-interview-crash-course-data-structures-and-algorithms/703/arraystrings/4501/
     */
    public int[] twoSum(int[] a, int target) {
        int[] b = Arrays.copyOf(a, a.length);
        Arrays.sort(b);
        int i = 0, j = a.length - 1;
        int s;
        while (i < j) {
            s = b[i] + b[j];
            // System.out.printf("s = %d, i = %d, j = %d%n", s, i, j);
            if (s == target) {
                // find locations of b[i], b[j] in a[] and return these indices
                int originalIdx1 = -1, originalIdx2 = -1;
                for (int k = 0; k < a.length; k++) {
                    if (originalIdx1 == -1 && a[k] == b[i])
                        originalIdx1 = k;
                    if (originalIdx2 == -1 && a[k] == b[j] && k != originalIdx1)
                        originalIdx2 = k;
                }
                return new int[] {originalIdx1, originalIdx2};
            } else if (s < target) {
                i++;
            } else {
                j--;
            }
        }
        return new int[] {-1, -1};
    }

    public static void main(String[] args) {
        Utils.printArray(new TwoSum_1().twoSum(new int[] {3, 2, 4}, 6)); // [1, 2]
        Utils.printArray(new TwoSum_1().twoSum(new int[] {3, 3}, 6)); // [0, 1]
    }
}
