package L3Q2;

public class Dummy implements Runnable {
    
    public void run() {
        System.out.println("The desired value is found!");
        Thread.currentThread().interrupt();
    }
}
