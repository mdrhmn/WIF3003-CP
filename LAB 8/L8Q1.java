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
    // Can use Long or BigInteger (but Long performs better than BigInteger)
    // BigInteger however can store more
    public static void main(String[] args) {
        List<BigInteger> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add(new BigInteger(Integer.toString(i)));
        }

        BigInteger sum = ForkJoinPool.commonPool().invoke(new FactorialSumTask(list));
        System.out.println("\nSum of the factorials from 1 to 20 (RecursiveTask) = " + sum);
    }

    // RecursiveTask<V>, which is the equivalent of Callable in the sense that it
    // DOES return a value.
    private static class FactorialSumTask extends RecursiveTask<BigInteger> {
        // Cleaner way = set threshold to 1
        // However will impact performance (communication overhead)
        // Maintainability (non-functional requirement)
        private static int SEQUENTIAL_THRESHOLD = 5;
        private List<BigInteger> integerList;

        private FactorialSumTask(List<BigInteger> integerList) {
            this.integerList = integerList;
        }

        @Override
        protected BigInteger compute() {
            // If list is small enough, calculate the factorials (base case)
            if (integerList.size() <= SEQUENTIAL_THRESHOLD) {
                return sumFactorials();
            } 
            // Recursive case
            else {
                // Split list in half
                int middle = integerList.size() / 2;
                // Second half/left
                List<BigInteger> newList = integerList.subList(middle, integerList.size());
                // First half/right
                integerList = integerList.subList(0, middle);

                // System.out.println("Sublist 1: " + newList);
                // System.out.println("Sublist 2: " + integerList);

                // Create new subtask from splitted list
                FactorialSumTask subtask = new FactorialSumTask(newList);
                
                // Submits task to the pool to run it asynchronously
                subtask.fork();

                // Add the value of sums together (method 1)
                BigInteger thisSum = this.compute();
                BigInteger thatSum = subtask.join();
                return thisSum.add(thatSum);

                // Method 2 (use invokeAll())
            }
        }

        // Calculate sum of factorials
        private BigInteger sumFactorials() {
            BigInteger sum = BigInteger.ZERO;
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
