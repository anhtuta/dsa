package practice.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import util.Utils;

/**
 * https://leetcode.com/problems/3sum/
 * 
 * Đôi khi chỉ cần thay đổi cách tính toán là pass được luôn, trong khi cách tính cũ có quá nhiều
 * case đặc biệt bị fail
 */
public class ThreeSum_15 {
    /**
     * Ý tưởng: cũng giống như bài Two Sum, sort dãy trước, rồi duyệt toàn bộ các cặp 3 số và tính tổng,
     * nếu = 0 thì add vào result. Nhưng do 3 số nên sẽ bị duplicate rất nhiều, ta cần loại bỏ bằng cách
     * nhảy qua các phần tử = nhau.
     * 
     * Ta sẽ duyệt từ index 1->n-2, tại mỗi vị trí i, ta sẽ dùng 2 con trỏ left, right, để tìm 2 phần tử
     * ở 2 bên của i, rồi cộng 3 số lại:
     * - Nếu = 0 thì add 3 số vào res, sau đó left--, right++. Và nhảy qua toàn bộ các phần tử a[left]
     * bằng nhau và a[right] bằng nhau
     * 
     * Cách này ko sai, nhưng chả hiểu sao nó cứ bị mấy cái case đặc biệt éo pass được hết! Bỏ!!!
     */
    public List<List<Integer>> threeSum_tooComplicated(int[] a) {
        Arrays.sort(a);
        System.out.print("Input: ");
        Utils.printArray(a);
        List<List<Integer>> res = new ArrayList<>();
        int left, right, sum;
        int cntZero = 0;
        boolean isDuplicated;
        for (int i = 1; i < a.length - 1; i++) {
            left = i - 1;
            right = i + 1;
            isDuplicated = false;
            while (left >= 0 && right < a.length) {
                sum = a[left] + a[i] + a[right];
                if (sum == 0) {
                    if (!(a[left] == 0 && a[i] == 0 && a[right] == 0)) {
                        res.add(Arrays.asList(a[left], a[i], a[right]));
                    }
                    right++;
                    while (right < a.length && a[right] == a[right - 1])
                        right++;
                    left--;
                    while (left >= 0 && a[left] == a[left + 1])
                        left--;
                } else if (sum < 0) {
                    right++;
                } else {
                    left--;
                }
            }
            while (a[i] == 0) {
                cntZero++;
                if (i + 1 < a.length && a[i + 1] == 0)
                    i++;
                else
                    break;
            }
            while (i < a.length - 1 && a[i] == a[i - 1]) {
                i++;
                isDuplicated = true;
            }
            if (isDuplicated)
                i--;
        }

        if (cntZero >= 3) {
            res.add(Arrays.asList(0, 0, 0));
        }

        return res;
    }

    /**
     * Idea giống với bên trên, chỉ khác ở chỗ ta sẽ duyệt từ 0->n-3. Tại mỗi vòng duyệt phần tử i, ta
     * vẫn dùng 2 con trỏ left right nhưng để tìm 2 phần tử còn lại đều ở bên phải của i, tức là:
     * - left = i+1
     * - right = n-1
     * [left...right] lúc này hình thành 1 window. Cần tìm left right để tổng 2 số = -a[i], bài toán giờ
     * lại trở thành Two sum rồi, chỉ khác ở chỗ tổng = -a[i] thay vì = 0.
     * Sau khi tìm được left, right thì vẫn phải nhảy qua các phần tử a[left], a[right] trùng nhau
     * 
     * Result: accepted
     * 
     * Ref:
     * https://leetcode.com/problems/3sum/solutions/7402/share-my-ac-c-solution-around-50ms-o-n-n-with-explanation-and-comments/
     */
    public List<List<Integer>> threeSum_ok(int[] a) {
        Arrays.sort(a);

        System.out.print("Input: ");
        Utils.printArray(a);

        List<List<Integer>> res = new ArrayList<>();
        int left, right, sum, target;
        int curr;
        for (int i = 0; i < a.length - 2; i++) {
            left = i + 1;
            right = a.length - 1;
            target = -a[i];
            while (left < right) {
                sum = a[left] + a[right];
                if (sum == target) {
                    res.add(Arrays.asList(a[i], a[left], a[right]));

                    // Skip toàn bộ các phần tử a[left] giống nhau
                    curr = a[left];
                    while (left < right && a[left] == curr)
                        left++;

                    // Skip toàn bộ các phần tử a[right] giống nhau
                    curr = a[right];
                    while (left < right && a[right] == curr)
                        right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }

            // Skip toàn bộ các phần tử a[i] giống nhau
            while (i < a.length - 2 && a[i + 1] == a[i]) {
                i++;
            }
        }

        return res;
    }

    /**
     * Giống threeSum_ok, nhưng bỏ biến curr và target đi. Tuy sum = a[i] + a[left] + a[right], nhưng
     * trong mỗi lần duyệt, a[i] ko đổi, chỉ có left và right là thay đổi, do đó:
     * - Nếu sum < 0 thì left++
     * - Nếu sum > 0 thì right--
     */
    public List<List<Integer>> threeSum(int[] a) {
        Arrays.sort(a);

        System.out.print("Input: ");
        Utils.printArray(a);

        List<List<Integer>> res = new ArrayList<>();
        int left, right, sum;
        for (int i = 0; i < a.length - 2; i++) {
            left = i + 1;
            right = a.length - 1;
            while (left < right) {
                sum = a[i] + a[left] + a[right];
                if (sum == 0) {
                    res.add(Arrays.asList(a[i], a[left], a[right]));

                    // Skip toàn bộ các phần tử a[left] giống nhau
                    while (left < right && a[left + 1] == a[left])
                        left++;

                    // Skip toàn bộ các phần tử a[right] giống nhau
                    while (left < right && a[right - 1] == a[right])
                        right--;

                    // Giảm kích thước window để tìm các cặp tiếp theo (tìm triplet nhưng a[i] ko đổi,
                    // chỉ tìm a[left] và a[right]). Note: phải giảm kích thước windows SAU khi skip các
                    // duplication, nếu ko sẽ bị skip quá 1 phần tử
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }

            // Skip toàn bộ các phần tử a[i] giống nhau
            while (i < a.length - 2 && a[i + 1] == a[i]) {
                i++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        ThreeSum_15 app = new ThreeSum_15();
        System.out.println(app.threeSum(new int[] {-1, 0, 1, 2, -1, -4})); // [[-1,-1,2],[-1,0,1]]
        System.out.println(app.threeSum(new int[] {0, 0, 0, 0})); // [[0,0,0]]
        System.out.println(app.threeSum(
                new int[] {-1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1})); // [[-1, 0, 1], [0, 0, 0]]
        System.out.println(app.threeSum(new int[] {-1, 0, 0, 0, 0, 1})); // [[-1, 0, 1], [0, 0, 0]]
        System.out.println(app.threeSum(new int[] {-1, 0, 0, 1})); // [[-1, 0, 1]]

        // [[-1,-1,2], [-1, 0, 1], [-3, 1, 2], [-3,0,3]]
        System.out.println(app.threeSum(new int[] {-3, -3, -3, -1, -1, 0, 0, 0, 1, 1, 2, 2, 3, 3}));
    }
}
