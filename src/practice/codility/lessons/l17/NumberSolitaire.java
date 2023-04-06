package practice.codility.lessons.l17;

/**
 * A game for one player is played on a board consisting of N consecutive squares, numbered from 0
 * to N − 1. There is a number written on each square. A non-empty array A of N integers contains
 * the numbers written on the squares. Moreover, some squares can be marked during the game.
 * 
 * At the beginning of the game, there is a pebble (viên sỏi) on square number 0 and this is the
 * only square on the board which is marked. The goal of the game is to move the pebble to square
 * number N − 1.
 * 
 * (six-sided die: xúc sắc 6 mặt)
 * During each turn we throw a six-sided die, with numbers from 1 to 6 on its faces, and consider
 * the number K, which shows on the upper face after the die comes to rest. Then we move the pebble
 * standing on square number I to square number I + K, providing that square number I + K exists. If
 * square number I + K does not exist, we throw the die again until we obtain a valid move. Finally,
 * we mark square number I + K.
 * 
 * After the game finishes (when the pebble is standing on square number N − 1), we calculate the
 * result. The result of the game is the sum of the numbers written on all marked squares.
 * 
 * For example, given the following array:
 * 
 * A[0] = 1
 * A[1] = -2
 * A[2] = 0
 * A[3] = 9
 * A[4] = -1
 * A[5] = -2
 * one possible game could be as follows:
 * 
 * the pebble is on square number 0, which is marked;
 * we throw 3; the pebble moves from square number 0 to square number 3; we mark square number 3;
 * we throw 5; the pebble does not move, since there is no square number 8 on the board;
 * we throw 2; the pebble moves to square number 5; we mark this square and the game ends.
 * The marked squares are 0, 3 and 5, so the result of the game is 1 + 9 + (−2) = 8. This is the
 * maximal possible result that can be achieved on this board.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given a non-empty array A of N integers, returns the maximal result that can be achieved on
 * the board represented by array A.
 * 
 * For example, given the array
 * 
 * A[0] = 1
 * A[1] = -2
 * A[2] = 0
 * A[3] = 9
 * A[4] = -1
 * A[5] = -2
 * the function should return 8, as explained above.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N is an integer within the range [2..100,000];
 * - each element of array A is an integer within the range [−10,000..10,000].
 */
public class NumberSolitaire {
    /**
     * Dùng DP bottom-up để giải: ta sẽ tìm kiếm lời cho dãy con bắt đầu từ 0 và KẾT THÚC tại a[0],
     * a[1], ... , a[n-1]
     * Ref: https://github.com/Mickey0521/Codility/blob/master/NumberSolitaire.java
     */
    public int solution(int[] a) {
        int n = a.length;

        // Lời giải của bài toán cho dãy con bắt đầu từ 0 và kết thúc tại a[i] (i = [0, n-1])
        // (max[i] = tổng lớn nhất từ a[0] -> a[i])
        int[] max = new int[n];
        for (int i = 1; i < n; i++) {
            max[i] = Integer.MIN_VALUE;
        }

        // Hiển nhiên nếu dãy chỉ có a[0], thì kq bài toán = a[0]
        max[0] = a[0];

        // vị trí tiếp theo sẽ đi tới tính từ a[i] hiện tại, và tổng khi đi tới đó
        int next, currMax;
        for (int i = 0; i < n; i++) {
            for (int die = 1; die <= 6; die++) {
                // Tại mỗi vị trí tiếp theo có thể nhảy tới từ vị trí hiện tại, tính toán
                // tổng sau khi nhảy tới đó = currMax. Update max[next] dựa theo currMax
                next = i + die;
                if (next >= n)
                    break;
                currMax = max[i] + a[next];
                max[next] = Math.max(max[next], currMax);
            }
        }
        return max[n - 1];
    }

    public static void main(String[] args) {
        System.out.println(new NumberSolitaire().solution(new int[] {1, -2, 0, 9, -1, -2})); // 8
        System.out.println(new NumberSolitaire().solution(new int[] {1, -2, 0, 9, -1, -2, 3, -4, 1})); // 14
    }
}
