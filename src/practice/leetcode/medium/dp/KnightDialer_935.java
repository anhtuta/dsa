package practice.leetcode.medium.dp;

/**
 * https://leetcode.com/problems/knight-dialer/description/
 */
public class KnightDialer_935 {
    // For debug only
    private static final int[][] dialer = new int[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {-1, 0, -1}
    };

    // Từ ô [i][j], con mã có thể đi đến 1 trong 8 ô sau: [i + moveX[k]][j + moveY[k]],
    // với k = [0...7]
    private static final int[] moveX = new int[] {-2, -1, 1, 2, 2, 1, -1, -2}; // row
    private static final int[] moveY = new int[] {1, 2, 2, 1, -1, -2, -2, -1}; // column
    private static final int UNSET = -1;
    private static final int MODULE = 1_000_000_007;
    private static final int DIALER_HEIGHT = 4;
    private static final int DIALER_WIDTH = 3;

    // memo[len][row][col] là số lượng phone có độ dài = len và kết thúc tại số có vị trí [row][col]
    // (vị trí trên dialer).
    // ex: memo[3][1][0] là số lượng phone có độ dài = 3 và kết thúc tại số 4 (vì dialer[1][0] = 4),
    // chẳng hạn: 834, 294, 604
    // nhưng ko được đếm phone = 234, vì phone này ko đi theo cách con mã đi
    private int[][][] memo;

    // dp[3][4] là số lượng phone có độ dài = 3 và kết thúc tại số 4
    private int[][] dp;

    public int knightDialer(int n) {
        if (n == 0)
            return 0;

        int ans;

        // Step 1: recursion top down nhưng bị timeout
        // ans = recursion(n);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // memo = new int[n + 1][4][3]; // size của dialer là 4 * 3
        // for (int i = 0; i <= n; i++) {
        // for (int j = 0; j < 4; j++) {
        // for (int k = 0; k < 3; k++) {
        // memo[i][j][k] = UNSET;
        // }
        // }
        // }
        // ans = dp_topDown(n);

        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        // memo = new int[n + 1][4][3]; // size của dialer là 4 * 3
        // ans = dp_bottomUp_memo(n);

        // Step 3.2: Tối ưu DP bottom up + memo
        // dp = new int[n + 1][10];
        // ans = dp_bottomUp_memo_optimize(n);

        // Step 4: tối ưu hơn nữa (tối ưu memory) bằng việc dùng DP bottom up without memo
        ans = dp_bottomUp_noMemo(n);

        return ans;
    }

    /**
     * Idea đệ quy: tổng số phone độ dài n có thể tạo được =
     * tổng số phone độ dài n có thể tạo được kết thúc tại 0 +
     * tổng số phone độ dài n có thể tạo được kết thúc tại 1 +
     * tổng số phone độ dài n có thể tạo được kết thúc tại 2 +
     * ... +
     * tổng số phone độ dài n có thể tạo được kết thúc tại 9.
     * Túm lại: chia bài toán thành 10 bài toán con: tìm số lượng các phone độ dài n kết thúc tại k,
     * với k = 0-9
     * 
     * Sau đó tính lần lượt từng bài toán con một:
     * tổng số phone độ dài n có thể tạo được kết thúc tại k =
     * tổng số phone độ dài n-1 có thể tạo được kết thúc tại x1 +
     * tổng số phone độ dài n-1 có thể tạo được kết thúc tại x2 +
     * ...
     * Trong đó x1,x2... là vị trí mà quân mã có thể đi được từ ô k
     * 
     * Nếu n=1 thì return 1 (base case)
     * 
     * Để cho đơn giản thì cách này CHƯA thêm phần chia module nếu kq quá to
     * 
     * Result: Time Limit Exceeded
     * Time: O(8^n), bởi vì quân mã có thể đi theo 8 hướng
     */
    public int recursion(int n) {
        int res = 0;
        for (int i = 0; i < 9; i++) {
            res += recursion(n, i / 3, i % 3); // các số từ 1-9
        }
        res += recursion(n, 3, 1); // số 0
        return res;
    }

    private int recursion(int len, int row, int col) {
        if (row < 0 || col < 0 || row > 3 || col > 2) // index out of bound (out of dialer)
            return 0;
        if (row == 3 && (col == 0 || col == 2)) // cell contains * or #
            return 0;
        if (len == 1)
            return 1;

        int res = 0;
        for (int i = 0; i < moveX.length; i++) {
            res += recursion(len - 1, row + moveX[i], col + moveY[i]);
        }
        System.out.printf("len = %d, total sdt ends at cell[%d][%d] (%d) is %d%n",
                len, row, col, dialer[row][col], res);
        return res;
    }

    /**
     * Tối ưu đệ quy dùng memo, đơn giản nên ko muốn giải thích dài dòng nữa
     * 
     * Runtime 231 ms Beats 19.59%
     * Memory 44.3 MB Beats 17.56%
     * 
     * Sao vẫn chậm thế nhờ?
     */
    public int dp_topDown(int n) {
        int res = 0;
        for (int i = 0; i < 9; i++) {
            res = (res + dp_topDown(n, i / 3, i % 3)) % MODULE; // các số từ 1-9
        }
        res = (res + dp_topDown(n, 3, 1)) % MODULE; // số 0
        return res;
    }

    private int dp_topDown(int len, int row, int col) {
        if (row < 0 || col < 0 || row > 3 || col > 2) // index out of bound (out of dialer)
            return 0;
        if (row == 3 && (col == 0 || col == 2)) // cell contains * or #
            return 0;
        if (len == 1)
            return 1;

        if (memo[len][row][col] == UNSET) {
            int res = 0;
            for (int i = 0; i < moveX.length; i++) {
                res = (res + dp_topDown(len - 1, row + moveX[i], col + moveY[i])) % MODULE;
            }
            memo[len][row][col] = res;
        }

        return memo[len][row][col];
    }

    /**
     * Bài này bottom up khá dễ, vì knightDialer(n) chỉ phụ thuộc vào knightDialer(n-1), hay để tính
     * toán các memo[2][i][j], chỉ cần biết các memo[1][i][j]. Mà các phần tử này luôn = 1, vì các số
     * điện thoại length = 1 chỉ có 1 cách để đi (đứng luôn tại đó chứ ko đi được đâu nữa). Vì input bé
     * nên có thể set các memo[1][i][j] trước, rồi tính các memo còn lại
     * 
     * Runtime 125 ms Beats 41.39%
     * Memory 43.9 MB Beats 22.37%
     * 
     * Đúng là nhanh hơn gần gấp đôi so với top down, chắc do ko gọi đệ quy nên vậy. Nhưng như vậy vẫn
     * còn khá chậm, cần tối ưu thêm
     */
    public int dp_bottomUp_memo(int n) {
        // Tính các memo[1][i][j] trước, chúng = 1
        for (int row = 0; row < DIALER_HEIGHT; row++) {
            for (int col = 0; col < DIALER_WIDTH; col++) {
                memo[1][row][col] = 1;
            }
        }
        memo[1][3][0] = 0; // cell *
        memo[1][3][2] = 0; // cell #
        int prevX, prevY;
        // tương ứng biến res ở cách top down: là đáp án bài toán con với các phone kết thúc tại k,
        // với k = 0-9. Cộng 10 biến res này lại là được kết quả bài toán.
        // Chú ý rằng dialer là mảng 2 chiều nên ta sẽ dùng 2 vòng for để duyệt các số 0-9
        int res;
        int ans = 0;

        for (int len = 2; len <= n; len++) { // length of phone
            for (int row = 0; row < DIALER_HEIGHT; row++) { // cell's row
                for (int col = 0; col < DIALER_WIDTH; col++) { // cell's column
                    res = 0;
                    if (row == 3 && col != 1) // cell contains * or #
                        continue;
                    for (int t = 0; t < moveX.length; t++) { // 8 previous position which will leads to this position
                        prevX = row + moveX[t];
                        prevY = col + moveY[t];
                        if (prevX < 0 || prevY < 0 || prevX > 3 || prevY > 2) // out of bound of dialer
                            continue;
                        res = (res + memo[len - 1][prevX][prevY]) % MODULE;
                    }
                    memo[len][row][col] = res;
                    // System.out.printf("memo[%d][%d][%d] = %d%n", i, j, k, res);
                }
            }
        }

        // Cộng kq của 10 bài toán con (số lượng phone độ dài n kết thúc bằng k, với k = 0-9)
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                ans = (ans + memo[n][i][j]) % MODULE;
            }
        }

        return ans;
    }

    /**
     * Tại sao cách trên vẫn chậm quá vậy? Có lẽ bởi vì xét thừa quá nhiều case. Chẳng hạn: ta chỉ có
     * duy nhất 2 cách đi tới số 1, là từ số 6 và 8, nhung vẫn phải xét 8 ô (do con mã đi theo 8 hướng)
     * rồi sau đó loại bỏ 6 ô ko hợp lệ (out of bound of dialer).
     * Với bài toán kiểu này, tốt nhất ko cần dùng mảng moveX, moveY nữa, mà chỉ xét các case có thể đi
     * được thôi, chẳng hạn: memo[100][0][0] = memo[99][1][2] + memo[9][2][1]
     * (dialer[1][2] = 6, dialer[2][1] = 8)
     * 
     * Với cả dùng luôn mảng memo 2D thôi, cho dễ hiểu, ex:
     * dp[100][1] = dp[99][6] + dp[99][8]
     * 
     * Runtime 34 ms Beats 86.38%
     * Memory 43.9 MB Beats 22.37%
     * 
     * Cũng nhanh hơn khá nhiều rồi đó, tạm chấp nhận được!
     */
    public int dp_bottomUp_memo_optimize(int n) {
        // Tính các memo[1][i][j] trước, chúng = 1
        for (int i = 0; i < 10; i++) {
            dp[1][i] = 1;
        }

        int ans = 0;

        // Note: cứ cộng 2 số là phải chia module, vì 2 số đó có thể quá to
        for (int len = 2; len <= n; len++) { // length of phone
            dp[len][1] = (dp[len - 1][6] + dp[len - 1][8]) % MODULE;
            dp[len][2] = (dp[len - 1][7] + dp[len - 1][9]) % MODULE;
            dp[len][3] = (dp[len - 1][4] + dp[len - 1][8]) % MODULE;
            dp[len][4] = ((dp[len - 1][3] + dp[len - 1][9]) % MODULE + dp[len - 1][0]) % MODULE;
            dp[len][5] = 0;
            dp[len][6] = ((dp[len - 1][1] + dp[len - 1][7]) % MODULE + dp[len - 1][0]) % MODULE;
            dp[len][7] = (dp[len - 1][2] + dp[len - 1][6]) % MODULE;
            dp[len][8] = (dp[len - 1][1] + dp[len - 1][3]) % MODULE;
            dp[len][9] = (dp[len - 1][2] + dp[len - 1][4]) % MODULE;
            dp[len][0] = (dp[len - 1][4] + dp[len - 1][6]) % MODULE;
        }

        // Cộng kq của 10 bài toán con (số lượng phone độ dài n kết thúc bằng k, với k = 0-9)
        for (int i = 0; i < 10; i++) {
            ans = (ans + dp[n][i]) % MODULE;
        }

        return ans;
    }

    /**
     * Khi làm quá nhiều các bài DP dùng bottom up, thì có thể đạt tới trình độ tư duy như cách này: chỉ
     * cần dùng 10 biến thay vì mảng dp như trên, bởi vì các dp[k] chỉ phụ thuộc dp[k-1] mà thôi.
     * Note: nếu dùng python thì chỉ cần 10 biến, ko cần cách biến temp, vì python có thể gán:
     * x1, x2, x3 = x6 + x8, x7 + x9, x4 + x8
     * 
     * Runtime 13 ms Beats 97.90%
     * Memory 39.6 MB Beats 97.64%
     * 
     * Time: O(n)
     * Quá nhanh rồi, tối ưu như vậy có thể chấp nhận được rồi! Có thể tối ưu xuống O(log n) nhưng thôi!
     * Mệt roài!
     * 
     * Ref:
     * https://leetcode.com/problems/knight-dialer/solutions/189252/o-logn/
     */
    public int dp_bottomUp_noMemo(int n) {
        int x1 = 1, x2 = 1, x3 = 1, x4 = 1, x5 = 1, x6 = 1, x7 = 1, x8 = 1, x9 = 1, x0 = 1;
        int temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp0;
        for (int i = 2; i <= n; i++) {
            temp1 = (x6 + x8) % MODULE;
            temp2 = (x7 + x9) % MODULE;
            temp3 = (x4 + x8) % MODULE;
            temp4 = ((x3 + x9) % MODULE + x0) % MODULE;
            temp5 = 0;
            temp6 = ((x1 + x7) % MODULE + x0) % MODULE;
            temp7 = (x2 + x6) % MODULE;
            temp8 = (x1 + x3) % MODULE;
            temp9 = (x2 + x4) % MODULE;
            temp0 = (x4 + x6) % MODULE;

            x1 = temp1;
            x2 = temp2;
            x3 = temp3;
            x4 = temp4;
            x5 = temp5;
            x6 = temp6;
            x7 = temp7;
            x8 = temp8;
            x9 = temp9;
            x0 = temp0;
        }

        return sumModule(x1, x2, x3, x4, x5, x6, x7, x8, x9, x0);
    }

    private int sumModule(int... nums) {
        int sum = 0;
        for (int num : nums) {
            sum = (sum + num) % MODULE;
        }
        return sum;
    }

    public static void main(String[] args) {
        KnightDialer_935 app = new KnightDialer_935();
        System.out.println(app.knightDialer(1)); // 10
        System.out.println(app.knightDialer(2)); // 20
        System.out.println(app.knightDialer(3131)); // 136006598
    }
}
