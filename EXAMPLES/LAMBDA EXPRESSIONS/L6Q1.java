/*
 * Create a functional interface called MathOperation which has an abstract
 * method operation. Operation receives two arguments of int and returns an int.
 * Write four lambda expressions that implement the MathOperation interface:
 * addition, subtraction, multiplication and division. These lambda expressions
 * perform operation on the two int arguments as denoted by their names.
 */

interface MathOperation {
    int operation(int num1, int num2);
}

public class L6Q1 {

    public static void main(String[] args) {
        System.out.println("Question 1");
        System.out.println("==========\n");

        // Without data type, return statement and curly braces
        // MathOperation addition = (a, b) -> a + b;

        MathOperation addition = (int a, int b) -> {
            return a + b;
        };

        MathOperation subtraction = (int a, int b) -> {
            return a - b;
        };

        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        MathOperation division = (int a, int b) -> {
            return b != 0 ? a / b : b / a;
        };

        int a = 10, b = 5;

        // First way of method invocation
        System.out.println("First way of method invocation: \n");
        System.out.println("Sum: " + addition.operation(a, b));
        System.out.println("Subtract: " + subtraction.operation(a, b));
        System.out.println("Multiplication: " + multiplication.operation(a, b));
        System.out.println("Division: " + division.operation(a, b));

        // Second way of method invocation
        System.out.println("\nSecond way of method invocation: \n");
        System.out.printf("%d + %d = %s %n", a, b, addition.operation(a, b));
        System.out.printf("%d - %d = %s %n", a, b, subtraction.operation(a, b));
        System.out.printf("%d * %d = %s %n", a, b, multiplication.operation(a, b));
        System.out.printf("%d / %d = %s %n", a, b, division.operation(a, b));
    }
}
