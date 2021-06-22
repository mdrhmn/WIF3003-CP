package LAMBDA_EXPRESSIONS;
/* 
 * java.util.function.Predicate is a functional interface that can be used as 
 * assignment target for lambda expression. It represents an operation that 
 * takes a single input and returns a boolean value. The interface has an abstract 
 * method called test which evaluates the predicate on the given argument. Write an 
 * evaluate method which receives two arguments: a List of integers and a predicate. 
 * It then evaluates each element in the List against the argument given to the predicate 
 * and prints the element if the evaluation returns true. With an array of 10 integers, 
 * use this method to print:
 * 
 * a. All the elements
 * b. All the odd elements
 * c. All the even elements
 * d. All the elements that are greater than 5
 */

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class L6Q2 {

    private static void evaluate(List<Integer> listOfInt, Predicate<Integer> predicate) {
        listOfInt.forEach((num) -> {
            if (predicate.test(num)) {
                System.out.print(num + " ");
            }
        });
        System.out.println("");
    }

    public static void main(String[] args) { 
        System.out.println("Question 2");
        System.out.println("==========\n");

        Integer[] listArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        List<Integer> list = Arrays.asList(listArray);
        Predicate<Integer> allElement = (num) -> true;
        Predicate<Integer> oddNumber = (num) -> num % 2 != 0;
        Predicate<Integer> evenNumber = (num) -> num % 2 == 0;
        Predicate<Integer> greaterThanFive = (num) -> num > 5;

        System.out.println("All Elements:");
        evaluate(list, allElement);
        System.out.println("Even Number(s)");
        evaluate(list, evenNumber);
        System.out.println("Odd Number(s):");
        evaluate(list, oddNumber);
        System.out.println("Greater Than Five:");
        evaluate(list, greaterThanFive);
    }
}
