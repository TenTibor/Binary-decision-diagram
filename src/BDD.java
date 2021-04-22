import java.util.ArrayList;
import java.util.Arrays;

public class BDD {
    Node root = new Node();
    int size = 0; // countOfNodes

    void BDD_create(String bf) {
        root = root.insertToNode(bf);
        size = root.size;
    }

    void print() {
        for (int i = root.depth; i >= 0; i--) {
            ArrayList<Node> nodes = getNodesByDepth(root, i);
            StringBuilder text = new StringBuilder();

            // Append spaces
            for (int j = 0; j < i; j++)
                text.append(" ");
            // Append variables
            for (Node node : nodes) {
                text.append(node.value).append(" ");
            }
            System.out.println(text);
        }

    }

    ArrayList<Node> getNodesByDepth(Node node, int depth) {
        ArrayList<Node> nodes = new ArrayList<>();
        if (node == null) return nodes;
        if (node.depth == depth) nodes.add(node);
        else {
            nodes.addAll(getNodesByDepth(node.left, depth));
            nodes.addAll(getNodesByDepth(node.right, depth));
        }
        return nodes;
    }
}
