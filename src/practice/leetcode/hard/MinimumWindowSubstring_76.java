package practice.leetcode.hard;

import util.Utils;

/**
 * https://leetcode.com/problems/minimum-window-substring/
 * 
 * Given two strings s and t of lengths m and n respectively, return the minimum window
 * substring of s such that every character in t (including duplicates) is included in the window.
 * If there is no such substring, return the empty string "".
 * (Note: A substring is a contiguous non-empty sequence of characters within a string.)
 * 
 * The testcases will be generated such that the answer is unique.
 * 
 * Example 1:
 * 
 * Input: s = "ADOBECODEBANC", t = "ABC"
 * Output: "BANC"
 * Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
 * Example 2:
 * 
 * Input: s = "a", t = "a"
 * Output: "a"
 * Explanation: The entire string s is the minimum window.
 * Example 3:
 * 
 * Input: s = "a", t = "aa"
 * Output: ""
 * Explanation: Both 'a's from t must be included in the window.
 * Since the largest window of s only has one 'a', return empty string.
 * 
 * Constraints:
 * 
 * m == s.length
 * n == t.length
 * 1 <= m, n <= 10^5
 * s and t consist of uppercase and lowercase English letters.
 * 
 * Follow up: Could you find an algorithm that runs in O(m + n) time?
 * 
 * ===
 * Nên xem bài {@link practice.leetcode.medium.LongestSubstringWithoutRepeatingCharacters_3} trước
 */
public class MinimumWindowSubstring_76 {
    /**
     * Idea: sử dụng sliding window và counting element. Bài này giống hệt bài
     * {@link practice.others.MinSubstringContainsCharacters MinSubstringContainsCharacters}
     * Vui lòng mở bài đó mà đọc comment
     * 
     * Time complexity: O(m+n)
     * 
     * Result:
     * Runtime 6 ms Beats 83.86%
     * Memory 42.8 MB Beats 71.75%
     */
    public String minWindow_longButReadable(String s, String t) {
        if (s.length() < t.length())
            return "";

        // Do input chỉ là 2 string gồm các kí tự [A-Z], [a-z], và mã ASCII của z là 122
        int[] hashS = new int[123];
        int[] hashT = new int[123];
        for (int i = 0; i < t.length(); i++) {
            hashT[t.charAt(i)]++;
        }

        // Find the first character in S that exists in T
        int start = -1;
        for (int i = 0; i < s.length(); i++) {
            if (hashT[s.charAt(i)] > 0) {
                start = i;
                break;
            }
        }

        if (start == -1)
            return "";

        // Using two pointers on S, start at index = start
        int left = start;
        int count = 0; // count total characters in T are existed in S
        int len; // window size, len = right + left - 1
        int minLen = Integer.MAX_VALUE; // length of smallest substring we need to find
        int minStartIdx = -1, minEndIdx = -1; // indices of smallest substring we need to find
        int idx; // temp
        for (int right = start; right < s.length(); right++) {
            idx = s.charAt(right);
            hashS[idx]++;
            if (hashS[idx] <= hashT[idx])
                count++;

            if (count == t.length()) {
                System.out.print("\nFound a candidate window: ");
                Utils.printSubstring(s, left, right);

                // Tối ưu window:
                // Nếu như ký tự đầu tiên của window xuất hiện nhiều hơn số lần xuất hiện bên T,
                // ta sẽ remove nó để tối ưu length của substring là bé nhất có thể
                while (hashS[s.charAt(left)] > hashT[s.charAt(left)]) {
                    hashS[s.charAt(left)]--;
                    left++;

                    // Tiếp tục giảm window size từ left cho tới khi cạnh trái của window lại là
                    // ký tự có bên T
                    while (hashT[s.charAt(left)] == 0)
                        left++;

                    System.out.print("Window after optimizing: ");
                    Utils.printSubstring(s, left, right);
                }

                System.out.print("Found a result: ");
                Utils.printSubstring(s, left, right);

                len = right - left + 1;
                if (len < minLen) {
                    minLen = len;
                    minStartIdx = left;
                    minEndIdx = right;
                }

                // Cạnh trái của window của S chắc chắn là ký tự mà có ở string T,
                // và chắc chắn só lượng ký tự đó ở 2 bên S và T là bằng nhau,
                // ta sẽ remove ký tự này, window size lúc này cũng giảm đi 1 đơn vị
                count--;
                hashS[s.charAt(left)]--;
                left++;

                // Tiếp tục giảm window size từ left cho tới khi cạnh trái của window lại là
                // ký tự có bên T
                while (left < s.length() && hashT[s.charAt(left)] == 0)
                    left++;

                System.out.print("Window after removing from left: ");
                Utils.printSubstring(s, left, right);
            }
        }

        // return result, it is a substring between [minStartIdx...minEndIdx] of S
        if (minStartIdx != -1) {
            return s.substring(minStartIdx, minEndIdx + 1);
        }
        return "";
    }

    /**
     * Thử làm theo template này:
     * https://leetcode.com/problems/minimum-window-substring/solutions/26808/here-is-a-10-line-template-that-can-solve-most-substring-problems/comments/25816
     * 
     * Idea: cũng tương tự cách trên, dùng sliding window và counting element (hashmap), nhưng sẽ ngắn
     * gọn hơn, tối ưu hơn do chỉ dùng 1 hashmap. Mặc dù vậy, vẫn CHƯA hiểu bản chất của cách giải này.
     * Tại sao lại chỉ dùng 1 hashmap, tại sao lại có 2 chỗ này:
     * - cntArr[s.charAt(right)]--;
     * - cntArr[s.charAt(left)]++;
     * 
     * Sẽ quay lại tìm hiểu sau nếu có time, hoặc khi nào PRO hơn!
     * 
     * Result:
     * Runtime 5 ms Beats 85.57%
     * Memory 42.9 MB Beats 64.28%
     * 
     * Nhanh hơn cách trên 1 xíu, tuy chỉ dùng 1 map nhưng memory ko cải thiện được
     */
    public String minWindow(String s, String t) {
        int[] cntArr = new int[123];
        for (int i = 0; i < t.length(); i++) {
            cntArr[t.charAt(i)]++;
        }

        int count = 0; // count number of characters of S that are existed in T
        int left = 0, right = 0; // window boundary
        int minLen = Integer.MAX_VALUE; // length of smallest substring we need to find
        int minStartIdx = -1; // start index of smallest substring

        while (right < s.length()) {
            if (cntArr[s.charAt(right)] > 0) { // this character of S is existed in T
                count++;
            }
            cntArr[s.charAt(right)]--;
            right++;

            while (count == t.length()) { // window now contains all characters in T
                // Update answer
                if (right - left < minLen) {
                    minLen = right - left; // window size
                    minStartIdx = left;
                }

                // Remove left out of window and reduce count if s[left] existed in T
                cntArr[s.charAt(left)]++;
                if (cntArr[s.charAt(left)] > 0) { // Don't understand this block!
                    count--;
                }
                left++;
            }
        }
        if (minStartIdx != -1) {
            return s.substring(minStartIdx, minStartIdx + minLen);
        }
        return "";
    }

    public static void main(String[] args) {
        MinimumWindowSubstring_76 app = new MinimumWindowSubstring_76();
        System.out.println(app.minWindow("ADOBECODEBANC", "ABC")); // BANC
        System.out.println(app.minWindow("a", "a")); // a
        System.out.println(app.minWindow("a", "aa")); // [nothing, print empty]
    }
}
