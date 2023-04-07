package practice.codility.lessons.l10;

/**
 * An integer N is given, representing the area of some rectangle.
 * 
 * The area of a rectangle whose sides are of length A and B is A * B, and the perimeter is 2 * (A +
 * B).
 * 
 * The goal is to find the minimal perimeter of any rectangle whose area equals N. The sides of this
 * rectangle should be only integers.
 * 
 * For example, given integer N = 30, rectangles of area 30 are:
 * 
 * (1, 30), with a perimeter of 62,
 * (2, 15), with a perimeter of 34,
 * (3, 10), with a perimeter of 26,
 * (5, 6), with a perimeter of 22.
 * Write a function:
 * 
 * class Solution { public int solution(int N); }
 * 
 * that, given an integer N, returns the minimal perimeter of any rectangle whose area is exactly
 * equal to N.
 * 
 * For example, given an integer N = 30, the function should return 22, as explained above.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * N is an integer within the range [1..1,000,000,000].
 */
public class MinPerimeterRectangle {
    /**
     * Từ đề bài có thể đoán được rằng: nếu gọi diff = chiều dài - chiểu rộng, thì trong các HCN có cùng
     * diện tích, thì hình có chu vi bé nhất sẽ là hình có diff bé nhất, do đó đếm từ căn của n để tìm
     */
    public int solution(int n) {
        int sqrt = (int) Math.sqrt(n);
        int i = sqrt;
        while (i >= 0) {
            if (n % i == 0) {
                return 2 * (n / i + i);
            }
            i--;
        }
        return 1;
    }

    public static void main(String[] args) {
        System.out.println(new MinPerimeterRectangle().solution(30)); // 22
        System.out.println(new MinPerimeterRectangle().solution(101)); // 204
    }
}
