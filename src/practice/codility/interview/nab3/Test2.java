package practice.codility.interview.nab3;

import java.util.Arrays;
import java.util.Random;

public class Test2 {
    public int solution(int[][] a) {
        int n = a.length;
        int m = a[0].length;

        // Count number of hospitals each doctor will work at
        int[] cntHos = new int[n * m + 1];
        int res = 0;

        for (int i = 0; i < n; i++) {
            Arrays.sort(a[i]);
            int j = 0;
            while (j < m) {
                cntHos[a[i][j]]++;
                // We will calculate extra or duplicate if we calculate res here
                // if (cntHos[a[i][j]] > 1)
                // res++;
                while (j < a[i].length - 1 && a[i][j] == a[i][j + 1])
                    j++;
                j++;
            }
        }

        // util.Utils.printArray(a);
        // util.Utils.printArray(cntHos);

        for (int i = 0; i < cntHos.length; i++) {
            if (cntHos[i] > 1)
                res++;
        }

        return res;
    }

    public static void main(String[] args) {
        Test2 app = new Test2();
        System.out.println(app.solution(new int[][] {{1}})); // 0
        System.out.println(app.solution(new int[][] {{1, 1}, {1, 2}})); // 1
        System.out.println(app.solution(new int[][] {{1, 2, 2}, {3, 1, 4}})); // 1
        System.out.println(app.solution(
                new int[][] {{1, 1, 5, 2, 3}, {4, 5, 6, 4, 3}, {9, 4, 4, 1, 5}})); // 4

        // generate random large test
        final int SIZE = 1000;
        Random rd = new Random(1);
        int[][] a = new int[SIZE][SIZE];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = rd.nextInt(SIZE * SIZE) + 1;
            }
        }
        // util.Utils.printArray(a);
        System.out.println(app.solution(a));
    }
}
