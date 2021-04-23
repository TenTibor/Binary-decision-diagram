public class Main {

    public static void main(String[] args) {
        BDD bdd = new BDD();

        // Test from zadanie
        String bf = "1011101101000100"; //works
//        String bf = "11111101"; //works
//        String bf = "01101001"; // Works
        bdd.BDD_create(bf);
        bdd.print();

        if (checkBf(bdd)) System.out.println("PASSED");
        else System.out.println("SHTFCK");

        System.out.println("=======");
        int reducedNodesCount = bdd.reduce();
        System.out.println(reducedNodesCount + " nodes was removed");
        bdd.print();

        if (checkBf(bdd)) System.out.println("PASSED");
        else System.out.println("SHTFCK");

    }

    public static boolean checkBf(BDD bdd) {
        StringBuilder generatedBf = new StringBuilder();

        // Check all options to use
        for (int i = 0; i < bdd.bf.length(); i++) {
            StringBuilder inputToUse = new StringBuilder();
            inputToUse.append(Integer.toBinaryString(i));
            while (inputToUse.length() < bdd.countOfVariables) {
                inputToUse.insert(0, "0");
            }
            generatedBf.append(bdd.BDD_use(String.valueOf(inputToUse)));
        }
        return bdd.bf.equals(String.valueOf(generatedBf));
    }
}
