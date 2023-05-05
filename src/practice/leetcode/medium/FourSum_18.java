package practice.leetcode.medium;

import java.util.LinkedList;
import java.util.ArrayList;
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
 * 
 * =====
 * Tóm tắt: Cho 1 mảng, liệt kê tất cả các tổ hợp 4 phần tử mà có tổng = target
 */
public class FourSum_18 {
    /**
     * Idea: dùng đệ quy để chuyển bài toán tổng quát là kSum về dạng 2Sum
     * Ref: https://leetcode.com/problems/4sum/solutions/8609/my-solution-generalized-for-ksums-in-java/
     * 
     * Result:
     * Accepted
     * Runtime: 25 ms. Beats: 47.1% (chỉ nhan hơn 47% user khác)
     * Memory: 43.9 MB. Beats: 5.32% (chỉ dùng bộ nhớ ít hơn 5% user khác)
     * 
     * Ko hiểu sao lại chạy chậm và tốn bộ nhớ đến vậy. Có lẽ cần tìm lời giải tối ưu hơn...
     */
    public List<List<Integer>> fourSum(int[] a, int target) {
        Arrays.sort(a);
        System.out.print("Input: ");
        Utils.printArray(a);
        return kSum(a, target, 4, 0);
    }

    /**
     * Using LinkedList because later, we will add an element at the beginning
     */
    public List<List<Integer>> kSum(int[] a, long target, int k, int index) {
        if (index >= a.length)
            return new LinkedList<>();

        if (k == 2) {
            // base case
            List<List<Integer>> res2Sum = new LinkedList<>();
            int left = index, right = a.length - 1;
            long sum;
            while (left < right) {
                // Kiểu int KHÔNG dùng được tổng, vì sẽ bị tràn số nếu input quá lớn,
                // có thể thay thế bằng: if (target - a[left] == a[right])
                // nhưng ép sang kiểu long rồi thì chỗ này ko cần thiết phải sửa
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
                } else if (sum < target) { // có thể thay bằng if (target - a[left] > a[right]) để ko bị tràn
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
                // Find k-1 sum for each element of the list
                // Chỗ này, target sẽ liên tục trừ cho a[i], nếu như a[i] quá bé, thì việc trừ như vậy
                // sẽ bị tràn bộ nhớ, do kiểu int bé nhất = -2.1 tỉ thôi. Do đó cần ép sang kiểu long
                List<List<Integer>> lists = kSum(a, target - a[i], k - 1, i + 1);

                // Add this element to result (k-1 sum), so it will become k sum
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

    /**
     * Thử dùng array list thay cho linked list, bỏ biến sum, nhưng cũng ko cải thiện được nhiều
     * 
     * Result:
     * Accepted
     * Runtime: 22 ms. Beats: 56.29%
     * Memory: 43.5 MB. Beats: 22.71%
     */
    public List<List<Integer>> kSum_minorOptimized(int[] a, long target, int k, int index) {
        if (index >= a.length)
            return new ArrayList<>();

        if (k == 2) {
            // base case
            List<List<Integer>> res2Sum = new ArrayList<>();
            int left = index, right = a.length - 1;
            while (left < right) {
                if (target - a[left] == a[right]) {
                    ArrayList<Integer> al = new ArrayList<>();
                    al.add(a[left]);
                    al.add(a[right]);
                    res2Sum.add(al);

                    // Skip toàn bộ các phần tử a[left] giống nhau
                    while (left < right && a[left + 1] == a[left])
                        left++;

                    // Skip toàn bộ các phần tử a[right] giống nhau
                    while (left < right && a[right - 1] == a[right])
                        right--;

                    left++;
                    right--;
                } else if (target - a[left] > a[right]) {
                    left++;
                } else {
                    right--;
                }
            }

            return res2Sum;
        } else {
            List<List<Integer>> resKSum = new ArrayList<>();
            for (int i = index; i < a.length - k + 1; i++) {
                // Find k-1 sum for each element of the list
                List<List<Integer>> lists = kSum_minorOptimized(a, target - a[i], k - 1, i + 1);

                // Add this element to result, so it will become k sum
                for (List<Integer> ls : lists) {
                    ls.add(a[i]);
                }

                resKSum.addAll(lists);

                // Skip toàn bộ các phần tử a[i] giống nhau
                while (i < a.length - 1 && a[i + 1] == a[i]) {
                    i++;
                }
            }
            return resKSum;
        }
    }

    /**
     * Thử dùng nhánh cận, check trước khi gọi đệ quy, but still does NOT work!
     */
    public List<List<Integer>> kSum_branchAndBound(int[] a, long target, int k, int index) {
        if (index >= a.length)
            return new ArrayList<>();

        if (k == 2) {
            // base case
            List<List<Integer>> res2Sum = new ArrayList<>();
            int left = index, right = a.length - 1;
            while (left < right) {
                if (target - a[left] == a[right]) {
                    ArrayList<Integer> al = new ArrayList<>();
                    al.add(a[left]);
                    al.add(a[right]);
                    res2Sum.add(al);

                    // Skip toàn bộ các phần tử a[left] giống nhau
                    while (left < right && a[left + 1] == a[left])
                        left++;

                    // Skip toàn bộ các phần tử a[right] giống nhau
                    while (left < right && a[right - 1] == a[right])
                        right--;

                    left++;
                    right--;
                } else if (target - a[left] > a[right]) {
                    left++;
                } else {
                    right--;
                }
            }

            return res2Sum;
        } else {
            List<List<Integer>> resKSum = new ArrayList<>();
            for (int i = index; i < a.length - k + 1; i++) {
                ////////// Should do some check here to optimize ////////////
                // check if this `num + last max num * (K-1)` is greater than target
                if (a[i] + a[a.length - 1] * (k - 1) < target)
                    continue;

                // checking if smallest number 4 times makes it greater than target
                if (a[i] * k > target)
                    break;
                ////////// End of optimization ////////////

                // Find k-1 sum for each element of the list
                List<List<Integer>> lists = kSum_branchAndBound(a, target - a[i], k - 1, i + 1);

                // Add this element to result, so it will become k sum
                for (List<Integer> ls : lists) {
                    ls.add(a[i]);
                }

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
        System.out.println(app.fourSum(new int[] {1000000000, 1000000000, 1000000000, 1000000000}, -294_967_296)); // []
    }
}
