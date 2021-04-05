
package solutions.faidz;

import java.util.Random;

public class Q4 {
    public static void main(String[] args) {
        int[] num = new int[1000000];
        Random r = new Random();
        int x;

        for (int i = 0; i < num.length; i++) {
            x = r.nextInt(60000) + 1;
            num[i] = x;
        }

        Thread[] t = new Thread[4];
        findLargestNumber[] lg = new findLargestNumber[4];
        Timer ti = new Timer();

        int range = num.length / 4;
        for (int i = 0; i < 4; i++) {
            int startAt = i * range;
            int endAt = startAt + range;
            lg[i] = new findLargestNumber(startAt, endAt, num);
        }

        ti.start();
        for (int i = 0; i < 4; i++) {
            t[i] = new Thread(lg[i]);
            t[i].start();
        }

        for (int i = 0; i < 4; i++) {
            try {
                t[i].join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        int finalMax = lg[0].getMax();
        for (int i = 0; i < 4; i++) {
            // System.out.println("Largest number in thread " + (i + 1) + " = " + lg[i].getMax());
            if (finalMax < lg[i].getMax())
                finalMax = lg[i].getMax();
        }
        ti.end();

        System.out.println("Largest number from all threads: " + finalMax);
        // System.out.println("Duration: " + ti.getDuration());
        System.out.println("Duration: " + (float) ti.getDuration() / 1000000);
    }
}