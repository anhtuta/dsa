package practice.leetcode.medium;

/**
 * https://leetcode.com/problems/house-robber/
 * 
 * Tóm tắc đề: cho mảng arr đại diện cho số tiền của các ngôi nhà trên 1 con phố, nhiệm vụ của bạn
 * là cướp nhiều tiền nhất có thể, nhưng ko được cướp 2 nhà liên tiếp. Chẳng hạn, với arr =
 * [2,7,9,3,1], bạn sẽ cướp nhà có số tiền = 2,9,1. Tổng số tiền tối đa cướp được = 2+9+1 = 13
 * 
 * From good to great. How to approach most of DP problems: Bài post sau sẽ hướng dẫn chi tiết cách
 * tiếp cận từ đệ quy -> DP. Một bài post rất hay và đáng để học hỏi:
 * https://leetcode.com/problems/house-robber/solutions/156523/from-good-to-great-how-to-approach-most-of-dp-problems/
 */
public class HouseRobber_198 {
    private int[] memo;

    // [2,7,9,3,1] => 2 + 9 + 1 = 12
    // [2,7,9,3,1,10] => 2 + 9 + 10 = 21
    public int rob(int[] a) {
        int ans;
        // Step 1: recursion nhưng bị timeout
        // ans = rob_recursion(a, a.length - 1);

        // Step 2: tối ưu recursion bằng cách dùng bảng memo
        // memo = new int[a.length];
        // for (int i = 0; i < memo.length; i++) {
        // memo[i] = -1;
        // }
        // ans = rob_DP_topDown(a, a.length - 1);

        // Step 3: tối ưu hơn nữa bằng việc dùng DP bottom up thay cho top down
        ans = rob_DP_bottomUp(a);
        return ans;
    }

    /**
     * Đầu tiên hãy nghĩ đến solution dùng đệ quy. Tại ngôi nhà i, ta có 2 option:
     * - Cướp nhà này => phải ko được cướp nhà i-1
     * - KHÔNG cướp nhà này => có thể cướp nhà i-1
     * => Chỉ cần tìm max từ 2 option này là sẽ thu được đáp án bài toán rồi (tại i = n-1):
     * maxLoot = rob(i) = max(rob(i-1), a[i] + rob(i-2))
     * => Đây là recursion top down
     * 
     * Result:
     * Solution này lại giống với fibonacci_naive: nó phải tính toán rob(k) rất nhiều lần.
     * Time complexity: O(2^n) (kinh khủng hơn rất nhiều so với n^2, input size <= 1000 mà còn timeout)
     * Time Limit Exceeded: 55 / 70 testcases passed
     */
    public int rob_recursion(int[] a, int pos) {
        if (pos < 0)
            return 0;
        return Math.max(rob_recursion(a, pos - 1), a[pos] + rob_recursion(a, pos - 2));
    }

    /**
     * Để tối ưu rob_recursion, ta có thể dùng DP top down (Memoization): tạo bảng memo và lưu trữ các
     * giá trị rob(k), tới khi nào gọi lại thì return từ memo thay vì tính toán lại
     * 
     * Note: ông nào copy code từ method rob_recursion ở trên nhớ đổi phần gọi đệ quy bên trong thành
     * rob_DP_topDown nhé, ko là vẫn y như cũ đó :v
     * 
     * Result:
     * Accepted. Runtime: 0 ms
     * Chạy quá nhanh luôn, chắc do input size chỉ 1000 nên vậy. Tuy nhiên nên tiếp tục nghĩ tới việc
     * bottom up để tối ưu tiếp, bởi vì nếu input quá lớn, thì việc gọi đệ quy dễ bị overhead
     * Time complexity: O(n)
     */
    public int rob_DP_topDown(int[] a, int pos) {
        if (pos < 0)
            return 0;
        if (memo[pos] == -1)
            memo[pos] = Math.max(rob_DP_topDown(a, pos - 1), a[pos] + rob_DP_topDown(a, pos - 2));
        return memo[pos];
    }

    public int rob_DP_bottomUp(int[] a) {
        int memo_1 = 0, memo_2 = 0; // memo_1 đại diện cho a[i-1], memo_2 đại diện cho a[i-2]
        int ans = 0;
        for (int i = 0; i < a.length; i++) {
            ans = Math.max(memo_1, a[i] + memo_2);
            memo_2 = memo_1;
            memo_1 = ans;
        }
        return ans;
    }

    public static void main(String[] args) {
        HouseRobber_198 app = new HouseRobber_198();
        System.out.println(app.rob(new int[] {1, 2, 3, 1})); // 4
        System.out.println(app.rob(new int[] {2, 7, 9, 3, 1})); // 12
    }
}
