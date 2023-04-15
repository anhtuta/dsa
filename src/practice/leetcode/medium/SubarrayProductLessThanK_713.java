package practice.leetcode.medium;

/**
 * https://leetcode.com/problems/subarray-product-less-than-k
 */
public class SubarrayProductLessThanK_713 {
    /**
     * Idea: using Sliding window pattern, but we need to use a neat math trick to calculate the number
     * of sub-arrays
     * 
     * Tại sao chỗ dưới ko dùng ans++ mà phải dùng ans += right - left + 1?
     * 
     * Ý tưởng ở đây là đếm số dãy con có thể tạo thành trong khoảng left, right và kết thúc tại right.
     * Bởi vì nếu slice [left...right] có tích < k thì toàn bộ các slice con của slice đó cũng sẽ có
     * tích < k
     * VD: slice = [5, 2, 6], k = 100, thì ta sẽ có các slice con cũng sẽ có tích < 100 là: [2, 6], [6]
     * 
     * Note: ta sẽ KHÔNG đếm slice [5], [5, 2]. Bởi vì hiện tại cạnh phải của window là số 6, và ta chỉ
     * cần care tới các slice kết thúc tại số 6 thôi. 2 slice kia đã được đếm tại window có cạnh phải là
     * 5 và 2 rồi
     * 
     * Việc đếm này khá là tricky, 1 bài tương tự cũng đếm tricky như vậy là bài
     * {@link practice.codility.exercises.e4.ArrayInversionCount ArrayInversionCount} bên
     * codility
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1)
            return 0;
        int left = 0;
        int currPrd = 1; // Tích của dãy con hiện tại
        int ans = 0;
        for (int right = 0; right < nums.length; right++) {
            currPrd *= nums[right];
            while (currPrd >= k) {
                currPrd /= nums[left];
                left++;
            }
            ans += right - left + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        SubarrayProductLessThanK_713 s = new SubarrayProductLessThanK_713();
        System.out.println(s.numSubarrayProductLessThanK(new int[] {10, 5, 2, 6}, 100)); // 8
    }
}
