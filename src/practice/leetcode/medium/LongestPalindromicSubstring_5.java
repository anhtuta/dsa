package practice.leetcode.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/longest-palindromic-substring/
 */
public class LongestPalindromicSubstring_5 {
    private int[][] memo;
    private int[][][] dp;
    private Map<MyKey, Integer> map;

    /**
     * Idea: do input size <= 1000 nên có thể dùng 2 vòng for, O(n^2) được
     * Brute-force: tìm tất cả các Palindromic Substring và so sánh. Cứ duyệt từ đầu, tại mỗi vị trí
     * charAt của string s, cần xét cả 2 case sau (gọi tắt là Palindromic cho gọi):
     * - len của Palindromic = số chẵn, ex: bb, xảy ra khi cs[i] = cs[i+1]
     * - len của Palindromic = số lẻ, ex: bab, luôn xảy ra
     * (cs ở đây là mảng char, cs = s.toCharArray())
     * 
     * Tại sao phải xét cả 2 case như vậy, ví dụ s = "aaaaa", tại i = 2, nếu chỉ xét case len của
     * Palindromic = số chẵn, thì kq thu được chỉ là aaaa, với length = 4, trong khi đáp án đúng phải là
     * aaaaa
     * 
     * Note: bài này có thể giải = Dynamic Programming, nhưng t chưa tìm được solution nào dùng DP
     * 
     * Update: đã nghĩ ra công thức đệ quy rồi. Xem ở dưới
     */
    public String longestPalindrome(String s) {
        if (s.length() <= 1)
            return s;
        char[] cs = s.toCharArray();
        int max = 0, maxStart = -1, maxEnd = -1;
        int currLen = 0;
        int j = 0, k = 0;
        for (int i = 0; i < cs.length - 1; i++) {
            if (cs[i] == cs[i + 1]) {
                // len của Palindromic = số chẵn
                currLen = 2;
                j = i - 1;
                k = i + 2;
                while (j >= 0 && k < cs.length) {
                    if (cs[j] == cs[k]) {
                        currLen += 2;
                        j--;
                        k++;
                    } else {
                        break;
                    }
                }
                if (max < currLen) {
                    max = currLen;
                    maxStart = j + 1;
                    maxEnd = k - 1;
                }
            }

            // len của Palindromic = số lẻ
            currLen = 1;
            j = i - 1;
            k = i + 1;
            while (j >= 0 && k < cs.length) {
                if (cs[j] == cs[k]) {
                    currLen += 2;
                    j--;
                    k++;
                } else {
                    break;
                }
            }
            if (max < currLen) {
                max = currLen;
                maxStart = j + 1;
                maxEnd = k - 1;
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = maxStart; i <= maxEnd; i++) {
            builder.append(cs[i]);
        }
        return builder.toString();
    }

    /**
     * Bắt đầu bằng bài toán đơn giản hơn là tìm length của palindromic thay vì tìm substring đó. Ta vẫn
     * dùng brute-force nhưng sẽ đệ quy. Tại mỗi vị trí charAt của s, xét từng các substring có middle
     * là vị trí đó, rồi đi sang 2 bên cùng lúc để check xem substring đó có phải là palindromic hay ko
     */
    public int longestPalindrome2_findLen(String s) {
        if (s.length() <= 1)
            return s.length();
        int max = -1;

        // Duyệt từng ký tự của s, tại mỗi vị trí coi nó là ký tự middle của substring cần tìm.
        // Ta sẽ phải đi sang 2 bên để tìm palindromic substring đó
        for (int i = 0; i < s.length(); i++) {
            int curr = Math.max(
                    recursion(s, i, i), // len của palindromic là số lẻ, s[i] là phần tử ở giữa
                    recursion(s, i, i + 1)); // len của palindromic là số chẵn, s[i] và s[i+1] là 2 phần tử ở giữa
            if (curr > max) {
                max = curr;
            }
        }
        return max;
    }

    private int recursion(String s, int left, int right) {
        if (left < 0 || right > s.length() - 1)
            return 0;
        if (left == right)
            return 1 + recursion(s, left - 1, right + 1);
        if (s.charAt(left) == s.charAt(right))
            return 2 + recursion(s, left - 1, right + 1);
        else
            return 0;
    }

    /**
     * Idea: recursion
     * Sau khi tìm được len rồi thì việc tìm substring sẽ đơn giản: chỉ cần 1 biến lưu vị trí middle của
     * substring đó là được
     * 
     * Runtime 42 ms Beats 40.20%
     * Memory 41.4 MB Beats 72%
     * 
     * Vậy là đã tìm ra công thức đệ quy thành công rồi đó! Giờ chỉ việc dùng memo tối ưu thôi!
     */
    public String longestPalindrome2(String s) {
        if (s.length() <= 1)
            return s;
        int max = -1;
        int maxMidIdx = -1; // vị trí ở chính giữa của palindromic cần tìm
        int maxLeftIdx, maxRightIdx; // vị trí bắt đầu và kết thúc của palindromic cần tìm
        for (int i = 0; i < s.length(); i++) {
            int curr = Math.max(
                    recursion(s, i, i), // len của palindromic là số lẻ, s[i] là phần tử ở giữa
                    recursion(s, i, i + 1)); // len của palindromic là số chẵn, s[i] và s[i+1] là 2 phần tử ở giữa
            if (curr > max) {
                max = curr;
                maxMidIdx = i;
            }
        }

        // Giờ duyệt từ mid sang 2 bên để tìm 2 điểm bắt đầu và kết thúc của substring
        if (max % 2 != 0) { // len của palindromic là số lẻ
            maxLeftIdx = maxMidIdx;
            maxRightIdx = maxMidIdx;
            max++; // để tý nữa tại vòng while đầu tiên có thể gán max -= 2;
            // Vì vòng while đầu tiên đó, s.charAt(maxLeftIdx) và s.charAt(maxRightIdx) là 1 vị trí, nên khi
            // duyệt sang 2 ký tự 2 bên của nó, ta chỉ được giảm max đi 1 đơn vị. Nhưng các vòng while sau đều
            // phải giảm 2 đơn vị
        } else { // len của palindromic là số chẵn
            maxLeftIdx = maxMidIdx;
            maxRightIdx = maxMidIdx + 1;
        }
        // System.out.printf("maxMidIdx = %d, maxLeftIdx = %d, maxRightIdx = %d%n",
        // maxMidIdx, maxLeftIdx, maxRightIdx);
        while (s.charAt(maxLeftIdx) == s.charAt(maxRightIdx)) {
            maxLeftIdx--;
            maxRightIdx++;
            max -= 2;
            if (max == 0)
                break;
        }

        return s.substring(maxLeftIdx + 1, maxRightIdx);
    }

    /**
     * Idea: optimize recursion by using DP top down + memo
     * 
     * Result:
     * Time Limit Exceeded
     * 58 / 141 testcases passed
     * 
     * Why this solution slower than recursion? Maybe because we have to init memo n times,
     * whereas n = s.len
     */
    public String longestPalindrome3(String s) {
        if (s.length() <= 1)
            return s;

        memo = new int[s.length() + 1][s.length() + 1];
        int max = -1;
        int maxMidIdx = -1; // vị trí ở chính giữa của palindromic cần tìm
        int maxLeftIdx, maxRightIdx; // vị trí bắt đầu và kết thúc của palindromic cần tìm
        for (int i = 0; i < s.length(); i++) {
            // Init memo for each position of middle character (we must find middle character of substring)
            // Maybe this init leads to timeout, because we do it n times
            for (int i1 = 0; i1 < memo.length; i1++) {
                for (int i2 = 0; i2 < memo[0].length; i2++) {
                    memo[i1][i2] = -1;
                }
            }

            // Call recursion and memo
            int curr = Math.max(
                    dp_topDown(s, i, i), // len của palindromic là số lẻ, s[i] là phần tử ở giữa
                    dp_topDown(s, i, i + 1)); // len của palindromic là số chẵn, s[i] và s[i+1] là 2 phần tử ở giữa
            if (curr > max) {
                max = curr;
                maxMidIdx = i;
            }
        }

        // Giờ duyệt từ mid sang 2 bên để tìm 2 điểm bắt đầu và kết thúc của substring
        if (max % 2 != 0) { // len của palindromic là số lẻ
            maxLeftIdx = maxMidIdx;
            maxRightIdx = maxMidIdx;
            max++; // để tý nữa tại vòng while đầu tiên có thể gán max -= 2;
        } else { // len của palindromic là số chẵn
            maxLeftIdx = maxMidIdx;
            maxRightIdx = maxMidIdx + 1;
        }
        while (s.charAt(maxLeftIdx) == s.charAt(maxRightIdx)) {
            maxLeftIdx--;
            maxRightIdx++;
            max -= 2;
            if (max == 0)
                break;
        }

        return s.substring(maxLeftIdx + 1, maxRightIdx);
    }

    private int dp_topDown(String s, int left, int right) {
        if (left < 0 || right > s.length() - 1)
            return 0;
        if (memo[left][right] == -1) {
            if (left == right)
                memo[left][right] = 1 + dp_topDown(s, left - 1, right + 1);
            // Note: must have else at this point, because in recursion method, we return a value,
            // so no need else statement
            else if (s.charAt(left) == s.charAt(right))
                memo[left][right] = 2 + dp_topDown(s, left - 1, right + 1);
            else
                memo[left][right] = 0;
        }
        return memo[left][right];
    }

    /**
     * Idea: optimize solution 3 by using 3D array of memo
     * 
     * Memory Limit Exceeded
     * 46 / 141 testcases passed
     * 
     * Even worse than previous, because out of heap memory: declare 3D array [1000][1000][1000] is NOT
     * possible
     */
    public String longestPalindrome4(String s) {
        if (s.length() <= 1)
            return s;

        dp = new int[s.length() + 1][s.length() + 1][s.length() + 1];
        // Init memo
        for (int i = 0; i < dp.length; i++) {
            for (int i1 = 0; i1 < dp.length; i1++) {
                for (int i2 = 0; i2 < dp.length; i2++) {
                    dp[i][i1][i2] = -1;
                }
            }
        }

        int max = -1;
        int maxMidIdx = -1; // vị trí ở chính giữa của palindromic cần tìm
        int maxLeftIdx, maxRightIdx; // vị trí bắt đầu và kết thúc của palindromic cần tìm
        for (int i = 0; i < s.length(); i++) {
            // Call recursion and memo
            int curr = Math.max(
                    dp_topDown2(s, i, i, i), // len của palindromic là số lẻ, s[i] là phần tử ở giữa
                    dp_topDown2(s, i, i, i + 1)); // len của palindromic là số chẵn, s[i] và s[i+1] là 2 phần tử ở giữa
            if (curr > max) {
                max = curr;
                maxMidIdx = i;
            }
        }

        // Giờ duyệt từ mid sang 2 bên để tìm 2 điểm bắt đầu và kết thúc của substring
        if (max % 2 != 0) { // len của palindromic là số lẻ
            maxLeftIdx = maxMidIdx;
            maxRightIdx = maxMidIdx;
            max++; // để tý nữa tại vòng while đầu tiên có thể gán max -= 2;
        } else { // len của palindromic là số chẵn
            maxLeftIdx = maxMidIdx;
            maxRightIdx = maxMidIdx + 1;
        }
        while (s.charAt(maxLeftIdx) == s.charAt(maxRightIdx)) {
            maxLeftIdx--;
            maxRightIdx++;
            max -= 2;
            if (max == 0)
                break;
        }

        return s.substring(maxLeftIdx + 1, maxRightIdx);
    }

    private int dp_topDown2(String s, int currPos, int left, int right) {
        if (left < 0 || right > s.length() - 1)
            return 0;
        if (dp[currPos][left][right] == -1) {
            if (left == right)
                dp[currPos][left][right] = 1 + dp_topDown2(s, currPos, left - 1, right + 1);
            // Note: must have else at this point, because in recursion method, we return a value,
            // so no need else statement
            else if (s.charAt(left) == s.charAt(right))
                dp[currPos][left][right] = 2 + dp_topDown2(s, currPos, left - 1, right + 1);
            else
                dp[currPos][left][right] = 0;
        }
        return dp[currPos][left][right];
    }

    /**
     * Idea: optimize space of solution 4 by using HashMap for memo
     * 
     * Runtime 204 ms Beats 26.48%
     * Memory 130.6 MB Beats 5.6%
     * 
     * Vẫn chậm hơn recursion rất rất nhiều! Haizzz, thế nên tốt nhất đừng bao giờ dùng thư viện Java,
     * vì nó quá chậm so với mảng
     * 
     * Thôi tới đây bỏ cuộc, sau này nghĩ được solution khác sẽ làm tiếp
     */
    public String longestPalindrome5(String s) {
        if (s.length() <= 1)
            return s;

        map = new HashMap<>(s.length());
        int max = -1;
        int maxMidIdx = -1; // vị trí ở chính giữa của palindromic cần tìm
        int maxLeftIdx, maxRightIdx; // vị trí bắt đầu và kết thúc của palindromic cần tìm
        for (int i = 0; i < s.length(); i++) {
            // Call recursion and memo
            int curr = Math.max(
                    dp_topDown3(s, i, i, i), // len của palindromic là số lẻ, s[i] là phần tử ở giữa
                    dp_topDown3(s, i, i, i + 1)); // len của palindromic là số chẵn, s[i] và s[i+1] là 2 phần tử ở giữa
            if (curr > max) {
                max = curr;
                maxMidIdx = i;
            }
        }

        // Giờ duyệt từ mid sang 2 bên để tìm 2 điểm bắt đầu và kết thúc của substring
        if (max % 2 != 0) { // len của palindromic là số lẻ
            maxLeftIdx = maxMidIdx;
            maxRightIdx = maxMidIdx;
            max++; // để tý nữa tại vòng while đầu tiên có thể gán max -= 2;
        } else { // len của palindromic là số chẵn
            maxLeftIdx = maxMidIdx;
            maxRightIdx = maxMidIdx + 1;
        }
        while (s.charAt(maxLeftIdx) == s.charAt(maxRightIdx)) {
            maxLeftIdx--;
            maxRightIdx++;
            max -= 2;
            if (max == 0)
                break;
        }

        return s.substring(maxLeftIdx + 1, maxRightIdx);
    }

    private int dp_topDown3(String s, int currPos, int left, int right) {
        if (left < 0 || right > s.length() - 1)
            return 0;
        MyKey key = new MyKey(currPos, left, right);
        if (!map.containsKey(key)) {
            if (left == right)
                map.put(key, 1 + dp_topDown3(s, currPos, left - 1, right + 1));
            // Note: must have else at this point, because in recursion method, we return a value,
            // so no need else statement
            else if (s.charAt(left) == s.charAt(right))
                map.put(key, 2 + dp_topDown3(s, currPos, left - 1, right + 1));
            else
                map.put(key, 0);
        }
        return map.get(key);
    }

    static class MyKey {
        int currPos;
        int left;
        int right;

        public MyKey(int currPos, int left, int right) {
            this.currPos = currPos;
            this.left = left;
            this.right = right;
        }

        @Override
        public int hashCode() {
            return currPos * 97 + left * 89 + right * 83;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof MyKey) {
                MyKey m = (MyKey) o;
                return this.currPos == m.currPos && this.left == m.left && this.right == m.right;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        LongestPalindromicSubstring_5 app = new LongestPalindromicSubstring_5();
        // System.out.println(app.longestPalindrome("babad")); // bab
        // System.out.println(app.longestPalindrome("cbbd")); // bb
        // System.out.println(app.longestPalindrome("a")); // a
        // System.out.println(app.longestPalindrome("")); //
        // System.out.println(app.longestPalindrome("aaaaa")); // aaaaa
        // System.out.println(app.longestPalindrome("aaaaaa")); // aaaaaa
        // System.out.println(app.longestPalindrome("aaa")); // aaa

        // System.out.println(app.longestPalindrome2("babad")); // bab
        // System.out.println(app.longestPalindrome2("cbbd")); // bb
        // System.out.println(app.longestPalindrome2("a")); // a
        // System.out.println(app.longestPalindrome2("")); //
        // System.out.println(app.longestPalindrome2("aaaaa")); // aaaaa
        // System.out.println(app.longestPalindrome2("aaaaaa")); // aaaaaa
        // System.out.println(app.longestPalindrome2("aaa")); // aaa

        // System.out.println(app.longestPalindrome3("babad")); // bab
        // System.out.println(app.longestPalindrome3("cbbd")); // bb
        // System.out.println(app.longestPalindrome3("a")); // a
        // System.out.println(app.longestPalindrome3("")); //
        // System.out.println(app.longestPalindrome3("aaaaa")); // aaaaa
        // System.out.println(app.longestPalindrome3("aaaaaa")); // aaaaaa
        // System.out.println(app.longestPalindrome3("aaa")); // aaa

        // System.out.println(app.longestPalindrome4("babad")); // bab
        // System.out.println(app.longestPalindrome4("cbbd")); // bb
        // System.out.println(app.longestPalindrome4("a")); // a
        // System.out.println(app.longestPalindrome4("")); //
        // System.out.println(app.longestPalindrome4("aaaaa")); // aaaaa
        // System.out.println(app.longestPalindrome4("aaaaaa")); // aaaaaa
        // System.out.println(app.longestPalindrome4("aaa")); // aaa

        System.out.println(app.longestPalindrome5("babad")); // bab
        System.out.println(app.longestPalindrome5("cbbd")); // bb
        System.out.println(app.longestPalindrome5("a")); // a
        System.out.println(app.longestPalindrome5("")); //
        System.out.println(app.longestPalindrome5("aaaaa")); // aaaaa
        System.out.println(app.longestPalindrome5("aaaaaa")); // aaaaaa
        System.out.println(app.longestPalindrome5("aaa")); // aaa
        System.out.println(app.longestPalindrome5("null")); // ll
    }
}
