package practice.leetcode.medium;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import util.Utils;

/**
 * https://leetcode.com/problems/4sum/
 * 
 * Given an array nums of n integers, return an array of all the unique quadruplets [nums[a],
 * nums[b], nums[c], nums[d]] such that:
 * 
 * 0 <= a, b, c, d < n
 * a, b, c, and d are distinct.
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * You may return the answer in any order.
 * 
 * Example 1:
 * Input: nums = [1,0,-1,0,-2,2], target = 0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * 
 * Example 2:
 * Input: nums = [2,2,2,2,2], target = 8
 * Output: [[2,2,2,2]]
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 200
 * -10^9 <= nums[i] <= 10^9
 * -10^9 <= target <= 10^9
 */
public class FourSum_18 {
    public List<List<Integer>> fourSum(int[] a, int target) {
        Arrays.sort(a);
        System.out.print("Input: ");
        Utils.printArray(a);
        return kSum(a, target, 4, 0);
    }

    /**
     * Using LinkedList because later, we will add an element at the beginning
     */
    public List<List<Integer>> kSum(int[] a, int target, int k, int index) {
        if (index >= a.length)
            return new LinkedList<>();

        if (k == 2) {
            // base case
            List<List<Integer>> res2Sum = new LinkedList<>();
            int left = index, right = a.length - 1, sum;
            while (left < right) {
                sum = a[left] + a[right];
                if (sum == target) {
                    // Dùng Arrays.asList thì ở dưới ko add được vào cái list đó nữa
                    // res2Sum.add(Arrays.asList(a[left], a[right]));
                    LinkedList<Integer> al = new LinkedList<>();
                    al.add(a[left]);
                    al.add(a[right]);
                    res2Sum.add(al);

                    // Skip toàn bộ các phần tử a[left] giống nhau
                    while (left < right && a[left + 1] == a[left])
                        left++;

                    // Skip toàn bộ các phần tử a[right] giống nhau
                    while (left < right && a[right - 1] == a[right])
                        right--;

                    // Giảm kích thước window để tìm các cặp tiếp theo (tìm triplet nhưng a[i] ko đổi,
                    // chỉ tìm a[left] và a[right]). Note: phải giảm kích thước windows SAU khi skip các
                    // duplication, nếu ko sẽ bị skip quá 1 phần tử
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }

            // System.out.printf("%d sum, start from a[%d] = %d: ", k, index, a[index]);
            // System.out.println(res2Sum);

            return res2Sum;
        } else {
            List<List<Integer>> resKSum = new LinkedList<>();
            for (int i = index; i < a.length - k + 1; i++) {
                // Found k-1 sum for each element of the list
                List<List<Integer>> lists = kSum(a, target - a[i], k - 1, i + 1);

                // Add this element to result, so it will become k sum
                for (List<Integer> ls : lists) {
                    ls.add(0, a[i]);
                }

                // System.out.printf("%d sum, start from a[%d] = %d: ", k, i, a[i]);
                // System.out.println(lists);

                resKSum.addAll(lists);

                // Skip toàn bộ các phần tử a[i] giống nhau
                while (i < a.length - 1 && a[i + 1] == a[i]) {
                    i++;
                }
            }
            return resKSum;
        }
    }

    public static void main(String[] args) {
        int a = 1000000000;
        int b = a + a + a + a;
        System.out.println("Overflow: b = " + b); // -294967296 (bị tràn số)

        FourSum_18 app = new FourSum_18();
        System.out.println(app.fourSum(new int[] {1, 0, -1, 0, -2, 2}, 0)); // [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
        System.out.println(app.fourSum(new int[] {1000000000, 1000000000, 1000000000, 1000000000}, -294967296)); // []
    }
}
