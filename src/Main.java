public class Main {

    public static void main(String[] args) {
        BDD bdd = new BDD();

        // Test from zadanie
        bdd.BDD_create("01101001");
        bdd.print();
        System.out.println("====Pocet uzlov " + bdd.size + "====");

        // change num to binary shit
        // always same string size
//
        System.out.println(bdd.BDD_use("000"));
        System.out.println(bdd.BDD_use("001"));
        System.out.println(bdd.BDD_use("010"));
        System.out.println(bdd.BDD_use("011"));
        System.out.println(bdd.BDD_use("100"));
        System.out.println(bdd.BDD_use("101"));
        System.out.println(bdd.BDD_use("110"));
        System.out.println(bdd.BDD_use("111"));

        System.out.println("============");
        int reducedNodesCount = bdd.reduce();
        bdd.print();

//        System.out.println(bdd.BDD_use("000"));
//        System.out.println(bdd.BDD_use("001"));
//        System.out.println(bdd.BDD_use("010"));
//        System.out.println(bdd.BDD_use("011"));
//        System.out.println(bdd.BDD_use("100"));
//        System.out.println(bdd.BDD_use("101"));
//        System.out.println(bdd.BDD_use("110"));
//        System.out.println(bdd.BDD_use("111"));
    }

//    public boolean
}
