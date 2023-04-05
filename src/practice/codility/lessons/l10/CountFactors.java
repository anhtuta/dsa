package practice.codility.lessons.l10;

/**
 * A positive integer D is a factor of a positive integer N if there exists an integer M such that N
 * = D * M.
 * 
 * For example, 6 is a factor of 24, because M = 4 satisfies the above condition (24 = 6 * 4).
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int N); }
 * 
 * that, given a positive integer N, returns the number of its factors.
 * 
 * For example, given N = 24, the function should return 8, because 24 has 8 factors, namely 1, 2,
 * 3, 4, 6, 8, 12, 24. There are no other factors of 24.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * N is an integer within the range [1..2,147,483,647].
 */
public class CountFactors {
    public int solution(int n) {
        if (n <= 2)
            return n;
        int i = 1;
        int cnt = 0;
        int sqrt = (int) Math.sqrt(n);
        // System.out.println("sqrt = " + sqrt);
        while (i <= sqrt) {
            if (n % i == 0) {
                cnt += 2;
                // System.out.println(i);
            }
            i++;
        }
        if (sqrt * sqrt == n) {
            // nếu n là số chính phương, giả sử n = 25, thì sqrt = 5, lúc này chỉ có 1 ước là 5 chứ ko phải 2
            // như ở trên, do đó cần trừ đi 1
            cnt--;
        }
        return cnt;
    }

    public static void main(String[] args) {
        System.out.println(new CountFactors().solution(24)); // 8
        System.out.println(new CountFactors().solution(25)); // 8
        System.out.println(new CountFactors().solution(2_000_000_000)); //
        System.out.println(new CountFactors().solution(Integer.MAX_VALUE)); //
    }
}
