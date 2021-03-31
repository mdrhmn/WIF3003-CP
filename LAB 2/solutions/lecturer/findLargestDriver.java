
package solutions.lecturer;

import java.util.Random;

public class findLargestDriver {
    public static int size = 1000000;
    public static int range = 50000;

    public static void main(String[] args) {
        final int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            Random rand = new Random();
            array[i] = rand.nextInt(range) + 1;
        }

        Timer t = new Timer();
        int max;
        
        t.start();
        findLargestSequential ms1 = new findLargestSequential(array);
        max = ms1.getMax();
        t.finish();

        System.out.println("Max is " + max);
        System.out.println("Single thread: Time taken = " + (double) t.timeTaken() / 1000000 + " ms\n");

        t.start();
        findLargestConcurrent fmc2 = new findLargestConcurrent(array, 2);
        max = fmc2.getMax();
        t.finish();

        System.out.println("Max is " + max);
        System.out.println("Two threads: Time taken = " + (double) t.timeTaken() / 1000000 + " ms\n");

        t.start();
        findLargestConcurrent fmc4 = new findLargestConcurrent(array, 4);
        max = fmc4.getMax();
        t.finish();

        System.out.println("Max is " + max);
        System.out.println("Four threads: Time taken = " + (double) t.timeTaken() / 1000000 + " ms\n");
    }

}
