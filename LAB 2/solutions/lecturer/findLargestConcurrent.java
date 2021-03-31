package solutions.lecturer;

public class findLargestConcurrent {
    private int[] array;
    private int numOfThreads;

    public findLargestConcurrent(int[] array, int numOfThreads) {
        this.array = array;
        this.numOfThreads = numOfThreads;
    }

    public int getMax() {
        int size = array.length;
        findLargestRunnable fmw[] = new findLargestRunnable[numOfThreads];
        Thread t[] = new Thread[numOfThreads];

        int begin = 0;
        int range = array.length / numOfThreads;
        int next = range;
        for (int i = 0; i < numOfThreads; i++) {
            if (i == numOfThreads - 1) {
                next = size;
            }

            fmw[i] = new findLargestRunnable(begin, next, array);
            t[i] = new Thread(fmw[i]);
            t[i].start();

            begin = next;
            next += range;
        }

        try {
            for (int i = 0; i < numOfThreads; i++) {
                t[i].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        int max = fmw[0].getMax();
        for (int i = 1; i < numOfThreads; i++) {
            if (fmw[i].getMax() > max) {
                max = fmw[i].getMax();
            }
        }

        return max;
    }

}
