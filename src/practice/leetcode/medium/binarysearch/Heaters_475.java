package practice.leetcode.medium.binarysearch;

import java.util.Arrays;

/**
 * Input: houses = [1,2,3,4,5], heaters = [1,5]
 * Output: 2
 */
public class Heaters_475 {
    public int findRadius_wrong(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int m = houses.length;
        int n = heaters.length;
        int diff;
        int res = Math.max(heaters[0] - houses[0], houses[m - 1] - heaters[n - 1]);
        for (int i = 0; i < n - 1; i++) {
            diff = heaters[i + 1] - heaters[i] - 1;
            if (diff % 2 == 0)
                diff = diff / 2;
            else
                diff = diff / 2 + 1;
            res = Math.max(res, diff);
        }
        return res;
    }

    /**
     * Idea: Binary search + Two Pointers
     * 
     * Bài này khá giống bài {@link practice.codility.lessons.l14.MinMaxDivision},
     * ta sẽ binary search theo đáp án của bài toán, tức là ta sẽ duyệt res = mid
     * trong khoảng [0, MAX], nếu res là đáp án của bải toán, ta sẽ tìm đáp án nhỏ
     * hơn, nếu ko, ta tăng res để check xem nó có phải là đáp án ko
     * 
     * Runtime 15 ms Beats 66.74%
     * Memory 46.9 MB Beats 29.56%
     * 
     * Ref:
     * https://leetcode.com/problems/heaters/solutions/3480058/java-sorting-binary-search-two-pointers/
     */
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int start = 0, end = 1_000_000_000;
        int mid;
        int res = -1;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (isPossible_twoPointers(houses, heaters, mid)) {
                end = mid - 1;
                res = mid;
            } else {
                start = mid + 1;
            }
        }
        return res;
    }

    /**
     * Check xem bán kính = radius có thoả mãn đk bài toán hay ko (Every house can
     * be warmed).
     * Ta sẽ dùng 2 con trỏ để duyệt 2 mảng houses và heaters. Tại mỗi vị trí của
     * house, nếu 1 trong 2 heaters gần nó nhất (bên trái hoặc bên phải) có thể sưởi
     * ấm nhà này, thì radius là hợp lệ
     * 
     * Ý tưởng là thế, nhưng khi làm sẽ check ngược lại: nếu 2 heaters ở 2 bên đều
     * ko sưởi ấm 1 nhà bất kỳ thì return false luôn. Ta sẽ duyệt theo houses.
     * Bắt đầu tại i và j = 0. Tại mỗi vị trí:
     * - Nếu heater ở bên phải ngôi nhà và nó ko sưởi ấm được, thì return false
     * luôn, vì toàn bộ các heater còn lại đều ở bên phải nhà và còn xa hơn
     * - Nếu heater ở bên trái ngôi nhà và nó ko sưởi ấm được, lúc này cần di chuyển
     * sang phải đến heater tiếp theo và check tiếp
     * 
     * Cách check này khá khó hiểu!
     */
    private boolean isPossible_twoPointers(int[] houses, int[] heaters, int radius) {
        int i = 0, j = 0;
        while (i < houses.length) {
            if (j >= heaters.length)
                return false;

            if (houses[i] - heaters[j] < -radius) {
                // heater is on the right side of house:
                // if this heater (the nearest heater) cannot reach this house,
                // then the rest of heaters (far from right) cannot reach this house
                return false;
            } else if (houses[i] - heaters[j] > radius) {
                // heater is on the left side of house:
                // if this heater cannot reach this house, then move on to the next heater (on
                // the right) and check (stay at current house to check)
                j++;
            } else {
                // if this heater reach this house: move to next house and check
                i++;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Heaters_475 app = new Heaters_475();
        System.out.println(app.findRadius(
                new int[] { 1, 2, 3, 4 }, new int[] { 1, 4 })); // 1
        System.out.println(app.findRadius(
                new int[] { 1, 2, 3, 4, 5 }, new int[] { 1, 5 })); // 2
        System.out.println(app.findRadius(
                new int[] { 1, 2, 3, 5, 15 }, new int[] { 2, 30 })); // 13
        System.out.println(app.findRadius(
                new int[] { 1, 2, 3, 5, 15 }, new int[] { 2, 30, 100 })); // 13
    }
}
