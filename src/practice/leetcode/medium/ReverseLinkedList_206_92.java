package practice.leetcode.medium;

import util.ListNode;

/**
 * Class này làm chung cho 2 bài sau:
 * 206. https://leetcode.com/problems/reverse-linked-list/ (Easy)
 * 92. https://leetcode.com/problems/reverse-linked-list-ii/ (Medium)
 */
public class ReverseLinkedList_206_92 {
    private void printList(ListNode head) {
        ListNode curr = head;
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
     * 206. https://leetcode.com/problems/reverse-linked-list/
     * Ref: https://emre.me/coding-patterns/in-place-reversal-of-a-linked-list/
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode curr = head, prev = null, next = head.next;
        while (curr != null) {
            // for this while loop
            next = curr.next; // temporarily store the next node
            curr.next = prev; // reverse the current node

            // for next while loop
            prev = curr; // store previous node for next while loop
            curr = next; // move on to the next node
        }

        // return head là SAI, vì head giờ nằm cuối LinkedList rồi
        return prev;
    }

    /**
     * 92. https://leetcode.com/problems/reverse-linked-list-ii/
     * Idea: cũng giống như reverseList ở trên, nhưng ta sẽ chỉ reverse trong khoảng [left...right]
     * Ngoài khoảng đó giữ nguyên các node và next của chúng
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null)
            return head;

        ListNode curr = head, prev = null, next = head.next;
        ListNode beforeLeft = null, leftNode = null;
        int pos = 0;
        while (curr != null) {
            pos++;
            if (pos < left) {
                prev = curr;
                curr = curr.next;
                continue;
            }
            if (pos == left) {
                beforeLeft = prev;
                leftNode = curr;
            }
            if (pos > right) {
                break;
            }
            // for this while loop
            next = curr.next; // temporarily store the next node
            curr.next = prev; // reverse the current node

            // for next while loop
            prev = curr; // store previous node for next while loop
            curr = next; // move on to the next node
        }

        // Gắn next của node trước vị trí left ban đầu vào node trước vị trí right ban đầu,
        // node trước vị trí right ban đầu lúc này là prev
        if (beforeLeft != null) {
            beforeLeft.next = prev;
        }

        // Gắn next của node vị trí left ban đầu vào vào node sau vị trí right ban đầu,
        // node sau vị trí right ban đầu lúc này là curr
        if (leftNode != null) {
            leftNode.next = curr;
        }

        // Case left = 1 hơi đặc biệt xíu, giống với reverseList ở trên
        if (left == 1)
            return prev;
        return head;
    }

    public static void main(String[] args) {
        ReverseLinkedList_206_92 app = new ReverseLinkedList_206_92();

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);
        ListNode node7 = new ListNode(7);
        ListNode node8 = new ListNode(8);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node8;
        app.printList(node1);

        ListNode newList = app.reverseBetween(node1, 3, 5);
        System.out.println("After reverse between:");
        app.printList(newList);

        System.out.println("\n============\n");

        ListNode n1 = new ListNode(3);
        ListNode n2 = new ListNode(5);
        n1.next = n2;

        app.printList(n1);

        ListNode newList2 = app.reverseBetween(n1, 1, 2);
        System.out.println("After reverse between:");
        app.printList(newList2);
    }

}
