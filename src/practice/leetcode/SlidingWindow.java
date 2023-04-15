package practice.leetcode;

public class SlidingWindow {
    public int findLength_ex1(int[] nums, int k) {
        int left = 0;
        int currSum = 0; // sum of current slice
        int ans = 0;

        for (int right = 0; right < nums.length; right++) {
            currSum += nums[right];
            while (currSum > k) { // while condition from problem not met: reduce window's size
                currSum -= nums[left]; // "remove" element at arr[left] from window
                left++;
            }

            // update the answer when condition is satisfied again
            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }

    public int findLength_ex2(String s) {
        int left = 0;
        int totalZero = 0; // total number of '0' in current substring
        int ans = 0;

        for (int right = 0; right < s.length(); right++) {
            if (s.charAt(right) == '0') {
                totalZero++;
            }

            while (totalZero > 1) { // while condition from problem not met: reduce window's size
                if (s.charAt(left) == '0') {
                    totalZero--;
                }

                left++;
            }

            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }

    public static void main(String[] args) {
        SlidingWindow sw = new SlidingWindow();
        System.out.println(sw.findLength_ex1(new int[] {3, 1, 2, 7, 4, 2, 1, 1, 5}, 8)); // 4
        System.out.println(sw.findLength_ex2("1101100111")); // 5
    }
}
