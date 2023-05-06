package practice.leetcode.easy;

import util.ListNode;

/**
 * https://leetcode.com/problems/merge-two-sorted-lists/description/
 * 
 * You are given the heads of two sorted linked lists list1 and list2.
 * 
 * Merge the two lists in a one sorted list. The list should be made by splicing together the nodes
 * of the first two lists.
 * 
 * Return the head of the merged linked list.
 */
public class MergeTwoSortedLists_21 {
    /**
     * Làm giống như merge sort, dùng Two pointer
     * 
     * Result: accepted
     * Runtime: 0 ms
     * Memory: 42.4 MB, Beats: 28.56%
     * 
     * Cần tối ưu bộ nhớ
     */
    public ListNode mergeTwoLists_highMemory(ListNode list1, ListNode list2) {
        if (list1 == null)
            return list2;
        if (list2 == null)
            return list1;

        ListNode res = new ListNode();
        ListNode curr, next; // temp
        ListNode curr1 = list1, curr2 = list2;

        // Init res, so it will NOT be null
        if (curr1.val < curr2.val) {
            res.val = curr1.val;
            curr1 = curr1.next;
        } else {
            res.val = curr2.val;
            curr2 = curr2.next;
        }

        curr = res;

        // Compare and merge two list into res, until one of them is null
        while (curr1 != null && curr2 != null) {
            next = new ListNode();
            if (curr1.val < curr2.val) {
                next.val = curr1.val;
                curr1 = curr1.next;
            } else {
                next.val = curr2.val;
                curr2 = curr2.next;
            }
            curr.next = next;
            curr = curr.next;
        }

        // Merge the rest of none-empty list into res
        while (curr1 != null) {
            next = new ListNode();
            next.val = curr1.val;
            curr1 = curr1.next;
            curr.next = next;
            curr = curr.next;
        }
        while (curr2 != null) {
            next = new ListNode();
            next.val = curr2.val;
            curr2 = curr2.next;
            curr.next = next;
            curr = curr.next;
        }

        return res;
    }

    /**
     * Nhận thấy ko cần con trỏ next làm gì, dùng luôn curr.next là được. Dùng luôn cả list1, list2 nữa,
     * mặc dù cách này thay đổi giá trị của param truyền vào, nhưng kệ, tối ưu hơn là được
     * (Thực ra dùng curr1, curr2 ko tệ hơn dùng trực tiếp list1, list2 đâu. Việc bỏ next mới tối ưu)
     * 
     * Result: accepted
     * Runtime: 0 ms
     * Memory: 42 MB, Beats: 83.67%
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null)
            return list2;
        if (list2 == null)
            return list1;

        ListNode res = new ListNode();
        ListNode curr; // temp

        // Init res, so it will NOT be null
        if (list1.val < list2.val) {
            res.val = list1.val;
            list1 = list1.next;
        } else {
            res.val = list2.val;
            list2 = list2.next;
        }

        curr = res;

        // Compare and merge two list into res, until one of them is null
        while (list1 != null && list2 != null) {
            curr.next = new ListNode();
            if (list1.val < list2.val) {
                curr.next.val = list1.val;
                list1 = list1.next;
            } else {
                curr.next.val = list2.val;
                list2 = list2.next;
            }
            curr = curr.next;
        }

        // Merge the rest of none-empty list into res
        while (list1 != null) {
            curr.next = new ListNode();
            curr.next.val = list1.val;
            list1 = list1.next;
            curr = curr.next;
        }
        while (list2 != null) {
            curr.next = new ListNode();
            curr.next.val = list2.val;
            list2 = list2.next;
            curr = curr.next;
        }

        return res;
    }

    public static void main(String[] args) {
        MergeTwoSortedLists_21 app = new MergeTwoSortedLists_21();
        ListNode list1 = new ListNode(new int[] {1, 2, 4});
        ListNode list2 = new ListNode(new int[] {1, 3, 4});
        list1.print();
        list2.print();
        ListNode res = app.mergeTwoLists(list1, list2);
        res.print(); // [1,1,2,3,4,4]
    }
}
