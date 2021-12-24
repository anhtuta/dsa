package algorithm.sorting;

import java.util.Arrays;

public class CountingSort implements SortAlgorithm {

    /*
     * Ex: arr = [1, 4, 1, 2, 7, 5, 2]
     * cntArray = [0, 2, 2, 0, 1, 1, 0, 1, 0, 0]
     */
    @Override
    public void sort(int[] arr) {
        int[] cntArray = new int[arr.length + 1]; // lấy k = n + 1

        // counting
        for (int i = 0; i < arr.length; i++) {
            cntArray[arr[i]]++;
        }

        /*
         * traverse cntArray and put each INDEX of cntArray into arr,
         * after that, arr will be sorted
         * (Note: index of cntArray will be the value of arr)
         * 
         * Có cách làm khác là dùng 1 bảng tính toán cumulative value giống như trên geekforgeek,
         * nhưng cách đó lúc đầu khá khó hiểu, thực sự chả hiểu sao lại tư duy được như thế, sao ko
         * dùng cách dưới đây, dễ hiểu hơn
         */
        int k = 0;
        for (int i = 0; i < cntArray.length; i++) {
            while (cntArray[i] > 0) {
                arr[k++] = i;
                cntArray[i]--;
            }
        }
    }

    @Override
    public String getSortName() {
        return "CountingSort";
    }

    public static void main(String[] args) {
        CountingSort cs = new CountingSort();
        int[] arr = {1, 4, 1, 2, 7, 5, 2};
        cs.sort(arr);
        Arrays.stream(arr).forEach(System.out::println);
    }
}
