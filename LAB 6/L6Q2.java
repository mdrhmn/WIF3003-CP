
// Fig. 23.31: FibonacciDemo.java
// Fibonacci calculations performed synchronously and asynchronously
import java.time.Duration;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// class that stores two Instants in time
class Timer {
    public Instant start;
    public Instant end;

    // return total time in seconds
    public double timeInSeconds() {
        return Duration.between(start, end).toMillis() / 1000.0;
    }
}

public class L6Q2 {
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

    // Executes function fibonacci asynchronously
    private static Timer startFibonacci(int n) {
        // Create a Timer object to store times
        Timer Timer = new Timer();
        System.out.printf("  Calculating fibonacci(%d)%n", n);
        Timer.start = Instant.now();
        long fibonacciValue = fibonacci(n);
        Timer.end = Instant.now();
        displayResult(n, fibonacciValue, Timer);
        return Timer;
    }

    // Fibonacci (Recursive method)
    private static long fibonacci(long n) {
        if (n == 0 || n == 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    // Display fibonacci calculation result and total calculation time
    private static void displayResult(int n, long value, Timer Timer) {
        System.out.printf("  fibonacci(%d) = %d%n", n, value);
        System.out.printf("  Calculation time for fibonacci(%d) = %.3f seconds%n", n, Timer.timeInSeconds());
    }

    // Display fibonacci calculation result and total calculation time
    private static double calculateTime(Timer result1, Timer result2) {

        Timer bothThreads = new Timer();

        // Determine earlier start time
        bothThreads.start = result1.start.compareTo(result2.start) < 0 ? result1.start : result2.start;

        // Determine later end time
        bothThreads.end = result1.end.compareTo(result2.end) > 0 ? result1.end : result2.end;

        return bothThreads.timeInSeconds();
    }
}

/**************************************************************************
 * (C) Copyright 1992-2015 by Deitel & Associates, Inc. and * Pearson Education,
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this
 * book have used their * best efforts in preparing the book. These efforts
 * include the * development, research, and testing of the theories and programs
 * * to determine their effectiveness. The authors and publisher make * no
 * warranty of any kind, expressed or implied, with regard to these * programs
 * or to the documentation contained in these books. The authors * and publisher
 * shall not be liable in any event for incidental or * consequential damages in
 * connection with, or arising out of, the * furnishing, performance, or use of
 * these programs. *
 *************************************************************************/
