public class Main {

    public static void main(String[] args) {
        // Parameter 1: how many test
        // Parameter 2: how many variables
        testManyBDDs(2000, 13);
    }

    public static void testManyBDDs(int countOfTests, int countOfVariables) {
        System.out.print("Test started for " + countOfTests + " BDDs " + "with " + countOfVariables + " variables");
        long timeStarted = System.currentTimeMillis();

        int countOfNodesBeforeReduce = 0;
        int countOfRemovedNodes = 0;

        long totalTimeOfCrete = 0;
        long totalTimeOfUse = 0;
        long totalTimeOfReduce = 0;

        for (int i = 0; i < countOfTests; i++) {
            // generate binary function
            String bf = generateBf(countOfVariables);

            // create new BDD
            BDD bdd = new BDD();
            long timerStarted = System.currentTimeMillis();

            bdd.BDD_create(bf);

            long timerFinished = System.currentTimeMillis();
            totalTimeOfCrete += timerFinished - timerStarted;

            countOfNodesBeforeReduce += bdd.countOfNodes;

            // check if whole BDD is created good by bdd_use
            timerStarted = System.currentTimeMillis();

            if (!checkBf(bdd))
                break;

            timerFinished = System.currentTimeMillis();
            totalTimeOfUse += timerFinished - timerStarted;

            // Reduce BDD and get count of reduced BDDs
            timerStarted = System.currentTimeMillis();

            int reducedNodesCount;
            if ((reducedNodesCount = bdd.reduce()) == -1)
                System.out.println("Something went wrong with reduce");
            else
                countOfRemovedNodes += reducedNodesCount;

            timerFinished = System.currentTimeMillis();
            totalTimeOfReduce += timerFinished - timerStarted;

            // check if BDD is reduced good
            if (!checkBf(bdd))
                break;
            else {
                // PASSED test
                if ((i % 100) == 0) {
                    System.out.print("\n");
                } else System.out.print(".");
            }
        }
        System.out.print("\n");

        // Calc summary
        long timeFinished = System.currentTimeMillis();
        double reductionRate = (((double) countOfRemovedNodes / (double) countOfNodesBeforeReduce) * 100);

        // REPORT tests
        System.out.println("====SUMMARY FOR ALL TESTS====");
        System.out.println("> Count of tests: " + countOfTests);
        System.out.println("> Count of variables: " + countOfVariables);
        System.out.println("-----------------------------");
        System.out.println("> Nodes created: " + countOfNodesBeforeReduce);
        System.out.println("> Nodes removed: " + countOfRemovedNodes);
        System.out.printf("> Reduction rate: %.2f%%\n", reductionRate);
        System.out.println("-----------------------------");
        System.out.println("> Total time: " + (timeFinished - timeStarted) + " ms");
        System.out.println("> Total time for create: " + (totalTimeOfCrete) + " ms");
        System.out.println("> Total time for use: " + (totalTimeOfUse) + " ms");
        System.out.println("> Total time for reduce: " + (totalTimeOfReduce) + " ms");
        System.out.println("-----------------------------");
        System.out.println("> Average time for test: " + ((timeFinished - timeStarted) / countOfTests) + " ms");
        System.out.println("> Average time for create: " + (totalTimeOfCrete / countOfTests) + " ms");
        System.out.println("> Average time for use: " + (totalTimeOfUse / countOfTests) + " ms");
        System.out.println("> Average time for reduce: " + (totalTimeOfReduce / countOfTests) + " ms");
    }

    // generate boolean function by count of variables
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

    // check if BDD is created good. It compares all BDD_use with inputted boolean function
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
