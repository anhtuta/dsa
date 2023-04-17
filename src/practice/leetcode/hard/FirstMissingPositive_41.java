package practice.leetcode.hard;

import util.Utils;

/**
 * https://leetcode.com/problems/first-missing-positive/
 * Not done yet
 */
public class FirstMissingPositive_41 {
    /**
     * Should modify this to pass all test cases
     */
    public void cyclicSort(int[] a) {
        // int temp;
        // int i = 0;
        // while (i < a.length) {
        // if (a[i] < a.length && a[i] != i) {
        // // swap a[i] vs a[a[i]]. In python, we can use following statement:
        // // a[i], a[a[i]] = a[a[i]], a[i]
        // temp = a[a[i]];
        // a[a[i]] = a[i];
        // a[i] = temp;
        // } else {
        // i++;
        // }
        // }

        int temp;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < a.length && a[i] != i) {
                // swap a[i] vs a[a[i]]. After that, a[a[i]] will be in the correct place, but a[i] maybe NOT
                // ex: a[2] = 4, a[4] = 3 => a[4] = a[2] = 4, a[2] = 3
                temp = a[a[i]];
                a[a[i]] = a[i];
                a[i] = temp;
            }
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] < a.length && a[i] != i) {
                // swap a[i] vs a[a[i]]. After that, a[a[i]] will be in the correct place, but a[i] maybe NOT
                // ex: a[2] = 4, a[4] = 3 => a[4] = a[2] = 4, a[2] = 3
                temp = a[a[i]];
                a[a[i]] = a[i];
                a[i] = temp;
            }
        }

        System.out.print("After cyclic sort: ");
        Utils.printArray(a);
    }

    public int missingNumber(int[] a) {
        cyclicSort(a);
        for (int i = 0; i < a.length; i++) {
            if (i != a[i])
                return i;
        }
        return a.length;
    }

    public int firstMissingPositive(int[] a) {
        int max = -1;
        int min = Integer.MAX_VALUE;

        // find min, max of the array, but NOT take the negative numbers into account
        // tìm min, max của mảng, nhưng KHÔNG tính đến các số âm
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= 0) {
                if (a[i] > max)
                    max = a[i];
                if (a[i] < min)
                    min = a[i];
            }
        }

        if (min > 1)
            return 1;
        if (max == 1)
            return 2;

        // Decrease all elements by min, and convert all negative numbers to max+1
        // This solution would lead to overflow if max = Integer.MAX_VALUE
        max -= min;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < 0)
                a[i] = ++max;
            else
                a[i] -= min;
        }

        System.out.print("After reduce all elements by min: ");
        Utils.printArray(a);
        int missing = missingNumber(a);
        return missing + min;
    }

    public static void main(String[] args) {
        System.out.println(new FirstMissingPositive_41().firstMissingPositive(new int[] {3, 4, -1, 1})); // 2
        System.out.println(new FirstMissingPositive_41().firstMissingPositive(new int[] {2, 2, 2, 1})); // 3
        System.out.println(new FirstMissingPositive_41().firstMissingPositive(new int[] {1, 1, 1, 2})); // 3
        System.out.println(new FirstMissingPositive_41().firstMissingPositive(new int[] {1, 1, 1, 1})); // 2
        System.out.println(new FirstMissingPositive_41().firstMissingPositive(new int[] {1, 2, 0})); // 3
        System.out.println(new FirstMissingPositive_41().firstMissingPositive(new int[] {3, 7, -1, 1})); // 2
        System.out.println(new FirstMissingPositive_41().firstMissingPositive(new int[] {7, 8, 9, 11, 15})); // 1
        System.out.println(new FirstMissingPositive_41().firstMissingPositive(
                new int[] {-10, -3, -100, -1000, -239, 1})); // 2
        System.out.println(new FirstMissingPositive_41().firstMissingPositive(
                new int[] {2147483647, 2147483646, 2147483645, 3, 2, 1, -1, 0, -2147483648})); // 4
    }
}
