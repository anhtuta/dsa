package practice.leetcode.medium;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 */
public class LongestSubstringWithoutRepeatingCharacters_3 {
    /**
     * Idea: dùng Sliding window và HashSet: dùng 2 con trỏ left và right chạy tử đầu string.
     * - Nếu như character tại vị trí right hiện tại CHƯA tồn tại trong Set, add character đó vào Set và
     * tăng kích thước window (right++)
     * - Nếu như character tại vị trí right hiện tại ĐÃ tồn tại trong Set, giảm kích thước window
     * (left--), chú ý trước khi giảm phải remove character tại vị trí left khỏi Set
     * 
     * Cảm thấy bài này ko khó lắm, tự làm được!
     */
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int left = 0;
        int right = 0;
        int currLen = 0, maxLen = 0;

        while (right < s.length()) {
            if (!set.contains(s.charAt(right))) {
                set.add(s.charAt(right));
                currLen++;
                maxLen = Math.max(maxLen, currLen);
                right++;
            } else {
                set.remove(s.charAt(left));
                currLen--;
                left++;
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters_3 app = new LongestSubstringWithoutRepeatingCharacters_3();
        System.out.println(app.lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(app.lengthOfLongestSubstring("bbbbb")); // 1
        System.out.println(app.lengthOfLongestSubstring("pwwkew")); // 3
        System.out.println(app.lengthOfLongestSubstring("a")); // 1
        System.out.println(app.lengthOfLongestSubstring("")); // 0
    }
}
