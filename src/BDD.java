import java.util.Arrays;

public class BDD {
    Node root = new Node();
    int size = 0; // countOfNodes

    void BDD_create(String bf) {
        root = root.insertToNode(bf);
    }

    void BDD_print(){

    }
}
