import java.util.ArrayList;
import java.util.Arrays;

public class BDD {
    Node root = new Node();
    int size = 0; // countOfNodes

    void BDD_create(String bf) {
        root = root.insertToNode(bf);
        size = root.size + 1;
    }

    String BDD_use(String input) {
        char[] inputs = new char[input.length()];
        for (int i = 0; i < input.length(); i++) {
            inputs[i] = input.charAt(i);
        }
        Node actNode = root;
        for (char c : inputs) {
            switch (c) {
                case '0':
                    actNode = actNode.left;
                    break;
                case '1':
                    actNode = actNode.right;
                    break;
            }
        }
        return actNode.value;
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
