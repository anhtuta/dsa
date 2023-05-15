package practice.leetcode.medium.math;

/**
 * https://leetcode.com/problems/2-keys-keyboard/submissions/950709811/
 */
public class TwoKeysKeyboard_650 {
    public int minSteps(int n) {
        return recursion(n);
    }

    /**
     * Bài này dùng toán học là tính ra, công thứ như sau:
     * minSteps(n) = minSteps(gd) + n/gd;
     * Trong đó gd = ước lớn nhất của n
     * 
     * Ex:
     * Now, take n=9.
     * We need the lowest number just before 9 such that (9% number = 0). So the lowest number is 3.
     * So 9%3=0. We need to copy 3 'A's three times to get 9. (3)
     * For getting 3 'A's, we need to copy 1 'A' three times. (3)
     * So the answer is 6
     * 
     * Result:
     * Runtime 0 ms Beats 100%
     * Memory 39.3 MB Beats 85.65%
     * 
     * Những lời giải = công thức toán học luôn nhanh hơn mọi lời giải khác, vì nó ko phải duyệt tất cả
     * như DP
     * 
     * Ref:
     * https://leetcode.com/problems/2-keys-keyboard/solutions/105908/very-simple-java-solution-with-detail-explanation/
     */
    private int recursion(int n) {
        // base cases
        if (n == 1)
            return 0;
        if (n == 2)
            return 2;

        // Ước lớn nhất của n
        int greatestDivisor = n / 2;
        while (n % greatestDivisor != 0) {
            greatestDivisor--;
        }
        return n / greatestDivisor + recursion(greatestDivisor);
    }

    public static void main(String[] args) {
        TwoKeysKeyboard_650 app = new TwoKeysKeyboard_650();
        System.out.println(app.minSteps(1)); // 0
        System.out.println(app.minSteps(2)); // 2
        System.out.println(app.minSteps(3)); // 3
        System.out.println(app.minSteps(4)); // 3
        System.out.println(app.minSteps(5)); // 5
        System.out.println(app.minSteps(6)); // 5
        System.out.println(app.minSteps(9)); // 6
    }
}
