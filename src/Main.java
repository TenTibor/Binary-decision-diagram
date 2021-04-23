public class Main {

    public static void main(String[] args) {
        testManyBDDs(2000, 13);

        //        BDD bdd = new BDD();
//        bdd.BDD_create("1101");
//        bdd.print();
//        bdd.reduce();
//        bdd.print();
    }

    public static void testManyBDDs(int count, int countOfVariables) {
        long timeStarted = System.currentTimeMillis();
        int countOfNodesBeforeReduce = 0;
        int countOfRemovedNodes = 0;
        for (int i = 0; i < count; i++) {
            // generate binary function
            String bf = generateBf(countOfVariables);

            // create BDD
            BDD bdd = new BDD();
            bdd.BDD_create(bf);

            countOfNodesBeforeReduce += bdd.countOfNodes;

            // check if BDD is created good
            if (!checkBf(bdd))
                break;

            // Reduce BDD
            int reducedNodesCount;
            if ((reducedNodesCount = bdd.reduce()) == -1)
                System.out.println("Something went wrong with reducent");
            else
                countOfRemovedNodes += reducedNodesCount;

            // check if BDD is reduced good
            if (!checkBf(bdd))
                break;
            else System.out.println((i + 1) + ": OK");
        }

        long timeFinished = System.currentTimeMillis();
        System.out.println(countOfNodesBeforeReduce + " nodes was created and " + countOfRemovedNodes + " nodes was removed");
        double percentSuccess = (((double) countOfRemovedNodes / (double) countOfNodesBeforeReduce) * 100);
        System.out.printf("%.2f%% of nodes was removed\n", percentSuccess);
        System.out.println(count + " BDDs with " + countOfVariables + " variables was tested in: " + (timeFinished - timeStarted) + " ms");
        System.out.println(((timeFinished - timeStarted) / count) + "ms was average time for one test ");
    }

    public static String generateBf(int countOfVariables) {
        StringBuilder generatedBf = new StringBuilder();
        int countOfEndNodes = (int) Math.pow(2, countOfVariables);
        for (int i = 0; i < countOfEndNodes; i++) {
            if (Math.random() < 0.5)
                generatedBf.append("0");
            else generatedBf.append("1");
        }
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

        // Return true if check was success
        boolean output = bdd.bf.equals(String.valueOf(generatedBf));
        if (!output) System.out.println("PROBLEM with checking BDD by BF");
        return output;
    }
}
