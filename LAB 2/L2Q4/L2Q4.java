package L2Q4;
import java.util.Random;

public class L2Q4 {

    public static final int SIZE = 1000000;
    public static final int MAX = 60000;
    public static final int NO_OF_THREADS = 4;

    public static void main(String args[]) {

        // Initialise array
        int[] arr = randomArray(new int[SIZE], MAX);
        int finalMax;
        Timer timer = new Timer();

        // Timer start
        timer.start();
        findLargestSequential fls = new findLargestSequential(arr);
        finalMax = fls.getMax();
        // Timer end
        timer.end();

        System.out.println("Largest number from 1 thread: " + finalMax);
        System.out.println("Duration: " + (double) timer.timeTaken() / 1000000 + " ms\n");

        // Timer start
        timer.start();
        findLargestConcurrent[] flc = new findLargestConcurrent[NO_OF_THREADS];

        // Divide array equally among threads and start threads
        int range = arr.length / NO_OF_THREADS;
        for (int i = 0; i < NO_OF_THREADS; i++) {
            int startAt = i * range;
            int endAt = startAt + range;
            flc[i] = new findLargestConcurrent(startAt, endAt, arr);
            flc[i].thread.start();
        }

        for (int i = 0; i < NO_OF_THREADS; i++) {
            try {
                flc[i].thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        finalMax = flc[0].getMax();
        for (int i = 0; i < NO_OF_THREADS; i++) {
            // System.out.println("Largest number in thread " + (i + 1) + " = " + flc[i].getMax());
            if (finalMax < flc[i].getMax())
                finalMax = flc[i].getMax();
        }

        // Timer end
        timer.end();

        System.out.println("Largest number from " + NO_OF_THREADS + " threads: " + finalMax);
        System.out.println("Duration: " + (double) timer.timeTaken() / 1000000 + " ms\n");
    }

    private static int[] randomArray(int[] arr, int max) {
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(max) + 1;
        }

        return arr;
    }

}

class findLargestConcurrent implements Runnable {

    private int beginAt;
    private int endAt;
    private int[] array;
    public int max;
    public Thread thread;

    public findLargestConcurrent(int beginAt, int endAt, int[] array) {
        this.beginAt = beginAt;
        this.endAt = endAt;
        this.array = array;
        thread = new Thread(this);
    }

    public void run() {
        max = array[beginAt];
        for (int i = beginAt; i < endAt; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
    }

    public int getMax() {
        return max;
    }
}

class Timer {
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void end() {
        endTime = System.nanoTime();
    }

    public long timeTaken() {
        return endTime - startTime;
    }
}

class findLargestSequential {
    private int[] array;

    public findLargestSequential(int[] array) {
        this.array = array;
    }

    public int getMax() {
        int size = array.length;
        int max = array[0];

        for (int i = 1; i < size; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

}
