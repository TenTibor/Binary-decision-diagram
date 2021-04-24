import java.util.ArrayList;

public class BDD {
    Node root = new Node();
    int countOfVariables = 0;
    int countOfNodes = 0;
    String bf; // boolean function

    void BDD_create(String bf) {
        this.bf = bf;

        // insert all nodes by boolean function
        root = root.insertToNode(bf);

        // set count of nodes and variables
        countOfNodes = root.size + 1;
        countOfVariables = log2(bf.length());
    }

    // use pattern to get result of boolean function
    char BDD_use(String input) {

        // get string characters to array
        char[] inputs = new char[input.length()];
        for (int i = 0; i < input.length(); i++) {
            inputs[i] = input.charAt(i);
        }

        // navigate in BDD by array of chars
        Node actNode = root;
        try {
            for (char c : inputs) {
                if (c == '0')
                    actNode = actNode.left;
                else if (c == '1')
                    actNode = actNode.right;
            }
        } catch (NullPointerException e) {
            // if route was bad return problem
            return '-';
        }

        return actNode.value.charAt(0);
    }

    // reduce BDD and return count of removed nodes
    public int reduce() {
        if (root == null) return -1;
        int newCountOfNodes = 1;

        // Reduce all layers except root
        for (int layer = root.depth; layer > 0; layer--) {

            // get all unique nodes in this layer
            ArrayList<Node> nodes = getNodesByDepth(root, layer, false);
            int sizeOfNodes = nodes.size();

            // compare any child node with another child node
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

            // Check layer before this and get count of nodes after reduce
            ArrayList<Node> newNodesOnLayer = getNodesByDepth(root, layer - 1, false);
            newCountOfNodes += newNodesOnLayer.size();
        }

        // calc removed nodes
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
            }
            System.out.println(text);
        }
        System.out.println("=======Pocet uzlov " + this.countOfNodes + "=======");
    }

    // get on nodes in exact layer
    // set duplicate to false for unique nodes
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

    // removed duplicate nodes in ArrayList<Node>
    private ArrayList<Node> removedDuplicates(ArrayList<Node> nodes) {
        ArrayList<Node> newNodes = new ArrayList<>();
        for (Node thisNode : nodes) {
            if (!newNodes.contains(thisNode)) newNodes.add(thisNode);
        }
        return newNodes;
    }

    // help function to calc log2
    public static int log2(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }
}
