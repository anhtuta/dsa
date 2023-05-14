package practice.leetcode.medium.dp;

/**
 * https://leetcode.com/problems/coin-change/
 * 
 * You are given an integer array coins representing coins of different denominations and an integer
 * amount representing a total amount of money.
 * 
 * Return the fewest number of coins that you need to make up that amount. If that amount of money
 * cannot be made up by any combination of the coins, return -1.
 * 
 * You may assume that you have an infinite number of each kind of coin.
 * 
 * Example 1:
 * Input: coins = [1,2,5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 * 
 * Example 2:
 * Input: coins = [2], amount = 3
 * Output: -1
 * 
 * Example 3:
 * Input: coins = [1], amount = 0
 * Output: 0
 * 
 * Constraints:
 * 
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 2^31 - 1
 * 0 <= amount <= 10^4
 * 
 * =====
 * Tóm tắt: mảng coins đại diện cho các mệnh giá tiền khác nhau (denominations), chẳng hạn [1, 2, 5]
 * nghĩa là có 3 mệnh giá tiền bao gồm 1 đồng, 2 đồng và 5 đồng
 * amount là số tiền bạn cần phải tạo được từ các mệnh giá tiền đó
 * Tìm số lượng bé nhất các mệnh giá mà có thể tạo được amount, nếu ko tạo được thì return -1
 */
public class CoinChange_322 {
    private int[] memo; // memo[i] = số lượng tờ tiền tối thiểu để có thể tạo thành amount

    public int coinChange(int[] coins, int amount) {
        int ans;

        // Step 1: recursion top down nhưng bị timeout
        // ans = recursion(coins, amount);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // Note: init các memo[i] = 0 tức là các vị trí đó chưa tính toán (trừ memo[0] luôn = 0)
        // memo = new int[amount + 1];
        // ans = dp_topDown(coins, amount);

        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        // init base case: memo[i] = 1, nếu i là các mệnh giá đã cho
        // memo = new int[amount + 1];
        // for (int coin : coins) {
        // if (coin > amount)
        // continue;
        // memo[coin] = 1;
        // }
        // ans = dp_bottomUp_memo(coins, amount);

        // Step 3.2: Alternative way...
        memo = new int[amount + 1];
        for (int coin : coins) {
            if (coin > amount)
                continue;
            memo[coin] = 1;
        }
        ans = dp_bottomUp_memo_alternative(coins, amount);

        // Step 4: tối ưu hơn nữa (tối ưu memory) bằng việc dùng DP bottom up without memo
        // Khả năng bài này ko làm được theo cách này

        return ans;
    }

    /**
     * Đệ quy top down brute-force: thử tất cả các cách chọn mệnh giá rồi return giá trị bé nhất.
     * Dễ thấy sau khi lấy tờ tiền coin thì tổng số tờ cần thiết sẽ là
     * 1 + số tờ có thể tạo thành (amount - coin)
     * 
     * Result
     * Time Limit Exceeded 15 / 189 testcases passed
     * 
     * Thậm chí còn timeout ở test case bé như này:
     * coinChange([1, 2, 5], 100)
     */
    public int recursion(int[] coins, int amount) {
        if (amount == 0)
            // Bài toán có thể tìm được đáp án, đến đây là tính toán xong rồi, ko cần đệ quy nữa
            return 0;
        if (amount < 0)
            // Bài toán ko có đáp án (tức là ko thể dùng các mệnh giá đã cho để tạo thành amount)
            return -1;
        int min = Integer.MAX_VALUE;
        int temp;
        // tìm min của các memo trước đó, là các memo[amount - coin]
        for (int coin : coins) {
            temp = recursion(coins, amount - coin);
            // nếu như temp = -1 tức là ko thể dùng các mệnh giá đã cho để tạo thành (amount - coin)
            if (temp >= 0 && temp < min) {
                min = temp;
            }
        }
        // nếu như min = -1 tức là ko thể dùng các mệnh giá đã cho để tạo thành amount
        if (min == Integer.MAX_VALUE)
            return -1;
        return min + 1;
    }

    /**
     * Result:
     * Runtime 29 ms Beats 31.94%
     * Memory 42.5 MB Beats 44.36%
     */
    public int dp_topDown(int[] coins, int amount) {
        if (amount == 0)
            memo[amount] = 0;
        else if (amount < 0)
            return -1;
        else if (memo[amount] == 0) {
            // Bê nguyên từ đệ quy xuống đây, lưu vào biến memo
            int min = Integer.MAX_VALUE;
            int temp;
            for (int coin : coins) {
                if (coin > amount)
                    continue;
                temp = dp_topDown(coins, amount - coin);
                if (temp >= 0 && temp < min) {
                    min = temp;
                }
            }
            if (min == Integer.MAX_VALUE)
                memo[amount] = -1;
            else
                memo[amount] = min + 1;
        }

        System.out.printf("memo[%d] = %d%n", amount, memo[amount]);
        return memo[amount];
    }

    /**
     * Khử đệ quy của top down bằng bottom up + memo
     * Idea: ta sẽ tính toán từ memo[0] -> memo[1] -> memo[2] -> ... -> memo[amount]
     * Ta sẽ dùng 1 biến tên là money, và duyệt money từ 1 -> amount để tính toán memo[money]
     * Ko cần dùng biến temp như ở trên nữa, vì top down cần biến temp đó để gọi đệ quy chứ nó chưa được
     * tính toán ở thời điểm được gọi, còn cách bottom up, ta lấy luôn giá trị trước đó vì nó đã được
     * tính toán rồi
     * 
     * Cách làm bottom up này hơi khó nghĩ vì dùng mảng 1 chiều memo[], đại diện cho đáp án của các bài
     * toán con khi amount = memo[i] (cách này t tự làm nhé, accepted)
     * 
     * Nếu dùng mảng 2D thì có lẽ sẽ dễ hiểu hơn chăng:
     * memo[][] = new int[coins.length + 1][amount + 1]
     * Thử đọc cái này xem:
     * https://leetcode.com/problems/coin-change/solutions/1371738/c-recursion-dp-memoization-dp-tabulation/
     * 
     * Result:
     * Runtime 20 ms Beats 46.13%
     * Memory 42.4 MB Beats 52.47%
     * 
     * Vẫn chỉ nhanh hơn 1 xíu so với top down + memo
     */
    public int dp_bottomUp_memo(int[] coins, int amount) {
        if (amount == 0)
            return 0;

        int min;
        for (int money = 1; money <= amount; money++) {
            if (memo[money] == 0) {
                min = Integer.MAX_VALUE;
                // tìm min của các memo trước đó, là các memo[money - coin]
                for (int coin : coins) {
                    if (coin > money)
                        continue;
                    if (memo[money - coin] >= 0 && memo[money - coin] < min) {
                        min = memo[money - coin];
                    }
                }
                if (min == Integer.MAX_VALUE)
                    memo[money] = -1;
                else
                    memo[money] = min + 1;
            }
        }

        return memo[amount];
    }

    /**
     * Cũng giống dp_bottomUp_memo, nhưng đổi vị trí 2 vòng for với nhau
     * Ref:
     * https://leetcode.com/problems/coin-change/solutions/77360/c-o-n-amount-time-o-amount-space-dp-solution/comments/81414
     */
    public int dp_bottomUp_memo_alternative(int[] coins, int amount) {
        if (amount == 0)
            return 0;

        for (int coin : coins) {
            for (int money = coin; money <= amount; money++) {
                // ko thể tạo được số tiền = money từ đồng có mệnh giá = (money - coin)
                if (memo[money - coin] == 0)
                    continue;
                if (memo[money] == 0) {
                    // lần đầu tiên tính toán
                    memo[money] = memo[money - coin] + 1;
                } else {
                    // từ các lần sau, update min value cho memo[money]
                    memo[money] = Math.min(memo[money], memo[money - coin] + 1);
                }
            }
        }

        return memo[amount] == 0 ? -1 : memo[amount];
    }

    public static void main(String[] args) {
        CoinChange_322 app = new CoinChange_322();
        System.out.println(app.coinChange(new int[] {1, 2, 5}, 11)); // 3
        System.out.println(app.coinChange(new int[] {1, 2, 5}, 100)); // 20
        System.out.println(app.coinChange(new int[] {3, 7}, 5)); // -1
        System.out.println(app.coinChange(new int[] {186, 419, 83, 408}, 6249)); // 20
    }
}
