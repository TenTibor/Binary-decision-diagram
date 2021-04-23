import java.util.ArrayList;

public class BDD {
    Node root = new Node();
    String bf;
    int countOfNodes = 0; // countOfNodes
    int countOfVariables = 0;

    void BDD_create(String bf) {
        this.bf = bf;
        root = root.insertToNode(bf);
        countOfNodes = root.size + 1;
        countOfVariables = log2(bf.length());
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
        int newCountOfNodes = 0;
        // Reduce all layers
        for (int layer = 1; layer < root.depth; layer++) {
            ArrayList<Node> nodes = getNodesByDepth(root, layer, true);
            for (int i = 0; i < nodes.size(); i += 2) {
                // Check if node have two same options
                if (nodes.get(i).left.value.equals(nodes.get(i).right.value)) {
                    nodes.get(i).right = nodes.get(i).left;
                }
                // Check nodes of this item with all others
                for (int j = 1; i+j < nodes.size(); j++) {
                    if (nodes.get(i).left.value.equals(nodes.get(i + j).right.value)) {
                        nodes.get(i + j).right = nodes.get(i).left;
                    }
                    if (nodes.get(i).left.value.equals(nodes.get(i + j).left.value)) {
                        nodes.get(i + j).left = nodes.get(i).left;
                    }
                    if (nodes.get(i).right.value.equals(nodes.get(i + j).right.value)) {
                        nodes.get(i + j).right = nodes.get(i).right;
                    }
                    if (nodes.get(i).right.value.equals(nodes.get(i + j).left.value)) {
                        nodes.get(i + j).left = nodes.get(i).right;
                    }
                }

//                if (nodes.get(i).left.value.equals(nodes.get(i + 1).right.value)) {
//                    nodes.get(i + 1).right = nodes.get(i).left;
//                } else if (nodes.get(i).right.value.equals(nodes.get(i + 1).right.value)) {
//                    nodes.get(i + 1).right = nodes.get(i).right;
//                }
//                if (nodes.get(i).right.value.equals(nodes.get(i + 1).left.value)) {
//                    nodes.get(i + 1).left = nodes.get(i).right;
//                } else if (nodes.get(i).left.value.equals(nodes.get(i + 1).left.value)) {
//                    nodes.get(i + 1).left = nodes.get(i).left;
//                }
            }
            ArrayList<Node> newNodesOnLayer = getNodesByDepth(root, layer, false);
            System.out.println(newNodesOnLayer);
            newCountOfNodes += newNodesOnLayer.size();
        }
        int countOfRemovedNodes = this.countOfNodes - 1 - newCountOfNodes;
        this.countOfNodes = newCountOfNodes + 1;
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
