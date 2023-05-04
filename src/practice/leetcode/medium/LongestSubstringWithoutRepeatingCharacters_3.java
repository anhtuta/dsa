package practice.leetcode.medium;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * 
 * Next: {@link practice.leetcode.hard.MinimumWindowSubstring_76}
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
     * Runtime: 7 ms
     */
    public int lengthOfLongestSubstring_hash(String s) {
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

    /**
     * Việc dùng HashSet thực ra là ko cần thiết, có thể thay thế = mảng counting, cái biến currLen cũng
     * ko cần, vì currLen = right - left + 1
     * Runtime: 3 ms
     * Nhanh hơn gấp 2 lần so với dùng thư viện Set của java
     */
    public int lengthOfLongestSubstring(String s) {
        int[] cntArr = new int[128]; // Bảng ASCII tối đa 127 ký tự
        int left = 0;
        int right = 0;
        int maxLen = 0;

        while (right < s.length()) {
            if (cntArr[s.charAt(right)] == 0) {
                // Ký tự tại right CHƯA có trong window, mở rộng window tới vị trí này, update answer
                cntArr[s.charAt(right)]++;
                maxLen = Math.max(maxLen, right - left + 1);
                right++;
            } else {
                // Ký tự tại right ĐÃ có trong window, cần giảm window size từ phía left, tới khi window remove được
                // 1 ký tự = với ký tự right thì dừng
                cntArr[s.charAt(left)]--;
                left++;
            }
        }
        return maxLen;
    }

    /**
     * Tương tự cách trên, nhưng code này copy từ người khác trên leetcode
     * Ref:
     * https://leetcode.com/problems/minimum-window-substring/solutions/26808/here-is-a-10-line-template-that-can-solve-most-substring-problems/
     */
    public int lengthOfLongestSubstring2(String s) {
        int[] cntArr = new int[128]; // Bảng ASCII tối đa 127 ký tự
        int counter = 0;
        int left = 0, right = 0; // window's boundary
        int maxLen = 0;

        while (right < s.length()) {
            // if (cntArr[s[right++]]++ > 0) counter++;
            if (cntArr[s.charAt(right)] > 0) {
                counter++;
            }
            cntArr[s.charAt(right)]++;
            right++;

            while (counter > 0) {
                // if (cntArr[s[left++]]-- > 1) counter--;
                if (cntArr[s.charAt(left)] > 1) {
                    counter--;
                }
                cntArr[s.charAt(left)]--;
                left++;
            }
            maxLen = Math.max(maxLen, right - left); // while valid, update maxLen
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
