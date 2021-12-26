package practice.leetcode.dc;

/**
 * Problem: https://leetcode.com/problems/maximum-subarray/
 * (Easy)
 * 
 * Bài này chính là bài dãy con lớn nhất:
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which
 * has the largest sum and return its sum.
 * 
 * A subarray is a contiguous part of an array
 * 
 * Ex:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 * 
 * @author tatu
 *
 */
public class MaximumSubarray_53 {

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    private int max(int a, int b, int c) {
        return a > b ? (a > c ? a : c) : (b > c ? b : c);
    }

    private int maxSub(int[] nums, int left, int right) {
        if (left == right)
            return nums[left];
        int mid = (left + right) / 2;
        return max(maxSub(nums, left, mid), maxSub(nums, mid + 1, right),
                maxSubMid(nums, left, mid, right));
    }

    private int maxSubMid(int[] nums, int left, int mid, int right) {
        int sum = nums[mid];
        int maxSum = sum;
        for (int i = mid - 1; i >= left; i--) {
            sum += nums[i];
            maxSum = max(maxSum, sum);
        }
        sum = maxSum;   // cần lấy giá trị lớn nhất của nửa trái để tính tiếp
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            maxSum = max(maxSum, sum);
        }
        System.out.println(left + "," + mid + "," + right + "," + maxSum);
        return maxSum;
    }

    public int maxSubArray(int[] nums) {
        return maxSub(nums, 0, nums.length - 1);
    }

    public static void main(String[] args) {
        MaximumSubarray_53 ms = new MaximumSubarray_53();
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(ms.maxSubArray(nums));
    }

}
