package practice.inspiustest.solution;

import java.util.ArrayList;
import java.util.List;

class Node {
    public List<Node> children;
    public String name;
    public Node right = null;

    public Node(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}


class NodeLinker {

    ////////////////////////////
    // You may add additional
    // data members here.
    ////////////////////////////

    public void linkNodes(Node rootNode) {
        ////////////////////////////
        // Complete this method
        // such that all the nodes
        // within the tree with
        // the given root node have
        // a valid 'right' property
        // as per the diagram.
        // Ensure that the logic
        // will work for any tree
        // and not just for the
        // tree in the given
        // problem.
        ////////////////////////////
        if (rootNode.children == null)
            return;

        // Đầu tiên connect các node con của node root trước, sau đó duyệt sẽ đơn giản hơn
        for (int i = 0; i < rootNode.children.size() - 1; i++) {
            rootNode.children.get(i).right = rootNode.children.get(i + 1);
        }

        // Khởi tạo các biến cần dùng trong quá trình duyệt
        Node firstChild = rootNode.children.get(0);
        Node currNode = rootNode.children.get(0);
        Node prevChild = null;

        // Duyệt từng level, tại mỗi level sẽ connect các node con lại với nhau
        while (currNode != null) {
            List<Node> currChildren = currNode.children;

            // 1. Nếu node hiện tại có con, connect các con đó lại.
            // Nếu ko, chuyển sang node tiếp theo (bước 4)
            if (currChildren != null) {
                // 6. Nếu như chưa có prevChild, tức là currNode là node đầu tiên trong level đang
                // duyệt, khởi tạo firstChild tại đây
                if (prevChild == null) {
                    firstChild = currChildren.get(0);
                } else {
                    // 7. currNode ko phải là node đầu tiên trong level đang duyệt, cần
                    // connect node con cuối cùng của node trước đó (prevChild)
                    // với con đầu tiên của nó
                    prevChild.right = currChildren.get(0);
                }

                // 2. Connect từng thằng con của node hiện tại với nhau
                for (int i = 0; i < currChildren.size() - 1; i++) {
                    currChildren.get(i).right = currChildren.get(i + 1);
                }

                // 3. Dùng prevChild để connect thằng con cuối cùng của node hiện tại
                // với thằng con đầu tiên của node tiếp theo
                prevChild = currChildren.get(currChildren.size() - 1);
            }

            // 4. Chuyển đến node tiếp theo trong level hiện tại để bắt đầu connect các
            // node con của nó
            currNode = currNode.right;

            // 5. Nếu như node tiếp theo ko còn nữa, chuyển sang duyệt level thấp hơn
            if (currNode == null) {
                currNode = firstChild;
                prevChild = null; // nhớ phải reset prevChild, vì xuống level dưới mà
                firstChild = null;
            }
        }
    }

    ////////////////////////////
    // You may add additional
    // methods here.
    ////////////////////////////

}


public class JavaTest_Solution {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node E = new Node("E");
        Node F = new Node("F");
        Node G = new Node("G");
        Node H = new Node("H");
        Node I = new Node("I");
        Node J = new Node("J");
        Node K = new Node("K");
        Node L = new Node("L");
        Node M = new Node("M");
        Node N = new Node("N");
        Node O = new Node("O");
        Node P = new Node("P");
        Node Q = new Node("Q");
        Node R = new Node("R");

        A.children = new ArrayList<>();
        A.children.add(B);
        A.children.add(C);
        A.children.add(D);

        B.children = new ArrayList<>();
        B.children.add(E);
        B.children.add(F);

        D.children = new ArrayList<>();
        D.children.add(G);

        E.children = new ArrayList<>();
        E.children.add(H);
        E.children.add(I);
        E.children.add(J);

        F.children = new ArrayList<>();
        F.children.add(K);

        G.children = new ArrayList<>();
        G.children.add(L);
        G.children.add(M);

        J.children = new ArrayList<>();
        J.children.add(N);
        J.children.add(O);

        L.children = new ArrayList<>();
        L.children.add(P);

        N.children = new ArrayList<>();
        N.children.add(Q);

        P.children = new ArrayList<>();
        P.children.add(R);

        NodeLinker nodeLinker = new NodeLinker();
        nodeLinker.linkNodes(A);

        if (A.right == null && B.right == C && C.right == D && D.right == null && //
                E.right == F && F.right == G && G.right == null && //
                H.right == I && I.right == J && J.right == K && K.right == L && //
                L.right == M && M.right == null && //
                N.right == O && O.right == P && P.right == null && //
                Q.right == R && R.right == null)
            System.out.println("Test Successful!");
        else
            System.out.println("Test failed!");

    }
}
