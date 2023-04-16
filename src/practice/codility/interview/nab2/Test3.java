package practice.codility.interview.nab2;

import util.Utils;

public class Test3 {
    private int[] getPrefixSums(int[] a) {
        int[] p = new int[a.length];
        p[0] = a[0];
        for (int i = 1; i < a.length; i++) {
            p[i] = p[i - 1] + a[i];
        }
        return p;
    }

    private int getSliceSum(int[] p, int left, int right) {
        if (left == 0)
            return p[right];
        return p[right] - p[left - 1];
    }

    /**
     * Patterns: prefix sum, sliding window, two pointers
     * Idea: duyệt tất cả các subarray start và end giống nhau (tạm gọi là valid subarray)
     * Dùng 2 con trỏ left và right
     * - left chạy từ đầu, right chạy từ cuối dãy
     * - khi a[left] = a[right] => đây là 1 valid subarray
     * - Tính tổng của subarray này, so sánh với biến max để update giá trị cho max (answer của bài
     * toán). Việc tính tổng a[left...right] có thể dùng prefix sum để tối ưu
     * - Khi a[left] != a[right] => giảm right tới khi tìm được valid subarray
     * - Khi left == right mà vẫn chưa tìm được valid subarray, tăng left để bắt đầu tìm tiếp các valid
     * subarray có start và end giống nhau và = a[left]
     */
    public int solution(int[] a) {
        System.out.print("\n\nInput array: ");
        Utils.printArray(a);

        int left = 0, right = a.length - 1;
        int max = -1; // answer of this problem
        int[] p = getPrefixSums(a);
        int currSum = p[p.length - 1]; // sum of subarray a[left...right], init it = sum of a[]

        System.out.print("Prefix sum array: ");
        Utils.printArray(p);

        while (left < a.length - 1) {
            if (a[left] == a[right]) {
                max = Math.max(max, currSum);

                System.out.printf("Found valid subarray with sum = %d: ", currSum);
                Utils.printArray(a, left, right);

                // currSum = currSum - a[left];
                // update currSum when move right to the end
                // for (int i = right + 1; i < a.length; i++) {
                // currSum += a[i];
                // }
                // using prefix sum will be faster
                // currSum += (getSliceSum(p, right + 1, a.length - 1));

                // now move right to end, and move left one step ahead, to find subarray starts and ends with the
                // same value = a[left]
                left++;
                right = a.length - 1;

                // Update currSum when move right to the end. Using prefix sum will be faster, to calculate new
                // currSum is more simple than calculating it via previous currSum
                currSum = getSliceSum(p, left, right);
            } else {
                currSum = currSum - a[right];
                right--;
                if (right == left) {
                    // there is no valid subarray starts and ends with the same value = a[left]
                    left++;
                    right = a.length - 1;
                    currSum = getSliceSum(p, left, right);
                }
            }
        }

        return max;
    }

    public static void main(String[] args) {
        System.out.println(new Test3().solution(new int[] {1, 3, 6, 1, 6, 6, 9, 9})); // 19
        System.out.println(new Test3().solution(new int[] {5, 1, 4, 3})); // -1
        System.out.println(new Test3().solution(new int[] {2, 2, 2, 3, 2, 3})); // 11
    }
}
