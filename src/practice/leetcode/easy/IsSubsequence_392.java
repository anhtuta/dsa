package practice.leetcode.easy;

/**
 * https://leetcode.com/problems/is-subsequence/
 */
public class IsSubsequence_392 {
    /**
     * Ref:
     * https://leetcode.com/explore/interview/card/leetcodes-interview-crash-course-data-structures-and-algorithms/703/arraystrings/4501/
     */
    public boolean isSubsequence(String s, String t) {
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                j++;
            }
        }
        return i == s.length();
    }

    public static void main(String[] args) {
        System.out.println(new IsSubsequence_392().isSubsequence("abc", "ahbgdc")); // true
    }
}
