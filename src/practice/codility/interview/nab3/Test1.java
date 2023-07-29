package practice.codility.interview.nab3;

public class Test1 {
    public String solution(String s) {
        int[] cntArrLow = new int[26];
        int[] cntArrUp = new int[26];

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < 'a') {
                // uppercase
                cntArrUp[s.charAt(i) - 'A']++;
            } else {
                // lowercase
                cntArrLow[s.charAt(i) - 'a']++;
            }
        }

        for (int i = cntArrLow.length - 1; i >= 0; i--) {
            if (cntArrLow[i] > 0 && cntArrUp[i] > 0) {
                // This letter occurs in both lowercase and uppercase
                return (char) (i + 'A') + "";
            }
        }

        return "NO";
    }

    public static void main(String[] args) {
        Test1 app = new Test1();
        System.out.println(app.solution("a")); // NO
        System.out.println(app.solution("A")); // NO
        System.out.println(app.solution("aA")); // A
        System.out.println(app.solution("aB")); // NO
        System.out.println(app.solution("bB")); // B
        System.out.println(app.solution("abcdefABCDFEghuvTR")); // F
        System.out.println(app.solution("aaBabcDaA")); // B
    }
}
