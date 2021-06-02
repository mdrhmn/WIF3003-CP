import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Chiew Thiam Kian
 */
class Employee {

    String name;
    String gender;
    int age;

    Employee(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return name;
    }

}

/**
 * QUESTION 1:
 * 
 * Create an Employee class that represent an Employee. Use Java stream to
 * filter and print all female employees aged 21 and above. Provide two
 * alternative solutions.
 * 
 * https://www.journaldev.com/14068/java-stream-filter
 * https://dzone.com/articles/single-filter-perform-better-than-multiple-one-in
 */
public class L7Q1 {
    public static void main(String[] args) {
        List<Employee> Employees = Arrays.asList(new Employee("Poseidon", "M", 23), new Employee("Hera", "F", 18),
                new Employee("Apollo", "M", 20), new Employee("Athena", "F", 35), new Employee("Demeter", "F", 50));

        // Solution 1: Filter gender and then age
        System.out.println("Solution 1: Filter gender and then age");
        Instant start = Instant.now();
        // Stream is useful when dealing with large amount of data without modifying it
        Employees.stream()
                // Filter all Female employees
                .filter(e -> e.gender.equalsIgnoreCase("F"))
                // Filter all Female employees aged 21 and above
                .filter(e -> e.age >= 21).forEach(System.out::println);
        Instant end = Instant.now();
        System.out.println("Time taken for Solution 1: " + Duration.between(start, end).toMillis() + " ms\n");

        /*
         * Output: Athena Demeter
         */

        // Solution 2: Filter age and then gender
        System.out.println("Solution 2: Filter age and then gender");
        start = Instant.now();
        Employees.stream()
                // Filter all employees aged 21 and above
                .filter(e -> e.age >= 21).filter(e -> e.gender.equalsIgnoreCase("F"))
                // Filter all Female employees aged 21 and above
                .forEach(System.out::println);
        end = Instant.now();
        System.out.println("Time taken for Solution 2: " + Duration.between(start, end).toMillis() + " ms\n");
        /*
         * Output: Athena Demeter
         */

        // Solution 3: Filter age and gender using single filter
        System.out.println("Solution 3: Filter age and gender using single filter");
        start = Instant.now();
        Employees.stream()
                // Filter all employees aged 21 and above
                .filter(e -> e.age >= 21 && e.gender.startsWith("F"))
                // Filter all Female employees aged 21 and above
                .forEach(System.out::println);
        end = Instant.now();
        System.out.println("Time taken for Solution 3: " + Duration.between(start, end).toMillis() + " ms");
        /*
         * Output: Athena Demeter
         */

    }
}