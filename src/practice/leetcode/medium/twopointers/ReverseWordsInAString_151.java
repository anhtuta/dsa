package practice.leetcode.medium.twopointers;

/**
 * https://leetcode.com/problems/reverse-words-in-a-string/
 */
public class ReverseWordsInAString_151 {
    /**
     * Idea: reverse từng từ trong string trước, sau đó reverse toàn bộ string
     * s = " the sky is blue "
     * => "eht yks si eulb"
     * => "blue is sky the"
     * 
     * Ref:
     * https://leetcode.com/problems/reverse-words-in-a-string/solutions/47840/c-solution-in-place-runtime-o-n-memory-o-1/
     * T chỉ đọc ý tưởng ở đầu solution này rồi implement theo
     * 
     * Result:
     * Runtime 6 ms Beats 78.81%
     * Memory 42.3 MB Beats 93.66%
     * 
     * Do ko dùng thêm nhiều bộ nhớ nên memory khá tối ưu. Nhưng runtime hơi chậm chắc do lúc reverse
     * word
     */
    public String reverseWords(String s) {
        // Đầu tiên dùng 2 con trỏ để duyệt và reverse từng từ. Duyệt sao cho i và j là start và end của 1
        // từ ko chứa dấu cách. Sau đó append s[i...j] vào mảng cs theo thứ tự ngược lại
        char[] cs = new char[s.length() + 1]; // thêm thừa 1 vị trí để tý nữa gán cnt[cnt++] = ' ';
        int i = 0, j = 0, k = 0, cnt = 0; // i always <= j
        char temp;
        while (i < s.length() && j < s.length()) {
            if (s.charAt(i) == ' ' && s.charAt(j) == ' ') {
                i++;
                j++;
                continue;
            }
            if (s.charAt(j) != ' ') {
                j++;

                // Thêm điều kiện if này trước khi continue để cover case mà s KHÔNG có dẫu cách ở cuối string.
                // Như vậy nếu j = s.length(), ta sẽ tiếp tục thực thi code ở dưới chứ ko thoát luôn vòng while
                if (j < s.length())
                    continue;
            }

            // lúc này s.charAt(i) != ' ' và (s.charAt(j) == ' ' hoặc j == s.length). Word hoàn chỉnh ko chứa
            // dẫu cách bên trong nó là đoạn s[i...j-1]. Ta phải append s[i...j-1] vào cs mảng
            for (k = j - 1; k >= i; k--) {
                cs[cnt++] = s.charAt(k);
            }
            cs[cnt++] = ' ';
            i = j; // now s[i] = s[j] = ' ', start to search next word again; or i = j = s.length, exit loop
        }

        // remove dấu cách cuối cùng
        if (cs[cnt - 1] == ' ')
            cnt--;

        // Reverse cs[]
        for (i = 0; i < cnt / 2; i++) {
            temp = cs[i];
            cs[i] = cs[cnt - 1 - i];
            cs[cnt - 1 - i] = temp;
        }

        return new String(cs, 0, cnt);
    }

    public static void main(String[] args) {
        ReverseWordsInAString_151 app = new ReverseWordsInAString_151();
        System.out.println(app.reverseWords("the sky is blue"));
        System.out.println(app.reverseWords(" the  sky   is    blue "));
    }
}
