package practice.hackerrank.dc.largestrect;

import java.util.Arrays;
import java.util.List;

/**
 * Đề bài: xem file 1
 * Cách dưới đây là cách giải đúng: tìm mid = tòa nhà thấp nhất, tuy vậy worse case có thể là O(n^2)
 * 
 * @author tatu
 *
 */
public class LargestRectangle2 {

    public static long min(long a, long b) {
        return a < b ? a : b;
    }

    public static long max(long a, long b) {
        return a > b ? a : b;
    }

    public static long max(long a, long b, long c) {
        return a > b ? (a > c ? a : c) : (b > c ? b : c);
    }

    public static long largestRectangle(List<Integer> h) {
        int[] arr = new int[h.size()];
        for (int i = 0; i < h.size(); i++) {
            arr[i] = h.get(i);
        }
        return largestRect(arr, 0, arr.length - 1);
    }

    private static long largestRect(int[] arr, int left, int right) {
        if (left == right)
            return arr[left];
        if (left >= arr.length - 1 || right <= 0 || left > right)
            return -1;


        int mid = left, min = arr[left];
        for (int i = left + 1; i <= right; i++) {
            if (arr[i] < min) {
                min = arr[i];
                mid = i;
            }
        }

        return max(largestRect(arr, left, mid - 1), largestRect(arr, mid + 1, right),
                largestRectMid(arr, left, mid, right));
    }

    private static long largestRectMid(int[] arr, int left, int mid, int right) {
        int cnt = 1;
        for (int i = mid - 1; i >= left; i--) {
            if (arr[i] >= arr[mid])
                cnt++;
            else
                break;
        }
        for (int i = mid + 1; i <= right; i++) {
            if (arr[i] >= arr[mid])
                cnt++;
            else
                break;
        }
        // System.out.println(left + "," + mid + "," + right + "," + arr[mid] * cnt);
        return arr[mid] * cnt;
    }

    public static void main(String[] args) {
        // List<Integer> h = Arrays.asList(1, 3, 5, 9, 11);
        // System.out.println(LargestRectangle2.largestRectangle(h));
        List<Integer> h2 = Arrays.asList(11, 11, 10, 10, 10);
        System.out.println(LargestRectangle2.largestRectangle(h2));
    }
}
