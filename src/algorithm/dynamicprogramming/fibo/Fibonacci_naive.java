/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.dynamicprogramming.fibo;

/**
 *
 * @author AnhTu
 */
public class Fibonacci_naive {
    static int Fib(int n) {
        if (n <= 1)
            return n;
        return Fib(n - 1) + Fib(n - 2);
    }

    public static void main(String[] args) {
        long curr = System.currentTimeMillis();
        System.out.println(Fib(43));
        System.out
                .println("Thời gian thực hiện là: " + (System.currentTimeMillis() - curr) + "(ms)");
    }
}
/*
 * Chỉ số: 0,1,2,3,4,5,6,7 ,8 ,...
 * Dãy số: 0,1,1,2,3,5,8,13,21,...
 * Time: 1.6^n => T(n) = O(2^n)
 * Fib(43):
 * 433494437
 * Thời gian thực hiện là: 9812(ms)
 */
