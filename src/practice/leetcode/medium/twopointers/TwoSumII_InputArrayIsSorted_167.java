package practice.leetcode.medium.twopointers;

import util.Utils;

/**
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 */
public class TwoSumII_InputArrayIsSorted_167 {
    /**
     * Idea: two pointers
     * Ý tưởng giống hệt bài {@link practice.leetcode.easy.TwoSum_1}, thậm chí bài này input đã đc sort
     * rồi, nên còn dễ hơn bài TwoSum_1 nữa. Chả hiểu sao level là medium
     * 
     * Runtime 2 ms Beats 27.31%
     * Memory 45.9 MB Beats 15.45%
     * 
     * Time O(n). Chắc cần tối ưu
     */
    public int[] twoSum(int[] numbers, int target) {
        int i = 0, j = numbers.length - 1;
        int s;
        while (i < j) {
            s = numbers[i] + numbers[j];
            if (s == target) {
                return new int[] {i + 1, j + 1};
            } else if (s < target) {
                i++;
            } else {
                j--;
            }
        }
        return new int[] {-1, -1};
    }

    /**
     * Idea: two pointer + binary search
     * Giống như bài three some, ta sẽ cố định 1 phần tử, sau đó tìm phần tử còn lại bằng binary search
     * 
     * Runtime 4 ms Beats 18.80%
     * Memory 45.1 MB Beats 94.71%
     * 
     * Time: O(nlogn), that's why it't slower
     */
    public int[] twoSum2(int[] numbers, int target) {
        int diff; // hiệu số
        int start, end;
        int mid, s;
        for (int i = 0; i < numbers.length - 1; i++) {
            diff = target - numbers[i];
            start = i + 1;
            end = numbers.length - 1;

            // Binary search to find element equals to diff
            while (start <= end) {
                mid = start + (end - start) / 2;
                if (numbers[mid] == diff) {
                    return new int[] {i + 1, mid + 1};
                } else if (numbers[mid] < diff) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }

        return new int[] {-1, -1};
    }

    public static void main(String[] args) {
        TwoSumII_InputArrayIsSorted_167 app = new TwoSumII_InputArrayIsSorted_167();
        Utils.printArray(app.twoSum2(new int[] {2, 7, 11, 15}, 9)); // [1 2]
        Utils.printArray(app.twoSum2(new int[] {3, 24, 50, 79, 88, 150, 345}, 200)); // [3 6]
    }
}
