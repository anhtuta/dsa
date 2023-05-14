package practice.leetcode.hard.dp;

import util.Utils;

public class DungeonGame_174 {
    private int[][] minHP; // minHP[i][j] = min HP needed to go to the cell [i][j]
    private int[][] hp; // hp[i][j] = current HP at cell [i][j]

    public int calculateMinimumHP(int[][] dungeon) {
        Utils.printArray(dungeon);
        int ans;
        // hp[0][0] = dungeon[0][0];

        // Step 1: recursion top down nhưng bị timeout
        ans = recursion(dungeon, dungeon.length - 1, dungeon[0].length - 1);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        // initMemo(a.length, a[0].length);
        // ans = dp_topDown(a, a.length - 1, a[0].length - 1);

        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        // minHP = new int[dungeon.length][dungeon[0].length];
        // hp = new int[dungeon.length][dungeon[0].length];
        // ans = dp_bottomUp_memo(dungeon);

        // Step 4: tối ưu hơn nữa (tối ưu memory) bằng việc dùng DP bottom up without memo
        // Khả năng bài này ko làm được theo cách này

        return ans;
    }

    /**
     * Rename mảng dungeon[] là a[] cho ngắn gọn
     * Xét bài toán đơn giản hơn là ko có phòng nào chứa magic orbs, tức là đi qua phòng nào cũng phải
     * đánh quái (demon) và mất máu. Khi đó bài toán sẽ trở thành bài
     * {@link practice.leetcode.medium.dp.MinimumPathSum_64}, tìm con đường tối ưu đi tới chỗ công chúa
     * sao cho bị mất máu ít nhất
     * Nhưng bài này lại có phòng có thứ giúp hồi máu, do đó ta ko chỉ đơn thuần tính min[i][j] theo
     * min[i-1][j] và min[i][j-1] được, mà min[i][j] còn phụ thuộc cả lượng HP hiện tại tại ô [i][j], do
     * đó phải cần thêm 1 mảng nữa lưu số lượng máu đang có ở ô hiện tại
     * Mà lượng máu hp[i][j] bắt buộc phải tính theo hp các ô trước đó, nên bài này khó đệ quy top down
     * được, mà phải dùng bottom up (duyệt từ ô [0][0] đến [m-1][n-1])
     * => SAI, vì minHP[i][j] sẽ phụ thuộc vào 2 thành phần sau:
     * - minHP[i-1][j] hoặc minHP[i][j-1]
     * - hp ở tương lai, tức là toàn bộ hp[i][j+1], hp[i+1][j] -> hp[m-1][n-1]
     * Do đó duyệt từ đầu có vẻ bất khả kháng thì đúng hơn
     */
    public int recursion(int[][] a, int i, int j) {
        int res;
        if (i == 0 && j == 0) {
            res = a[i][j] > 0 ? 1 : 1 - a[i][j];
            return res;
        } else if (i == 0) {
            res = a[i][j] + recursion(a, i, j - 1);
        } else if (j == 0) {
            res = a[i][j] + recursion(a, i - 1, j);
        } else
            res = a[i][j] + Math.min(
                    recursion(a, i - 1, j),
                    recursion(a, i, j - 1));
        if (res > 0)
            res = 1;
        else
            res = 1 - res;
        System.out.printf("res[%d][%d] = %d%n", i, j, res);
        return res;
    }

    public int dp_bottomUp_memo(int[][] a) {
        int needMoreHP; // the HP we need more when enter a[i][j], otherwise, HP will < 1
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                needMoreHP = 0;
                if (i == 0 && j == 0) {
                    hp[i][j] = a[i][j];
                    if (hp[i][j] < 1) {
                        needMoreHP = 1 - hp[i][j];
                    }
                    hp[i][j] += needMoreHP;
                    minHP[i][j] = needMoreHP;
                } else if (i == 0) {
                    hp[i][j] = hp[i][j - 1] + a[i][j];
                    if (hp[i][j] < 1) {
                        needMoreHP = 1 - hp[i][j];
                    }
                    hp[i][j] += needMoreHP;
                    minHP[i][j] = minHP[i][j - 1] + needMoreHP;
                } else if (j == 0) {
                    hp[i][j] = hp[i - 1][j] + a[i][j];
                    if (hp[i][j] < 1) {
                        needMoreHP = 1 - hp[i][j];
                    }
                    hp[i][j] += needMoreHP;
                    minHP[i][j] = minHP[i - 1][j] + needMoreHP;
                } else {
                    // SAI, ko thể chọn cách tính minHP hiện tại theo minHP nào chỉ với việc so sánh 2 minHP trước đó,
                    // bởi vì minHP hiện tại còn phụ thuộc vào lượng HP của tất cả các ô sau đó
                    if (minHP[i][j - 1] < minHP[i - 1][j]) {
                        hp[i][j] = hp[i][j - 1] + a[i][j];
                        if (hp[i][j] < 1) {
                            needMoreHP = 1 - hp[i][j];
                        }
                        hp[i][j] += needMoreHP;
                        minHP[i][j] = minHP[i][j - 1] + needMoreHP;
                    } else {
                        hp[i][j] = hp[i - 1][j] + a[i][j];
                        if (hp[i][j] < 1) {
                            needMoreHP = 1 - hp[i][j];
                        }
                        hp[i][j] += needMoreHP;
                        minHP[i][j] = minHP[i - 1][j] + needMoreHP;
                    }
                }
            }
        }
        return minHP[a.length - 1][a[0].length - 1];
    }

    public static void main(String[] args) {
        DungeonGame_174 app = new DungeonGame_174();
        int[][] a = {{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}}; // 7
        System.out.println(app.calculateMinimumHP(a));
        System.out.println(app.calculateMinimumHP(new int[][] {{0, 0}})); // 1
        System.out.println(app.calculateMinimumHP(new int[][] {{0, 0, 0}, {-1, 1, -1}})); // 1
    }
}
