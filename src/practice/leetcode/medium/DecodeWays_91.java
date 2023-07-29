package practice.leetcode.medium;

/**
 * Problem: https://leetcode.com/problems/decode-ways/
 * (Medium)
 * 
 * A message containing letters from A-Z can be encoded into numbers using the following mapping:
 * 
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * To decode an encoded message, all the digits must be grouped then mapped back into letters using
 * the reverse of the mapping above (there may be multiple ways). For example, "11106" can be mapped
 * into:
 * 
 * "AAJF" with the grouping (1 1 10 6)
 * "KJF" with the grouping (11 10 6)
 * Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is
 * different from "06".
 * 
 * Given a string s containing only digits, return the number of ways to decode it.
 * 
 * The test cases are generated so that the answer fits in a 32-bit integer.
 * 
 * Example 1:
 * 
 * Input: s = "12"
 * Output: 2
 * Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).
 * Example 2:
 * 
 * Input: s = "226"
 * Output: 3
 * Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
 * Example 3:
 * 
 * Input: s = "06"
 * Output: 0
 * Explanation: "06" cannot be mapped to "F" because of the leading zero ("6" is different from
 * "06").
 * 
 * Constraints:
 * 
 * 1 <= s.length <= 100
 * s contains only digits and may contain leading zero(s) (số 0 đứng đầu).
 * 
 * @author tatu
 *
 */
public class DecodeWays_91 {
    private int[] memo;

    public int numDecodings(String s) {
        if (s.charAt(0) == '0')
            return 0;
        int ans;

        // Step 1: recursion top down nhưng bị timeout
        // ans = recursion(s, 0);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        memo = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            memo[i] = -1;
        }
        ans = dp_topDown(s, 0);

        //////// Mệt quá, chưa muốn nghĩ ra các bottom up, bao giờ rảnh sẽ làm tiếp...
        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        // memo = new int[s.length()];
        // ans = dp_bottomUp_memo(s);

        // Step 4: tối ưu hơn nữa (tối ưu memory) bằng việc dùng DP bottom up without memo
        // Khả năng bài này ko làm được theo cách này

        return ans;
    }

    public int recursion_wrong(String s, int pos) {
        // Out of range
        if (pos >= s.length())
            return 0;

        // number 3-9 can be decoded to single character only
        if (s.charAt(pos) > '2')
            return 1 + recursion(s, pos + 1); // WRONG, should NOT +1 here

        // if next of this position is zero, then pos and pos+1 can be decoded to single character only
        if (pos <= s.length() - 2 && s.charAt(pos + 1) == '0')
            return 1 + recursion(s, pos + 2); // WRONG, should NOT +1 here

        // the rest cases: can be decoded to two characters,
        // ex: '11' -> 'AA' or 'K'
        return recursion(s, pos + 1) + recursion(s, pos + 2);
    }

    /**
     * Idea: recursion, xét ví dụ sau: s = '129', pos = 0. Ta nhận thấy tại 2 vị trí đầu tiên có thể
     * được decode thành 2 cách là 'AB' hoặc 'L'.
     * - Nếu decode thành 'AB', thì cần xét tiếp ký tự '9'
     * - Nếu decode thành 'L', thì cần xét tiếp ký tự '2'
     * Do đó:
     * recursion(s, 0) = recursion(s, 1) + recursion(s, 2)
     * 
     * Time Limit Exceeded
     * 23 / 269 testcases passed
     */
    public int recursion(String s, int pos) {
        // we have traversed all characters of s, this is a decode way (an answer)
        if (pos == s.length())
            return 1;

        // Out of range
        if (pos > s.length())
            return 0;

        // Invalid, no character encoded to a number that starts with 0
        if (s.charAt(pos) == '0')
            return 0;

        // number 3-9 can be decoded to single character only
        // ex: '465' -> 'D F E'
        if (s.charAt(pos) > '2')
            return recursion(s, pos + 1);

        // if next of this position is in [7,8,9], then 'pos and pos+1' can be decoded to single character
        // only,
        // ex: '2829' -> 'B H B I'
        if (s.charAt(pos) == '2' && pos <= s.length() - 2 && s.charAt(pos + 1) >= '7')
            return recursion(s, pos + 2);

        // if next of this position is zero, then 'pos and pos+1' can be decoded to single character only,
        // and pos+1 CANNOT be decoded to any character, it must be decoded with pos
        // ex: '1020' -> 'J T'
        // Update: no need to check this case, as we've already check '0' above
        // if (pos <= s.length() - 2 && s.charAt(pos + 1) == '0')
        // return recursion(s, pos + 2);

        // the rest cases: pos and pos+1 can be two characters from '10' to '26',
        // these cases can be decoded in two ways,
        // ex1: '11' -> 'A A' or 'K'
        // ex2: '26' -> 'B B' or 'Z'
        return recursion(s, pos + 1) + recursion(s, pos + 2);
    }

    /**
     * Optimize recursion by using DP top down + memo. Clone code from recursion.
     * Note: MUST add else clause, because in recursion, we return value directly and no need else
     * 
     * Runtime 1 ms Beats 95.9%
     * Memory 40.6 MB Beats 90.70%
     */
    public int dp_topDown(String s, int pos) {
        // we have traversed all characters of s, this is a decode way (an answer)
        if (pos == s.length())
            return 1;

        // Out of range
        if (pos > s.length())
            return 0;

        // Invalid, no character encoded to a number that starts with 0
        if (s.charAt(pos) == '0')
            return 0;

        if (memo[pos] == -1) {
            if (s.charAt(pos) > '2')
                memo[pos] = dp_topDown(s, pos + 1);

            else if (s.charAt(pos) == '2' && pos <= s.length() - 2 && s.charAt(pos + 1) >= '7')
                memo[pos] = dp_topDown(s, pos + 2);

            else
                memo[pos] = dp_topDown(s, pos + 1) + dp_topDown(s, pos + 2);
        }

        return memo[pos];
    }

    public static void main(String[] args) {
        DecodeWays_91 app = new DecodeWays_91();
        System.out.println(app.numDecodings("26")); // 2
        System.out.println(app.numDecodings("28")); // 1
        System.out.println(app.numDecodings("12")); // 2
        System.out.println(app.numDecodings("226")); // 3
        System.out.println(app.numDecodings("11106")); // 2: [1 1 10 6], [11 10 6]
        System.out.println(app.numDecodings("4")); // 1
        System.out.println(app.numDecodings("46857")); // 1
        System.out.println(app.numDecodings("106")); // 1
        System.out.println(app.numDecodings("418")); // 2
    }
}
