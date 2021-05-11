import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * QUESTION 1:
 * 
 * Write a program to find the integer in the range of 1 and 111111 that has the
 * largest number of divisors. The program will divide the task into several
 * concurrent sub-tasks. Each sub-task processes at most 1000 integers and
 * returns the result for integers that it processes. The result contains the
 * integer with the largest number of divisors and that number of divisors.
 * After all the sub-tasks have completed, all the returned (sub) results are
 * compared to get the final result.
 */

public class L6Q1 {

    private final static int MAX_INT = 111111;

    /**
     * A class to represent the result from one sub-task. The result contains the
     * integer with the largest number of divisors and that number of divisors.
     */
    private static class Result {
        int maxDivisorFromTask; // Maximum number of divisors found.
        int intWithMaxFromTask; // Which integer gave that maximum number.

        Result(int maxDivisors, int whichInt) {
            maxDivisorFromTask = maxDivisors;
            intWithMaxFromTask = whichInt;
        }
    }

    /**
     * A class to represent the task of finding the number in a given range of
     * integers (specified in countDivisorsExecutor for each sub-task) that has the
     * largest number of divisors
     */
    private static class CountDivisorsCallable implements Callable<Result> {
        int min, max; // Start and end of the range of integers for this task.

        CountDivisorsCallable(int min, int max) {
            this.min = min;
            this.max = max;
        }

        /*
         * The task is executed when the call() method is called, returning a Result
         * object
         */
        public Result call() {
            int maxDivisors = 0;
            int whichInt = 0;

            for (int i = min; i < max; i++) {
                int divisors = countDivisors(i);
                if (divisors > maxDivisors) {
                    maxDivisors = divisors;
                    whichInt = i;
                }
            }

            return new Result(maxDivisors, whichInt);
        }
    }

    /**
     * A method to find the integer in the range of 1 and MAX_INT (111111) that has
     * the largest number of divisors by dividing the work into sub-tasks that will be
     * submitted to an ExecutorService. The Futures that are returned when the sub-tasks
     * are submitted are placed into an ArrayList. The results from those Futures
     * are combined to produce the final output.
     * 
     * @param numberOfThreads the number of threads to be used by the executor
     */
    private static void countDivisorsExecutor(int numberOfThreads) {

        System.out.println("\nCounting divisors using " + numberOfThreads + " threads...");

        // Create the ExecutorService and an ArrayList to store all the Futures

        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        ArrayList<Future<Result>> results = new ArrayList<>();

        /*
         * Each sub-task processes at most 1000 integers. Use Math.ceil to round up the
         * number of sub-tasks to cater for MAX_INT which is not a multiple of 1000.
         */

        int numberOfSubTasks = (int) Math.ceil(MAX_INT / 1000.0);

        for (int i = 0; i < numberOfSubTasks; i++) {
            int start = i * 1000 + 1;
            int end = (i + 1) * 1000;

            /*
             * The last task in that case will consist of the last (MAX_INT%1000)) ints.
             */
            if (end > MAX_INT)
                end = MAX_INT;

            /*
             * submit() submits a Callable or a Runnable task to an ExecutorService and
             * returns a result of type Future, which will be stored inside the Future
             * ArrayList (res).
             */
            Future<Result> res = executor.submit(new CountDivisorsCallable(start, end));
            results.add(res);
        }

        /*
         * As the executor executes the tasks, results become available in the Futures
         * that are stored in the ArrayList.
         */

        int maxDivisorCount = 0; // Over maximum found by any task.
        int intWithMaxDivisorCount = 0; // Which integer gave that maximum?

        for (Future<Result> res : results) {
            try {

                /*
                 * After all the sub-tasks have completed, all the returned (sub) results are
                 * compared to get the final result. Note that each call to res.get() blocks, if
                 * necessary, until the result is available.
                 */
                Result result = res.get();

                /*
                 * Check max divisor from each Future and replace maxDivisorCount if necessary
                 */
                if (result.maxDivisorFromTask > maxDivisorCount) {
                    maxDivisorCount = result.maxDivisorFromTask;
                    intWithMaxDivisorCount = result.intWithMaxFromTask;
                }

            } catch (Exception e) {
                System.out.println("Error:");
                System.out.println(e);
                System.exit(1);
            }
        }

        /*
         * Print the results (max divisor and integer with max divisor) and total
         * elapsed time
         */
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("\nThe largest number of divisors " + "for numbers between 1 and " + MAX_INT + " is "
                + maxDivisorCount);
        System.out.println("An integer with that many divisors is " + intWithMaxDivisorCount);
        System.out.println("Total elapsed time:  " + (elapsedTime / 1000.0) + " seconds.\n");

        executor.shutdown();
    }

    /**
     * Main method to ask for number of threads from 1-10.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfThreads = 0;

        while (numberOfThreads < 1 || numberOfThreads > 10) {
            System.out.print("Enter number of threads (1-10): ");
            numberOfThreads = scanner.nextInt();
            if (numberOfThreads < 1 || numberOfThreads > 10)
                System.out.println("Please enter a number from 1 to 10!");
        }

        // Use Executor to generate threads
        countDivisorsExecutor(numberOfThreads);
        scanner.close();
    }

    /**
     * A method with algorithm to iterate all the numbers from 1 to sqrt(n) (instead
     * of naive solution to iterate from 1 to n), checking if that number divides n
     * and incrementing number of divisors.
     * 
     * @param N the integer to calculate its divisors
     */
    private static int countDivisors(int N) {
        int count = 0;
        for (int i = 1; i <= Math.sqrt(N); i++) {
            if (N % i == 0)
                // If divisors are equal, count only one
                if (N / i == i)
                    count++;
                else // Otherwise count both
                    count = count + 2;
        }
        return count;
    }

}