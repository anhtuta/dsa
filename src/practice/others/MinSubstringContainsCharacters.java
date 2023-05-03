package practice.others;

import util.Utils;

/**
 * Cho một chuỗi S và một chuỗi T, tìm chuỗi con liên tiếp ngắn nhất trong S sẽ chứa tất cả các ký
 * tự trong T
 * 
 * (độ phức tạp O (n))
 * 
 * Input: S = "JJGHIKAKFJDEIDJKEHHHDHHFGECJJIJ", T = "FKK"
 * Output: "KAKF"
 * 
 * Ghi chú:
 * Nếu không có chuỗi con như vậy trong S bao gồm tất cả các ký tự trong T, hãy trả về chuỗi trống
 * "".
 * Nếu có, bạn được đảm bảo rằng sẽ luôn chỉ có một đáp án duy nhất trong S.
 */
public class MinSubstringContainsCharacters {
    /**
     * Bài toán có thể phát biểu lại thành tìm substring nhỏ nhất của S mà chứa các ký tự bên T,
     * nên rõ ràng thứ tự các ký tự của T là ko quan trọng
     * 
     * Idea: sử dụng sliding window và hashmap (thực ra dùng counting element cũng tương tự, đỡ phải
     * dùng thư viện, nhưng bản chất 2 cái là như nhau): Tạo window phía S, sau đó đếm các ký tự trong
     * window mà tồn tại bên T, nếu như count này = T.length thì window đó là 1 substring cần tìm
     * 
     * Đầu tiên cần tìm ký tự đầu tiên của S mà có bên T, đó là vị trí start của 2 con trỏ left, right
     * (window trong bài này là window bên string S).
     * - Ta dùng mảng hashT để đếm các ký tự xuất hiện bên trong string T, nó khá giống với cntArr trong
     * kỹ thuật counting element
     * - Tương tự, ta dùng mảng hashS để đếm các ký tự xuất hiện bên trong window, chính là
     * substring của S trong khoảng [left...right]
     * - Đặt i = s.charAt(right) - 'A';
     * - Nếu như hashS[i] < hashT[i], tức là ký tự tại index = right của window có xuất hiện bên T, tuy
     * nhiên số lượng xuất hiện là chưa đủ (ex: window có 2 ký tự 'A', trong khi T có 4 ký tự 'A')
     * - Nếu như hashS[i] = hashT[i], lúc này window có số lượng ký tự tại index right = số lượng xuất
     * hiện bên T (ex: window có 4 ký tự 'A', và T cũng có 4 ký tự 'A')
     * - Dùng biến count để đếm số lượng các ký tự bên window mà có bên T. Cả 2 case trên, ta sẽ tăng
     * count lên
     * - Tới khi count = t.length, thì window này chứa toàn bộ các ký tự bên T rồi
     * - Cần tối ưu window bằng cách tăng left, tới khi hashS[i] <= hashT[i]
     * - Tiếp tục tìm các window mới và so sánh kích thước để return window bé nhất
     * - Việc tìm window mới = cách tăng left, tới khi hashS[j] <= hashT[j],
     * trong đó j = s.charAt(left) - 'A'
     * - Xem thêm hình smallest-window.png
     * 
     * Kinh nghiệm: nếu như việc xử lý case đặc biệt mãi ko cover hết, thử chuyển vị trí xử lý này từ
     * đầu vòng for/while xuống cuối hoặc từ cuối lên đầu xem sao
     * 
     * Ref:
     * https://www.geeksforgeeks.org/find-the-smallest-window-in-a-string-containing-all-characters-of-another-string/
     */
    public String minSubstring(String s, String t) {
        if (s.length() < t.length())
            return "";

        // Để đơn giản, coi như input chỉ là 2 string gồm các kí tự hoa = tiếng anh từ A -> Z
        int[] hashS = new int[26];
        int[] hashT = new int[26];
        for (int i = 0; i < t.length(); i++) {
            hashT[t.charAt(i) - 'A']++;
        }

        // Find the first character in S that exists in T
        int start = -1;
        for (int i = 0; i < s.length(); i++) {
            if (hashT[s.charAt(i) - 'A'] > 0) {
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
            idx = s.charAt(right) - 'A';
            hashS[idx]++;
            if (hashS[idx] <= hashT[idx])
                count++;

            if (count == t.length()) {
                System.out.print("\nFound a candidate window: ");
                Utils.printSubstring(s, left, right);

                // Tối ưu window:
                // Nếu như ký tự đầu tiên của window xuất hiện nhiều hơn số lần xuất hiện bên T,
                // ta sẽ remove nó để tối ưu length của substring là bé nhất có thể
                while (hashS[s.charAt(left) - 'A'] > hashT[s.charAt(left) - 'A']) {
                    hashS[s.charAt(left) - 'A']--;
                    left++;

                    // Tiếp tục giảm window size từ left cho tới khi cạnh trái của window lại là
                    // ký tự có bên T
                    while (hashT[s.charAt(left) - 'A'] == 0)
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
                hashS[s.charAt(left) - 'A']--;
                left++;

                // Tiếp tục giảm window size từ left cho tới khi cạnh trái của window lại là
                // ký tự có bên T
                while (hashT[s.charAt(left) - 'A'] == 0)
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

    public static void main(String[] args) {
        MinSubstringContainsCharacters app = new MinSubstringContainsCharacters();
        System.out.println(app.minSubstring("JJGHIKAKFJDEIDJKEHHHDHHFGECJJIJ", "FKK")); // KAKF
        System.out.println(app.minSubstring("THISISATESTSTRING", "TIST")); // TSTRI
        System.out.println(app.minSubstring("ADOOOOOBECODEBANC", "ADOBE")); // ODEBA
        System.out.println(app.minSubstring("TESKTHEREMANGOTEST", "TEST")); // TEST
        System.out.println(app.minSubstring("NIHARIKA", "IR")); // RI
    }
}
