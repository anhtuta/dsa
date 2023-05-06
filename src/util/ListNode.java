package util;

/**
 * Data structure Node thường dùng cho các bài toán về linked list bên leetcode
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {}

    public ListNode(int[] a) {
        val = a[0];
        ListNode curr = this;
        ListNode next;
        for (int i = 1; i < a.length; i++) {
            next = new ListNode(a[i]);
            curr.next = next;
            curr = curr.next;
        }
    }

    public ListNode(int x) {
        val = x;
        next = null;
    }

    public void print() {
        System.out.print("ListNode [");
        ListNode curr = this;
        while (curr != null) {
            System.out.print(curr.val);
            if (curr.next != null) {
                System.out.print(", ");
            }
            curr = curr.next;
        }
        System.out.println("]");
    }
}
