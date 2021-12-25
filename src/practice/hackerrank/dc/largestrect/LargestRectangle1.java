package practice.hackerrank.dc.largestrect;

import java.util.Arrays;
import java.util.List;

/**
 * Problem: https://www.hackerrank.com/challenges/largest-rectangle/problem
 * (Medium)
 * 
 * Hiểu đơn giản như này: có n tòa nhà liền kề nhau với width = nhau và = 1. Chiều cao các
 * tòa nhà lưu trong mảng h[i], với 1 <= h[i] <= n. Tính diện tích hình chữ nhật lớn nhất tạo bởi
 * các tòa nhà liên tiếp nhau.
 * Một số trang web khác có cách diễn đạt khác bằng cách thay tòa nhà = cột bar trong 1 biểu đồ
 * (histogram) (xem hình vẽ)
 * 
 * Cách làm: Trên hackerrank thì bài này được xếp trong mục Stack, tức là có thể chỉ cần dùng stack
 * để giải. Nhưng mình thì nghĩ mãi chả biết cách dùng stack như nào nên nghĩ tới chia để trị
 * 
 * Cụ thể: bài này giống bài dãy con lớn nhất: chia đôi dãy mid = (left+right)/2, ta thu được 2 dãy,
 * gọi là dãy trái và dãy phải, thì diện tích HCN lớn nhất sẽ là 1 dãy rơi vào 1 trong 3 case sau:
 * - Dãy trái
 * - Dãy phải
 * - Bắt đầu từ nửa trái và kết thúc ở nửa phải (gọi là Dãy mid)
 * Hoàn toàn chính xác. Ta tính Dãy trái, Dãy phải = đệ quy.
 * 
 * Tuy nhiên vấn đề lớn nhất ở đây là tính dãy mid cực khó. Trong bài toán dãy con lớn nhất,
 * dãy mid = tổng của 2 dãy:
 * - Dãy 1 bắt đầu từ nửa trái và kết thúc ở mid (1)
 * - Dãy 2 bắt đầu từ mid+1 và kết thúc ở nửa phải (2)
 * Nhưng trong bài toán này thì ko thể tính được dãy mid theo cách ý, lý do là diện tích 2 HCN (1)
 * và (2) có thể có chiều cao KHÁC nhau, nên khi ghép lại sẽ ko thành 1 HCN được!
 * 
 * Cách giải quyết (xem file2): chỉ cần thay đổi cách tính mid chút xíu đó là ta sẽ chọn mid là phần
 * tử nhỏ nhất của dãy, khi này dãy các tòa nhà (1) và (2) chắc chắn có cùng chiều cao!
 * 
 * Link ref: https://www.geeksforgeeks.org/largest-rectangular-area-in-a-histogram-set-1/
 * Việc tìm tòa nhà thấp nhất = linear search có thể dẫn tới worst case là O(n^2) giống trường hợp
 * của quicksort (xem link trên)
 * 
 * @author tatu
 *
 */
public class LargestRectangle1 {

    public static long max(long a, long b) {
        return a > b ? a : b;
    }

    public static long max(long a, long b, long c) {
        return a > b ? a : (b > c ? b : c);
    }

    public static long largestRectangle(List<Integer> h) {
        int[] arr = new int[h.size()];
        for (int i = 0; i < h.size(); i++) {
            arr[i] = h.get(i);
        }
        return largestRect(arr, 0, arr.length - 1);
    }

    private static long largestRect(int[] arr, int left, int right) {
        if (left == right)
            return arr[left];
        int mid = (left + right) / 2;

        // Cách tính Dãy mid là SAI HOÀN TOÀN
        long largestRectMid =
                largestRectLeft(arr, left, mid) + largestRectRight(arr, mid + 1, right);

        return max(largestRect(arr, left, mid), largestRect(arr, mid + 1, right), largestRectMid);
    }

    private static long largestRectLeft(int[] arr, int left, int right) {
        long area = -1, maxArea = -1;
        long minHeight = Long.MAX_VALUE;
        for (int i = right; i >= left; i--) {
            if (arr[i] < minHeight) {
                minHeight = arr[i];
                area = minHeight * (right - i + 1);
                maxArea = max(maxArea, area);
            }
        }
        return maxArea;
    }

    private static long largestRectRight(int[] arr, int left, int right) {
        long area = -1, maxArea = -1;
        long minHeight = Long.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            if (arr[i] < minHeight) {
                minHeight = arr[i];
                area = minHeight * (i - left + 1);
                maxArea = max(maxArea, area);
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        List<Integer> h = Arrays.asList(1, 3, 5, 9, 11);
        System.out.println(LargestRectangle1.largestRectangle(h));
    }
}
