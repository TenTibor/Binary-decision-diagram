public class Node {
    String value = "nothing";
    Node left = null;
    Node right = null;
    int size = 0;
    int depth = 0;

    public Node insertToNode(String bf) {
        Node newNode = new Node();
        newNode.value = bf;
        if (newNode.value.length() > 1) {
            String[] splittedPattern = splitText(newNode.value);
            newNode.left = insertToNode(splittedPattern[0]);
            if (newNode.left != null) {
                newNode.size += newNode.left.size + 1;
            }

            newNode.right = insertToNode(splittedPattern[1]);
            if (newNode.right != null) {
                newNode.size += newNode.right.size + 1;
            }

            if (newNode.right != null) newNode.depth += newNode.right.depth + 1;
            else if (newNode.left != null) newNode.depth += newNode.left.depth + 1;
        }
        System.out.println(newNode.value + "/" + newNode.size + "-" + newNode.depth);
        return newNode;
//        System.out.println(Arrays.toString(splittedPattern));
    }

    private String[] splitText(String text) {
        int mid = text.length() / 2;
        return new String[]{text.substring(0, mid), text.substring(mid)};
    }
}
