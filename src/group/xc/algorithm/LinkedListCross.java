package group.xc.algorithm;

public class LinkedListCross {
    static class Node {
        private int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }
    public static int findCommonNode(Node a, Node b){
        int a_len = getListLen(a);
        int b_len = getListLen(b);
        if(a_len > b_len) {
            for (int i = 0; i < a_len - b_len; i++) {
                a = a.next;
            }
        }else {
            for (int i = 0; i < b_len - a_len; i++) {
                b = b.next;
            }
        }
        while (a != b && a != null) {
            a = a.next;
            b = b.next;
        }
        if(a == b) {
            return a.value;
        }else return -1;

    }
    public static int getListLen(Node a) {
        Node pointer = a;
        int len = 0;
        while (pointer != null) {
            len++;
            pointer = pointer.next;
        }
        return len;
    }
    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n7.next = n6;// 另一条链
        System.out.println(findCommonNode(n1, n7));
    }
}

