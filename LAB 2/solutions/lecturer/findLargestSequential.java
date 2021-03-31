package solutions.lecturer;
public class findLargestSequential {
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
