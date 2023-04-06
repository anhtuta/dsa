package practice.codility.lessons.l15;

/**
 * Bài toán trong file pdf:
 * Let's check whether a sequence a[0], a[1], ... , a[n−1] (1 <= a[i] <= 10^9) contains a contiguous
 * subsequence whose sum of elements equals s.
 * Check xem dãy a[] có tồn tại slice nào có tổng = số s cho trước hay ko
 */
public class CaterpillarMethod {

    private static void printSlice(int[] a, int s, int e) {
        if (s < 0 || e > a.length - 1) {
            System.out.println("Invalid input!");
            return;
        }

        System.out.print("[");
        for (int i = s; i < e; i++) {
            System.out.print(a[i] + ", ");
        }
        System.out.println(a[e] + "]");
    }

    /**
     * Giống như kỹ thuật window: tại mỗi dãy con đang xét, coi nó như con sâu bướm, và cần biết rằng
     * loài động vật này bò bằng cách kéo đuôi lại để co mình, xong sau đó duỗi thẳng người ra để đầu
     * tiến về phía trước.
     * Nó có thể tăng chiều dài bằng cách ăn thức ăn trên đường đi chẳng hạn, tức là ko cần kéo đuôi mà
     * đầu vẫn tiến về phía trước (lúc này tăng số lượng phần tử của dãy con)
     */
    public static boolean caterpillarMethod(int[] a, int s) {
        int front = 0;
        int currSum = 0;
        for (int back = 0; back < a.length; back++) {
            while (front < a.length && currSum + a[front] <= s) {
                currSum += a[front];
                System.out.printf("Checking slice: ");
                printSlice(a, back, front);
                if (currSum == s) {
                    System.out.printf("Found slice with sum = %d: ", s);
                    printSlice(a, back, front);
                    return true;
                }
                front++;
            }
            currSum -= a[back];
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(caterpillarMethod(new int[] {6, 2, 7, 4, 1, 3, 6}, 12)); // true
        System.out.println(caterpillarMethod(new int[] {6, 2, 7, 4, 1, 3, 6}, 16)); // false
        System.out.println(caterpillarMethod(new int[] {20, 1, 30, 2, 7, 4, 1, 3, 6}, 16)); // false
        System.out.println(caterpillarMethod(new int[] {20, 1, 30, 2, 7, 4, 1, 3, 6}, 12)); // true
    }
}
