public class Node {
    String value = "nothing";
    Node left = null;
    Node right = null;
    int size = 0;

    public Node insertToNode(String bf) {
        Node newNode = new Node();
        newNode.value = bf;
        if (newNode.value.length() > 1) {
            String[] splittedPattern = splitText(newNode.value);
            newNode.left = insertToNode(splittedPattern[0]);
            if (left != null) size++;

            right = insertToNode(splittedPattern[1]);
            if (right != null) size++;
        }
        System.out.println(newNode.value + "/" + newNode.size);
        return newNode;
//        System.out.println(Arrays.toString(splittedPattern));
    }

    private String[] splitText(String text) {
        int mid = text.length() / 2;
        return new String[]{text.substring(0, mid), text.substring(mid)};
    }
}
