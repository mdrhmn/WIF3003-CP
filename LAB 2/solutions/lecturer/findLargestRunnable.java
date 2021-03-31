package solutions.lecturer;
public class findLargestRunnable implements Runnable {

    private int beginAt;
    private int endAt;
    private int[] array;
    private int max;

    public findLargestRunnable(int beginAt, int endAt, int[] array) {
        this.beginAt = beginAt;
        this.endAt = endAt;
        this.array = array;
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
