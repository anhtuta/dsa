package practice.codility.lessons.l4;

import java.util.HashSet;
import java.util.Set;

/**
 * A small frog wants to get to the other side of a river. The frog is initially located on one bank
 * of the river (position 0) and wants to get to the opposite bank (position X+1). Leaves fall from
 * a tree onto the surface of the river.
 * 
 * You are given an array A consisting of N integers representing the falling leaves. A[K]
 * represents the position where one leaf falls at time K, measured in seconds.
 * 
 * The goal is to find the earliest time when the frog can jump to the other side of the river. The
 * frog can cross only when leaves appear at every position across the river from 1 to X (that is,
 * we want to find the earliest moment when all the positions from 1 to X are covered by leaves).
 * You may assume that the speed of the current in the river is negligibly small, i.e. the leaves do
 * not change their positions once they fall in the river.
 * 
 * For example, you are given integer X = 5 and array A such that:
 * 
 * A[0] = 1
 * A[1] = 3
 * A[2] = 1
 * A[3] = 4
 * A[4] = 2
 * A[5] = 3
 * A[6] = 5
 * A[7] = 4
 * In second 6, a leaf falls into position 5. This is the earliest time when leaves appear in every
 * position across the river.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int X, int[] A); }
 * 
 * that, given a non-empty array A consisting of N integers and integer X, returns the earliest time
 * when the frog can jump to the other side of the river.
 * 
 * If the frog is never able to jump to the other side of the river, the function should return −1.
 * 
 * For example, given X = 5 and array A such that:
 * 
 * A[0] = 1
 * A[1] = 3
 * A[2] = 1
 * A[3] = 4
 * A[4] = 2
 * A[5] = 3
 * A[6] = 5
 * A[7] = 4
 * the function should return 6, as explained above.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N and X are integers within the range [1..100,000];
 * - each element of array A is an integer within the range [1..X].
 * 
 * =====
 * Giải thích:
 * - frog muốn nhảy từ bờ sông bên này sang bờ bên kia
 * - bờ bên này vị trí 0, bên kia bờ là x+1, coi chiều rộng sông là 1 mảng b[0...x+1]
 * - frog cần nhảy từ vị trí b[1] -> b[x] là qua được sông
 * - Cần phải có lá ở TẤT CẢ các vị trí b[1] -> b[x]
 * - Input có thêm mảng a[i]: tại giây thứ i có lá rơi tại a[i] (1 <= a[i] <= x)
 * - VD: a[3] = 4, tức là tại giây thứ 3, có lá rơi tại vị trí 4 (b[4])
 * - Cần tìm thời gian ngắn nhất để lá phủ hết từ b[1] -> b[x]
 */
public class FrogRiverOne {
    /**
     * Dùng HashSet để lưu các phần tử b[i] (init cho nó chứa toàn bộ các b[i], i = [1, x]).
     * Lá rơi tại b[i] thì remove nó khỏi set
     * Tới khi remove hết (lá lấp đầy từ b[1] -> b[x]), thì return time
     */
    public int solution(int x, int[] a) {
        Set<Integer> set = new HashSet<>();
        for (int i = 1; i <= x; i++) {
            set.add(i);
        }

        for (int i = 0; i < a.length; i++) {
            set.remove(a[i]);
            if (set.isEmpty())
                return i;
        }
        return -1;
    }

    /**
     * Giống như cách dùng Set, nhưng ta sẽ dùng mảng và biến size để thay thế
     * (Vì dùng Set là thư viện của Java rồi)
     * Mảng b[] được init = 0, nếu b[i] > 0 là lá đã rơi tại b[i].
     * Cần tìm time sao cho toàn bộ b[1] -> b[x] đều > 0.
     * Cách này đúng là dùng kỹ thuật counting như trong file pdf
     */
    public int solution_useArray(int x, int[] a) {
        int[] b = new int[x + 1];
        int fallenLeaf = 0; // số vị trí lá đã rơi, nó = x là thời gian cần return
        for (int i = 0; i < a.length; i++) {
            // Nếu b[a[i]] > 0, tức là có lá ở vị trí này rồi, bỏ qua
            if (b[a[i]] == 0) {
                b[a[i]]++;  // lá rơi tại b[k], k = a[i], 1 <= k <= x
                fallenLeaf++;
                if (fallenLeaf == x)
                    return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int x = 5;
        int[] a = {1, 3, 1, 4, 2, 3, 5, 4};
        System.out.println(new FrogRiverOne().solution(x, a)); // 6
        int[] a2 = {1, 3, 1, 5, 2, 3, 4, 4};
        System.out.println(new FrogRiverOne().solution_useArray(x, a2)); // 6
    }
}
