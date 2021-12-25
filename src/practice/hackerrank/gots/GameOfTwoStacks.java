package practice.hackerrank.gots;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem: https://www.hackerrank.com/challenges/game-of-two-stacks/problem
 * (Medium)
 * 
 * Đề bài dịch đơn giản như này: có 2 stack a[n], b[m], top stack ở index = 0.
 * Mỗi lần Nick (player) có thể lấy 1 số ở 1 trong 2 stack bất kỳ.
 * Tính số lượng lớn nhất các số mà Nick có thể lấy được sao cho tổng các số đó <= maxSum
 * (choose the maximum number of elements from each stack so that their sum is no greater than
 * maxSum)
 * 
 * Ex1:
 * Input:
 * a = [1,2,3,4,5]
 * b = [6,7,8,9]
 * maxSum = 10
 * 
 * Output: 4 (lấy 4 số 1,2,3,4)
 * 
 * Ex2:
 * Input:
 * a = [4 2 4 6 1]
 * b = [2 1 8 5]
 * maxSum = 10
 * 
 * Output: 4 (lấy các số 4,2,2,1)
 * 
 * @author tatu
 *
 */
public class GameOfTwoStacks {

    /*
     * Complete the 'twoStacks' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     * 1. INTEGER maxSum
     * 2. INTEGER_ARRAY a
     * 3. INTEGER_ARRAY b
     * Cach lam: từ bức hình minh họa có thể hình dung ngay tới giải thuật greedy: duyệt 2 stack
     * cùng lúc, tại mỗi vòng duyệt, check xem giá trị top của stack nào bé hơn thì lấy phần tử đó.
     * Cách duyệt giống với merge 2 array trong MergeSort
     * 
     * Nếu như nhìn vào hình mà nghĩ ra cách này luôn thì xin chúc mừng, bạn đã bị lừa thành công
     * =))
     * Note: A greedy strategy to pick the smaller element from the top elements of A and B is
     * giving me wrong answer for many test cases, ex:
     * a = [17,1,1,1,8] and b = [8,8,4,5,9] and maxSum = 20
     * Greedy strategy will return answer = 3 (8,4,4). But the correct answer is 4 (17,1,1,1)
     */
    public static int twoStacks_greedy_wrong(int maxSum, List<Integer> a, List<Integer> b) {
        int cnt = 0;    // so luong phan tu da lay khoi ca 2 stack
        int sum = 0;    // tong cac phan tu da lay khoi ca 2 stack
        int i = 0, j = 0;   // index of a and b
        while (i < a.size() && j < b.size()) {
            if (a.get(i) < b.get(j))
                sum += a.get(i++);
            else
                sum += b.get(j++);
            if (sum > maxSum)
                return cnt;
            else
                cnt++;
        }

        while (i < a.size()) {
            sum += a.get(i++);
            if (sum > maxSum)
                return cnt;
            else
                cnt++;
        }

        while (j < b.size()) {
            sum += b.get(j++);
            if (sum > maxSum)
                return cnt;
            else
                cnt++;
        }

        return cnt;
    }

    /*
     * Đầu tiên coi như chỉ có 1 stack, ta lấy từng element của stack đầu tiên sao cho sum <=
     * maxSum. Sau đó dùng stack2: lần lượt lấy từng element của stack 2 vào, mỗi lần lấy check xem
     * nếu sum > maxSum thì remove từng phần tử đã lấy từ stack1 trước đó cho tới khi sum <= maxSum.
     * Lúc này check xem lấy được max là bao nhiêu phần tử rồi
     */
    public static int twoStacks(int maxSum, List<Integer> a, List<Integer> b) {
        int cnt = 0, maxCnt = 0;
        int sum = 0;
        int i = -1, j = 0;

        // firstly, only considering one stack and calculating the sum and count number
        // Note: Phai dung "i + 1" nhe, neu ko se bi loi Runtime Error (Array index out of bound!)
        // Phai mua testcase moi tim ra duoc loi nay!
        while (i + 1 < a.size()) {
            // if we can pick the NEXT element, just do it!
            if (sum + a.get(i + 1) <= maxSum) {
                i++;    // i now is the index of recent element we just pick
                cnt++;  // total element now contains element in index i
                sum += a.get(i);
            } else
                break;
        }
        maxCnt = cnt;

        // next, add one by one, each element from second stack, at the same time, calculate the sum
        // and comparing it with maxSum. If the sum > maxSum, we subract the last added element of
        // first stack from sum.
        while (j < b.size()) {
            sum += b.get(j++);
            cnt++;

            while (sum > maxSum && i >= 0) {
                sum -= a.get(i--);
                cnt--;
            }

            if (sum <= maxSum && cnt > maxCnt)
                maxCnt = cnt;
        }

        return maxCnt;
    }

    public static void main(String[] args) throws IOException {
        // change this according to your local machine
        String currFolder = System.getProperty("user.dir") + "/src/practice/hackerrank/gots/";
        BufferedReader bufferedReader =
                new BufferedReader(new FileReader(currFolder + "input.txt"));
        BufferedWriter bufferedWriter =
                new BufferedWriter(new FileWriter(currFolder + "output.txt"));

        int g = Integer.parseInt(bufferedReader.readLine().trim());

        for (int gItr = 0; gItr < g; gItr++) {
            String[] firstMultipleInput =
                    bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            int n = Integer.parseInt(firstMultipleInput[0]);

            int m = Integer.parseInt(firstMultipleInput[1]);

            int maxSum = Integer.parseInt(firstMultipleInput[2]);

            String[] aTemp = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> a = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int aItem = Integer.parseInt(aTemp[i]);
                a.add(aItem);
            }

            String[] bTemp = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> b = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                int bItem = Integer.parseInt(bTemp[i]);
                b.add(bItem);
            }

            int result = GameOfTwoStacks.twoStacks(maxSum, a, b);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
        // List<Integer> a = Arrays.asList(4, 2, 4, 6, 1);
        // List<Integer> b = Arrays.asList(2, 1, 8, 5);
        // int maxSum = 10;
        // System.out.println(GameOfTwoStacks.twoStacks(maxSum, a, b));
        //
        // List<Integer> a2 = Arrays.asList(17, 1, 1, 1, 8);
        // List<Integer> b2 = Arrays.asList(8, 8, 4, 5, 9);
        // int maxSum2 = 20;
        // System.out.println(GameOfTwoStacks.twoStacks(maxSum2, a2, b2));
    }
}


