package practice.leetcode.hard.dp;

/**
 * https://leetcode.com/problems/edit-distance/
 */
public class EditDistance_72 {
    private int[][] memo;
    private int countExecution; // số lần phải tính toán các biến memo[i][j]. For debug only

    public int minDistance(String word1, String word2) {
        int ans;

        countExecution = 0;

        // Step 1: recursion top down nhưng bị timeout
        // ans = recursion(word1, word2, word1.length() - 1, word2.length() - 1);

        // Step 2: tối ưu recursion bằng cách dùng DP top down + memo
        memo = new int[word1.length()][word2.length()];
        for (int i = 0; i < memo.length; i++) {
            for (int j = 0; j < memo[0].length; j++) {
                memo[i][j] = -1;
            }
        }
        ans = dp_topDown(word1, word2, word1.length() - 1, word2.length() - 1);
        System.out.println("countExecution = " + countExecution);

        // Step 3: khử đệ quy bằng cách dùng DP bottom up + memo
        // memo = new int[word1.length()][word2.length()];
        // ans = dp_bottomUp_memo(word1, word2);
        // System.out.println("countExecution = " + countExecution);

        // Step 4: tối ưu hơn nữa (tối ưu memory) bằng việc dùng DP bottom up without memo
        // Khả năng cao bài này ko làm được bằng bottom up + N variables, hoặc do lười chưa muốn nghĩ

        return ans;
    }

    /**
     * Recursion idea: Tham khảo {@link algorithm.dynamicprogramming.Edit_Distance}.
     * Ví dụ w1 = "horse", w2 = "abcde", cần convert w1 -> w2
     * Ta sẽ dùng 2 con trỏ i, j để duyệt từ cuối 2 string (duyệt từ đầu chắc ko được, vì nó khá lằng
     * nhằng lúc insert), tại mỗi lần duyệt:
     * - Nếu w1[i] == w2[j] thì ko cần edit gì, gọi đệ quy xuống tiếp: recursion(w1, w2, i-1, j-1)
     * - Nếu w1[i] != w2[j] (giả sử tại i = j = 3) thì có 3 cách edit:
     * 
     * + insert w2[j] vào vị trí w1[i], lúc này w1 = "horsde", tiếp tục i và j về phía bên trái. Nhưng
     * do thêm 'd' vào w1, nên ko cần dịch chuyển i nữa, i vẫn = 3 và ta vẫn có thể sang ký tự tiếp theo
     * là 's'. Túm lại sau đó chỉ cần dịch j về trái
     * + delete w1[i]: lúc này w1 = "hore", sau đó dịch i về phía trái, j giữ nguyên
     * + replace w1[i]: lúc này w1 = "horde", sau đó dịch cả i và j về trái
     * 
     * Return min của 3 option trên là được.
     * Đệ quy tới khi i hoặc j < 0, đó là base cases. Note: i,j là index hiện tại đang được duyệt, do đó
     * length từ đầu -> vị trí này phải là i+1, j+1
     * 
     * Result:
     * Time Limit Exceeded 25 / 1146 testcases passed
     * 
     * Time: O(3^(m+n))
     */
    public int recursion(String w1, String w2, int i, int j) {
        // Base cases
        if (i < 0)
            return j + 1; // delete all remaining from w1
        if (j < 0)
            return i + 1; // insert all remaining w2 to w1

        if (w1.charAt(i) == w2.charAt(j)) {
            return recursion(w1, w2, i - 1, j - 1);
        } else {
            return 1 + min(
                    recursion(w1, w2, i, j - 1), // insert w2[j] to w1[i]
                    recursion(w1, w2, i - 1, j), // delete w1[i]
                    recursion(w1, w2, i - 1, j - 1) // replace w1[i] by w2[j]
            );
        }
    }

    /**
     * Runtime 4 ms Beats 89.41%
     * Memory 43.5 MB Beats 19.4%
     * 
     * Time: O(m+n)
     */
    public int dp_topDown(String w1, String w2, int i, int j) {
        // Base cases
        if (i < 0)
            return j + 1; // delete all remaining from w1
        if (j < 0)
            return i + 1; // insert all remaining w2 to w1

        if (memo[i][j] == -1) {
            countExecution++; // debug only, should remove this when submit to leetcode
            if (w1.charAt(i) == w2.charAt(j)) {
                memo[i][j] = dp_topDown(w1, w2, i - 1, j - 1);
            } else {
                memo[i][j] = 1 + min(
                        dp_topDown(w1, w2, i, j - 1), // insert w2[j] to w1[i]
                        dp_topDown(w1, w2, i - 1, j), // delete w1[i]
                        dp_topDown(w1, w2, i - 1, j - 1) // replace w1[i] by w2[j]
                );
            }
        }

        return memo[i][j];
    }

    /**
     * Runtime 9 ms Beats 9.65%
     * Memory 43.7 MB Beats 17.94%
     * 
     * Tại sao lại chậm hơn? Thử tính toán tổng số lần tính toán các memo[i][j], input là:
     * "data_structure_algorithm", "date_string_alphabet", thì ta thấy:
     * - Với dp_topDown: countExecution = 446
     * - Với dp_bottomUp_memo: countExecution = 480
     * 
     * Có lẽ bottomUp duyệt thừa các case ko cần thiết (ko phải memo[i][j] nào cũng cần thiết để tính
     * toán), có thể đó là nguyên nhân khiến bottom up chậm hơn
     */
    public int dp_bottomUp_memo(String w1, String w2) {
        for (int i = 0; i < w1.length(); i++) {
            for (int j = 0; j < w2.length(); j++) {
                countExecution++; // debug only, should remove this when submit to leetcode
                if (w1.charAt(i) == w2.charAt(j)) {
                    memo[i][j] = getMemo(i - 1, j - 1);
                } else {
                    memo[i][j] = 1 + min(
                            getMemo(i, j - 1),
                            getMemo(i - 1, j),
                            getMemo(i - 1, j - 1));
                }
            }
        }
        return getMemo(w1.length() - 1, w2.length() - 1);
    }

    /**
     * Nếu index âm thì đó là base case:
     * memo[-1][j] = j + 1
     * memo[i][-1] = i + 1
     */
    private int getMemo(int i, int j) {
        if (i < 0)
            return j + 1;
        if (j < 0)
            return i + 1;
        return memo[i][j];
    }

    private int min(int a, int b, int c) {
        if (a < b)
            return a < c ? a : c;
        else
            return b < c ? b : c;
    }

    public static void main(String[] args) {
        EditDistance_72 app = new EditDistance_72();
        System.out.println(app.minDistance("horse", "ros")); // 3
        System.out.println(app.minDistance("ros", "horse")); // 3
        System.out.println(app.minDistance("intention", "execution")); // 5
        System.out.println(app.minDistance("execution", "intention")); // 5
        System.out.println(app.minDistance("data_structure_algorithm", "date_string_alphabet")); // 14
    }
}
