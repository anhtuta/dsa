package practice.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/permutations/
 * 
 * Given an array nums of distinct integers, return all the possible permutations. You can return
 * the answer in any order.
 * 
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * 
 * MUST read first: {@link NextPermutation_31} nếu dùng kỹ thuật two pointers
 * 
 * Bài này được gán tag là backtracking chứ ko phải two pointers, sẽ giải lại sau khi học về
 * backtrack
 */
public class Permutations_46 {

    /**
     * Idea: two pointers để tìm next permutation
     * 
     * Copy method từ bài {@link NextPermutation_31}, sau đó cứ gen permutation liên tục và add vào
     * result là xong. Tổng hoán vị của 1 tập n phần tử riêng biệt là n!
     */
    public List<List<Integer>> permute(int[] a) {
        int totalPermutations = factorial(a.length);
        List<List<Integer>> res = new ArrayList<>(totalPermutations);
        List<Integer> ls;
        while (totalPermutations > 0) {
            ls = new ArrayList<>(a.length);
            for (int i = 0; i < a.length; i++) {
                ls.add(a[i]);
            }
            res.add(ls);
            nextPermutation(a);
            totalPermutations--;
        }

        return res;
    }

    private int factorial(int n) {
        if (n <= 0)
            return 0;
        int fact = 1;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    private void nextPermutation(int[] a) {
        int n = a.length;
        int k;
        for (k = n - 2; k >= 0; k--) {
            if (a[k] < a[k + 1]) {
                break;
            }
        }
        if (k == -1) {
            reverse(a, 0, n - 1);
            return;
        }
        int l = n - 1;
        while (l >= k) {
            if (a[l] > a[k])
                break;
            l--;
        }
        swap(a, k, l);
        reverse(a, k + 1, n - 1);
    }

    private int[] reverse(int[] a, int start, int end) {
        while (start < end) {
            swap(a, start++, end--);
        }
        return a;
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
