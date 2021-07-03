public class Q5 {

    private final static int MAX_INT = 111111;
    private volatile static int maxDivisorCount = 0;
    private volatile static int intWithMaxDivisorCount;

    public static void main(String[] args) {
        int numOfThreads = 2;

        System.out.println("\nCounting divisors using " + numOfThreads + " threads...");
        long startTime = System.currentTimeMillis();

        CountDivisorsRunnable[] threadArr = new CountDivisorsRunnable[numOfThreads];
        Thread t[] = new Thread[numOfThreads];
        int intsInThread = MAX_INT / numOfThreads;

        int start = 1;
        int end = start + intsInThread - 1;

        for (int i = 0; i < numOfThreads; i++) {
            if (i == numOfThreads - 1) {
                end = MAX_INT;
            }

            threadArr[i] = new CountDivisorsRunnable(start, end);
            t[i] = new Thread(threadArr[i]);
            start = end + 1;
            end = start + intsInThread - 1;
        }

        maxDivisorCount = 0;

        // Start threads
        for (int i = 0; i < numOfThreads; i++)
            t[i].start();

        // Join threads to ensure all threads have completed before termination
        for (int i = 0; i < numOfThreads; i++) {
            while (t[i].isAlive()) {
                try {
                    t[i].join();
                } catch (InterruptedException e) {
                }
            }
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("\nMaximum divisor " + "for numbers between 1 and " + MAX_INT + " is: " + maxDivisorCount);
        System.out.println("An integer with max divisor " + maxDivisorCount + " is: " + intWithMaxDivisorCount);
        System.out.println("Total elapsed time: " + (elapsedTime / 1000.0) + " seconds.\n");
    }

    synchronized private static void printResult(int maxCountFromThread, int intWithMaxFromThread) {
        if (maxCountFromThread > maxDivisorCount) {
            maxDivisorCount = maxCountFromThread;
            intWithMaxDivisorCount = intWithMaxFromThread;
        }
    }

    private static class CountDivisorsRunnable implements Runnable {
        int min, max;

        public CountDivisorsRunnable(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public void run() {
            int maxDivisors = 0;
            int whichInt = 0;
            for (int i = min; i < max; i++) {
                int divisors = countDivisors(i);
                if (divisors > maxDivisors) {
                    maxDivisors = divisors;
                    whichInt = i;
                }
            }
            printResult(maxDivisors, whichInt);
        }
    }

    public static int countDivisors(int N) {
        int count = 0;
        for (int i = 1; i <= N; i++) {
            if (N % i == 0)
                count++;
        }
        return count;
    }

}
