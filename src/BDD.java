import java.util.Arrays;

public class BDD {
    Node root = new Node();

    void BDD_create(String bf) {
        root = root.insertToNode(bf);
    }
}
