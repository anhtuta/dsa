package practice.leetcode.medium.prefixsum;

import util.Utils;

/**
 * https://leetcode.com/problems/product-of-array-except-self/
 */
public class ProductOfArrayExceptSelf_238 {
    /**
     * Idea: dùng 2 mảng prefixProduct từ 2 phía left và right. Đối với left, prefixPrdLeft[i] là tích
     * của i phần tử đầu tiên, tức là tích của các số từ a[0] -> a[i-1]
     * Tương tự, prefixPrdRight[i] là tích của i phần tử cuối cùng: a[n-1] -> a[n-i]
     * Hiển nhiên, prefixPrdLeft[i] và prefixPrdRight[i] sẽ ko chứa i, chúng là tích của i phần tử trước
     * đó (right thì chiều từ cuối dãy)
     * 
     * Ex: a = [2, 3, 4, 5], ta có 2 mảng prefixProduct như sau (coi dấu _ là dấu cách, vì formatter sẽ
     * loại bỏ 2 dẫu cách trở lên):
     * ___a[]: 2______3______4______5
     * _Lefts: 1______2______2*3____2*3*4
     * Rights: 3*4*5__4*5____5______1
     * 
     * Nhìn phát biết luôn: answer[i] = prefixPrdLeft[i] * prefixPrdRight[i];
     * 
     * Result:
     * Runtime 2 ms Beats 49.79%
     * Memory 51.3 MB Beats 9.83%
     */
    public int[] productExceptSelf_readable(int[] a) {
        int[] res = new int[a.length];

        int[] prefixPrdLeft = new int[a.length];
        prefixPrdLeft[0] = 1;
        for (int i = 1; i < a.length; i++) {
            prefixPrdLeft[i] = prefixPrdLeft[i - 1] * a[i - 1];
        }

        int[] prefixPrdRight = new int[a.length];
        prefixPrdRight[a.length - 1] = 1;
        for (int i = a.length - 2; i >= 0; i--) {
            prefixPrdRight[i] = prefixPrdRight[i + 1] * a[i + 1];
        }

        Utils.printArray(prefixPrdLeft);
        Utils.printArray(prefixPrdRight);

        for (int i = 0; i < res.length; i++) {
            res[i] = prefixPrdLeft[i] * prefixPrdRight[i];
        }
        return res;
    }

    /**
     * Tối ưu cách trên, thay vì dùng 2 mảng prefix, ta chỉ dùng 1 thôi, còn mảng prefix sum kia chỉ cần
     * thay thế = 1 biến (giống phần tối ưu DP bottom up + memo -> DP bottom up + N variables)
     * 
     * Result:
     * Runtime 1 ms Beats 100%
     * Memory 50.8 MB Beats 53.34%
     */
    public int[] productExceptSelf(int[] a) {
        int[] res = new int[a.length];

        int[] prefixPrdLeft = new int[a.length];
        prefixPrdLeft[0] = 1;
        for (int i = 1; i < a.length; i++) {
            prefixPrdLeft[i] = prefixPrdLeft[i - 1] * a[i - 1];
        }

        int prefixPrdRight = 1;
        res[a.length - 1] = prefixPrdLeft[a.length - 1];
        for (int i = a.length - 2; i >= 0; i--) {
            prefixPrdRight = prefixPrdRight * a[i + 1];
            res[i] = prefixPrdLeft[i] * prefixPrdRight;
        }

        return res;
    }

    public static void main(String[] args) {
        ProductOfArrayExceptSelf_238 app = new ProductOfArrayExceptSelf_238();
        Utils.printArray(app.productExceptSelf(new int[] {2, 3, 4, 5})); // [60 40 30 24]
    }
}
