
package solutions;

import java.util.Random;

/**
 * Write a Timer class that can record a start time, an end time, and return the
 * difference between the two times. Using the Timer class, modify the programs
 * in (1) and (2) above to measure and compare the performance of the programs
 * in competing their tasks.
 */

public class Q3_2 {
    public static void main(String[] args) {
        int[] num = new int[10];
        Random r = new Random();
        int x = 0;

        for (int i = 0; i < num.length; i++) {
            x = r.nextInt(60000) + 1;
            num[i] = x;
        }

        Thread[] t = new Thread[2];
        findLargestNumber[] lg = new findLargestNumber[2];
        Timer ti = new Timer();

        int range = num.length / 2;
        for (int i = 0; i < 2; i++) {
            int startAt = i * range;
            int endAt = startAt + range;
            lg[i] = new findLargestNumber(startAt, endAt, num);
        }

        ti.start();
        for (int i = 0; i < 2; i++) {
            t[i] = new Thread(lg[i]);
            t[i].start();
        }

        for (int i = 0; i < 2; i++) {
            try {
                t[i].join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        int finalMax = lg[0].getMax();
        for (int i = 0; i < 2; i++) {
            System.out.println("Largest number in thread " + (i + 1) + " = " + lg[i].getMax());
            if (finalMax < lg[i].getMax())
                finalMax = lg[i].getMax();
        }
        ti.end();

        System.out.println("Largest number from both threads: " + finalMax);
        // System.out.println("Duration: " + ti.getDuration());
        System.out.println("Duration: " + (float) ti.getDuration() / 1000000);
    }
}

class findLargestNumber implements Runnable {
    private int startAt;
    private int endAt;
    private int num[];

    int max = 1;

    public findLargestNumber(int startAt, int endAt, int[] num) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.num = num;
    }

    public void run() {
        for (int index = startAt; index < endAt; index++) {
            if (max < num[index])
                max = num[index];
        }
    }

    public int getMax() {
        return max;
    }
}

class Timer {
    long start;
    long finish;
    long duration;

    public Timer() {

    }

    public void start() {
        start = System.nanoTime();
    }

    public void end() {
        finish = System.nanoTime();
    }

    public long getDuration() {
        duration = finish - start;
        return duration;
    }
}