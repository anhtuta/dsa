/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.dynamicprogramming;

/**
 * Bài đầu tiên của sách CTDL&GT của thầy Nghĩa:
 * Cho 1 dãy số nguyên, tìm và return trọng lượng của dãy con lớn nhất
 * ví dụ cho dãy: -2,11,-4,13,-5,2 thì trọng lượng lớn nhất của dãy con là 20,
 * là trọng lượng của dãy 11,-4,13
 * 
 * Bản pdf của cuốn sách, xem tại đây:
 * https://drive.google.com/file/d/1AIksqPQyMqCH0FotrqwbvF8w4xEwFOZz/view
 * 
 * @author AnhTu
 */
public class Heaviest_Subsequence {
    private static int[][] L;

    /**
     * Cách đơn giản nhất là duyệt tất cả các dãy con có thể:
     * Cách duyệt:
     * duyệt tất cả các dãy con có phần tử đầu tiên là a[0]
     * duyệt tất cả các dãy con có phần tử đầu tiên là a[1]
     * duyệt tất cả các dãy con có phần tử đầu tiên là a[2]
     * duyệt tất cả các dãy con có phần tử đầu tiên là a[3]
     * ...
     * duyệt tất cả các dãy con có phần tử đầu tiên là a[n-2]
     * duyệt tất cả các dãy con có phần tử đầu tiên là a[n-1]
     * tính maxSub từ từng trường hợp trên
     * 
     * Tổng số dãy con = C(n,2) + n = n^2/2 + n/2
     * Độ phức tạp = O(n^2)
     */
    public static int maxSub_naive(int[] a) {
        int n = a.length;
        int i, j;
        int maxSub = 0, sum = 0;

        for (i = 0; i < n; i++) {
            sum = 0;
            for (j = i; j < n; j++) {
                sum = sum + a[j];
                if (sum > maxSub)
                    maxSub = sum;
            }
        }

        return maxSub;
    }

    /**
     * Cách 2: dùng đệ qui: diễn giải chi tiết xem trong vở, bài này dùng Divide and conquer
     * 
     * Update: vở mất mẹ nó rồi còn đâu mà xem :v
     * 
     * Cách này ý tưởng như sau: input là dãy [s, e], gọi phần tử ở giữa của dãy là m, thế thì dãy
     * con lớn nhất của dãy này sẽ là dãy con lớn nhất của 3 dãy con sau:
     * - Dãy con nằm bên trái phần tử giữa: [s, m] (ko nhất thiết phải có phần tử m)
     * - Dãy con nằm bên phải phần tử giữa: [m, e] (ko nhất thiết phải có phần tử m)
     * - Dãy con nằm vắt ngang qua phần tử giữa, tức là bắt buộc phải có phần tử m
     * 
     * Note: tìm được dãy con đó rồi, phải return trọng lượng của nó chứ ko phải các phần tử nhé!
     * 
     * Điểm khó nhất của cách này là tìm ra vị trí bắt đầu của dãy con để tính và so sánh trọng
     * lượng
     * 
     * Độ phức tạp = O(nlogn)
     */
    public static int maxSub_Recursion(int[] a, int s, int e) {
        if (s >= e)
            return a[s];
        int mid = (s + e) / 2;
        int wL = maxSub_Recursion(a, s, mid);
        int wR = maxSub_Recursion(a, mid + 1, e);
        int wM = maxSubMid(a, s, mid, e);
        return max(wL, wR, wM);
    }

    private static int max(int a, int b, int c) {
        int max;
        if (a > b)
            max = a;
        else
            max = b;
        if (c > max)
            max = c;

        return max;
    }

    /**
     * Tìm trọng lượng của dãy con [s, e] mà bắt buộc phải có phần tử mid
     * 
     * Ý tưởng: duyệt từ phần tử mid, 2 lần:
     * - Duyệt lần 1 từ mid -> trái, tính tổng lớn nhất có thể của dãy con
     * - Duyệt lần 2 từ mid -> phải, tính tổng lớn nhất có thể của dãy con
     * - Cộng 2 tổng trên là ra kết quả
     */
    private static int maxSubMid(int[] a, int s, int mid, int e) {
        int i;
        int sum = 0;
        int max = mid;

        // nửa trái của mid:
        for (i = mid; i >= s; i--) {
            sum = sum + a[i];
            if (max < sum)
                max = sum;
        }

        sum = max;

        // nửa phải của mid:
        for (i = mid + 1; i <= e; i++) {
            sum = sum + a[i];
            if (max < sum)
                max = sum;
        }

        return max;
    }

    /**
     * Dynamic Programming using Top Down: cách này tạo 1 bảng tạm lưu các kq trước đó. Thực ra cách
     * này cũng chỉ là chia để trị nhưng tối ưu hơn bằng cách dùng thêm bảng tạm
     */
    public static int maxSub_DP_TopDown(int[] a, int s, int e) {
        L = new int[500][500];
        return maxSub_TopDown(a, s, e);
    }

    private static int maxSub_TopDown(int[] a, int s, int e) {
        if (L[s][e] == 0) {
            if (s >= e)
                L[s][e] = a[s];
            else {
                int mid = (s + e) / 2;
                int wL = maxSub_TopDown(a, s, mid);
                int wR = maxSub_TopDown(a, mid + 1, e);
                int wM = maxSubMid(a, s, mid, e);
                L[s][e] = max(wL, wR, wM);
            }
        }
        return L[s][e];
    }

    /**
     * Cách 4: dùng quy hoạch động: xem giải thích trong vở và sách
     * 
     * Update: vở mất mẹ nó rồi còn đâu mà xem :v
     * 
     * Ý tưởng: dùng Bottom-up: ta sẽ tìm dãy lớn nhất của dãy con s[i], là dãy a[0] -> a[i],
     * với i từ 1 -> n-1. Khi i = n-1 thì ta thu được dãy lớn nhất của dãy đã cho
     * Để ý rằng s[i] chỉ có thể là 1 trong 2 dãy:
     * - Dãy con lớn nhất của s[i-1]
     * - Dãy con lớn nhất của s[i] và phải chứa a[i]
     * 
     * Giải thích cách khác đơn giản hơn: duyệt từ đầu đến cuối, tại mỗi vòng lặp i, cần tìm giá trị
     * mEH (mEH đại diện cho khối lượng lớn nhất của dãy con KẾT THÚC tại phần tử a[i]).
     * 
     * Chú ý rằng tại đầu vòng lặp, mEH là khối lượng lớn nhất của vòng trước đó, tức là dãy con kết
     * thúc tại phần tử a[i-1], và tại vòng lặp hiện tại:
     * - Nếu như mEH trước đó > 0, thì mEH hiện tại = mEH trước đó + a[i]
     * - Nếu như mEH trước đó < 0, thì mEH hiện tại = a[i]
     * 
     * Note: mEH bắt buộc phải chứa a[i] nhé, do đó ý tưởng lại được phát biểu đơn giản hơn là:
     * Tìm tất cả các mEH của các dãy con kết thúc tại a[k], và return biến mEH lớn nhất
     * (k = 0 -> n-1)
     * 
     * Nhận xét: cách này cũng giống với cách naive đầu tiên, chỉ khác ở chỗ thay vì duyệt tất cả các
     * dãy bắt đầu tại a[i], thì cách này duyệt tất cả các dãy KẾT THÚC tại a[i]
     * Và bởi vì trọng lượng dãy kết thúc tại a[i] có thể tính được từ trọng lượng dãy kết thúc tại
     * a[i-1], do đó cách này dùng được kq trước đó mà ko cần lặp for lồng nhau
     */
    public static int maxSub_DP(int[] a) {
        int smax = a[0]; // Trọng lượng của dãy con lớn nhất
        int mEH = a[0];  // mEH = maxEndHere = khối lượng lớn nhất của "dãy con kết thúc tại a[i]"
        int imax = 0;    // vị trí kết thúc của dãy con lớn nhất
        int i;

        for (i = 1; i < a.length; i++) {
            if (mEH + a[i] > a[i])  // if (mEH > 0)
                mEH = mEH + a[i];
            else
                mEH = a[i];

            if (mEH > smax) {
                smax = mEH;
                imax = i;
            }
        }

        System.out.printf("imax = %d\n", imax);
        return smax;
    }


    public static void main(String[] args) {
        // int [] a = {-2,11,-4,13,-5,2};
        int[] a = {-2, 11, -4, 13, -5, 2, 3, 5, 2, 1, -2, 3, -4, 12, -32, 33, 43, -22, 99, 4, 33,
                -11, -19, 33, -99, 30, -03, 33, 31, 55, -3, -33, 9, -2, -22, -2, 11, -4, 13, -5, 2,
                3, 5, 2, 1, -2, 3, -4, 12, -32, 33, 43, -22, 99, 4, 33, -11, -19, 33, -99, 30, -03,
                33, 31, 55, -3, -33, 9, -2, -22, -2, 11, -4, 13, -5, 2, 3, 5, 2, 1, -2, 3, -4, 12,
                -32, 33, 43, -22, 99, 4, 33, -11, -19, 33, -99, 30, -03, 33, 31, 55, -3, -33, 9, -2,
                -22, -2, 11, -4, 13, -5, 2, 3, 5, 2, 1, -2, 3, -4, 12, -32, 33, 43, -22, 99, 4, 33,
                -11, -19, 33, -99, 30, -03, 33, 31, 55, -3, -33, 9, -2, -22, -2, 11, -4, 13, -5, 2,
                3, 5, 2, 1, -2, 3, -4, 12, -32, 33, 43, -22, 99, 4, 33, -11, -19, 33, -99, 30, -03,
                33, 31, 55, -3, -33, 9, -2, -22, -2, 11, -4, 13, -5, 2, 3, 5, 2, 1, -2, 3, -4, 12,
                -32, 33, 43, -22, 99, 4, 33, -11, -19, 33, -99, 30, -03, 33, 31, 55, -3, -33, 9, -2,
                -22, 99, 4, 33, -11, -19, 33, -99, 30, -03, 33, 31, 55, -3, -33, 9, -2, -22, -2, 11,
                -4, 13, -5, 2, 3, 5, 2, 1, -2, 3, 99, 4, 33, -11, -19, 33, -99, 30, -03, 33, 31, 55,
                -3, -33, 9, -2, -22, -2, 11, -4, 13, -5, 2, 3, 5, 2, 1, -2, 3, 99, 4, 33, -11, -19,
                33, -99, 30, -03, 33, 31, 55, -3, -33, 9, -2, -22, -2, 11, -4, 13, -5, 2, 3, 5, 2,
                1, -2, 3, 99, 4, 33, -11, -19, 33, -99, 30, -03, 33, 31, 55, -3, -33, 9, -2, -22,
                -2, 11, -4, 13, -5, 2, 3, 5, 2, 1, -2, 3};
        int n = a.length;
        long curr;
        curr = System.currentTimeMillis();
        System.out.println(maxSub_naive(a));
        System.out.println("Thời gian thực hiện khi dùng maxSub_naive là: "
                + (System.currentTimeMillis() - curr) + "(ms)");

        curr = System.currentTimeMillis();
        System.out.println(maxSub_DP_TopDown(a, 0, n - 1));
        System.out.println("Thời gian thực hiện khi dùng maxSub_TopDown là: "
                + (System.currentTimeMillis() - curr) + "(ms)");

        curr = System.currentTimeMillis();
        System.out.println(maxSub_Recursion(a, 0, n - 1));
        System.out.println("Thời gian thực hiện khi dùng maxSub_Recursion là: "
                + (System.currentTimeMillis() - curr) + "(ms)");

        curr = System.currentTimeMillis();
        System.out.println(maxSub_DP(a));
        System.out.println("Thời gian thực hiện khi dùng maxSub_BottomUp là: "
                + (System.currentTimeMillis() - curr) + "(ms)");
    }
}
/*
 * kq:
 * - Trường hợp worst của TopDown:
 * 1826
 * Thời gian thực hiện khi dùng maxSub_naive là: 9(ms)
 * 1826
 * Thời gian thực hiện khi dùng maxSub_TopDown là: 9(ms)
 * 1826
 * Thời gian thực hiện khi dùng maxSub_Recursion là: 3(ms)
 * 1826
 * Thời gian thực hiện khi dùng maxSub_BottomUp là: 1(ms)
 * - Trường hợp bình thường:
 * 1826
 * Thời gian thực hiện khi dùng maxSub_naive là: 5(ms)
 * 1826
 * Thời gian thực hiện khi dùng maxSub_TopDown là: 3(ms)
 * 1826
 * Thời gian thực hiện khi dùng maxSub_Recursion là: 2(ms)
 * 1826
 * Thời gian thực hiện khi dùng maxSub_BottomUp là: 0(ms)
 * Rõ ràng đệ quy nhanh hơn top-down???
 */
