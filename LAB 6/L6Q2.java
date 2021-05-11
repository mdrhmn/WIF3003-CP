import java.time.Instant;
import java.time.Duration;
import java.text.NumberFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * QUESTION 2:
 * 
 * Write a program that computes 2 Fibonacci values, one using sequential
 * calculation (e.g. recursion) and another using asynchronous task with
 * CompletableFuture. Compare the time taken for these two calculations
 * (recursion vs asynchronous).
 */

/**
 * A class that stores two Instants in time and contains a method to calculate
 * total time in seconds.
 */
class Timer {
    public Instant start;
    public Instant end;

    // return total time in seconds
    public double timeInSeconds() {
        return Duration.between(start, end).toMillis() / 1000.0;
    }
}

public class L6Q2 {

    /**
     * Main method
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // 2 Fibonacci numbers
        final int FIBO_1 = 45;
        final int FIBO_2 = 44;

        // Perform recursive Fibonacci calculations
        System.out.println("Fibonacci (Recursion):");
        Timer recursiveResult1 = startFibonacci(FIBO_1);
        Timer recursiveResult2 = startFibonacci(FIBO_2);
        double recursiveDuration = calculateTime(recursiveResult1, recursiveResult2);
        System.out.printf("  Total calculation duration = %.3f seconds%n", recursiveDuration);

        // Perform asynchronous Fibonacci calculations
        System.out.printf("\nFibonacci (Asynchronous):\n");

        /*
         * Run a task asynchronously on the default thread pool of Java and return the
         * result using supplyAsync() and lambda expression. The supplyAsync() method
         * returns CompletableFuture on which we can apply other methods
         */
        CompletableFuture<Timer> futureResult1 = CompletableFuture.supplyAsync(() -> startFibonacci(FIBO_1));
        CompletableFuture<Timer> futureResult2 = CompletableFuture.supplyAsync(() -> startFibonacci(FIBO_2));

        /*
         * The CompletableFuture.get() method is blocking. It waits until the Future is
         * completed and returns the result after its completion.
         */
        Timer asyncResult1 = futureResult1.get();
        Timer asyncResult2 = futureResult2.get();
        double asyncDuration = calculateTime(asyncResult1, asyncResult2);
        System.out.printf("  Total calculation duration = %.3f seconds%n", asyncDuration);

        // Display time difference as a percentage
        String percentage = NumberFormat.getPercentInstance()
                .format((recursiveDuration - asyncDuration) / asyncDuration);
        System.out.printf("\nRecursive calculations took %s" + " more time than the asynchronous ones\n", percentage);
    }

    /**
     * A method to start the Fibonacci calculation and time measurement. Returns a
     * Timer object which contains the elapsed time of calculation.
     * 
     * @param n the nth Fibonacci number to calculate
     */
    private static Timer startFibonacci(int n) {
        // Create a Timer object to store times
        Timer Timer = new Timer();
        System.out.printf("  Calculating fibonacci(%d)%n", n);

        // Start time
        Timer.start = Instant.now();
        long fibonacciValue = fiboRecur(n);
        // End time
        Timer.end = Instant.now();

        displayResult(n, fibonacciValue, Timer);
        return Timer;
    }

    /**
     * A recursive method to calculate the nth Fibonacci number.
     * 
     * @param n the nth Fibonacci number to calculate
     */
    private static long fiboRecur(long n) {
        if (n == 0 || n == 1) {
            return n;
        } else {
            return fiboRecur(n - 1) + fiboRecur(n - 2);
        }
    }

    /**
     * A void method to display the results of the Fibonacci calculation.
     * 
     * @param n     the nth Fibonacci number to calculate
     * @param value the result of the nth Fibonacci number calculation
     * @param Timer Timer object containing elapsed time in seconds
     */
    private static void displayResult(int n, long value, Timer elapsedTime) {
        System.out.printf("  fibonacci(%d) = %d%n", n, value);
        System.out.printf("  Calculation time for fibonacci(%d) = %.3f seconds%n", n, elapsedTime.timeInSeconds());
    }

    /**
     * A method to combine the elapsed time of calculation of 2 Fibonacci values.
     * Returns the total elapsed time in seconds.
     * 
     * @param result1 elapsed time of 1st Fibonacci value
     * @param result2 elapsed time of 2nd Fibonacci value
     */
    private static double calculateTime(Timer result1, Timer result2) {

        Timer bothThreads = new Timer();

        // Determine earlier start time using ternary operator
        bothThreads.start = result1.start.compareTo(result2.start) < 0 ? result1.start : result2.start;

        // Determine later end time using ternary operator
        bothThreads.end = result1.end.compareTo(result2.end) > 0 ? result1.end : result2.end;

        return bothThreads.timeInSeconds();
    }
}
