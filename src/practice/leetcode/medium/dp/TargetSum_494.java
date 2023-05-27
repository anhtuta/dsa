package practice.leetcode.medium.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/target-sum/
 */
public class TargetSum_494 {
    // memo[i][j] = số cách đặt dấu '+', '-' sao cho j phần tử đầu tiên có tổng = i
    // number of ways for first j-th element to reach a sum i
    private int[][] memo;
    private static final int UNSET = -1;
    private Map<Key, Integer> dp; // dp.get(new Key(i, j)) tương tự như memo[i][j]

    public int findTargetSumWays(int[] nums, int target) {
        int ans;

        // Step 1: recursion top down nhưng bị timeout
        // ans = recursion(nums, target, 0);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // 2.1. Cách làm sau là sai
        // memo = new int[target + 1][nums.length]; // size của dialer là 4 * 3
        // for (int i = 0; i < memo.length; i++) {
        // for (int j = 0; j < memo[0].length; j++) {
        // memo[i][j] = UNSET;
        // }
        // }
        // ans = dp_topDown_wrong(nums, target, 0);

        // 2.2. Cách sau mới đúng
        dp = new HashMap<>();
        ans = dp_topDown(nums, target, 0);

        return ans;
    }

    /**
     * Bài này khá giống với bài {@link practice.leetcode.medium.HouseRobber_198}: tại mỗi phần tử của
     * nums, ta phải quyết định chọn hoặc bỏ nó đi, chỉ khác ở chỗ: bỏ đi thì phải trừ, nhưng bên bài
     * HouseRobber_198 bỏ đi là ko trừ hay cộng gì
     * 
     * Recursion idea: Xét input đơn giản: nums = [1,3,2,4,1], target = 3
     * Ta sẽ recursion topDown theo target, chứ ko phải theo mảng nums, vì mảng này duyệt theo chiều nào
     * cũng như nhau (Rename mảng nums = mảng a cho gọn). Nhiệm vụ là đặt dấu '+' hoặc '-' trước từng
     * phần tử của a. Gọi sum = tổng các phần tử đã thêm dấu trước nó. Duyệt từ a[0] nhé, có 2 cách đặt
     * dấu trước a[0]
     * - Nếu đặt dấu '+', lúc này sum = +a[0] = 1, mảng nums còn lại là [3,2,4,1]. Ta cần đặt dấu sao
     * cho tổng của subArray này = target - sum = 2
     * - Nếu đặt dấu '-', lúc này sum = -a[0] = -1, mảng nums còn lại là [3,2,4,1]. Ta cần đặt dấu sao
     * cho tổng của subArray này = target - sum = 4
     *
     * Tức là, ta có công thức (rename hàm findTargetSumWays thành hàm F cho gọn):
     * 
     * F([1,3,2,4,1], 3) = F([3,2,4,1], 2) + F([3,2,4,1], 4)
     * 
     * Cứ đệ quy như vậy, ta lại tính tiếp:
     * F([3,2,4,1], 2) = F([2,4,1], -1) + F([2,4,1], 5)
     * ...
     * 
     * Đệ quy tới khi nào mảng subArray là empty, lúc này nếu target = 0 là đó là 1 đáp án, còn nếu
     * target != 0 thì việc đặt dấu cho mảng nums đó KHÔNG phải là đáp án
     * 
     * Result:
     * Runtime 587 ms Beats 19.85%
     * Memory 40.7 MB Beats 64.51%
     * 
     * Time: O(2^n), do có 2 dấu + và -, giống với bài Fibonacci
     * Bài này input bé nên vẫn pass hết testcases, tuy vậy vẫn cần tối ưu
     */
    public int recursion(int[] a, int target, int pos) {
        // Base cases
        if (pos == a.length) {
            if (target == 0)
                return 1;
            else
                return 0;
        }
        return recursion(a, target - a[pos], pos + 1) + // place '+' before a[pos]
                recursion(a, target + a[pos], pos + 1); // place '-' before a[pos]
    }

    /**
     * Bài này top down + memo như nào đây, khi mà target có thể âm, ex: F([2,4,1], -1),
     * do đó nếu dùng mảng memo thì ko được, vì mảng trong Java ko được index âm
     * Result: ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 4
     */
    public int dp_topDown_wrong(int[] a, int target, int pos) {
        if (pos == a.length) {
            if (target == 0)
                return 1;
            else
                return 0;
        }

        if (memo[target][pos] == UNSET) {
            memo[target][pos] = dp_topDown_wrong(a, target - a[pos], pos + 1) + // place '+' before a[pos]
                    dp_topDown_wrong(a, target + a[pos], pos + 1); // place '-' before a[pos]
        }

        return memo[target][pos];
    }

    /**
     * Có lẽ phải dùng HashMap thay cho mảng, tạo 1 object Key với 2 field đại diện cho target và pos
     * Runtime 73 ms Beats 51.48%
     * Memory 44.3 MB Beats 5.25%
     * 
     * Có lẽ do dùng HashMap nên memory quá tệ!
     * Từ lời giải ta thấy: dp[i][j] = dp[i-a[j]][j+1] + dp[i+a[j]][j+1]
     * dp[i][j] phụ thuộc vào cả thằng trước và sau nó, làm sao mà giải được bằng cách bottom up???
     */
    public int dp_topDown(int[] a, int target, int pos) {
        if (pos == a.length) {
            if (target == 0)
                return 1;
            else
                return 0;
        }

        Key key = new Key(target, pos);
        if (dp.get(key) == null) {
            dp.put(key, dp_topDown(a, target - a[pos], pos + 1) + // place '+' before a[pos]
                    dp_topDown(a, target + a[pos], pos + 1)); // place '-' before a[pos]
        }

        return dp.get(key);
    }

    public static void main(String[] args) {
        TargetSum_494 app = new TargetSum_494();
        System.out.println(app.findTargetSumWays(new int[] {1, 1, 1, 1, 1}, 3)); // 5
        System.out.println(app.findTargetSumWays(new int[] {1, 3, 2, 4, 1}, 3)); // 4
    }

    private static class Key {
        private int target;
        private int pos;

        public Key(int target, int pos) {
            this.target = target;
            this.pos = pos;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key) {
                return ((Key) obj).target == target && ((Key) obj).pos == pos;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return target * pos << 1;
        }
    }
}
