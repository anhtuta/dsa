package practice.leetcode.easy.dp;

/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/
 * 
 * Bài này được gán tag DP, nhưng xem solutions của mọi người trên leetcode thì ít thấy cách dùng
 * recursion hay DP
 * 
 * Bài này mà dễ à??? Nghĩ mãi ko giải được bằng DP!!!
 */
public class BestTimeToBuyAndSellStock_121 {
    private int[][] memo;

    public int maxProfit(int[] prices) {
        int ans;

        // Step 1: recursion top down nhưng bị timeout
        // ans = recursion(prices, 0, prices.length - 1, 0);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // memo = new int[prices.length + 1][prices.length + 1];
        // for (int i = 0; i < prices.length; i++) {
        // for (int j = 0; j < prices.length; j++) {
        // memo[i][j] = -1;
        // }
        // }
        // ans = dp_topDown(prices, 0, prices.length - 1, 0);

        // Không dùng DP nữa, mà sử dụng 2 mảng tạm minLeft, maxRight
        // ans = usingMinLeftMaxRight(prices);

        // Dùng two pointers để tối ưu memory
        ans = usingTwoPointers(prices);

        return ans;
    }

    /**
     * Idea: Two pointers, recursion
     * Dùng 2 con trỏ duyệt từ 2 phía của mảng, tương ứng là 2 vị trí mua và bán cổ phiếu.
     * Tại mỗi thời điểm, tính toán profit rồi so sánh với maxProfit để cập nhật maxProfit.
     * 
     * Tại mỗi thời điểm, ta tính toán profit = a[sell] - a[buy], tức là ta sẽ mua và bán ở 2 ngày này,
     * sau đó ta sẽ duyệt tiếp để so sánh, thì có 2 option:
     * - tăng ngày mua lên
     * - giảm ngày bán xuống
     * 
     * Result:
     * Time Limit Exceeded 197 / 211 testcases passed
     * 
     * @param buy index of day to buy stock
     * @param sell index of day to sell stock
     * @param maxProfit max profit so far. That's the answer of this problem when recursion ends
     */
    public int recursion(int[] a, int buy, int sell, int maxProfit) {
        if (buy >= sell)
            return maxProfit;

        int profit = a[sell] - a[buy];
        if (profit < maxProfit) {
            profit = maxProfit;
        }
        return Math.max(
                recursion(a, buy + 1, sell, profit),
                recursion(a, buy, sell - 1, profit));
    }

    /**
     * Tối ưu recursion bằng memo, nhưng vẫn bị timeout!!!
     * Tại sao thế???
     */
    public int dp_topDown(int[] a, int buy, int sell, int maxProfit) {
        if (buy >= sell)
            return maxProfit;

        if (memo[buy][sell] == -1) {
            int profit = a[sell] - a[buy];
            if (profit < maxProfit) {
                profit = maxProfit;
            }
            memo[buy][sell] = Math.max(
                    dp_topDown(a, buy + 1, sell, profit),
                    dp_topDown(a, buy, sell - 1, profit));
        }
        return memo[buy][sell];
    }

    /**
     * Idea: sử dụng 2 mảng tạm là minLeft và maxRight, giống như bài
     * {@link practice.leetcode.hard.TrappingRainWater_42}, nhưng khác ở chỗ minLeft[i] và maxRight[i]
     * có thể chính là a[i] (trong khi bài water trên thì chúng Không thể là a[i]):
     * minLeft[i] = min end here (i) = phần tử thấp nhất trong khoảng từ a[0] -> a[i]
     * maxRight[i] = max end here (i) from right = phần tử lớn nhất trong khoảng từ a[n-1] -> a[i]
     * 
     * Duyệt từ đầu tới cuối: profit[i] = maxRight[i+1] - minRight[i] (vì ko được mua và bán trong cùng
     * 1 ngày)
     * 
     * Runtime 5 ms Beats 6.55% (76% of solutions used 2ms of runtime)
     * Memory 61.7 MB Beats 5.79%
     * 
     * Not bad!!!
     */
    public int usingMinLeftMaxRight(int[] a) {
        int n = a.length;
        int[] minLeft = new int[n];  // minLeft[i] = min(a[0...i]): phần tử bé nhất từ 0 -> i
        int[] maxRight = new int[n]; // maxRight[i] = max(a[i...n-1]): phần tử lớn nhất từ i -> n-1

        minLeft[0] = a[0];
        for (int i = 1; i < n; i++) {
            minLeft[i] = Math.min(minLeft[i - 1], a[i]);
        }
        maxRight[n - 1] = a[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], a[i]);
        }

        int maxProfit = 0;
        for (int i = 0; i < n - 1; i++) {
            maxProfit = Math.max(maxProfit, maxRight[i + 1] - minLeft[i]);
        }

        return maxProfit;
    }

    /**
     * Ý tưởng: tìm cực đại và cực tiểu. Dùng 2 con trỏ bắt đầu từ đầu mảng, left và right,
     * right > left.
     * Ta phải tìm vị trí mà a[left] < a[right], nếu ko cứ tăng cả 2 con trỏ lên.
     * Tới khi a[left] < a[right], thì profit hiện tại = a[right] - a[left]. Update maxProfit là được
     * 
     * Ref:
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/solutions/1735550/python-javascript-easy-solution-with-very-clear-explanation/
     * 
     * Runtime 2 ms Beats 93.73%
     * Memory 61.2 MB Beats 8.8%
     * 
     * Tại sao memory vẫn tệ thế nhỉ???
     */
    public int usingTwoPointers(int[] a) {
        int left = 0, right = left + 1;
        int maxProfit = 0;
        while (left < a.length && right < a.length) {
            if (a[left] >= a[right]) {
                left = right;
                right++;
            } else {
                maxProfit = Math.max(maxProfit, a[right] - a[left]);
                right++;
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStock_121 app = new BestTimeToBuyAndSellStock_121();
        System.out.println(app.maxProfit(new int[] {7, 1, 5, 3, 6, 4})); // 5
        System.out.println(app.maxProfit(new int[] {7, 6, 4, 3, 1})); // 0
    }
}
