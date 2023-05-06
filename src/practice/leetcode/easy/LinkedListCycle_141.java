package practice.leetcode.easy;

import util.ListNode;

/**
 * https://leetcode.com/problems/linked-list-cycle
 */
public class LinkedListCycle_141 {
    /**
     * Idea: using pattern Fast & Slow Pointers
     * https://emre.me/coding-patterns/fast-slow-pointers/
     */
    public boolean hasCycle(ListNode head) {
        ListNode fastPointer = head;
        ListNode slowPointer = head;
        while (true) {
            if (fastPointer == null || fastPointer.next == null) {
                return false;
            }
            fastPointer = fastPointer.next.next;
            slowPointer = slowPointer.next;
            if (fastPointer == slowPointer) {
                return true;
            }
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(3);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(0);
        ListNode node3 = new ListNode(-4);

        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node1;

        System.out.println(new LinkedListCycle_141().hasCycle(head)); // true
    }
}
