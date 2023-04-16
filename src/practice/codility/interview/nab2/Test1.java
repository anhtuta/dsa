package practice.codility.interview.nab2;

public class Test1 {
    /**
     * Phát biểu lại đề bài: đếm số lượng các dãy con có 3 phần tử và 3 phần tử đó giống nhau
     * (các dãy con ko được overlap nhau)
     */
    public int solution(String s) {
        char curr = s.charAt(0); // current character to check identical
        int countChar = 1; // identical consecutive letters
        int i = 1; // start at index 1
        int ans = 0;
        while (i < s.length()) {
            if (s.charAt(i) == curr) {
                countChar++;
                if (countChar == 3) {
                    ans++;
                    countChar = 0;
                }
            } else {
                curr = s.charAt(i);
                countChar = 1;
            }
            i++;
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println(new Test1().solution("baaaaa")); // 1
        System.out.println(new Test1().solution("baaabbaabbba")); // 2
        System.out.println(new Test1().solution("baabab")); // 0
    }
}
