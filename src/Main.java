public class Main {

    public static void main(String[] args) {
        BDD bdd = new BDD();
        bdd.BDD_create("01010111");
        bdd.print();
        System.out.println("==================");
        System.out.println(bdd.BDD_use("000"));
        if (bdd.BDD_use("000").equals("1"))
            System.out.println("error, for A=0, B=0, C=0 it should be 0");
    }
}
