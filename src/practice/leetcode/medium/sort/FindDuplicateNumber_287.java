package practice.leetcode.medium.sort;

/**
 * https://leetcode.com/problems/find-the-duplicate-number/
 * 
 * Simple idea: counting element then check
 * - Dùng 1 vòng for để count element, lưu vào mảng cntArr
 * - Sau đó duyệt mảng cntArr để check duplicate: nếu cntArr[k] > 1 => k là duplicate
 * 
 * Cách này đơn giản nhưng phải duyệt 2 lần và tốn thêm bộ nhớ cntArr
 * 
 * Better solution: cyclic sort
 */
public class FindDuplicateNumber_287 {
    /**
     * Idea: cyclic sort. For easy implement, we will reduce all element by 1, because the constraint of
     * problem is: 1 <= nums[i] <= n.
     * But it is easier if 0 <= nums[i] <= n-1
     * 
     * Runtime 4 ms Beats 88.3%
     * Memory 56.1 MB Beats 96.84%
     * 
     * Maybe because of reducing each element by 1, so it takes more time
     */
    public int findDuplicate(int[] a) {
        // Reduce each element by 1, for easily cycle sorting
        // Later will return a[k]+1 (we will find a[k], it is the duplicate)
        for (int i = 0; i < a.length; i++) {
            a[i]--;
        }

        // cyclic sort, a[0] = 0, a[i] = i, whereas i = 0 -> a.len-1
        int temp;
        int i = 0;
        while (i < a.length) {
            // because we're using cyclic sort, so a[i] MUST >= i
            // If not, then that's the duplicate
            // Ex: [0 1 2 3 4 5 2 7 6], đang sort tới đoạn i = 6, lúc này, các vị trí trc đó đã đc sort hết rồi,
            // nhưng a[i] = 2 < i thì chắc chắn số này là duplicate
            if (a[i] < i)
                return a[i] + 1;
            if (a[i] != a.length && a[i] != i) {
                // Ex: [0 2 2 3 4 1], tại i = 1. Nếu cứ swap a[i] và a[a[i]] thì sẽ lặp vô hạn, do đó phải check nếu
                // duplicate thì return luôn
                if (a[i] == a[a[i]])
                    return a[i] + 1;

                // swap a[i] vs a[a[i]]
                temp = a[a[i]];
                a[a[i]] = a[i];
                a[i] = temp;
            } else {
                i++;
            }
        }

        return -1; // Never happen, because the problem always exists a duplicate
    }

    public static void main(String[] args) {
        FindDuplicateNumber_287 app = new FindDuplicateNumber_287();
        System.out.println(app.findDuplicate(new int[] {1, 3, 4, 2, 2})); // 2
        System.out.println(app.findDuplicate(new int[] {3, 1, 3, 4, 2})); // 3
        System.out.println(app.findDuplicate(new int[] {1, 3, 5, 3, 4, 2})); // 3
    }
}
