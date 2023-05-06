package practice.leetcode.medium;

import java.util.HashSet;
import java.util.Set;
import util.ListNode;

public class LinkedListCycleII_142 {
    /**
     * Idea: using Set to check whether a node is already in the set or not.
     * If it is, then that node is the starting point of cycle
     * But this method requires extra space (Set). Anyway, this is a accepted answer
     */
    public ListNode detectCycle_usingSet(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        ListNode node = head;
        while (node != null) {
            if (set.contains(node))
                return node;
            else {
                set.add(node);
                node = node.next;
            }
        }
        return null;
    }

    /**
     * Idea: vẫn dùng pattern: fast and slow pointer (thuật toán rùa và thỏ) như bài 141. Khi 2 con trỏ
     * gặp nhau, ta ký hiệu:
     * - s1 = quãng đường từ head -> điểm gặp nhau
     * - s2 = quãng đường từ điểm gặp nhau -> điểm bắt đầu của cycle
     * Thế thì s1 = s2
     * VD: xem ảnh ../images/2019-10-23-tortoise-and-hare.gif
     * Sau khi 2 con trỏ gặp nhau tại node = 5, thì quãng đường từ 5 -> 3 sẽ bằng từ 1 -> 3
     * 
     * Chứng minh:
     * https://leetcode.com/problems/linked-list-cycle-ii/solutions/3275601/floyd-cycle-dectection-explaination-proper-diagram-java-soln/
     * Đọc cái link trên vẫn éo hiểu, haizzz!
     * Again: solution này yêu cầu bạn cần phải giỏi toán, và chứng minh được s1 = s2
     */
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                slow = head;
                // Now: distance from meeting point -> cycle starting point =
                // distance from head -> cycle starting point
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
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

        System.out.println("Start of cycle is: " + new LinkedListCycleII_142().detectCycle(head));
    }

}
