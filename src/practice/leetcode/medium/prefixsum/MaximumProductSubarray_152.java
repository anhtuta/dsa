package practice.leetcode.medium.prefixsum;

import util.Utils;

/**
 * https://leetcode.com/problems/maximum-product-subarray/
 */
public class MaximumProductSubarray_152 {
    /**
     * Idea: brute-force: dùng 2 vòng for thử tất cả các subarray, sau đó tính tích và update answer.
     * Cách duyệt tất cả các subarray: tại vòng lặp thứ i, tìm tất cả các subarray bắt đầu tại a[i]
     * 
     * Result:
     * Time: O(n^2)
     * Runtime 273 ms Beats 6.17%
     * Memory 42.8 MB Beats 52.30%
     */
    public int maxProduct_bruteForce(int[] a) {
        int prd, maxPrd = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            prd = a[i];
            maxPrd = Math.max(maxPrd, prd);
            for (int j = i + 1; j < a.length; j++) {
                prd *= a[j];
                maxPrd = Math.max(maxPrd, prd);
            }
        }

        return maxPrd;
    }

    /**
     * Idea: prefix product and suffix product. Tích lớn nhất là số lớn nhất trong 2 mảng đó
     * 
     * Giải thích: giả sử ko có số 0 trong input a[]
     * - Nếu số lượng số âm là số chẵn => maxPrd = tích tất cả các phần tử
     * - Nếu số lượng số âm là số lẻ: gọi i, j là index của số âm đầu tiên và cuối cùng, thì
     * maxPrd = tích của a[0...j-1], hoặc tích của a[i+1...n-1]
     * maxPrd = max(product(a[0...j-1]), product(a[i+1...n-1]))
     * Ex: a[] = [2, 1, -1, 3, -2, 1, -3, 4], thì maxPrd sẽ tích của 1 trong 2 dãy sau
     * [2, 1, -1, 3, -2, 1] hoặc [3, -2, 1, -3, 4]
     * 
     * Trong cả 2 case đó, maxPrd đều chứa phần tử a[0] hoặc a[n-1], tức là maxPrd sẽ là 1 phần tử nào
     * đó trong 2 mảng prefix product and suffix product
     * 
     * Nếu input a[] có số 0, thì ta dùng số 0 đó chia mảng a thành các mảng con rồi lại tìm maxPro trên
     * các mảng con như case trên
     * 
     * Do đó, khi tính prefix product, nếu gặp số 0 thì set prefixPrd[i] = 1 để bắt đầu prefixPrd mới:
     * prefixPrd[i] = prefixPrd[i - 1] * a[i - 1];
     * if (prefixPrd[i] == 0) prefixPrd[i] = 1;
     * maxPrd = Math.max(maxPrd, prefixPrd[i]);
     * 
     * Nhưng làm như trên sẽ bị sai case input toàn số âm và có 1 số 0, lúc này maxPrd = 1, nhưng kq
     * đúng phải là 0. Do đó ta phải update maxPrd TRƯỚC khi if (prefixPrd[i] == 0) prefixPrd[i] = 1:
     * prefixPrd[i] = (prefixPrd[i - 1] == 0 ? 1 : prefixPrd[i - 1]) * a[i - 1];
     * maxPrd = Math.max(maxPrd, prefixPrd[i]);
     * 
     * Result:
     * Time: O(n)
     * Runtime 1 ms Beats 87.54%
     * Memory 42.8 MB Beats 39.86%
     * 
     * Ref:
     * https://leetcode.com/problems/maximum-product-subarray/solutions/183483/java-c-python-it-can-be-more-simple/
     */
    public int maxProduct(int[] a) {
        int n = a.length;
        int maxPrd = a[0];
        int[] prefixPrd = new int[n + 1];
        prefixPrd[0] = 1;
        for (int i = 1; i <= n; i++) {
            prefixPrd[i] = (prefixPrd[i - 1] == 0 ? 1 : prefixPrd[i - 1]) * a[i - 1];
            maxPrd = Math.max(maxPrd, prefixPrd[i]);
        }

        int[] suffixPrd = new int[n + 1];
        suffixPrd[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            suffixPrd[i] = (suffixPrd[i + 1] == 0 ? 1 : suffixPrd[i + 1]) * a[i];
            maxPrd = Math.max(maxPrd, suffixPrd[i]);
        }

        Utils.printArray(prefixPrd);
        Utils.printArray(suffixPrd);

        return maxPrd;
    }

    public int maxProduct_wrong(int[] a) {
        int maxPrd = a[0];
        int mEH = a[0];
        int pEH = a[0];

        for (int i = 1; i < a.length; i++) {
            mEH = Math.max(pEH * a[i], a[i]); // mEH = tích lớn nhất của slice KẾT THÚC tại a[i]
            pEH = pEH * a[i];
            maxPrd = max(maxPrd, mEH, pEH);
        }

        return maxPrd;
    }

    private int max(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    public static void main(String[] args) {
        MaximumProductSubarray_152 app = new MaximumProductSubarray_152();
        System.out.println(app.maxProduct(new int[] {2, 3, -2, 4})); // 6
        System.out.println(app.maxProduct(new int[] {-2, 0, -1})); // 0
        System.out.println(app.maxProduct(new int[] {-2, 3, -4})); // 24
        System.out.println(app.maxProduct(new int[] {-2, 3, -4, -3, 1})); // 36
        System.out.println(app.maxProduct(new int[] {-2, 3, -4, -3, -1})); // 72
        System.out.println(app.maxProduct(new int[] {-3, 0, 4, -2})); // 4
        System.out.println(app.maxProduct(new int[] {2, -1, 1, 4})); // 4
        System.out.println(app.maxProduct(new int[] {-2, 0, -1})); // 0
    }
}
