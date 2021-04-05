package solutions.faidz;

public class findLargestNumber implements Runnable {
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