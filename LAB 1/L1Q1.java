public class L1Q1 {
    public static void main(String args[]) {
        PrintNumRunnable e1 = new PrintNumRunnable(5);
        Thread t1 = new Thread(e1);

        PrintCharRunnable e2 = new PrintCharRunnable('A', 5);
        Thread t2 = new Thread(e2);

        PrintCharRunnable e3 = new PrintCharRunnable('B', 5);
        Thread t3 = new Thread(e3);

        t2.start();
        t3.start();
        t1.start();

    }
}

// Approach 2: Implementing Runnable Interface
class PrintNumRunnable implements Runnable {
    private int times;

    public PrintNumRunnable(int times) {
        this.times = times;
    }

    public void run() {
        for (int i = 0; i < times; i++) {
            System.out.println(i + 1);
        }
    }

}

// Approach 2: Implementing Runnable Interface
class PrintCharRunnable implements Runnable {
    private char ch;
    private int times;

    public PrintCharRunnable(char ch, int times) {
        this.ch = ch;
        this.times = times;
    }

    public void run() {
        for (int i = 0; i < times; i++) {
            System.out.println(ch);
        }
    }
}