package practice.hackerrank.dc.largestrect;

import java.util.Arrays;
import java.util.List;

public class LargestRectangle3 {

    public static long max(long a, long b, long c) {
        return a > b ? (a > c ? a : c) : (b > c ? b : c);
    }

    public static long largestRectangle(List<Integer> h) {
        return largestRect(h.stream().mapToInt(i -> i).toArray(), 0, h.size() - 1);
    }

    public static long largestRect(int[] arr, int left, int right) {
        if (left == right)
            return arr[left];
        else if (left >= arr.length - 1 || right <= 0 || left > right)
            return -1;

        // Find mid = index of smallest number in arr
        int mid = left;
        int min = arr[left];
        for (int i = left + 1; i <= right; i++) {
            if (arr[i] < min) {
                mid = i;
                min = arr[i];
            }
        }

        ////////// divide and conquer

        // largest rect start from mid-1 to left
        long leftRect = largestRect(arr, left, mid - 1);

        // largest rect start from mid+1 to right
        long rightRect = largestRect(arr, mid + 1, right);

        // rect that contains mid
        long midRect = midRect(arr, left, mid, right);

        return max(leftRect, rightRect, midRect);
    }

    public static long midRect(int[] arr, int left, int mid, int right) {
        // number of building that has height bigger than or equal mid building,
        // This value also includes mid building, so init it = 1
        int building = 1;
        int index = mid - 1;
        while (index >= left) {
            if (arr[index] < arr[mid])
                break;
            building++;
            index--;
        }
        index = mid + 1;
        while (index <= right) {
            if (arr[index] < arr[mid])
                break;
            building++;
            index++;
        }
        return building * arr[mid];
    }

    public static void main(String[] args) {
        List<Integer> h1 = Arrays.asList(1, 3, 5, 9, 11);
        System.out.println(LargestRectangle2.largestRectangle(h1));
        List<Integer> h2 = Arrays.asList(11, 11, 10, 10, 10);
        System.out.println(LargestRectangle3.largestRectangle(h2));
    }
}
