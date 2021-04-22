public class Node {
    String value;
    Node left = null;
    Node right = null;

    public Node() {
    }

    public Node(String value) {
        this.value = value;
    }

    public Node insertToNode(String bf) {
        value = bf;
        System.out.println(value);
        if (bf.length() > 1) {
            String[] splittedPattern = splitText(bf);
            left = insertToNode(splittedPattern[0]);
            right = insertToNode(splittedPattern[1]);
        }
        return this;
//        System.out.println(Arrays.toString(splittedPattern));
    }

    private String[] splitText(String text) {
        int mid = text.length() / 2;
        return new String[]{text.substring(0, mid), text.substring(mid)};
    }
}
