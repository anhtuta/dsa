package practice.codility;

public class Utils {
    public static void printArray(int[] a) {
        System.out.print("Array [");
        for (int i = 0; i < a.length - 1; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println(a[a.length - 1] + "]");
    }
}
