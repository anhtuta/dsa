package practice.inspiustest.solution;

import java.util.ArrayList;
import java.util.List;

class Node {
    public List<Node> children;
    public Node right = null;
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
        for (int i = 0; i < rootNode.children.size() - 1; i++) {
            rootNode.children.get(i).right = rootNode.children.get(i + 1);
            linkNodes(rootNode.children.get(i));
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
        Node A = new Node();
        Node B = new Node();
        Node C = new Node();
        Node D = new Node();
        Node E = new Node();
        Node F = new Node();
        Node G = new Node();

        A.children = new ArrayList<>();
        A.children.add(B);
        A.children.add(C);
        A.children.add(D);

        B.children = new ArrayList<>();
        B.children.add(E);
        B.children.add(F);

        D.children = new ArrayList<>();
        D.children.add(G);

        NodeLinker nodeLinker = new NodeLinker();
        nodeLinker.linkNodes(A);

        if (A.right == null && B.right == C && C.right == D && D.right == null && E.right == F
                && F.right == G && G.right == null)
            System.out.println("Test Successful!");
        else
            System.out.println("Test failed!");

    }
}
