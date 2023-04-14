package practice.leetcode.easy;

/**
 * Problem: https://leetcode.com/problems/fibonacci-number/
 * The Fibonacci numbers, commonly denoted F(n) form a sequence, called the Fibonacci sequence, such
 * that each number is the sum of the two preceding ones, starting from 0 and 1. That is,
 * 
 * F(0) = 0, F(1) = 1
 * F(n) = F(n - 1) + F(n - 2), for n > 1.
 * Given n, calculate F(n).
 * 
 * @author tatu
 *
 */
public class Fibonacci_509 {
    public int fib(int n) {
        if (n <= 1)
            return n;
        int f2 = 0, f1 = 1, f0 = 0;
        for (int i = 2; i <= n; i++) {
            f2 = f1 + f0;
            f0 = f1;
            f1 = f2;
        }
        return f2;
    }
}
