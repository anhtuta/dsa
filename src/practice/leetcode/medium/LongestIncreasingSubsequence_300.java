package practice.leetcode.medium;

/**
 * https://leetcode.com/problems/longest-increasing-subsequence/
 * 
 * Given an integer array nums, return the length of the longest strictly increasing
 * subsequence
 * 
 * Example 1:
 * Input: nums = [10,9,2,5,3,7,101,18]
 * Output: 4
 * 
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
 * Example 2:
 * Input: nums = [0,1,0,3,2,3]
 * Output: 4
 * 
 * Example 3:
 * Input: nums = [7,7,7,7,7,7,7]
 * Output: 1
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 2500
 * -10^4 <= nums[i] <= 10^4
 * 
 * Follow up: Can you come up with an algorithm that runs in O(n log(n)) time complexity?
 * 
 * ===
 * Bài này giống với bài HouseRobber_198
 * 
 * Not done yet! Vẫn chưa tìm được công thức cho việc đệ quy
 */
public class LongestIncreasingSubsequence_300 {
    private int[] memo;

    public int lengthOfLIS(int[] a) {
        int ans;

        // ans = lengthOfLIS_recursion(a, a.length - 1, 0);
        ans = lengthOfLIS_recursion(a, 0, 0);

        // memo = new int[a.length];
        // ans = lengthOfLIS_DP_bottomUp_memo(a);

        return ans;
    }

    public int lengthOfLIS_recursion_wrong(int[] a, int pos) {
        int ans;
        if (pos == 0)
            return 1;
        if (a[pos] > a[pos - 1]) {
            ans = 1 + lengthOfLIS_recursion_wrong(a, pos - 1);
            System.out.printf("Accept, pos = %d, ans = %d%n", pos, ans);
        } else {
            ans = lengthOfLIS_recursion_wrong(a, pos - 1);
            System.out.printf("Deny, pos = %d, ans = %d%n", pos, ans);
        }

        return ans;
    }

    // public int lengthOfLIS_recursion(int[] a, int pos) {
    // if (pos == 0)
    // return 1;
    // if (a[pos] > a[pos - 1]) {
    // return Math.max(
    // lengthOfLIS_recursion(a, pos - 1) + 1, // pos-1 là end của LIS hiện tại
    // lengthOfLIS_recursion(a, pos));
    // } else {
    // return lengthOfLIS_recursion(a, pos - 1);
    // }
    // }

    public int lengthOfLIS_recursion(int[] a, int pos, int lastIndexLIS) {
        // System.out.printf("pos = %d%n", pos);
        if (pos >= a.length - 1)
            return 1;

        int ans;
        if (a[pos + 1] > a[pos]) {
            ans = Math.max(
                    lengthOfLIS_recursion(a, pos + 1, pos) + 1, // add a[pos] to the subsequence
                    lengthOfLIS_recursion(a, pos + 1, lastIndexLIS) // don't add a[pos] to the subsequence
            );
        } else {
            ans = lengthOfLIS_recursion(a, pos + 1, lastIndexLIS);
        }
        // System.out.printf("\tans = %d%n", ans);
        return ans;
    }

    /**
     * Wrong answer!
     */
    public int lengthOfLIS_DP_bottomUp_memo(int[] a) {
        if (a == null || a.length == 0)
            return 0;
        if (a.length == 1)
            return 1;
        memo[0] = 1;
        // int lastIndexLIS = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] > a[i - 1]) {
                memo[i] = 1 + memo[i - 1];
            } else {
                memo[i] = memo[i - 1];
            }
        }

        return memo[a.length - 1];
    }

    public static void main(String[] args) {
        LongestIncreasingSubsequence_300 app = new LongestIncreasingSubsequence_300();
        // System.out.println(app.lengthOfLIS(new int[] {10, 9, 2, 5, 3, 7, 101, 18})); // 4
        // System.out.println(app.lengthOfLIS(new int[] {0, 1, 0, 3, 2, 3})); // 4
        // System.out.println(app.lengthOfLIS(new int[] {7, 7, 7, 7, 7, 7, 7})); // 1
        // System.out.println(app.lengthOfLIS(new int[] {1, 2, 3, 4, 1, 2})); // 4
        // System.out.println(app.lengthOfLIS(new int[] {1, 2, 7, 1, 3, 4, 5, 6})); // 6
        System.out.println(app.lengthOfLIS(new int[] {1, 3, 9, 2, 4, 5, 7, 8})); // 6
    }
}
