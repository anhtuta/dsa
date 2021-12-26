package practice.hackerrank.stack.equalstack;

import java.util.Arrays;
import java.util.List;

/**
 * Problem: https://www.hackerrank.com/challenges/equal-stacks/problem
 * (Easy)
 * Có 3 stack h1,h2,h3. Chiều cao của chúng = tổng các phần tử trong stack. Có thể giảm chiều cao
 * của 1 stack = cách pop (remove) top của stack đó. Sau 1 vài bước remove top của từng stack, 3
 * stack sẽ có chung chiều cao. Tìm chiều cao chung lớn có thể
 * 
 * Cách làm: giải thuật tham lam: tại mỗi bước, tìm stack cao nhất xong remove thằng top, sau đó
 * check nếu 3 stack cao = nhau thì return
 * 
 * @author tatu
 *
 */
public class EqualStacks {

    /*
     * Note: top của từng stack ở index = 0 nhé
     */
    public static int equalStacks(List<Integer> h1, List<Integer> h2, List<Integer> h3) {
        int i1 = 0, i2 = 0, i3 = 0;
        int sum1 = h1.stream().reduce((n1, n2) -> n1 + n2).get();
        int sum2 = h2.stream().reduce((n1, n2) -> n1 + n2).get();
        int sum3 = h3.stream().reduce((n1, n2) -> n1 + n2).get();

        while (i1 < h1.size() && i2 < h2.size() && i3 < h3.size()) {
            if (sum1 == sum2 && sum1 == sum3)
                return sum1;

            // Find the highest top between 3 stacks, then pop it out (remove)
            if (sum1 > sum2) {
                if (sum1 > sum3) {
                    // h1 is highest
                    sum1 -= h1.get(i1++);
                } else {
                    // h3 is highest.
                    sum3 -= h3.get(i3++);
                }
            } else {
                if (sum2 > sum3) {
                    // h2 is highest
                    sum2 -= h2.get(i2++);
                } else {
                    // h3 is highest.
                    sum3 -= h3.get(i3++);
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        List<Integer> h1 = Arrays.asList(3, 2, 1, 1, 1);
        List<Integer> h2 = Arrays.asList(4, 3, 2);
        List<Integer> h3 = Arrays.asList(1, 1, 4, 1);
        System.out.println(equalStacks(h1, h2, h3));
    }
}
