import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * QUESTION 2:
 * 
 * Modify the program in Q1 using RecursiveAction in the Java ForkJoin
 * Framework.
 * 
 * https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading/fork-and-join.html
 * https://github.com/vladiksun/Coursera/tree/master/Parallel%20Programming%20in%20Java/fork-and-join-examples/src/main/java/com/logicbig/example
 */

public class L8Q2 {

    public static void main(String[] args) {
        List<BigInteger> list = new ArrayList<>();

        // Create list of all numbers to calculate their factorials of
        for (int i = 1; i <= 20; i++) {
            list.add(new BigInteger(Integer.toString(i)));
        }

        // Create RecursiveAction task based on list of numbers
        FactorialTask ft = new FactorialTask(list);

        // Forks the task and waits for the result, and doesn’t need any manual joining
        // Schedule RecursiveAction task
        ForkJoinPool.commonPool().invoke(ft);

        System.out.println("\nSum of the factorials from 1 to 20 (RecursiveAction) = " + FactorialTask.sum);
    }

    // RecursiveAction, which is the equivalent of Runnable in the sense that it
    // DOESN'T return a value.
    private static class FactorialTask extends RecursiveAction {
        private static int SEQUENTIAL_THRESHOLD = 5;
        private List<BigInteger> integerList;

        // Use static variable to store factorial sum
        static BigInteger sum = BigInteger.ZERO;

        private FactorialTask(List<BigInteger> integerList) {
            this.integerList = integerList;
        }

        @Override
        protected void compute() {
            // If list is small enough, calculate the factorials
            if (integerList.size() <= SEQUENTIAL_THRESHOLD) {
                sumFactorials();
            } else {
                // Split list in half
                int middle = integerList.size() / 2;
                // First half/left
                List<BigInteger> newList = integerList.subList(middle, integerList.size());
                // Second half/right
                integerList = integerList.subList(0, middle);

                // System.out.println("Sublist 1: " + newList);
                // System.out.println("Sublist 2: " + integerList);

                // Create new subtask from splitted list
                FactorialTask subtask = new FactorialTask(newList);

                // Submits task to the pool to run it asynchronously
                subtask.fork();

                this.compute();

                // Blocks and returns the result of the computation when it is done
                subtask.join();
            }
        }

        // Calculate sum of factorials
        private BigInteger sumFactorials() {
            for (BigInteger i : integerList) {
                sum = sum.add(calculateFactorial(i));
                System.out.printf("%s! = %s, sum = %s, thread = %s \n", i, calculateFactorial(i), sum,
                        Thread.currentThread().getName());
            }
            return sum;
        }

        // Calculate the factorials
        private BigInteger calculateFactorial(BigInteger input) {
            BigInteger factorial = BigInteger.ONE;
            for (BigInteger i = BigInteger.ONE; i.compareTo(input) <= 0; i = i.add(BigInteger.ONE)) {
                factorial = factorial.multiply(i);
            }
            return factorial;
        }
    }
}