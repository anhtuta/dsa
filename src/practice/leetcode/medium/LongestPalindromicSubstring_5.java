package practice.leetcode.medium;

/**
 * https://leetcode.com/problems/longest-palindromic-substring/
 */
public class LongestPalindromicSubstring_5 {
    /**
     * Idea: do input size <= 1000 nên có thể dùng 2 vòng for, O(n^2) được
     * Cứ duyệt từ đầu, tại mỗi vị trí charAt của string s, cần xét cả 2 case sau:
     * - len của Palindromic = số chẵn, ex: bb, xảy ra khi cs[i] = cs[i+1]
     * - len của Palindromic = số lẻ, ex: bab, luôn xảy ra
     * (cs ở đây là mảng char, cs = s.toCharArray())
     * 
     * Tại sao phải xét cả 2 case như vậy, ví dụ s = "aaaaa", tại i = 2, nếu chỉ xét case len của
     * Palindromic = số chẵn, thì kq thu được chỉ là aaaa, với length = 4, trong khi đáp án đúng phải là
     * aaaaa
     * 
     * Note: bài này có thể giải = Dynamic Programming, nhưng t chưa tìm được solution nào dùng DP
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

    public static void main(String[] args) {
        LongestPalindromicSubstring_5 app = new LongestPalindromicSubstring_5();
        System.out.println(app.longestPalindrome("babad")); // bab
        System.out.println(app.longestPalindrome("cbbd")); // bb
        System.out.println(app.longestPalindrome("a")); // a
        System.out.println(app.longestPalindrome("")); //
        System.out.println(app.longestPalindrome("aaaaa")); // aaaaa
        System.out.println(app.longestPalindrome("aaaaaa")); // aaaaaa
        System.out.println(app.longestPalindrome("aaa")); // aaa
    }
}
