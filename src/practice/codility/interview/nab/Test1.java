package practice.codility.interview.nab;

import java.util.Arrays;

public class Test1 {
    private int min(int a, int b, int c) {
        return a < b ? Math.min(a, c) : Math.min(b, c);
    }

    public int solution(String s) {
        char[] cArray = s.toCharArray();
        Arrays.sort(cArray);
        int cntA = 0, cntB = 0, cntN = 0;
        for (int i = 0; i < cArray.length; i++) {
            if (cArray[i] == 'A')
                cntA++;
            else if (cArray[i] == 'B')
                cntB++;
            else if (cArray[i] == 'N')
                cntN++;
        }
        // System.out.println(cntA + ", " + cntB + ", " + cntN);
        int resA = cntA / 3;
        int resB = cntB / 1;
        int resN = cntN / 2;
        return min(resA, resB, resN);
    }

    /**
     * Score: 100%
     */
    public static void main(String[] args) {
        System.out.println(new Test1().solution("NAABXXAN")); // 1
        System.out.println(new Test1().solution("NAANAAXNABABYNNBZ")); // 2
        System.out.println(new Test1().solution("QABAAAWOBL")); // 0
        System.out.println(new Test1().solution("")); // 0
    }
}
