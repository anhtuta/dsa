package practice.codility.interview.nab3;

import java.util.Arrays;
import java.util.Random;

public class Test3 {
    public int solution(int[] a) {
        Arrays.sort(a);
        // util.Utils.printArray(a);

        int cnt = 0;
        int i = 0;
        while (i < a.length) {
            cnt++;
            i += a[i];
        }

        return cnt;
    }

    /**
     * Ref: code of Nam Anh Techvify
     */
    public int solution2(int[] A) {
        Arrays.sort(A);
        int cnt = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] == 1)
                continue;
            if (A[i] + i - 1 < A.length) {
                cnt = cnt + (A[i] - 1);
                i = i + (A[i] - 1);
            } else {
                cnt = cnt + (A.length - i - 1);
                break;
            }
        }

        return A.length - cnt;
    }

    public static void main(String[] args) {
        Test3 app = new Test3();
        System.out.println(app.solution(new int[] {1, 1, 1, 1, 1})); // 5
        System.out.println(app.solution(new int[] {2, 1, 4})); // 2
        System.out.println(app.solution(new int[] {2, 7, 2, 9, 8})); // 2
        System.out.println(app.solution(new int[] {7, 3, 1, 1, 4, 5, 4, 9})); // 4

        // generate random large test
        final int SIZE = 100_000;
        Random rd = new Random(1);
        int[] a = new int[SIZE];
        for (int i = 0; i < a.length; i++) {
            a[i] = rd.nextInt(SIZE) + 1;
        }
        System.out.println(app.solution(a));
        System.out.println(app.solution2(a));
    }
}
