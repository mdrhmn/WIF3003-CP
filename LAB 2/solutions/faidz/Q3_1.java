
package solutions.faidz;

import java.util.Random;
// import java.util.concurrent.TimeUnit;

/**
 * Write a Timer class that can record a start time, an end time, and return the
 * difference between the two times. Using the Timer class, modify the programs
 * in (1) and (2) above to measure and compare the performance of the programs
 * in competing their tasks.
 */

public class Q3_1 {
    public static void main(String[] args) {
        int[] num = new int[60000];
        Random r = new Random();
        int x;

        for (int i = 0; i < num.length; i++) {
            x = r.nextInt(60000) + 1;
            num[i] = x;
        }

        Timer ti = new Timer();
        ti.start();

        System.out.println("Largest Number : " + largestNumber(num));
        ti.end();

        // System.out.println("Duration: " + ti.getDuration());
        System.out.println("Duration: " + (float) ti.getDuration() / 1000000);

    }

    static int largestNumber(int[] mArray) {
        int max = mArray[0];

        for (int i = 0; i < mArray.length; i++) {
            if (max < mArray[i]) {
                max = mArray[i];
            }
        }

        return max;
    }

}