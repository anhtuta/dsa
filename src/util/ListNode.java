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

    /**
     * Print the whole list, from head to tail
     */
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

    /**
     * Print list with arrow between current and next
     */
    public void printArrow() {
        ListNode curr = this;
        System.out.print("List [");
        while (curr != null) {
            System.out.print(curr.val);
            curr = curr.next;
            if (curr != null) {
                System.out.print(" -> ");
            }
        }
        System.out.println("]");
    }

    /**
     * Only return current node
     */
    @Override
    public String toString() {
        return String.format("ListNode [val = %d]", val);
    }
}
