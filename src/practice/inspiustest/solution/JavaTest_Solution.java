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

        // Initialize
        Node currNode = rootNode;
        Node firstChild = null;
        Node prevChild = null;

        // Traverse each level and connect their children
        while (currNode != null) {
            List<Node> currChildren = currNode.children;

            // 1. If current node has children, then connect all these children.
            // If not, then move to step 4
            if (currChildren != null) {
                // 6. If prevChild is null, then current node is the first node at current level,
                // we should initialize firstChild here
                if (prevChild == null) {
                    firstChild = currChildren.get(0);
                } else {
                    // 7. Else, current Node is not the first node at current level, we need to
                    // connect last child of previous node to first child of this current node
                    prevChild.right = currChildren.get(0);
                }

                // 2. Connect all children of current node
                for (int i = 0; i < currChildren.size() - 1; i++) {
                    currChildren.get(i).right = currChildren.get(i + 1);
                }

                // 3. Use prevChild to connect the last child of current node to the first child of
                // next node at current level
                prevChild = currChildren.get(currChildren.size() - 1);
            }

            // 4. Move to next node at current level and start connecting its children
            currNode = currNode.right;

            // 5. If there is no node left at this level, move down to next level
            if (currNode == null) {
                currNode = firstChild;
                prevChild = null; // we need to reset prevChild each time we move down to next level
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
