package practice.codility.interview.nab;

public class Test3 {

    /**
     * Vét cạn dùng 2 vòng for lồng nhau
     * Timeout!
     */
    public int solution(int[] a) {
        int cnt = 0;
        int max = -1;
        for (int i = 0; i < a.length; i++) {
            max = Math.max(max, a[i]);
        }

        for (int i = 1; i <= max; i++) {
            int currCnt = 0;
            for (int j = 0; j < a.length; j++) {
                if (a[j] >= i) {
                    currCnt++;
                    // Các toà nhà kề nó và cao bằng hoặc hơn nó:
                    // vẫn dùng nét vẽ hiện tại để vẽ tiếp được
                    while (j < a.length && a[j] >= i) {
                        j++;
                    }
                }
            }
            cnt += currCnt;
            if (cnt > 1_000_000_000)
                return -1;
        }
        return cnt;
    }

    /**
     * Example test: [1, 3, 2, 1, 2, 1, 5, 3, 3, 4, 2]
     * OK
     * 
     * Example test: [5, 8]
     * OK
     * 
     * Example test: [1, 1, 1, 1]
     * OK
     * 
     * Score:
     * Correctness test cases
     * Passed 5 out of 5
     * 
     * Performance test cases
     * Passed 0 out of 5
     */
    public static void main(String[] args) {
        System.out.println(new Test3().solution(new int[] {1, 3, 2, 1, 2, 1, 5, 3, 3, 4, 2})); // 9
    }
}
