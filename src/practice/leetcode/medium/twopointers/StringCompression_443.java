package practice.leetcode.medium.twopointers;

import util.Utils;

/**
 * https://leetcode.com/problems/string-compression/
 */
public class StringCompression_443 {
    /**
     * Idea: two pointers
     * Similar to problem {@link RemoveDuplicatesFromSortedArrayII_80}, I will clone code from that
     * problem
     * 
     * Runtime 1 ms Beats 96.75%
     * Memory 43.4 MB Beats 34.28%
     */
    public int compress(char[] a) {
        int left = 0, right = 0;
        int cnt = 1; // total elements that equals to a[right] (include a[right])
        char[] stack = new char[10]; // To store digits of an integer, so length = 10 is enough
        int top = -1; // top of stack
        while (right < a.length) {
            if (right < a.length - 1 && a[right + 1] == a[right]) {
                cnt++;
                right++;
            } else {
                if (cnt > 1) {
                    // We cannot store a[left + 1] = cnt, because it cnt can large than 9, which means it can have
                    // more than single character
                    // a[left] = a[right];
                    // a[left + 1] = (char) (cnt + '0');
                    // left += 2;

                    // Using while loop to store each character of cnt on separate place
                    a[left++] = a[right]; // Firstly, store a[right]
                    // Then store cnt, we have to use a stack to extract all digits of cnt
                    while (cnt > 0) {
                        stack[++top] = (char) (cnt % 10 + '0');
                        cnt /= 10;
                    }
                    while (top != -1) {
                        a[left++] = stack[top--];
                    }
                } else {
                    a[left] = a[right];
                    left++;
                }
                cnt = 1;
                right++;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        StringCompression_443 app = new StringCompression_443();
        char[] chars = new char[] {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        System.out.println(app.compress(chars)); // 6
        Utils.printArray(chars); // [a 2 b 2 c 3]

        char[] chars2 = new char[] {'a', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'c'};
        System.out.println(app.compress(chars2)); // 5
        Utils.printArray(chars2); // [a b 1 2 c]
    }
}
