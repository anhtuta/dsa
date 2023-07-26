package practice.leetcode.hard;

/**
 * https://leetcode.com/problems/trapping-rain-water/
 * 
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it can trap after raining
 * 
 * Xem ảnh /images/41_rainwatertrap.png, hoặc
 * https://docs.google.com/spreadsheets/d/1ukamQI4D9WXuyLxIo-Wp4OdbiFNZnouhn-54XK_OIrg/edit
 * 
 * Example 1:
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The above elevation map (black section) is represented by array
 * [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
 * 
 * Example 2:
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 * 
 * Constraints:
 * n == height.length
 * 1 <= n <= 2 * 10^4
 * 0 <= height[i] <= 10^5
 * 
 * =====
 * Bài tương tự có thể dùng maxLeft, maxRight để giải:
 * {@link practice.leetcode.easy.dp.BestTimeToBuyAndSellStock_121}
 */
public class TrappingRainWater_42 {
    /**
     * Idea: dùng sliding window với 2 con trỏ duyệt từ trái sang phải. Tính số lượng water bên trong
     * window, sau đó bắt đầu window mới bằng việc:
     * - Gán left = right
     * - Tăng right lên 1 đơn vị
     * Tuy vậy, solution này khá khó và nhiều case đặc biệt ko pass được
     */
    public int trap_mySolution_notDone_wrong(int[] height) {
        if (height.length < 3)
            return 0;
        int left = 0;
        for (int i = 0; i < height.length; i++) {
            if (height[i] > 0) {
                left = i;
                break;
            }
        }
        int right = left + 1;
        int water = 0;
        int minHeight = Integer.MAX_VALUE;

        while (right < height.length) {
            minHeight = Math.min(minHeight, height[right]);
            // if (left <= right - 2 && height[left] <= height[right]) {
            if (left <= right - 2 && minHeight < height[left] && minHeight < height[right]) {
                water += Math.min(height[left], height[right]) * (right - left - 1);
                left++;
                while (left < right) {
                    // remove water
                    water -= height[left];
                    left++;
                }
                minHeight = height[left];
            }
            right++;
        }

        return water;
    }

    /**
     * Idea: tính water trên từng bar thay vì window như trên.
     * Dùng 2 mảng hỗ trợ chứ KHÔNG dùng Two pointers
     * 
     * Ref:
     * https://leetcode.com/problems/trapping-rain-water/solutions/1374608/c-java-python-maxleft-maxright-so-far-with-picture-o-1-space-clean-concise/?orderBy=most_votes
     * 
     * 1 solution đơn giản hơn là tính water của từng height[i], với i chạy từ 0->n-2, sau đó cộng lại
     * là được. Vị trí i chứa được nước nếu như có 2 thanh bar cao hơn nó ở 2 bên left và right.
     * (Xem ảnh ở thư mục /images/41_rainwatertrap_maxLeft_maxRight.png)
     * 
     * Nhiệm vụ của ta là tìm 2 thanh cao nhất bên trái và phải tại mỗi vị trí i. Việc này khá đơn giản
     * bằng công thức sau, ta sẽ tính toàn bộ maxLeft và maxRight cho cả mảng với time = O(n):
     * - maxLeft[i] = max(maxLeft[i - 1], height[i - 1]);
     * - maxRight[i] = Math.max(maxRight[i + 1], height[i + 1]);
     * Từ giờ thì việc tìm maxLeft hay maxRight bất kỳ chỉ tốn O(1)
     * 
     * Note: maxLeft[i] = max(height[0...i-1]), là thanh bar cao nhất bên trái thanh bar i, tập con này
     * KHÔNG chứa thanh bar i nhé. Tương tự với maxRight
     * 
     * Tiếp theo, cần check nếu như cả 2 thanh bar maxLeft[i] và maxRight[i] đều lớn hơn thanh bar hiện
     * tại, thì thanh bar này chứa được nước
     * 
     * Optimize: we can use single for loop to calculate both maxLeft and maxRight. See method trap2
     * below
     * 
     * Result:
     * Runtime 1 ms Beats 99.13%
     * Memory 43.1 MB Beats 55.65%
     */
    public int trap_extraSpace(int[] height) {
        int n = height.length;
        int water = 0;
        int[] maxLeft = new int[n];  // maxLeft[i] = max(height[0...i-1]): thanh bar cao nhất bên trái i
        int[] maxRight = new int[n]; // maxRight[i] = max(height[i+1...n-1]): thanh bar cao nhất bên phải i

        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i - 1]);
        }
        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i + 1]);
        }
        for (int i = 1; i < n - 1; i++) {
            if (maxLeft[i] > height[i] && maxRight[i] > height[i])
                water += Math.min(maxLeft[i], maxRight[i]) - height[i];
        }

        return water;
    }

    /**
     * Idea: ta hoàn toàn có thể tối ưu memory giống như tối ưu
     * DP bottom up + memo -> DP bottom up + N variables.
     * 
     * Ta nhận thấy vòng lặp tính water, ta hoàn toàn có thể tính toán maxLeft và maxRight ngay tại đây
     * mà ko cần đến 2 mảng nữa, nhưng phải dùng thêm cả Two pointers nữa. Để ý cách giải trước, tại vị
     * trí i, water tại bar i sẽ phụ thuộc vào 2 bar maxLeft và maxRight, nhưng lượng nước nó chứa được
     * sẽ tính toán theo bar nào thấp hơn, tức là nó chỉ phụ thuộc bar thấp hơn đó thôi. Do đó, với kỹ
     * thuật two pointer left right này:
     * - Nếu như maxLeft < maxRight, ta sẽ tính toán lượng nước chứa được của bar phía bên trái, tức là
     * tại bar height[left], rồi tăng left và tính tiếp
     * - Ngược lại, ta tính lượng nước chứa được của bar bên phải, rồi giảm right và tính tiếp
     * 
     * Lý thuyết là vậy, chứ thực ra t xem lời giải của họ xong giải thích lại, vì cách này khó quá, cần
     * ai có tư duy cao mới nghĩ được.
     * 
     * Bài này được gán tag Two pointers
     * 
     * Result:
     * Runtime 1 ms Beats 99.13%
     * Memory 43.4 MB Beats 24.3%
     * 
     * What the hell? Memory còn tệ hơn solution trước? Thế nên đừng nên tin vào Memory ở leetcode, nó
     * si đa vc!
     */
    public int trap(int[] height) {
        int n = height.length;
        int water = 0;
        int maxLeft = height[0], maxRight = height[n - 1];
        int left = 1, right = n - 2;

        while (left <= right) {
            if (maxLeft < maxRight) {
                if (maxLeft > height[left]) {
                    water += maxLeft - height[left];
                } else {
                    maxLeft = height[left];
                }
                left++;
            } else {
                if (maxRight > height[right]) {
                    water += maxRight - height[right];
                } else {
                    maxRight = height[right];
                }
                right--;
            }

        }

        return water;
    }

    /**
     * Re-practice
     * Idea: similar to trap_extraSpace, but use only single for loop to calculate both maxLeft and
     * maxRight
     */
    public int trap2(int[] height) {
        int n = height.length;
        if (n < 3)
            return 0;
        int water = 0;

        // Of course, we NOT care about maxLeft[0] and maxHeight[n-1]
        int[] maxLeft = new int[n]; // maxLeft[i] = highest bar on the left of bar height[i]
        int[] maxRight = new int[n];

        maxLeft[1] = height[0];
        maxRight[n - 2] = height[n - 1];
        int i = 2, j = n - 3;

        // Definitely, i and j will reach n-1 and 0 respectively and simultaneously
        while (i <= n - 1 && j >= 0) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i - 1]);
            maxRight[j] = Math.max(maxRight[j + 1], height[j + 1]);
            i++;
            j--;
        }

        for (i = 1; i < n - 1; i++) {
            if (maxLeft[i] > height[i] && maxRight[i] > height[i])
                water += Math.min(maxLeft[i], maxRight[i]) - height[i];
        }

        return water;
    }

    public static void main(String[] args) {
        TrappingRainWater_42 app = new TrappingRainWater_42();
        System.out.println(app.trap(new int[] {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})); // 6
        System.out.println(app.trap(new int[] {4, 2, 0, 3, 2, 5})); // 9
        System.out.println(app.trap2(new int[] {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})); // 6
        System.out.println(app.trap2(new int[] {4, 2, 0, 3, 2, 5})); // 9
    }
}
