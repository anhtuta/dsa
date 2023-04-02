package practice.codility.lessons.l5;

/**
 * Level: Medium
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int A, int B, int K); }
 * 
 * that, given three integers A, B and K, returns the number of integers within the range [A..B]
 * that are divisible by K, i.e.:
 * 
 * { i : A ≤ i ≤ B, i mod K = 0 }
 * 
 * For example, for A = 6, B = 11 and K = 2, your function should return 3, because there are three
 * numbers divisible by 2 within the range [6..11], namely 6, 8 and 10.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - A and B are integers within the range [0..2,000,000,000];
 * - K is an integer within the range [1..2,000,000,000];
 * - A ≤ B.
 */
public class CountDiv {
    /**
     * Cách đơn giản nhất là duyệt từ a->b, xem số nào chia hết cho k
     * Cách này bị timeout, vì a,b,k quá to, nên nếu cứ duyệt từ đầu đến cuối,
     * xong chia thử thì ko đủ time
     */
    public int solution_timeout(int a, int b, int k) {
        int cnt = 0;
        for (int i = a; i <= b; i++) {
            if (i % k == 0)
                cnt++;
        }
        return cnt;
    }

    /**
     * Note: mấy cái bài mà có dãy input to đùng như này, xong rồi yêu cầu:
     * - Tìm số lượng số trong dãy đó chia hết cho k,
     * - Tìm số lượng số trong dãy đó mà dạng nhị phân có k số 0
     * - Tìm số lượng số trong dãy đó mà ...
     * Thì đừng có duyệt từng số xong chia thử hay convert từng số sang dạng nhị phân, vì chắc chắn sẽ
     * bị timeout, cần tìm công thức để tính toán đơn giản hơn
     */
    public int solution(int a, int b, int k) {
        int cnt = 0;
        int firstNum = 0; // first number that is divisible by k
        boolean isExist = false;

        // Find first number which is divisible by k
        for (int i = a; i <= b; i++) {
            if (i % k == 0) {
                cnt++;
                firstNum = i;
                isExist = true;
                break;
            }
        }

        // Tìm số lượng các số chia hết cho k từ phần còn lại của dãy
        // (từ firstNum + k đến hết dãy) bằng 1 phép toán nhân chia đơn giản
        if (isExist) {
            int start = firstNum + k;
            if (start <= b) {
                cnt = cnt + (b - start) / k + 1;
            }
        }

        return cnt;
    }

    /**
     * Cũng như cách trên, nhưng chả cần tìm số đầu tiên chia hết cho k nữa, cứ tính toán luôn.
     * Nhưng chưa hiểu tại sao solution này bị sai 1 vài case đặc biệt!!!
     */
    public int solution_notPassedAllCase(int a, int b, int k) {
        if (a == b) {
            if (a % k == 0)
                return 1;
            else
                return 0;
        } else {
            return (b - a) / k + 1;
        }
    }

    public static void main(String[] args) {
        System.out.println(new CountDiv().solution(0, 12, 12)); // 2
        System.out.println(new CountDiv().solution(6, 11, 2)); // 3
        System.out.println(new CountDiv().solution(10, 10, 7)); // 0
        System.out.println(new CountDiv().solution(5, 30, 7)); // 4
        System.out.println(new CountDiv().solution(5, 8, 7)); // 1
        System.out.println(new CountDiv().solution(101, 123_000_000, 10_000)); // 12300
        System.out.println(new CountDiv().solution(0, Integer.MAX_VALUE, 1_000_000_000)); // 3
        System.out.println(new CountDiv().solution(0, Integer.MAX_VALUE, 10)); // 214748365
        System.out.println(new CountDiv().solution_notPassedAllCase(0, Integer.MAX_VALUE, Integer.MAX_VALUE)); // 2
    }
}
