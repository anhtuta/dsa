package practice.leetcode.easy;

/**
 * https://leetcode.com/problems/happy-number/
 */
public class HappyNumber_202 {
    /**
     * Idea: sử dụng thuật toán fast and slow pointer. Nếu ta cứ tính sumOfSquareDigits mãi, với số ko
     * phải happy number, nó sẽ lặp vô hạn mà ko bao giờ return 1. Do đó ta sẽ dùng 2 con trỏ giống như
     * bài check cyclic linked list. Khi 2 con trỏ gặp nhau thì ko tính sumOfSquareDigits nữa
     * 
     * Ref:
     * https://leetcode.com/problems/happy-number/solutions/56917/my-solution-in-c-o-1-space-and-no-magic-math-property-involved/?orderBy=most_votes
     */
    public boolean isHappy(int n) {
        int slow = n, fast = n;
        while (true) {
            slow = sumOfSquareDigits(slow);
            fast = sumOfSquareDigits(fast);
            fast = sumOfSquareDigits(fast);
            if (fast == 1 || slow == fast)
                break;
        }
        return fast == 1;
    }

    /**
     * Return sum of the squares of its digits.
     * if n = 19 => return 1^2 + 9^2 = 82
     * if n = 82 => return 8^2 + 2^2 = 68
     */
    private int sumOfSquareDigits(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }

    public static void main(String[] args) {
        HappyNumber_202 app = new HappyNumber_202();
        System.out.println(app.isHappy(19)); // true
        System.out.println(app.isHappy(2)); // false
    }
}
