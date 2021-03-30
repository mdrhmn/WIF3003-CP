package L2Q1;
import java.util.Random;

public class L2Q1 {
    public static void main(String args[]) {
        int[] numbers = new int[10];
        randomArray(numbers);
        int max = findLargest(numbers);
        System.out.println("Max (using iteration) = " + max);
    }

    private static void randomArray(int[] arr) {
        int min = 1;
        int max = 60001;
        Random random = new Random();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(max - min) + min; // generate a random number
        }

    }

    // Method to find maximum in arr[]
    private static int findLargest(int arr[])
    {
        // Initialize maximum element
        int max = arr[0];
       
        // Traverse array elements from second and
        // compare every element with current max  
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];
       
        return max;
    }
}