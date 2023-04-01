package practice.codility.lessons.l5;

/**
 * This problem is in pdf file, please open it for more detail (include an illustration image)
 * 
 * =====
 * Giải thích: có 1 mảng a[n], đại diện cho n ô liên tiếp nhau trên đường. Mỗi ô i có số
 * lượng nấm = a[i]. 1 <= n <= 100_000
 * Cho 2 số k, m (0 <= k,m < n): người hái nấm đứng ở ô k và sẽ di chuyển m lần, mỗi lần qua 1
 * ô và hái số nấm ở ô đó
 * Tính số lượng nấm lớn nhất có thể
 * 
 * =====
 * The best strategy is to move in one direction optionally followed by some moves in the opposite
 * direction. In other words, the mushroom picker should not change direction more than once.
 * 
 * Chiến lược tốt nhất là chỉ chuyển hướng nhiều nhất 1 lần, hoặc chỉ đi về 1 hướng
 */
public class MushroomPicker {
    /**
     * Tính toán mảng tổng của prefix từ mảng input a[].
     * Ex: p[i] = sum(a0, a1, ..., ai), trong đó 0 <= i < a.length, tức là p[i] = tổng của i+1 phần tử
     * đầu tiên của mảng a
     * Để ý rằng: p[i] = p[i-1] + a[i];
     * 
     * Lúc này, nếu ký hiệu s(i,j) = tổng của dãy con a[i] -> a[j], (0 <= i < j < n), thì:
     * s(i, j) = p[j] - p[i-1]
     * 
     * Note: trong file pdf thì nó dùng p[i], 0 <= i <= a.length, là tổng của i phần tử đầu tiên của a,
     * tức là p[i] = a[0] + a[1] + ... + a[i-1]
     */
    private int[] getPrefixSums(int[] a) {
        int[] p = new int[a.length];
        p[0] = a[0];
        for (int i = 1; i < a.length; i++) {
            p[i] = p[i - 1] + a[i];
        }
        return p;
    }

    /**
     * Tính tổng của mảng con của mảng a[]: từ vị trí a[left] -> a[right]
     * 
     * @param p mảng prefixSum tính toán từ mảng a
     * @param left mảng con bắt đầu từ a[left]
     * @param right mảng con kết thúc tại a[right]
     */
    private int getSliceSum(int[] p, int left, int right) {
        // System.out.printf("left = %d, right = %d%n", left, right);
        if (left == 0)
            return p[right];
        return p[right] - p[left - 1];
    }

    private void printArr(int[] a) {
        System.out.print("Array [");
        for (int i = 0; i < a.length - 1; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println(a[a.length - 1] + "]");
    }

    /**
     * Đầu tiên tính mảng prefix sum p
     * Ta sẽ đi sang trái x ô, rồi tại đó, sang phải m-x ô, tính toán tổng số nấm hái được =
     * totalMushroom
     * Duyệt để x chạy từ 0 -> m, tìm giá trị totalMushroom lớn nhất trong các lần duyệt
     */
    public int mushrooms(int[] a, int k, int m) {
        int[] p = getPrefixSums(a);
        printArr(p);
        int max = -1;
        int left, right;
        int totalMushroom; // tổng só nấm ở các ô từ a[left] -> a[right]
        int stepToRight; // đi sang phải stepToRight ô, kể từ vị trí k (sau khi đã đi sang trái)

        for (int i = 0; i <= k && i <= m; i++) {
            // di chuyển sang trái i ô, sau đó (tại vị trí hiện tại chứ ko phải tại k nữa),
            // di chuyển sang phải m-i ô
            // chú ý: nếu giả sử k = 4, thì chỉ được di chuyển sang trái tối đa 4 ô, sau đó phải di chuyển sang
            // phải
            left = k - i;
            stepToRight = m - 2 * i; // 2*i = quay lại vị trí ban đầu đứng
            if (stepToRight == -1) {
                // số lượt đi còn lại ko đủ để quay lại ô k, do đó totalMushroom = a[left] -> a[k]
                stepToRight = 0;
            }
            right = Math.min(k + stepToRight, a.length - 1);
            // right = Math.min(left + (m - (k - i)), a.length - 1);
            totalMushroom = getSliceSum(p, left, right);
            if (max < totalMushroom)
                max = totalMushroom;
        }
        return max;
    }

    public static void main(String[] args) {
        int[] a = {2, 3, 7, 5, 1, 3, 9};
        System.out.println(new MushroomPicker().mushrooms(a, 4, 6)); // 25
    }
}
