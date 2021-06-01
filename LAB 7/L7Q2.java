import java.util.stream.*;

/**
 * QUESTION 2:
 * 
 * Use a parallel stream to count number of prime numbers between 1 and 50.
 * Write your program in such a way so that you can display which worker thread
 * processes which numbers.
 * 
 * https://mkyong.com/java8/java-8-parallel-streams-examples/
 */
public class L7Q2 {
    public static void main(String[] args) {
        // Solution 1: Using parallel() method
        // long count = Stream.iterate(0, n -> n+1)
        // .limit(50)
        // .parallel()
        // .filter(L7Q2::checkPrime)
        // .peek(x -> System.out.println(x))
        // .count();

        // Solution 2: Using parallelStream() method
        long count =
                // Returns an IntStream from startInclusive (inclusive) to endInclusive
                // (inclusive) by an incremental step of 1.
                IntStream.rangeClosed(1, 50)
                        // Intermediate operation to return a Stream consisting of the elements of
                        // this stream, each boxed to an Integer (convert int stream to List of
                        // Integers)
                        .boxed()
                        // Terminal operation that transforms the elements of the stream into a List
                        .collect(Collectors.toList())
                        // Create a parallel stream of elements that utilizes all available threads from
                        // the common ForkJoinPool for executing the stream operations.
                        .parallelStream()
                        // Filter prime numbers
                        .filter(L7Q2::checkPrime)
                        // Print processing worker thread and identified prime number using peek()
                        // intermediate operation
                        .peek(x -> System.out.println(Thread.currentThread().getName() + " - " + x))
                        // Terminal operation that returns the count of elements in the stream
                        .count();

        // The primes from 1 to 50 are 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,
        // 43, and 47. (15 in total)
        System.out.println("\nTotal prime numbers between 1 and 50 (inclusive): " + count);
    }

    public static boolean checkPrime(int num) {
        if (num <= 1)
            return false;
        // Check if prime number is divisible by 2 (have remainders or not)
        return !IntStream.rangeClosed(2, num / 2).anyMatch(x -> num % x == 0);
    }
}
