package practice.codility.lessons.l8;

import java.util.Arrays;
import java.util.Stack;

/**
 * An array A consisting of N integers is given. The dominator of array A is the value that occurs
 * in more than half of the elements of A.
 * 
 * For example, consider array A such that
 * 
 * A[0] = 3 A[1] = 4 A[2] = 3
 * A[3] = 2 A[4] = 3 A[5] = -1
 * A[6] = 3 A[7] = 3
 * The dominator of A is 3 because it occurs in 5 out of 8 elements of A (namely in those with
 * indices 0, 2, 4, 6 and 7) and 5 is more than a half of 8.
 * 
 * Write a function
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given an array A consisting of N integers, returns index of any element of array A in which
 * the dominator of A occurs. The function should return −1 if array A does not have a dominator.
 * 
 * For example, given array A such that
 * 
 * A[0] = 3 A[1] = 4 A[2] = 3
 * A[3] = 2 A[4] = 3 A[5] = -1
 * A[6] = 3 A[7] = 3
 * the function may return 0, 2, 4, 6 or 7, as explained above.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N is an integer within the range [0..100,000];
 * - each element of array A is an integer within the range [−2,147,483,648..2,147,483,647].
 * 
 * =====
 * Bài này khó vãi, mãi mới pass 100%
 */
public class Dominator {
    /**
     * Bài này giống hệt bài leader trong file pdf, khác ở chỗ return index thay vì value
     * => SAI rồi nhé!
     * Nếu sort xong thì index của dominator ko còn đúng nữa!
     * Ta cần return index của leader TRƯỚC KHI SORT
     * => Cách làm sau là SAI HOÀN TOÀN
     * O(n log n)
     */
    public int solution_wrongAnswer(int[] a) {
        if (a.length == 0)
            return -1;
        Arrays.sort(a);
        int candidateIndex = a.length / 2;
        int candidate = a[candidateIndex];
        int cnt = 1; // total number = candidate
        int i = candidateIndex - 1;
        // Nhớ check i trước, nếu check như này thì sẽ bị lỗi ArrayIndexOutOfBoundsException: Index -1
        // while (a[i] == candidate && i >= 0) {
        while (i >= 0 && a[i] == candidate) {
            cnt++;
            i--;
        }
        i = candidateIndex + 1;
        while (i < a.length && a[i] == candidate) {
            cnt++;
            i++;
        }
        if (cnt > a.length / 2)
            return candidateIndex;
        return -1;
    }

    /**
     * Không được sort mảng input a, như vậy việc tìm dominator xong return index của nó mới đúng
     * Áp dụng cách dùng stack trong slide: nếu như remove 2 phần tử khác nhau bất kì, thì leader của
     * dãy vẫn ko đổi. Do đó ta làm như sau:
     * Duyệt từ đầu đến cuối dãy, nhét từng phần tử vào stack, nếu phần tử ở đầu stack = phần tử định
     * nhét vào thì remove chúng đi (ko nhét phần tử đó nữa, đồng thời remove top của stack). Sau khi
     * duyệt hết:
     * - Nếu stack empty: dãy ko có leader
     * - Nếu stack còn phần tử, thì khả năng nó là leader (CHỨ NÓ KO CHẮC CHẮN là leader nhé)
     * - Cần duyệt dãy lần nữa xem phần tử trên có phải là leader hay ko
     * 
     * Note: cách này chỉ khác cách sort ở chỗ tìm candidate:
     * - Sort thì candidate sẽ là phần tử ở vị trí n/2 sau khi dãy được sort
     * - Stack thì candidate là phần tử còn lại trong stack sau khi duyệt xong
     * Sau đó vẫn phải check candidate đó có là leader hay ko
     * 
     * O(n)
     */
    public int solution(int[] a) {
        if (a.length == 0)
            return -1;

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < a.length; i++) {
            if (stack.isEmpty() || a[i] == stack.peek()) {
                stack.push(a[i]);
            } else {
                stack.pop();
            }
        }

        if (stack.isEmpty())
            return -1;

        // Nếu dãy có nhiều hơn n/2 phần tử = candidate, thì candidate này mới là leader
        int candidate = stack.pop();
        int cnt = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == candidate) {
                cnt++;
                if (cnt > a.length / 2)
                    return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = {3, 4, 3, 2, 3, -1, 3, 3};
        System.out.println(new Dominator().solution(a));
        int[] a2 = {1, 2, 3, 4};
        System.out.println(new Dominator().solution(a2));
        int[] a3 = {9, 9, 9, 9};
        System.out.println(new Dominator().solution(a3));
        System.out.println(new Dominator().solution(new int[] {1, 2, 3, 4, 4, 4}));
        System.out.println(new Dominator().solution(new int[] {1, 4, 2, 4, 3, 4}));
    }
}
