package practice.leetcode.medium;

import java.util.Arrays;
import util.Utils;

/**
 * https://leetcode.com/problems/3sum-closest/
 * 
 * Nên làm bài {@link ThreeSum_15} trước
 */
public class ThreeSumClosest_16 {
    /**
     * Bài này khá easy nếu như đã làm bài ThreeSum_15. Idea tương tự, ta sẽ duyệt từ đầu -> cuối. Tại
     * mỗi vị trí i, dùng 2 con trỏ chạy từ 2 phía để tìm 2 phần tử còn lại ở bên phải của i, tức là:
     * - left = i+1
     * - right = n-1
     * Sau đó tính tổng sum = a[i] + a[left] + a[right], rồi tìm diff = abs(target - sum). Tìm min diff
     * giữa các lần duyệt sau đó return sum ở lần có min diff đó
     */
    public int threeSumClosest(int[] a, int target) {
        Arrays.sort(a);

        System.out.print("Input: ");
        Utils.printArray(a);

        int left, right, sum = Integer.MIN_VALUE, closestSum = 0, diff, minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < a.length - 2; i++) {
            left = i + 1;
            right = a.length - 1;
            while (left < right) {
                sum = a[i] + a[left] + a[right];
                if (sum == target) {
                    return sum;
                } else {
                    diff = Math.abs(target - sum);
                    if (diff < minDiff) {
                        minDiff = diff;
                        closestSum = sum;
                    }
                    if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }

        return closestSum;
    }

    public static void main(String[] args) {
        ThreeSumClosest_16 app = new ThreeSumClosest_16();
        System.out.println(app.threeSumClosest(new int[] {-1, 2, 1, -4}, 1)); // 2
        System.out.println(app.threeSumClosest(new int[] {0, 1, 1, 1}, -10)); // 2
    }
}
