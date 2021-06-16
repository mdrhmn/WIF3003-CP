import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * QUESTION 1:
 * 
 * Write a program that computes the sum of all factorials for numbers 1 to 20
 * using RecursiveTask in the Java ForkJoin Framework.
 * 
 * https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading/fork-and-join.html
 * https://github.com/vladiksun/Coursera/tree/master/Parallel%20Programming%20in%20Java/fork-and-join-examples/src/main/java/com/logicbig/example
 */

public class L8Q1 {

    public static void main(String[] args) {
        List<BigInteger> list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            list.add(new BigInteger(Integer.toString(i)));
        }

        BigInteger sum = ForkJoinPool.commonPool().invoke(new FactorialTask(list));
        System.out.println("\nSum of the factorials from 1 to 20 (RecursiveTask) = " + sum);
    }

    // RecursiveTask<V>, which is the equivalent of Callable in the sense that it
    // DOES return a value.
    private static class FactorialTask extends RecursiveTask<BigInteger> {
        private static int SEQUENTIAL_THRESHOLD = 5;
        private List<BigInteger> integerList;

        private FactorialTask(List<BigInteger> integerList) {
            this.integerList = integerList;
        }

        @Override
        protected BigInteger compute() {
            // If list is small enough, calculate the factorials
            if (integerList.size() <= SEQUENTIAL_THRESHOLD) {
                return sumFactorials();
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

                // Add the value of sums together
                BigInteger thisSum = this.compute();
                BigInteger thatSum = subtask.join();
                return thisSum.add(thatSum);
            }
        }

        // Calculate sum of factorials
        private BigInteger sumFactorials() {
            BigInteger sum = BigInteger.ZERO;
            for (BigInteger i : integerList) {
                System.out.printf("%s! = %s, thread = %s %n", i, calculateFactorial(i),
                        Thread.currentThread().getName());
                sum = sum.add(calculateFactorial(i));
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
