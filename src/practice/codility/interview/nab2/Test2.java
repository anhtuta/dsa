package practice.codility.interview.nab2;

/**
 * Thực sự thì, bản thân t vẫn chưa có idea nào cho bài toán này, dù là vét cạn (brute force), cũng
 * ko biết phải implement như nào
 */
public class Test2 {
    public int solution_wrong(String s) {
        int cntA = 0, cntB = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'A')
                cntA++;
            else
                cntB++;
        }
        // System.out.printf("cntA = %d, cntB = %d%n", cntA, cntB);

        int min = Math.min(cntA, cntB); // answer of this problem
        if (min == 0)
            return 0;
        int cnt = 0;

        int left = 0, right = s.length() - 1;
        while (left < s.length() && right >= 0 && left < right) {
            if (s.charAt(left) == 'B')
                cnt++;
            if (s.charAt(right) == 'A')
                cnt++;
            left++;
            right--;
        }

        min = Math.min(min, cnt);

        return min;
    }

    public int solution2(String s) {
        // find position where total consecutive letters A is max
        int cntA = 0;
        int maxA = 0;
        int maxIndexA = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'A') {
                cntA++;
            } else {
                if (maxA < cntA) {
                    maxA = cntA;
                    maxIndexA = i - 1;
                    cntA = 0;
                }
            }
        }

        System.out.println("maxIndexA = " + maxIndexA);

        // int cntA = 0, cntB = 0;
        // for (int i = 0; i < s.length(); i++) {
        // if (s.charAt(i) == 'A')
        // cntA++;
        // else
        // cntB++;
        // }
        // // System.out.printf("cntA = %d, cntB = %d%n", cntA, cntB);

        // int min = Math.min(cntA, cntB); // answer of this problem
        // if (min == 0)
        // return 0;
        // int cnt = 0;

        // int left = 0, right = s.length() - 1;
        // while (left < s.length() && right >= 0 && left < right) {
        // if (s.charAt(left) == 'B')
        // cnt++;
        // if (s.charAt(right) == 'A')
        // cnt++;
        // left++;
        // right--;
        // }

        // min = Math.min(min, cnt);

        return 0;
    }

    /**
     * Solution from Nam Anh
     */
    public int solution(String s) {
        char[] chars = s.toCharArray();
        int firstA = chars.length;
        int lastB = -1;
        int deleted = 0;

        // find first A and B
        int numA = 0;
        int numB = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'A') {
                numA++;
            }
            if (chars[i] == 'B') {
                numB++;
            }
            if (chars[i] == 'A' && i < firstA) {
                firstA = i;
            }
            if (chars[i] == 'B' && i > lastB) {
                lastB = i;
            }
        }

        System.out.printf("firstA = %d, numA = %d, lastB = %d, numB = %d%n", firstA, numA, lastB, numB);

        deleted = firstA + (chars.length - lastB - 1);
        int minNumAB = Math.min(numA, numB);
        // count num of wrong A and wrong B
        int wrongA = 0;
        int wrongB = 0;

        if (firstA < lastB) {
            while (firstA < lastB && chars[firstA + 1] == 'A') {
                firstA++;
            }
            while (lastB > firstA && chars[lastB - 1] == 'B') {
                lastB--;
            }
            for (int i = firstA + 1; i < lastB; i++) {
                if (chars[i] == 'B') {
                    wrongA++;
                }
            }
            for (int i = lastB - 1; i > firstA; i--) {
                if (chars[i] == 'A') {
                    wrongB++;
                }
            }
        }
        int minWrong = Math.min(wrongA, wrongB);

        return Math.min(minNumAB, deleted + minWrong);
    }

    /**
     * Solution from Nam Anh
     */
    public int secondSolution(String s) {
        char[] chars = s.toCharArray();

        boolean foundFirstA = false;
        int deletedPreB = 0;
        int deletedBA = 0;
        for (int i = 0; i < chars.length; i++) {
            if (foundFirstA == false) {
                if (chars[i] == 'A') {
                    foundFirstA = true;
                } else {
                    deletedPreB++;
                }
            } else {
                if (chars[i - 1] == 'B' && chars[i] == 'A') {
                    deletedBA++;
                }
            }
        }

        // System.out.println(deletedPreB);
        // System.out.println(deletedBA);

        return deletedBA + deletedPreB;
    }

    public static void main(String[] args) {
        System.out.println(new Test2().solution("BAAABAB")); // 2
        System.out.println(new Test2().solution("BBABAA")); // 3
        System.out.println(new Test2().solution("AABBBB")); // 0
        System.out.println(new Test2().secondSolution("BAAABAB")); // 2
        System.out.println(new Test2().secondSolution("BBABAA")); // 3
        System.out.println(new Test2().secondSolution("AABBBB")); // 0
    }
}
