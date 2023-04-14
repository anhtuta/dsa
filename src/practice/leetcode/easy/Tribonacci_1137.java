package practice.leetcode.easy;

/**
 * Problem: https://leetcode.com/problems/n-th-tribonacci-number/
 * The Tribonacci sequence Tn is defined as follows:
 * T0 = 0, T1 = 1, T2 = 1, and T[n+3] = Tn + T[n+1] + T[n+2] for n >= 0.
 * Given n, return the value of Tn.
 * 
 * @author tatu
 *
 */
public class Tribonacci_1137 {
    // Using bottom-up
    public int tribonacci(int n) {
        if (n == 0)
            return n;
        if (n < 3)
            return 1;
        int f3 = 0, f2 = 1, f1 = 1, f0 = 0;
        for (int i = 3; i <= n; i++) {
            f3 = f2 + f1 + f0;
            f0 = f1;
            f1 = f2;
            f2 = f3;
        }
        return f3;
    }
}
