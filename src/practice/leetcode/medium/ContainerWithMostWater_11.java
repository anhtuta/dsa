package practice.leetcode.medium;

/**
 * https://leetcode.com/problems/container-with-most-water/
 * 
 * You are given an integer array height of length n. There are n vertical lines drawn such that the
 * two endpoints of the ith line are (i, 0) and (i, height[i]).
 * 
 * Find two lines that together with the x-axis form a container, such that the container contains
 * the most water.
 * 
 * Return the maximum amount of water a container can store.
 * 
 * Notice that you may not slant the container.
 * 
 * Example 1:
 * Input: height = [1,8,6,2,5,4,8,3,7]
 * Output: 49
 * Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case,
 * the max area of water (blue section) the container can contain is 49.
 */
public class ContainerWithMostWater_11 {
    /**
     * Idea: greedy, two pointers (vì leetcode tag vậy). Sau khi làm xong bài
     * {@link practice.leetcode.hard.FirstMissingPositive_41}, nhận thấy bài này cũng thử dùng 2 con trỏ
     * và tính diện tích rồi update answer. Sau đó di chuyển con trỏ ở phía mà height của nó bé hơn
     * height tại con trỏ kia. Hoá ra cách làm greedy đó lại đúng :v
     * 
     * Mở tab solution thì thấy lời giải thích sau là dễ hiểu nhất:
     * https://leetcode.com/problems/container-with-most-water/solutions/6100/simple-and-clear-proof-explanation/
     * 
     * Giải thích:
     * - Bắt đầu 2 con trỏ từ 2 phía 0 và n-1, container lúc này có chiều rộng lớn nhất = n-1, đây sẽ là
     * 1 good candidate trong việc tìm diện tích lớn nhất. Tính diện tích container này và di chuyển con
     * trỏ để tiếp tục duyệt các container sau
     * - Vấn đề là di chuyển con trỏ nào? Giảm chiều rộng thì phải tăng chiều dài
     * - Sau khi di chuyển 1 con trỏ, chiều rộng container sẽ giảm đi, và để tìm được container có diện
     * tích lớn hơn container trước đó, ta phải tăng chiều dài lên, tức là phải di chuyển con trỏ nào có
     * chiều dài thấp hơn
     * 
     * Bài này khó ở chỗ tìm ra ý tưởng greedy, tìm được rồi thì implement dễ
     */
    public int maxArea(int[] height) {
        int n = height.length;
        int left = 0, right = n - 1;
        int max = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                max = Math.max(max, (right - left) * height[left]);
                left++;
            } else {
                max = Math.max(max, (right - left) * height[right]);
                right--;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        ContainerWithMostWater_11 app = new ContainerWithMostWater_11();
        System.out.println(app.maxArea(new int[] {1, 8, 6, 2, 5, 4, 8, 3, 7})); // 49
    }
}
