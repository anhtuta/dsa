package practice.leetcode.medium;

/**
 * https://leetcode.com/problems/longest-common-subsequence/
 * 
 * Bài này giống bài {@link LongestPalindromicSubsequence_516}
 */
public class LongestCommonSubsequence_1143 {
    private int[][] memo;

    public int longestCommonSubsequence(String text1, String text2) {
        int ans;
        // Step 1: recursion top down nhưng bị timeout
        // ans = longestCommonSubsequence_recursion(text1, text2, 0, 0);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // memo = new int[text1.length()][text2.length()];
        // for (int i = 0; i < memo.length; i++) {
        // for (int j = 0; j < memo[0].length; j++) {
        // memo[i][j] = -1;
        // }
        // }
        // ans = longestCommonSubsequence_DP_topDown(text1, text2, 0, 0);

        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        memo = new int[text1.length() + 1][text2.length() + 1];
        ans = longestCommonSubsequence_DP_bottomUp_memo(text1, text2);

        // Có thể duyệt ngược lại nếu thích =))
        // memo = new int[text1.length() + 1][text2.length() + 1];
        // ans = longestCommonSubsequence_DP_bottomUp_memo_reverseOrder(text1, text2);

        // Step 4: tối ưu hơn nữa (tối ưu memory) bằng việc dùng DP bottom up without memo
        // Khả năng cao bài này cũng ko làm được bằng bottom up + N variables, giống bài UniquePaths_62

        return ans;
    }

    /**
     * Solution dùng đệ quy: ta sẽ dùng 2 con trỏ chạy từ 2 đầu của 2 string, tại mỗi vòng lặp:
     * - Nếu như char tại 2 con trỏ = nhau, thì đây chính là 1 phần tử của common subsequence
     * Tiếp tục check các ký tự tiếp theo của cả 2 string để xem có cặp char nào = nhau nữa hay ko.
     * - Nếu như char tại 2 con trỏ != nhau, ta cần xét 2 case: tăng con trỏ 1 hoặc 2 để check tiếp, và
     * lấy max của 2 case này
     * 
     * Result:
     * Time complexity: O(2^(n+m)), where n and m are length of text1 and text2 respectively
     * Time Limit Exceeded: 17 / 47 testcases passed
     */
    public int longestCommonSubsequence_recursion(String text1, String text2, int i1, int i2) {
        if (i1 >= text1.length() || i2 >= text2.length())
            return 0;
        if (text1.charAt(i1) == text2.charAt(i2)) {
            return 1 + longestCommonSubsequence_recursion(text1, text2, i1 + 1, i2 + 1);
        } else {
            return Math.max(longestCommonSubsequence_recursion(text1, text2, i1 + 1, i2),
                    longestCommonSubsequence_recursion(text1, text2, i1, i2 + 1));
        }
    }

    /**
     * Giống hệt longestCommonSubsequence_recursion, chỉ khác mỗi ở chỗ dùng memo thay cho lời gọi hàm
     * đệ quy.
     * Again: gọi là top down nhưng ko phải duyệt từ cuối dãy mà lại là duyệt từ đầu dãy. Top down ở đây
     * ko phải là vậy, mà nó là duyệt từ bài toán tổng quát -> bài toán base.
     * Base trong bài này là tìm common subsequence của text1[n-1] và text2, hoặc text1 và text2[m-1]
     * 
     * Result:
     * Time complexity: O(n*m), where n and m are length of text1 and text2 respectively
     */
    public int longestCommonSubsequence_DP_topDown(String text1, String text2, int i1, int i2) {
        if (i1 >= text1.length() || i2 >= text2.length())
            return 0;
        if (memo[i1][i2] == -1) {
            if (text1.charAt(i1) == text2.charAt(i2)) {
                memo[i1][i2] = 1 + longestCommonSubsequence_DP_topDown(text1, text2, i1 + 1, i2 + 1);
            } else {
                memo[i1][i2] = Math.max(longestCommonSubsequence_DP_topDown(text1, text2, i1 + 1, i2),
                        longestCommonSubsequence_DP_topDown(text1, text2, i1, i2 + 1));
            }
        }
        return memo[i1][i2];
    }

    /**
     * Khử đệ quy bằng bottom up + memo.
     * Từ DP top down ở trên, ta có thể thấy memo[i1][i2] sẽ phụ thuộc vào:
     * - memo[i1+1][i2+1]
     * - memo[i1+1][i2]
     * - memo[i1][i2+1]
     * Tức là sẽ phụ thuộc vào các phần tử đứng sau nó trong mảng 2 chiều, do đó để implement bottom up,
     * ta sẽ tính các phần tử phía sau đó trước, tức là ta sẽ tính toán từ memo[n-1][m-1] -> memo[0][0]
     * 
     * Chú ý là memo[n-1][m-1] sẽ phụ thuộc vào memo[n][m], vượt quá index của text1, và text2, nên ta
     * sẽ tăng kích thước của memo lúc init: memo = new int[n+1][m+1], và hiển nhiên, memo[n][m] = 0,
     * và toàn bộ các memo[n][x1] = 0, memo[x2][m] = 0, với x1 < n, x2 < m
     * 
     * Runtime 30 ms Beats 68.93%
     * Memory 50.5 MB Beats 48.29%
     */
    public int longestCommonSubsequence_DP_bottomUp_memo(String text1, String text2) {
        for (int i1 = text1.length() - 1; i1 >= 0; i1--) {
            for (int i2 = text2.length() - 1; i2 >= 0; i2--) {
                if (text1.charAt(i1) == text2.charAt(i2)) {
                    memo[i1][i2] = 1 + memo[i1 + 1][i2 + 1];
                } else {
                    memo[i1][i2] = Math.max(memo[i1 + 1][i2], memo[i1][i2 + 1]);
                }
                // System.out.printf("memo[%d][%d] = %d%n", i1, i2, memo[i1][i2]);
            }
        }
        return memo[0][0];
    }

    /**
     * Có thể dùng bottom up theo chiều ngược lại, bởi vì bài này là đếm common subsequence nên thứ tự
     * duyệt ko quan trọng, nếu duyệt ngược thì memo[i1][i2] sẽ phụ thuộc vào:
     * - memo[i1-1][i2-1]
     * - memo[i1-1][i2]
     * - memo[i1][i2-1]
     * Do đó memo[0][0] sẽ bị index = -1, do đó mảng memo, ta sẽ dùng từ index 1 -> n,m
     */
    public int longestCommonSubsequence_DP_bottomUp_memo_reverseOrder(String text1, String text2) {
        int x1, x2; // duyệt text1, text2 = i1,i2 nhưng duyệt mảng memo = x1,x2 cho đỡ bị index của memo = -1
        for (int i1 = 0; i1 < text1.length(); i1++) {
            for (int i2 = 0; i2 < text2.length(); i2++) {
                x1 = i1 + 1;
                x2 = i2 + 1;
                if (text1.charAt(i1) == text2.charAt(i2)) {
                    memo[x1][x2] = 1 + memo[x1 - 1][x2 - 1];
                } else {
                    memo[x1][x2] = Math.max(memo[x1 - 1][x2], memo[x1][x2 - 1]);
                }
                // System.out.printf("memo[%d][%d] = %d%n", x1, x2, memo[x1][x2]);
            }
        }
        return memo[text1.length()][text2.length()];
    }

    public static void main(String[] args) {
        LongestCommonSubsequence_1143 app = new LongestCommonSubsequence_1143();
        System.out.println(app.longestCommonSubsequence("abcde", "ace")); // 3
    }
}
