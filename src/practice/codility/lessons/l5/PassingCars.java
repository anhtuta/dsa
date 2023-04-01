package practice.codility.lessons.l5;

/**
 * A non-empty array A consisting of N integers is given. The consecutive elements of array A
 * represent consecutive cars on a road.
 * 
 * Array A contains only 0s and/or 1s:
 * - 0 represents a car traveling east,
 * - 1 represents a car traveling west.
 * 
 * The goal is to count passing cars. We say that a pair of cars (P, Q), where 0 ≤ P < Q < N, is
 * passing when P is traveling to the east and Q is traveling to the west.
 * 
 * For example, consider array A such that:
 * 
 * A[0] = 0
 * A[1] = 1
 * A[2] = 0
 * A[3] = 1
 * A[4] = 1
 * We have five pairs of passing cars: (0, 1), (0, 3), (0, 4), (2, 3), (2, 4).
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given a non-empty array A of N integers, returns the number of pairs of passing cars.
 * 
 * The function should return −1 if the number of pairs of passing cars exceeds 1,000,000,000.
 * 
 * For example, given:
 * 
 * A[0] = 0
 * A[1] = 1
 * A[2] = 0
 * A[3] = 1
 * A[4] = 1
 * the function should return 5, as explained above.
 * 
 * Write an efficient algorithm for the following assumptions:
 * - N is an integer within the range [1..100,000];
 * - each element of array A is an integer that can have one of the following values: 0, 1.
 */
public class PassingCars {
    /**
     * Dùng 2 vòng for lồng, duyệt tất cả các cặp xe và check xem chúng có đi qua nhau hay ko
     * O(n^2) => Chắc chắn timeout, vì a.length <= 100_000
     */
    public int solution_timeout(int[] a) {
        int cnt = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] == 0 && a[j] == 1) {
                    cnt++;
                    if (cnt > 1_000_000_000)
                        return -1;
                }
            }
        }
        return cnt;
    }

    /**
     * Duyệt 1 lần, từ trái qua phải, O(n)
     * Tại mỗi lần duyệt:
     * - Nếu a[i] = 0, tức là xe đi sang phải (Đông), thì chắc chắn xe này sẽ gặp các xe sau đó đi sang
     * trái (Tây). Cần đếm tổng số lượng các xe đi sang trái = countZeros
     * - Nếu a[i] = 1, tức là xe đi sang trái, chắc chắn sẽ gặp các xe trước đó đi sang phải, tức là sẽ
     * gặp toàn bộ các xe đã được cộng vào biến countZeros ở trên
     * 
     * Ref: https://stackoverflow.com/a/25527974/7688028
     */
    public int solution(int[] a) {
        int countZeros = 0;
        int res = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 0)
                countZeros++;
            else if (a[i] == 1) {
                // res tại vị trí này = tổng các cặp xe sẽ gặp nhau. Tại i = n thì res là kq cần tìm
                res += countZeros;
                if (res > 1_000_000_000)
                    return -1;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 0, 1, 1};
        System.out.println(new PassingCars().solution_timeout(a)); // 5
        System.out.println(new PassingCars().solution(a)); // 5
    }
}
