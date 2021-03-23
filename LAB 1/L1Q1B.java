//Approach 2: Implementing Runnable Interface
class PrintNum implements Runnable {
    private int times;

    public PrintNum(int times) {
        this.times = times;
    }
    
    public void run() {
        for (int i = 0; i < times; i++) {
            System.out.println(i + 1);
        }
    }
}


public class L1Q1B {
    public static void main(String args[]) {
        PrintNum e1 = new PrintNum(5);
        Thread t1 = new Thread(e1);
        t1.start();
    }
}