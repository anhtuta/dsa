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
 * => Tư duy này SAI nhé, xét mảng counting sau:
 * [0 6 7 1 0 9 2 1]
 * Nếu theo tư duy trên, số 5 xuất hiện nhiều nhất (9 lần), chọn số cạnh nó là 6 (2 lần), ta thu
 * được dãy con gồm 11 phần tử, trong khi đáp án đúng phải là 6+7 = 13 phần tử
 * (dãy con gồm các số 1 và 2)
 * 
 * => Solution đúng là phải tìm tổng lớn nhất của 2 số kề nhau trong mảng counting
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

    /**
     * Cách này thiếu case (xem comment tư duy sai ở đầu class), nhưng ko hiểu sao vẫn pass được
     * hackerrank
     */
    public static int pickingNumbers(List<Integer> a) {
        int[] cntArr = new int[100 + 1];    // because a[i] < 100
        int maxRes = 0, res = 0;
        for (int i = 0; i < a.size(); i++) {
            cntArr[a.get(i)]++;
        }

        int max = cntArr[0];
        for (int i = 1; i < cntArr.length; i++) {
            if (cntArr[i] >= max) {
                max = cntArr[i];

                if (i == 0)
                    res = cntArr[i] + cntArr[i + 1];
                else
                    // Chưa xét trường hợp i = cntArr.length - 1 à?
                    // Nếu muốn bỏ qua case này thì chọn cntArr = new int[102] là được
                    res = cntArr[i] + max(cntArr[i - 1], cntArr[i + 1]);

                // Trường họp trong mảng counting có 2 số cùng xuất hiện nhiều nhất,
                // (giống ex3, số 2 và 4) ta phải so sánh xem trường hợp nào tốt hơn
                if (res > maxRes)
                    maxRes = res;
            }
        }

        return maxRes;
    }

    /**
     * Đây mới là cách đúng và đơn giản nhất: đầu tiên counting sort,
     * sau đó tìm tổng lớn nhất của 2 phần tử kề nhau trong mảng counting
     * 
     * Chọn cntArr = 102 vì giá trị lớn nhất là số 100,
     * và tại index 100 cần dùng số cntArr[101]
     */
    public static int pickingNumbers_correct(List<Integer> a) {
        int[] cntArr = new int[102];
        int max = 0, sum = 0;

        // counting sort
        a.stream().forEach(item -> {
            cntArr[item]++;
        });

        // find max sum of two adjacent numbers
        for (int i = 0; i < cntArr.length - 1; i++) {
            sum = cntArr[i] + cntArr[i + 1];
            if (sum > max)
                max = sum;
        }

        return max;
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

        // test case 3 trên Hackerrank (sau khi unlock bằng 5 hackos :v)
        List<Integer> arr4 = Arrays.asList(4, 2, 3, 4, 4, 9, 98, 98, 3, 3, 3, 4, 2, 98, 1, 98, 98,
                1, 1, 4, 98, 2, 98, 3, 9, 9, 3, 1, 4, 1, 98, 9, 9, 2, 9, 4, 2, 2, 9, 98, 4, 98, 1,
                3, 4, 9, 1, 98, 98, 4, 2, 3, 98, 98, 1, 99, 9, 98, 98, 3, 98, 98, 4, 98, 2, 98, 4,
                2, 1, 1, 9, 2, 4);
        result = PickingNumbers.pickingNumbers(arr4);
        System.out.println(result); // expected: 22
        result = PickingNumbers.pickingNumbers_correct(arr4);
        System.out.println(result); // expected: 22

    }
}
