package practice.codility.lessons.l13;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * The Fibonacci sequence is defined using the following recursive formula:
 * 
 * F(0) = 0
 * F(1) = 1
 * F(M) = F(M - 1) + F(M - 2) if M >= 2
 * A small frog wants to get to the other side of a river. The frog is initially located at one bank
 * of the river (position −1) and wants to get to the other bank (position N). The frog can jump
 * over any distance F(K), where F(K) is the K-th Fibonacci number. Luckily, there are many leaves
 * on the river, and the frog can jump between the leaves, but only in the direction of the bank at
 * position N.
 * 
 * The leaves on the river are represented in an array A consisting of N integers. Consecutive
 * elements of array A represent consecutive positions from 0 to N − 1 on the river. Array A
 * contains only 0s and/or 1s:
 * 
 * 0 represents a position without a leaf;
 * 1 represents a position containing a leaf.
 * The goal is to count the minimum number of jumps in which the frog can get to the other side of
 * the river (from position −1 to position N). The frog can jump between positions −1 and N (the
 * banks of the river) and every position containing a leaf.
 * 
 * For example, consider array A such that:
 * 
 * A[0] = 0
 * A[1] = 0
 * A[2] = 0
 * A[3] = 1
 * A[4] = 1
 * A[5] = 0
 * A[6] = 1
 * A[7] = 0
 * A[8] = 0
 * A[9] = 0
 * A[10] = 0
 * The frog can make three jumps of length F(5) = 5, F(3) = 2 and F(5) = 5.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given an array A consisting of N integers, returns the minimum number of jumps by which the
 * frog can get to the other side of the river. If the frog cannot reach the other side of the
 * river, the function should return −1.
 * 
 * For example, given:
 * 
 * A[0] = 0
 * A[1] = 0
 * A[2] = 0
 * A[3] = 1
 * A[4] = 1
 * A[5] = 0
 * A[6] = 1
 * A[7] = 0
 * A[8] = 0
 * A[9] = 0
 * A[10] = 0
 * the function should return 3, as explained above.
 * 
 * Write an efficient algorithm for the following assumptions:
 * 
 * - N is an integer within the range [0..100,000];
 * - each element of array A is an integer that can have one of the following values: 0, 1.
 * 
 * =====
 * Chú ý: coi như có thêm 2 phần tử nữa ở 2 đầu mảng:
 * - Bờ sông bên này: A[-1] = 1
 * - Bờ sông bên kia: A[N] = 1
 * Frog sẽ nhảy từ A[-1] -> A[N], mỗi lần nhảy chỉ nhảy được số bước = với 1 số trong dãy
 * Fibonacci. Cần tìm số bước nhảy tối thiểu
 */
public class FibFrog {

    private int minStep = Integer.MAX_VALUE;
    private boolean reachedOtherBank = false;

    private boolean isValidPosition(int[] a, int position) {
        return (position < a.length && a[position] == 1) || position == a.length;
    }

    private void printArray(int[] a) {
        System.out.print("Array [");
        for (int i = 0; i < a.length - 1; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println(a[a.length - 1] + "]");
    }

    /**
     * This solution using BFS, not done yet, wrong answer
     */
    private int solution_BFS(int[] a) {
        // Calculate Fib sequence first
        int[] F = new int[26]; // because F[26] = 121393 > maximum of N, so we only need first 25 Fib numbers
        F[0] = 0;
        F[1] = 1;
        for (int i = 2; i < F.length; i++) {
            F[i] = F[i - 1] + F[i - 2];
        }
        printArray(F);

        // Init queue
        Queue<Integer> queue = new ArrayBlockingQueue<>(26);
        queue.add(-1); // Frog bắt đầu từ bờ sông bên này

        // BFS
        int n = a.length;
        int currPos, nextPos; // vị trí hiện tại và tiếp theo của frog (là index của mảng a[])
        int totalStep = -1;
        int minStep = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            currPos = queue.remove();
            totalStep++;
            System.out.printf("currPos = %d, totalStep = %d%n", currPos, totalStep);
            if (currPos == n) {
                System.out.println("Reached other bank, totalStep = " + totalStep);
                if (totalStep < minStep)
                    minStep = totalStep;
            }

            // duyệt từ 2, vì F[0] = 0, F[1] = F[2] = 1, do đó skip F[1], F[1]
            // Nhét các vị trí tiếp theo có thể nhảy tới vào queue
            for (int i = 2; i < F.length; i++) {
                nextPos = currPos + F[i];
                if (nextPos > n)
                    break;
                if (isValidPosition(a, nextPos)) {
                    queue.add(nextPos);
                }
                // totalStep--;
            }
        }

        return minStep;
    }

    /**
     * This solution using DFS, nhưng reset được totalStep sau khi đến bờ bên kìa và quay lại đếm
     * solution khác
     */
    private int solution_DFS(int[] a) {
        // Calculate Fib sequence first
        int[] F = new int[26]; // because F[26] = 121393 > maximum of N, so we only need first 25 Fib numbers
        F[0] = 0;
        F[1] = 1;
        for (int i = 2; i < F.length; i++) {
            F[i] = F[i - 1] + F[i - 2];
        }
        printArray(F);

        // Init queue
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // Frog bắt đầu từ bờ sông bên này

        // DFS
        int n = a.length;
        int currPos, nextPos; // vị trí hiện tại và tiếp theo của frog (là index của mảng a[])
        int totalStep = -1;
        int minStep = Integer.MAX_VALUE;
        while (!stack.isEmpty()) {
            currPos = stack.pop();
            totalStep++;
            System.out.printf("currPos = %d, totalStep = %d%n", currPos, totalStep);
            if (currPos == n) {
                System.out.println("Reached other bank, totalStep = " + totalStep + "\n");
                if (totalStep < minStep)
                    minStep = totalStep;
                totalStep--;
            }

            // duyệt từ 2, vì F[0] = 0, F[1] = F[2] = 1, do đó skip F[1], F[1]
            // Nhét các vị trí tiếp theo có thể nhảy tới vào queue
            for (int i = 2; i < F.length; i++) {
                nextPos = currPos + F[i];
                if (nextPos > n)
                    break;
                if (isValidPosition(a, nextPos)) {
                    System.out.printf("Fib = %d, nextPos = %d%n", F[i], nextPos);
                    stack.push(nextPos);
                }
                // totalStep--;
            }
        }

        return minStep;
    }

    private int solution_recursion(int[] a) {
        // Calculate Fib sequence first
        int[] F = new int[26]; // because F[26] = 121393 > maximum of N, so we only need first 25 Fib numbers
        F[0] = 0;
        F[1] = 1;
        for (int i = 2; i < F.length; i++) {
            F[i] = F[i - 1] + F[i - 2];
        }
        // printArray(F);

        // recursion(a, F, minStep, minStep, minStep);
        // recursion_improve(a, F, a.length, -1, 0);
        recursion_improve2(a, F, a.length, -1, 0);
        return reachedOtherBank ? this.minStep : -1;
    }

    /**
     * Đệ quy vét cạn: tại mỗi vị trí, nhảy tới mọi vị trí có thể tiếp theo, tới khi đến bờ bên kia thì
     * đếm tổng số bước
     * 
     * Cách giải này bị timeout, chỉ được 41%
     */
    private void recursion(int[] a, int[] F, int n, int currPos, int totalStep) {
        // System.out.printf("currPos = %d, totalStep = %d%n", currPos, totalStep);
        if (currPos == n) {
            // System.out.println("Reached other bank, totalStep = " + totalStep + "\n");
            reachedOtherBank = true;
            if (totalStep < minStep)
                minStep = totalStep;
        }

        // duyệt từ 2, vì F[0] = 0, F[1] = F[2] = 1, do đó skip F[1], F[1]
        // Nhét các vị trí tiếp theo có thể nhảy tới vào queue
        for (int i = 2; i < F.length; i++) {
            int nextPos = currPos + F[i];
            if (nextPos > n)
                break;
            if (isValidPosition(a, nextPos)) {
                // System.out.printf("Fib = %d, nextPos = %d%n", F[i], nextPos);
                recursion(a, F, n, nextPos, totalStep + 1);
            }
        }
    }

    /**
     * Đệ quy vét cạn: tại mỗi vị trí, nhảy tới mọi vị trí có thể tiếp theo, tới khi đến bờ bên kia thì
     * đếm tổng số bước
     * Improve: tại mỗi vị trí tiếp theo, check nếu như totalStep > minStep thì KHÔNG nhảy nữa!
     */
    private void recursion_improve(int[] a, int[] F, int n, int currPos, int totalStep) {
        // System.out.printf("currPos = %d, totalStep = %d%n", currPos, totalStep);
        if (currPos == n) {
            // System.out.println("Reached other bank, totalStep = " + totalStep + "\n");
            reachedOtherBank = true;
            if (totalStep < minStep)
                minStep = totalStep;
        }

        // duyệt từ 2, vì F[0] = 0, F[1] = F[2] = 1, do đó skip F[1], F[1]
        // Nhét các vị trí tiếp theo có thể nhảy tới vào queue
        for (int i = 2; i < F.length; i++) {
            int nextPos = currPos + F[i];
            if (nextPos > n || totalStep + 1 >= minStep)
                break;
            if (isValidPosition(a, nextPos)) {
                // System.out.printf("Fib = %d, nextPos = %d%n", F[i], nextPos);
                recursion_improve(a, F, n, nextPos, totalStep + 1);
            }
        }
    }

    /**
     * Đệ quy vét cạn: tại mỗi vị trí, nhảy tới mọi vị trí có thể tiếp theo, tới khi đến bờ bên kia thì
     * đếm tổng số bước
     * Improve: tại mỗi vị trí tiếp theo, check nếu như totalStep > minStep thì KHÔNG nhảy nữa!
     * Improve2: for loop mảng Fib từ cuối dãy, để chọn các step lớn hơn trước
     * 
     * Timeout: score = 66%
     */
    private void recursion_improve2(int[] a, int[] F, int n, int currPos, int totalStep) {
        // System.out.printf("currPos = %d, totalStep = %d%n", currPos, totalStep);
        if (currPos == n) {
            // System.out.println("Reached other bank, totalStep = " + totalStep + "\n");
            reachedOtherBank = true;
            if (totalStep < minStep)
                minStep = totalStep;
        }

        if (totalStep >= minStep)
            return;

        // duyệt từ 2, vì F[0] = 0, F[1] = F[2] = 1, do đó skip F[1], F[1]
        // Nhét các vị trí tiếp theo có thể nhảy tới vào queue
        for (int i = F.length - 1; i >= 2; i--) {
            int nextPos = currPos + F[i];
            if (isValidPosition(a, nextPos)) {
                // System.out.printf("Fib = %d, nextPos = %d%n", F[i], nextPos);
                recursion_improve2(a, F, n, nextPos, totalStep + 1);
            }
        }
    }

    int res = -1;

    /**
     * Ý tưởng: cách đệ quy ở trên duyệt tất cả các bước tiếp theo của vị trí hiện tại. Cách này thì
     * duyệt từ cuối: tại vị trí i, tìm số bước ngắn nhất đến bờ bên kia (i = n-1 -> 0)
     * 
     * Sau đó, lưu giá trị này vào bảng minSteps[]
     * 
     * Cứ thể duyệt i, và tại mỗi nextStep, check xem bảng minSteps có giá trị chưa, nếu có rồi thì dùng
     * luôn, chưa có thì mới đệ quy
     * 
     * Hiện tại chưa xong!!! Vẫn đang debug dở
     */
    private int solution_recursion_DP(int[] a) {
        // Calculate Fib sequence first
        int[] F = new int[26]; // because F[26] = 121393 > maximum of N, so we only need first 25 Fib numbers
        F[0] = 0;
        F[1] = 1;
        for (int i = 2; i < F.length; i++) {
            F[i] = F[i - 1] + F[i - 2];
        }
        // printArray(F);

        int[] minSteps = new int[a.length];

        for (int i = a.length - 1; i >= 0; i--) {
            if (isValidPosition(a, i)) {
                this.minStep = Integer.MAX_VALUE;
                recursion_improve3(a, F, minSteps, i, i, 0);
                minSteps[i] = this.minStep;
                printArray(minSteps);
            }
        }

        System.out.println("i = -1");
        recursion_improve3(a, F, minSteps, -1, -1, 0);
        // return reachedOtherBank ? this.minStep : -1;
        // return this.minStep;
        return this.res;
    }

    private void recursion_improve3(int[] a, int[] F, int[] minSteps, int startIndex, int currPos, int totalStep) {
        System.out.printf("currPos = %d, totalStep = %d%n", currPos, totalStep);
        if (currPos == a.length) {
            System.out.println("Reached other bank, totalStep = " + totalStep + "\n");
            if (totalStep < this.minStep)
                this.minStep = totalStep;
            if (startIndex == -1) {
                reachedOtherBank = true;
                this.res = this.minStep;
            }
            System.out.printf("res = %d, this.minStep = %d%n", this.res, this.minStep);
            return;
        }

        // if (totalStep >= minStep)
        // return;

        if (currPos >= 0 && minSteps[currPos] > 0) {
            recursion_improve3(a, F, minSteps, startIndex, a.length, totalStep + minSteps[currPos]);
            return;
        }

        // duyệt từ 2, vì F[0] = 0, F[1] = F[2] = 1, do đó skip F[1], F[1]
        // Nhét các vị trí tiếp theo có thể nhảy tới vào queue
        for (int i = F.length - 1; i >= 2; i--) {
            int nextPos = currPos + F[i];
            if (isValidPosition(a, nextPos)) {
                System.out.printf("Fib = %d, nextPos = %d%n", F[i], nextPos);
                recursion_improve3(a, F, minSteps, startIndex, nextPos, totalStep + 1);
            }
        }
    }


    public int solution(int[] a) {
        return solution_recursion(a);
        // return solution_recursion_DP(a);
    }

    // 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1
    // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    public static void main(String[] args) {
        System.out.println(new FibFrog().solution(new int[] {0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0})); // 3
        System.out.println(new FibFrog().solution(new int[] {0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1})); // 3
        System.out.println(new FibFrog().solution(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})); // -1
    }
}
