package practice.leetcode.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/combinations/
 * 
 * Bài toán liệt kê tất cả các tổ hợp
 */
public class Combinations_77 {

    private List<List<Integer>> res;
    private int[] combination; // a single combination, later we will add this data to res
    private int n;
    private int k;

    public List<List<Integer>> combine(int n, int k) {
        this.n = n;
        this.k = k;
        combination = new int[k]; // 1 tổ hợp k phần tử từ tập n phần tử đã cho
        res = new ArrayList<>();

        // Use one of these solution and comment the rest
        // backtracking(0);
        backtracking2(0);

        return res;
    }

    /**
     * Liệt kê tất cả các tổ hợp và lưu vào mảng combination, sau đó add mảng này vào list res
     * 
     * Runtime 153 ms Beats 5.5%
     * Memory 45.7 MB Beats 5.30%
     * 
     * Chậm quá!!!
     */
    public void backtracking(int pos) {
        if (pos > k - 1) {
            buildSolution();
            return;
        }

        for (int i = 1; i <= n; i++) {
            if (isValidCandidate(pos, i)) {
                combination[pos] = i;
                backtracking(pos + 1);
                // combination[pos] = -1; // No need to remove candidate for current position
            }
        }
    }

    /**
     * Tối ưu: tập candidate sẽ là các số lớn hơn số trước đó tới n, thay vì từ 1 tới n. Và bởi vì tập
     * candidate là các số > số trước nó, nên cả tập này đều valid, do đó ko cần check isValid nữa
     * 
     * Runtime 6 ms Beats 87.32%
     * Memory 45.7 MB Beats 5.30%
     * 
     * Nhanh hơn khá nhiều rồi đó, tạm chấp nhận được
     */
    public void backtracking2(int pos) {
        if (pos > k - 1) {
            buildSolution();
            return;
        }

        for (int i = pos == 0 ? 1 : combination[pos - 1] + 1; i <= n; i++) {
            // if (isValidCandidate(pos, i)) {
            combination[pos] = i;
            backtracking2(pos + 1);
            // }
        }
    }

    /**
     * 1 combination hợp lệ thì các số sau phải > các số trước nó, bởi vì ta sẽ liệt kê toàn bộ các tổ
     * hợp theo thứ tự từ điển. Ex: list combine(4,2) sẽ là:
     * [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
     */
    private boolean isValidCandidate(int pos, int num) {
        for (int i = 0; i < pos; i++) {
            if (combination[i] >= num)
                return false;
        }
        return true;
    }

    /**
     * Thêm 1 tổ hợp (chính là mảng combination[]) vào result
     */
    private void buildSolution() {
        List<Integer> comb = new ArrayList<>(k);
        for (int num : combination) {
            comb.add(num);
        }
        res.add(comb);
    }

    public static void main(String[] args) {
        Combinations_77 app = new Combinations_77();
        System.out.println(app.combine(4, 2)); // [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
        System.out.println(app.combine(6, 3));
    }
}
