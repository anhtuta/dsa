package util;

public class Utils {
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
}
