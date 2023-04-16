package practice.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import util.Utils;

public class MergeIntervals_56 {
    /**
     * Pattern: merge interval
     * Pattern này chả có gì đặc sắc, nó chính là việc merge 2 phần tử overlap lên nhau
     */
    public int[][] merge(int[][] intervals) {
        List<int[]> list = new ArrayList<>(intervals.length);

        // Sort theo phần tử đầu tiên của các mảng (là các phần tử của mảng intervals)
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] != o2[0])
                    return o1[0] - o2[0];
                else
                    return o1[1] - o2[1];
            }
        });

        // Phần tử A bắt đầu tại index 0
        int start = intervals[0][0];
        int end = intervals[0][1];
        int[] curr;

        // Duyệt phần tử B bắt đầu tại index 1
        for (int i = 1; i < intervals.length; i++) {
            curr = intervals[i];
            if (curr[0] <= end) {
                // Nếu như B overlap với A, gộp chúng lại = cách update end của A
                end = Math.max(end, curr[1]);
            } else {
                // Nếu như phần tử tiếp theo ko overlap, add phần tử A hiện tại vào res,
                // Sau đó update để A là phần tử hiện tại
                list.add(new int[] {start, end});
                start = curr[0];
                end = curr[1];
            }
        }

        // Add nốt phần tử A cuối cùng vào res
        list.add(new int[] {start, end});

        int[][] res = new int[list.size()][2];
        int i = 0;
        for (int[] ele : list) {
            res[i++] = ele;
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] intervals = {{2, 6}, {15, 18}, {1, 3}, {8, 10}};
        Utils.printArray(intervals);
        Utils.printArray(new MergeIntervals_56().merge(intervals));
    }
}
