package practice.leetcode.easy;

import util.Utils;

/**
 * https://leetcode.com/problems/missing-number/
 */
public class MissingNumber_268 {
    // Pattern: counting elements
    public int missingNumber_usingExtraSpace(int[] a) {
        int[] cntArr = new int[10001];
        for (int i = 0; i < a.length; i++) {
            cntArr[a[i]]++;
        }
        for (int i = 0; i < cntArr.length; i++) {
            if (cntArr[i] == 0)
                return i;
        }
        return -1;
    }

    /**
     * Sort này đang bị sai
     * Ex: [8, 6, 4, 2, 3, 5, 7, 0, 1] -> [0 7 2 3 4 5 6 1 8]
     */
    public void cyclicSort_wrong(int[] a) {
        int temp;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != a.length && a[i] != i) {
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

    public void cyclicSort(int[] a) {
        int temp;
        int i = 0;
        while (i < a.length) {
            if (a[i] != a.length && a[i] != i) {
                // swap a[i] vs a[a[i]]. In python, we can use following statement:
                // a[i], a[a[i]] = a[a[i]], a[i]
                temp = a[a[i]];
                a[a[i]] = a[i];
                a[i] = temp;
            } else {
                i++;
            }
        }

        System.out.print("After cyclic sort: ");
        Utils.printArray(a);
    }

    // using cyclic sort, no extra space
    public int missingNumber(int[] a) {
        cyclicSort(a);
        for (int i = 0; i < a.length; i++) {
            if (i != a[i])
                return i;
        }
        return a.length;
    }

    public static void main(String[] args) {
        System.out.println(new MissingNumber_268().missingNumber(new int[] {3, 0, 1})); // 2
        System.out.println(new MissingNumber_268().missingNumber(new int[] {0, 1})); // 2
        System.out.println(new MissingNumber_268().missingNumber(new int[] {9, 6, 4, 2, 3, 5, 7, 0, 1})); // 8
        System.out.println(new MissingNumber_268().missingNumber(new int[] {8, 6, 4, 2, 3, 5, 7, 0, 1})); // 9
    }
}
