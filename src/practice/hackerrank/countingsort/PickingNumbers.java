package practice.hackerrank.countingsort;

import java.util.Arrays;
import java.util.List;

/**
 * Problem: https://www.hackerrank.com/challenges/picking-numbers/problem
 * (Easy)
 * Given an array of integers, find the longest subarray where the absolute difference between any
 * two elements is less than or equal to 1.
 * 
 * Đề bài của bài này đọc hơi sai so với example ở dưới, nếu dịch đúng phải như sau: cho 1 mảng các
 * số int, tìm số lượng lớn nhất các phần tử con lấy từ dãy (ko cần kề nhau), sao cho 2 phần tử
 * bất kỳ sai khác nhau <= 1
 * 
 * Example
 * Input: [4 6 5 3 3 1]
 * Output: 3
 * Vì có thể pick được nhiều nhất 3 số đó là [4,3,3] mà 2 số bất kỳ khác nhau <= 1
 * 
 * Ex2:
 * Input: [1 2 2 3 1 2]
 * Output: 5
 * Đó là 5 số: [1 1 2 2 2]
 * 
 * Bài này dùng counting sort, giả sử như Ex2, sau khi counting, ta có mảng:
 * cntArr = [0 2 3 1 0]
 * Từ mảng này ta thấy số 2 xuất hiện nhiều nhất, nên chỉ cần lấy 1 số cạnh nó là được, và phải chọn
 * số xuất hiện nhiều hơn. Do 2 > 1 nên số 1 xuất hiện nhiều hơn số 3
 * 
 * Ex3
 * Input: [1 2 2 3 1 2 4 5 4 4 5 5 6]
 * Output: 6
 * 
 * Trong vd này, cntArr = [0 2 3 1 3 3 1]
 * Trường họp này khá đặc biệt, vì có 3 số xuất hiện 3 lần là 2,4,5. Chọn số nào? Tất nhiên là chọn
 * số 4 rồi, và số cạnh nó là 5. Lúc này pick được 6 số là [4 4 4 5 5 5]
 * 
 * @author anhtu
 *
 */
public class PickingNumbers {

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    /*
     * Cách này thì Ex3 bị sai
     */
    public static int pickingNumbers_missingCase(List<Integer> a) {
        int[] cntArr = new int[100 + 1];    // because a[i] < 100
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < a.size(); i++) {
            cntArr[a.get(i)]++;
            if (cntArr[a.get(i)] > max) {
                max = cntArr[a.get(i)];
                maxIndex = a.get(i);
            }
        }
        if (maxIndex == 0)
            return cntArr[maxIndex] + cntArr[maxIndex + 1];
        else
            return max(cntArr[maxIndex] + cntArr[maxIndex - 1],
                    cntArr[maxIndex] + cntArr[maxIndex + 1]);

    }

    public static int pickingNumbers(List<Integer> a) {
        int[] cntArr = new int[100 + 1];    // because a[i] < 100
        int maxRes = 0, res = 0;
        for (int i = 0; i < a.size(); i++) {
            cntArr[a.get(i)]++;
        }

        int max = cntArr[0];
        int maxIndex = 0;
        for (int i = 1; i < cntArr.length; i++) {
            if (cntArr[i] >= max) {
                max = cntArr[i];
                maxIndex = i;

                if (maxIndex == 0)
                    res = cntArr[maxIndex] + cntArr[maxIndex + 1];
                else
                    res = cntArr[maxIndex] + max(cntArr[maxIndex - 1], cntArr[maxIndex + 1]);

                // Trường họp trong mảng counting có 2 số cùng xuất hiện nhiều nhất,
                // (giống ex3, số 2 và 4) ta phải so sánh xem trường hợp nào tốt hơn
                if (res > maxRes)
                    maxRes = res;
            }
        }

        return maxRes;
    }

    public static void main(String[] args) {
        List<Integer> arr1 = Arrays.asList(4, 6, 5, 3, 3, 1);
        int result = PickingNumbers.pickingNumbers(arr1);
        System.out.println(result);

        List<Integer> arr2 = Arrays.asList(1, 2, 2, 3, 1, 2);
        result = PickingNumbers.pickingNumbers(arr2);
        System.out.println(result);

        List<Integer> arr3 = Arrays.asList(1, 2, 2, 3, 1, 2, 4, 5, 4, 4, 5, 5, 6);
        result = PickingNumbers.pickingNumbers(arr3);
        System.out.println(result);
    }
}
