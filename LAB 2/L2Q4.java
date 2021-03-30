import java.util.Random;

public class L2Q4 {
    public static void main(String args[]) {

        int[] numbers = new int[1000000];
        randomArray(numbers);
        final int NO_OF_THREADS = 4;

        findLargest[] findMax = new findLargest[NO_OF_THREADS];
        findMax[0] = new findLargest(numbers, 0, numbers.length / 4);
        findMax[1] = new findLargest(numbers, numbers.length / 4, (2 * numbers.length)/4);
        findMax[2] = new findLargest(numbers, (2 * numbers.length)/4, (3 * numbers.length)/4);
        findMax[3] = new findLargest(numbers, (3 * numbers.length)/4, numbers.length);

        long startTime = System.nanoTime();

        for (int i = 0; i < NO_OF_THREADS; i++) {
            findMax[i].thread.start();
            try {
                findMax[i].thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int result = 0;
        for (int i = 0; i < NO_OF_THREADS; i++) {
            // System.out.println("Thread " + i + " " + findMax[i].threadMax);
            if (result < findMax[i].threadMax) {
                result = findMax[i].threadMax;
            }
        }

        // int result = Math.max(findMax[0].threadMax, findMax[1].threadMax);
        long estimatedTime = System.nanoTime() - startTime;
        double elapsedTimeInMs = (double) estimatedTime / 1_000_000;
        System.out.println("Max (using multithreading) = " + result);
        System.out.println("Execution time in ms: " + elapsedTimeInMs);
        // System.out.println("Execution time in ns: " + estimatedTime);

        // long ST_I = System.nanoTime();
        // int max = findLargest(numbers);
        // long ET_I = System.nanoTime() - ST_I;
        // double elapsed_I = (double) ET_I / 1_000_000;
        // System.out.println("Max (using iteration) = " + max);
        // System.out.println("Execution time in ms: " + elapsed_I);
    }

    private static void randomArray(int[] arr) {
        int min = 1;
        int max = 50001;
        Random random = new Random();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(max - min) + min; // generate a random number
        }

    }

    private static int findLargest(int arr[]) {
        // Initialize maximum element
        int max = arr[0];

        // Traverse array elements from second and
        // compare every element with current max
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];

        return max;
    }

}

class findLargest implements Runnable {
    private int start;
    private int end;
    private int[] arr;
    public int threadMax;
    public Thread thread;

    public findLargest(int[] arr, int start, int end) {
        this.start = start;
        this.end = end;
        this.arr = arr;
        thread = new Thread(this);
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            if (arr[i] > threadMax) {
                threadMax = arr[i];
            }
        }
    }
}