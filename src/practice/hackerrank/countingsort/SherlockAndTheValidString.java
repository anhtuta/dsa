package practice.hackerrank.countingsort;

/**
 * Problem: https://www.hackerrank.com/challenges/sherlock-and-valid-string/problem
 * (Medium)
 * Updating, mới pass được 16/20 testcase
 * 
 * @author tatu
 *
 */
public class SherlockAndTheValidString {

    public static String isValid(String s) {
        int[] cntArray = new int[26];
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            cntArray[arr[i] - 'a']++;
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < cntArray.length; i++) {
            if (cntArray[i] == 0)
                continue;
            if (cntArray[i] < min)
                min = cntArray[i];
            if (cntArray[i] > max)
                max = cntArray[i];
        }

        if (min == max)
            return "YES";
        if (max - min == 1) {
            int cntMin = 0, cntMax = 0;
            for (int i = 0; i < cntArray.length; i++) {
                if (cntArray[i] == min)
                    cntMin++;
                if (cntArray[i] == max)
                    cntMax++;
            }
            if (cntMin == 2 || cntMax == 2)
                return "YES";
        }
        return "NO";
    }
}
