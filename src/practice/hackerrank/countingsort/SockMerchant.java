package practice.hackerrank.countingsort;

import java.util.List;

/**
 * Problem: https://www.hackerrank.com/challenges/sock-merchant/problem
 * (Easy)
 * Tên gọi khác: Sales by Match
 * Có 1 đống tất nhiều màu khác nhau, check xem có thể tạo thành tối đa bao nhiêu cặp tất (mỗi cặp
 * gồm 2 chiếc tất cùng màu)
 * 
 * Input:
 * n: số lượng tất, 1 <= n <= 100
 * ar[i]: màu chiếc tất thứ i, 1 <= ar[i] <= 100
 * 
 * Cách làm: lại dùng counting sort và check, khá đơn giản
 * Nếu được dùng thư viện như Hash, Set thì có thể nhanh hơn (ko dùng counting sort nữa)
 * 
 * @author tatu
 */
public class SockMerchant {

    /**
     * Cách này dễ tư duy nhất: đầu tiên đếm xem mỗi chiếc tất màu ar[i] xuất hiện bao nhiêu lần,
     * việc đếm đó dùng 1 mảng cntArray (giống thuật toán countingSort).
     * Sau đó duyệt mảng cntArray và đến số cặp có thể tạo thành
     */
    public static int sockMerchant_easyWay(int n, List<Integer> ar) {

        // counting sort first
        int[] cntArray = new int[101]; // because ar[i] <= 100
        ar.stream().forEach(item -> {
            cntArray[item]++;
        });

        // calculate pair
        int cnt = 0;
        for (int i = 0; i < cntArray.length; i++) {
            cnt += cntArray[i] / 2;
        }

        return cnt;
    }

    /**
     * Cách này tối ưu hơn cách trên ở chỗ: chỉ cần dùng 1 lần duyệt
     * (vừa countingSort vừa count pair)
     */
    public static int sockMerchant(int n, List<Integer> ar) {
        int[] cntArray = new int[101];  // because ar[i] <= 100
        int cntPairs = 0;
        for (int i = 0; i < ar.size(); i++) {
            int color = ar.get(i);
            if (cntArray[color] == 0)
                cntArray[color]++;
            else {
                cntArray[color] = 0;
                cntPairs++;
            }
        }

        return cntPairs;
    }
}
