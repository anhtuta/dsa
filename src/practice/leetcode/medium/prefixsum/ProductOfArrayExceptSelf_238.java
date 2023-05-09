package practice.leetcode.medium.prefixsum;

import util.Utils;

/**
 * https://leetcode.com/problems/product-of-array-except-self/
 */
public class ProductOfArrayExceptSelf_238 {
    /**
     * Idea: dùng 2 mảng prefixP product và suffix product từ 2 phía left và right. Đối với left,
     * prefixPrd[i] là tích của i phần tử đầu tiên, tức là tích của các số từ a[0] -> a[i-1]
     * Tương tự, suffixPrd[i] là tích của i phần tử cuối cùng: a[n-1] -> a[n-i]
     * Hiển nhiên, prefixPrd[i] và suffixPrd[i] sẽ ko chứa i, chúng là tích của i phần tử trước
     * đó (suffixPrd thì chiều duyệt từ cuối dãy)
     * 
     * Ex: a = [2, 3, 4, 5], ta có 2 mảng prefix và suffix như sau (coi dấu _ là dấu cách, vì formatter
     * sẽ loại bỏ 2 dẫu cách trở lên):
     * ___a[]: 2______3______4______5
     * prefix: 1______2______2*3____2*3*4
     * suffix: 3*4*5__4*5____5______1
     * 
     * Nhìn phát biết luôn: answer[i] = prefixPrd[i] * suffixPrd[i];
     * 
     * Result:
     * Runtime 2 ms Beats 49.79%
     * Memory 51.3 MB Beats 9.83%
     */
    public int[] productExceptSelf_readable(int[] a) {
        int[] res = new int[a.length];

        int[] prefixPrd = new int[a.length];
        prefixPrd[0] = 1;
        for (int i = 1; i < a.length; i++) {
            prefixPrd[i] = prefixPrd[i - 1] * a[i - 1];
        }

        int[] suffixPrd = new int[a.length];
        suffixPrd[a.length - 1] = 1;
        for (int i = a.length - 2; i >= 0; i--) {
            suffixPrd[i] = suffixPrd[i + 1] * a[i + 1];
        }

        Utils.printArray(prefixPrd);
        Utils.printArray(suffixPrd);

        for (int i = 0; i < res.length; i++) {
            res[i] = prefixPrd[i] * suffixPrd[i];
        }
        return res;
    }

    /**
     * Tối ưu cách trên, thay vì dùng 2 mảng prefix và suffix, ta chỉ dùng prefix thôi, còn mảng suffix
     * product kia chỉ cần thay thế = 1 biến
     * (giống phần tối ưu DP bottom up + memo -> DP bottom up + N variables)
     * 
     * Result:
     * Runtime 1 ms Beats 100%
     * Memory 50.8 MB Beats 53.34%
     */
    public int[] productExceptSelf(int[] a) {
        int[] res = new int[a.length];

        int[] prefixPrd = new int[a.length];
        prefixPrd[0] = 1;
        for (int i = 1; i < a.length; i++) {
            prefixPrd[i] = prefixPrd[i - 1] * a[i - 1];
        }

        int suffixPrd = 1;
        res[a.length - 1] = prefixPrd[a.length - 1];
        for (int i = a.length - 2; i >= 0; i--) {
            suffixPrd = suffixPrd * a[i + 1];
            res[i] = prefixPrd[i] * suffixPrd;
        }

        return res;
    }

    public static void main(String[] args) {
        ProductOfArrayExceptSelf_238 app = new ProductOfArrayExceptSelf_238();
        Utils.printArray(app.productExceptSelf(new int[] {2, 3, 4, 5})); // [60 40 30 24]
    }
}
