package practice.hackerrank.countingsort;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Problem: https://www.hackerrank.com/challenges/migratory-birds/problem
 * (Easy)
 * 
 * Given an array of bird sightings where every element represents a bird type id, determine the id
 * of the most frequently sighted type. If more than 1 type has been spotted that maximum amount,
 * return the smallest of their ids.
 * 
 * Example
 * arr = [1 4 4 4 5 3]
 * Tức là số lần nhìn thấy bird = arr.length = 6 lần. Mỗi element của mảng ám chỉ loại chim được
 * nhìn thấy, tức là đã nhìn thấy:
 * Loại 1: 1 lần
 * Loại 4: 3 lần
 * Loại 5: 1 lần
 * Loại 3: 1 lần
 * => Loại bird 4 được nhìn thấy nhiều nhất, return 4 (chứ ko phải return số lần là 3 nhá)
 * 
 * Function Description
 * 
 * Complete the migratoryBirds function in the editor below.
 * migratoryBirds has the following parameter(s):
 * int arr[n]: the types of birds sighted
 * 
 * Returns
 * int: the lowest type id of the most frequently sighted birds
 * 
 * Input Format
 * The first line contains an integer, n, the size of arr.
 * The second line describes arr as n space-separated integers, each a type number of the bird
 * sighted.
 * 
 * Constraints: 5 <= n <= 2*10^5
 * 
 * It is guaranteed that each type is 1, 2, 3, 4, or 5. (Tức là chỉ có tối đa 5 loại chim thôi)
 * 
 * Sample Input 0:
 * 6
 * 1 4 4 4 5 3
 * 
 * Sample Output 0:
 * 4
 * 
 * Sample Input 1:
 * 11
 * 1 2 3 4 5 4 3 2 1 3 4
 * 
 * Sample Output 1:
 * 3
 * 
 * @author anhtu
 *
 */
class MigratoryBirds {

    /*
     * Complete the 'migratoryBirds' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY arr as parameter.
     * 
     * Bai nay lam giong thuat toan counting sort
     * Gia su input = [1, 4, 4, 4, 5, 3]
     * Cach lam: cntArr = new int[6]
     * sau khi counting thi: cntArr = [0, 1, 0, 1, 3, 1]
     */
    public static int migratoryBirds(List<Integer> arr) {
        final int MAX_BIRD_TYPE = 5;    // de bai noi nhu vay
        int[] cntArr = new int[MAX_BIRD_TYPE + 1];
        for (int i = 0; i < arr.size(); i++) {
            cntArr[arr.get(i)]++;
        }

        // return index of max element, because value of element is the counting of number of that
        // bird type, and index of that element is bird type
        int max = cntArr[0];
        int maxIndex = 0;
        for (int i = 1; i < cntArr.length; i++) {
            if (cntArr[i] > max) {
                max = cntArr[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static void main(String[] args) throws IOException {
        // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        // BufferedWriter bufferedWriter =
        // new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        //
        // int arrCount = Integer.parseInt(bufferedReader.readLine().trim());
        //
        // List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split("
        // "))
        // .map(Integer::parseInt).collect(toList());

        List<Integer> arr = Arrays.asList(1, 4, 4, 4, 5, 3);
        int result = MigratoryBirds.migratoryBirds(arr);
        System.out.println(result);

        // bufferedWriter.write(String.valueOf(result));
        // bufferedWriter.newLine();
        //
        // bufferedReader.close();
        // bufferedWriter.close();
    }
}

