package practice.leetcode.medium;

/**
 * https://leetcode.com/problems/longest-palindromic-subsequence/
 * 
 * Cách tiếp cận tự đệ quy -> DP, tham khảo bài {@link HouseRobber_198} và:
 * https://emre.me/coding-patterns/palindromes/
 */
public class LongestPalindromicSubsequence_516 {
    private int[][] memo;

    public int longestPalindromeSubseq(String s) {
        int ans;
        // Step 1: recursion top down nhưng bị timeout
        // ans = longestPalindromeSubseq_recursion(s, 0, s.length() - 1);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // memo = new int[s.length()][s.length()];
        // for (int i = 0; i < memo.length; i++) {
        // for (int j = 0; j < memo[0].length; j++) {
        // memo[i][j] = -1;
        // }
        // }
        // ans = longestPalindromeSubseq_DP_topDown(s, 0, s.length() - 1);

        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        memo = new int[s.length()][s.length()];
        ans = longestPalindromeSubseq_DP_bottomUp_memo_noLog(s);

        // Step 4: tối ưu hơn nữa (tối ưu memory) bằng việc dùng DP bottom up without memo
        // Khả năng cao bài này cũng ko làm được bằng bottom up + N variables, giống bài UniquePaths_62

        return ans;
    }

    /**
     * Solution dùng đệ quy: ta sẽ dùng 2 con trỏ chạy từ 2 phía của string s (bởi vì Palindromic là
     * chuỗi mà đọc theo 2 chiều là như nhau, nên mới nghĩ đến việc duyệt từ 2 phía), tại mỗi vòng lặp:
     * - Nếu như char tại 2 con trỏ = nhau, thì đây chính là 1 Palindromic Subsequence có độ dài bằng 2.
     * Tiếp tục check các ký tự ở giữa để xem có cặp char nào = nhau nữa hay ko.
     * - Nếu như char tại 2 con trỏ != nhau, ta cần xét 2 case: tăng con trỏ bên trái hoặc giảm con trỏ
     * bên phải để check tiếp, và lấy max của 2 case này
     * 
     * Result:
     * Time complexity: O(2^n)
     * Time Limit Exceeded: 61 / 86 testcases passed
     */
    public int longestPalindromeSubseq_recursion(String s, int start, int end) {
        if (start > end)
            return 0;
        if (start == end)
            return 1;
        if (s.charAt(start) == s.charAt(end)) {
            return 2 + longestPalindromeSubseq_recursion(s, start + 1, end - 1);
        } else {
            return Math.max(longestPalindromeSubseq_recursion(s, start, end - 1),
                    longestPalindromeSubseq_recursion(s, start + 1, end));
        }
    }

    /**
     * Tối ưu đệ quy = DP topDown + memo
     * Cơ mà, bài này duyệt từ 2 phía đầu và cuối của string, chứ có phải duyệt từ 1 phía cuối của
     * string đâu mà gọi là top down được nhỉ?
     * 
     * Result:
     * Accepted. Runtime: 39 ms
     * Time complexity: O(n^2), because we're using two pointers, and memo is an 2-D array
     */
    public int longestPalindromeSubseq_DP_topDown(String s, int start, int end) {
        if (start > end)
            return 0;
        if (start == end)
            return 1;
        if (memo[start][end] == -1) {
            if (s.charAt(start) == s.charAt(end)) {
                memo[start][end] = 2 + longestPalindromeSubseq_DP_topDown(s, start + 1, end - 1);
            } else {
                memo[start][end] = Math.max(longestPalindromeSubseq_DP_topDown(s, start, end - 1),
                        longestPalindromeSubseq_DP_topDown(s, start + 1, end));
            }
        }
        return memo[start][end];
    }

    /**
     * Để khử đệ quy thì phải tìm ra cách duyệt ntn để tính được các memo[i][j] dựa vào các memo trước
     * nó. Nếu như bài toán Fibonacci, việc tính F(i) = F(i-1) + F(i-2), thì lại rất đơn giản rồi. F(i)
     * chỉ phụ thuộc vào 2 số trước nó, do đó chỉ cần duyệt từ 0 là sẽ tính được
     * 
     * Với bài này thì sao? Bởi vì i,j chạy từ 2 phía đầu và cuối của string, nên memo[i][j] sẽ phụ
     * thuộc những thằng sau đây (i,j tương ứng với start,end):
     * - memo[i+1][j-1]
     * - memo[i][j-1]
     * - memo[i+1][j]
     * Dễ thấy i sẽ phụ thuộc những thằng sau nó, còn j thì ngược lại, phụ thuộc những thằng trước nó
     * trong mảng 2 chiều. Vì vậy, ta cần duyệt i từ cuối dãy -> 0, và duyệt j từ 0 -> cuối dãy.
     * Nhưng i <= j, do đó phải duyệt j từ i -> cuối dãy
     */
    public int longestPalindromeSubseq_DP_bottomUp_memo(String s) {
        for (int start = s.length() - 1; start >= 0; start--) {
            for (int end = start; end < s.length(); end++) {
                // System.out.printf("\nstart = %d, end = %d", start, end);
                if (start == end) {
                    memo[start][end] = 1;
                    System.out.printf(", memo[%d][%d] = %d", start, end, memo[start][end]);
                    continue;
                }
                if (s.charAt(start) == s.charAt(end)) {
                    memo[start][end] = 2 + memo[start + 1][end - 1];
                    System.out.printf(", memo[%d][%d] = 2 + memo[%d][%d] = %d",
                            start, end, start + 1, end - 1, memo[start][end]);
                } else {
                    memo[start][end] = Math.max(memo[start][end - 1], memo[start + 1][end]);
                    System.out.printf(", memo[%d][%d] = max(memo[%d][%d], memo[%d][%d]) = %d",
                            start, end, start, end - 1, start + 1, end, memo[start][end]);
                }
            }
        }
        System.out.println("\nDone!");
        return memo[0][s.length() - 1];
    }

    public int longestPalindromeSubseq_DP_bottomUp_memo_noLog(String s) {
        for (int start = s.length() - 1; start >= 0; start--) {
            for (int end = start; end < s.length(); end++) {
                if (start == end) {
                    memo[start][end] = 1;
                    continue;
                }
                if (s.charAt(start) == s.charAt(end)) {
                    memo[start][end] = 2 + memo[start + 1][end - 1];
                } else {
                    memo[start][end] = Math.max(memo[start][end - 1], memo[start + 1][end]);
                }
            }
        }
        return memo[0][s.length() - 1];
    }

    public static void main(String[] args) {
        LongestPalindromicSubsequence_516 app = new LongestPalindromicSubsequence_516();
        System.out.println(app.longestPalindromeSubseq("abcdef")); // 1
        System.out.println(app.longestPalindromeSubseq("bbbab")); // 4
        System.out.println(app.longestPalindromeSubseq("cbbd")); // 2
    }
}
