package com.hair.hairstyle.test;

/**
 * Created by yunshan on 17/8/7.
 */

public class NodeTest {

    public static void main(String[] args) {
        Node head = new Node(3);
        Node node2 = new Node(4);
        Node node3 = new Node(5);
        Node node4 = new Node(6);
        Node node5 = new Node(7);

        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

//        while (head != null) {
//            System.out.print(head.val + " ");
//            head = head.next;
//
//        }

        head = reverseNode3(head);


        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;

        }


    }

    public static Node reverseNode3(Node head) {

        Node p;
        Node q;
        p = head.next;

        while (p.next != null) {
            q = p.next;
            p.next = q.next;
            q.next = head.next;
            head.next = q;
        }

        p.next = head;//相当于成环
        head = p.next.next;//新head变为原head的next
        p.next.next = null;//断掉环
        return head;
    }

    public static Node reverseNode(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node p;
        Node q;
        Node r;
        p = head;
        q = head.next;

        head.next = null;

        while (q != null) {
            r = q.next;
            q.next = p;
            p = q;
            q = r;
        }

        return p;
    }

    static class Node {
        private int val;
        private Node next;

        public Node(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }


}
