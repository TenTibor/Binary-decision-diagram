import java.util.ArrayList;

public class BDD {
    Node root = new Node();
    int countOfVariables = 0;
    int countOfNodes = 0;
    String bf;

    void BDD_create(String bf) {
        this.bf = bf;
        root = root.insertToNode(bf);
        countOfNodes = root.size + 1;
        countOfVariables = log2(bf.length());
        System.out.println("Pocet premennych:" + countOfVariables);
    }

    char BDD_use(String input) {
        char[] inputs = new char[input.length()];
        for (int i = 0; i < input.length(); i++) {
            inputs[i] = input.charAt(i);
        }
        Node actNode = root;
        for (char c : inputs) {
            if (c == '0')
                actNode = actNode.left;
            else if (c == '1')
                actNode = actNode.right;
        }
        return (actNode.value.equals("0") || actNode.value.equals("1")) ? actNode.value.charAt(0) : '-';
    }


    public int reduce() {
        if (root == null) return -1;
        int newCountOfNodes = 1;
        // Reduce all layers
        for (int layer = 1; layer < root.depth; layer++) {
            ArrayList<Node> nodes = getNodesByDepth(root, layer, true);
            for (int i = 0; i < nodes.size(); i++) {
                // Check if both nodes are same
                if (nodes.get(i).left.value.equals(nodes.get(i).right.value)) {
                    nodes.get(i).right = nodes.get(i).left;
                }
                // Check nodes of this item with all others
                for (int j = 1; i + j < nodes.size(); j++) {
                    // Compare left node with every other
                    if (nodes.get(i).left.value.equals(nodes.get(i + j).right.value)) {
                        nodes.get(i + j).right = nodes.get(i).left;
                    }
                    if (nodes.get(i).left.value.equals(nodes.get(i + j).left.value)) {
                        nodes.get(i + j).left = nodes.get(i).left;
                    }

                    // Compare right node with every other
                    if (nodes.get(i).right.value.equals(nodes.get(i + j).right.value)) {
                        nodes.get(i + j).right = nodes.get(i).right;
                    }
                    if (nodes.get(i).right.value.equals(nodes.get(i + j).left.value)) {
                        nodes.get(i + j).left = nodes.get(i).right;
                    }
                }
            }
            ArrayList<Node> newNodesOnLayer = getNodesByDepth(root, layer - 1, false);
            newCountOfNodes += newNodesOnLayer.size();
        }
        ArrayList<Node> newNodesOnLayer = getNodesByDepth(root, root.depth - 1, false);
        newCountOfNodes += newNodesOnLayer.size();

        int countOfRemovedNodes = this.countOfNodes - newCountOfNodes;
        this.countOfNodes = newCountOfNodes;
        return countOfRemovedNodes;
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
                text.append(node.value).append(" ");
//                text.append(node).append(" ");
            }
            System.out.println(text);
        }
        System.out.println("=======Pocet uzlov " + this.countOfNodes + "=======");
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

    public static int log2(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }
}
