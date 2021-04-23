import java.util.ArrayList;

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

    public int reduce() {
//        Reduce all layers
        for (int layer = 1; layer < root.depth; layer++) {
            ArrayList<Node> nodes = getNodesByDepth(root, layer, true);
//            System.out.println(nodes.size());
            for (int i = 0; i < nodes.size(); i += 2) {
                if (nodes.get(i).left.value.equals(nodes.get(i + 1).right.value))
                    nodes.get(i + 1).right = nodes.get(i).left;
                if (nodes.get(i).right.value.equals(nodes.get(i + 1).left.value))
                    nodes.get(i + 1).left = nodes.get(i).right;
                if (nodes.get(i).left.value.equals(nodes.get(i + 1).left.value))
                    nodes.get(i + 1).left = nodes.get(i).left;
                if (nodes.get(i).right.value.equals(nodes.get(i + 1).right.value))
                    nodes.get(i + 1).right = nodes.get(i).right;
            }
        }

        return 1;
    }

    void print() {
        for (int i = root.depth; i >= 0; i--) {
            ArrayList<Node> nodes = getNodesByDepth(root, i, false);
            StringBuilder text = new StringBuilder();
            text.append(i).append(": ");

            // Append spaces
            for (int j = 0; j < i; j++)
                text.append(" ");
            // Append variables
            for (Node node : nodes) {
                text.append(node).append(" ");
            }
            System.out.println(text);
        }
    }

    ArrayList<Node> getNodesByDepth(Node node, int depth, boolean duplicates) {
        ArrayList<Node> nodes = new ArrayList<>();
        if (node == null) return nodes;
        if (node.depth == depth) {
            nodes.add(node);
        } else {
            nodes.addAll(getNodesByDepth(node.left, depth, true));
            nodes.addAll(getNodesByDepth(node.right, depth, true));
        }
        return duplicates ? nodes : removedDuplicates(nodes);
    }

    private ArrayList<Node> removedDuplicates(ArrayList<Node> nodes) {
        ArrayList<Node> newNodes = new ArrayList<>();
        for (Node thisNode : nodes) {
            if (!newNodes.contains(thisNode)) newNodes.add(thisNode);
        }
        return newNodes;
    }
}
