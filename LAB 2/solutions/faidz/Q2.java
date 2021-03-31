package solutions.faidz;

import java.util.Random;

/**
 * Repeat the above program but using two threads to concurrently find the largest number.
 */
public class Q2 {
    public static void main(String[] args){
        int []num = new int[60000];
        Random r = new Random(); 
        int x;
        
        for (int i = 0; i < num.length; i++){
            x = r.nextInt(60000) + 1;
            num[i] = x;
        }
        
        Thread[] t = new Thread[2];
        findLargestNumber[] lg = new findLargestNumber[2];
        
        int range = num.length / 2;
        for (int i = 0; i < 2; i++) {
            int startAt = i * range;
            int endAt = startAt + range;
            lg[i] = new findLargestNumber(startAt, endAt, num);
        }

        for (int i = 0; i < 2; i++) {
            t[i] = new Thread(lg[i]);
            t[i].start();
        }
        
        for (int i = 0; i<2; i++){
            try {
                t[i].join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        
        int finalMax = lg[0].getMax();
        System.out.println("Largest number in thread 1 = " + lg[0].getMax());
        for (int i = 1; i<2 ; i++) {
            System.out.println("Largest number in thread " + i + " = " + lg[i].getMax());
            if(finalMax < lg[i].getMax())        
                finalMax = lg[i].getMax();
        }
        
        System.out.println("Largest number from both threads: " + finalMax );
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

    public void run(){
        for (int index = startAt; index < endAt; index++) {
            if (max < num[index])
                max = num[index];
        }
    }

    public int getMax() {
        return max;
    }
}