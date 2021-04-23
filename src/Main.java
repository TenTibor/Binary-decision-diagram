public class Main {

    public static void main(String[] args) {
        BDD bdd = new BDD();

        // Test from zadanie
        String bf = generateBf(13);
        bdd.BDD_create(bf);
        bdd.print();

        if (checkBf(bdd)) System.out.println("PASSED");
        else System.out.println("SHTFCK");

        // Reduce node
        System.out.println("=======");
        int reducedNodesCount;
        if ((reducedNodesCount = bdd.reduce()) == -1)
            System.out.println("Something went wrong with reducent");
        else
            System.out.println(reducedNodesCount + " nodes was removed");

        bdd.print();

        if (checkBf(bdd)) System.out.println("PASSED");
        else System.out.println("SHTFCK");

    }

    public static String generateBf(int countOfVariables) {
        StringBuilder generatedBf = new StringBuilder();
        int countOfEndNodes = (int) Math.pow(2, countOfVariables);
        for (int i = 0; i < countOfEndNodes; i++) {
            if (Math.random() < 0.5)
                generatedBf.append("0");
            else generatedBf.append("1");
        }
        System.out.println(generatedBf);
        return String.valueOf(generatedBf);
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
