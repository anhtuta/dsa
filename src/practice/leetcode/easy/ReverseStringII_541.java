package practice.leetcode.easy;

/**
 * https://leetcode.com/problems/reverse-string-ii/
 */
public class ReverseStringII_541 {
    /**
     * Ref: https://leetcode.com/problems/reverse-string-ii/solutions/100866/java-concise-solution/
     * Bài này chỉ loằng ngoằng lúc tính toán, nên đọc cái solution kia cho nhanh
     */
    public String reverseStr(String s, int k) {
        if (k <= 1)
            return s;

        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i = i + 2 * k) {
            // If there are fewer than k characters left, reverse all of them
            // => Phải so sánh giữa i + k - 1 và s.length - 1 để lấy thằng bé hơn
            // => Dùng như này sẽ bị miss case: reverse(cs, i, i + k - 1);
            reverse(cs, i, Math.min(i + k - 1, s.length() - 1));
        }

        return new String(cs);
    }

    /**
     * Reverse mảng char từ vị trí l->r
     * Ex: reverse('abcdxyz', 4, 6) = 'abcdzyx'
     */
    private void reverse(char[] arr, int l, int r) {
        while (l < r) {
            char temp = arr[l];
            arr[l++] = arr[r];
            arr[r--] = temp;
        }
    }

    public static void main(String[] args) {
        ReverseStringII_541 app = new ReverseStringII_541();
        System.out.println(app.reverseStr("a", 2)); // a
        System.out.println(app.reverseStr("abcdefg", 2)); // bacdfeg
        System.out.println(app.reverseStr("abcdef", 3)); // cbadef

        char[] cs = "abcdxyz".toCharArray();
        app.reverse(cs, 4, 6);
        System.out.println(cs);
    }
}
