package util;

public class Utils {
    private Utils() {
    }

    public static void printArray(int[] a) {
        System.out.print("Array [");
        for (int i = 0; i < a.length - 1; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println(a[a.length - 1] + "]");
    }

    public static void printArray(int[] a, int start, int end) {
        if (start < 0 || end > a.length - 1) {
            System.out.println("Invalid params");
            return;
        }
        System.out.print("Array [");
        for (int i = start; i <= end - 1; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println(a[end] + "]");
    }

    public static void printArray(int[][] a) {
        System.out.print("Array [");
        for (int i = 0; i < a.length; i++) {
            System.out.print("[");
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + (j < a[i].length - 1 ? ", " : ""));
            }
            System.out.print("]" + (i < a.length - 1 ? ", " : ""));
        }
        System.out.println("]");
    }

    public static void printArray(char[][] a) {
        System.out.print("Array [");
        for (int i = 0; i < a.length; i++) {
            System.out.print("[");
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + (j < a[i].length - 1 ? ", " : ""));
            }
            System.out.print("]" + (i < a.length - 1 ? ", " : ""));
        }
        System.out.println("]");
    }

    public static void printSubstring(String s, int start, int end) {
        System.out.print("Substring: ");
        StringBuilder builder = new StringBuilder();
        for (int i = start; i <= end; i++) {
            builder.append(s.charAt(i));
        }
        System.out.println(builder.toString());
    }

    /**
     * Tìm phần tử lớn nhất mà NHỎ HƠN HOẶC BẰNG key
     * Note: input a must be a SORTED array
     * 
     * Note: this method is NOT fully tested yet
     * 
     * Ref:
     * https://github.com/anhtuta/aps/blob/master/DSA/BinarySearch/BinarySearch.cpp
     * 
     * @return index của phần tử đó
     */
    public static int binarySearchGreatestLesserOrEqual(int[] a, int key) {
        int start = 0;
        int end = a.length - 1;
        int mid;
        int ans = -1;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (a[mid] <= key) {
                start = mid + 1;
                ans = mid;
            } else {
                end = mid - 1;
            }
        }
        return ans;
    }

    /**
     * Tìm phần tử nhỏ nhất mà LỚN HƠN key
     * Note: input a must be a SORTED array
     * 
     * Note: this method is NOT fully tested yet
     * 
     * Ref:
     * https://github.com/anhtuta/aps/blob/master/DSA/BinarySearch/BinarySearch.cpp
     * 
     * @return index của phần tử đó
     */
    public static int binarySearchSmallestGreater(int[] a, int key) {
        int start = 0;
        int end = a.length - 1;
        int mid;
        int ans = -1;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (a[mid] > key) {
                end = mid - 1;
                ans = mid;
            } else {
                start = mid + 1;
            }
        }
        return ans;
    }
}
