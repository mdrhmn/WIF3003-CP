package solutions;

import java.util.Random;

/**
 * Write a Java program that sequentially finds the largest number in an 
 * array of integers with 60,000 elements filled with randomly generated numbers 
 * in the range of 1 to 60,000.
 */

public class Q1 {
    public static void main(String[] args){
        int []num = new int[60000];
        Random r = new Random(); 
        int x;
        
        for (int i = 0; i < num.length; i++)
        {
            x = r.nextInt(60000) + 1;
            num[i] = x;
        }
        
       System.out.println("Largest Number : "+ largestNumber(num));
    }
    
    static int largestNumber(int[] mArray){
        int max = mArray[0];
        
        for(int i=0;i< mArray.length;i++){
            if(max < mArray[i]){
                max = mArray[i];
            }
        }

        return max;
    }
}
