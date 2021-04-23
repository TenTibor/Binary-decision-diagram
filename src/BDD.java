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
        for (int layer = root.depth; layer > 0; layer--) {
            ArrayList<Node> nodes = getNodesByDepth(root, layer, false);
            int sizeOfNodes = nodes.size();
            for (int i = 0; i < sizeOfNodes; i++) {
                Node thisNode = nodes.get(i);
                String thisNodeLeftValue = thisNode.left.value;
                String thisNodeRightValue = thisNode.right.value;

                // Check nodes of this item with all others
                for (int j = 0; i + j < sizeOfNodes; j++) {
                    Node nextNode = nodes.get(i + j);
                    String nextNodeLeftValue = nextNode.left.value;
                    String nextNodeRightValue = nextNode.right.value;
                    // Compare left node with every other
                    if (thisNodeLeftValue.equals(nextNodeRightValue)) {
                        nextNode.right = thisNode.left;
                    }
                    if (thisNodeLeftValue.equals(nextNodeLeftValue)) {
                        nextNode.left = thisNode.left;
                    }

                    // Compare right node with every other
                    if (thisNodeRightValue.equals(nextNodeRightValue)) {
                        nextNode.right = thisNode.right;
                    }
                    if (thisNodeRightValue.equals(nextNodeLeftValue)) {
                        nextNode.left = thisNode.right;
                    }
                }
            }
            ArrayList<Node> newNodesOnLayer = getNodesByDepth(root, layer - 1, false);
            newCountOfNodes += newNodesOnLayer.size();
        }

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
