public class L1Q2 {
    public static void main(String args[]) {
        PrintNumRunnableInterleave e1 = new PrintNumRunnableInterleave(5);
        Thread t1 = new Thread(e1);

        PrintCharRunnableInterleave e2 = new PrintCharRunnableInterleave('A', 5);
        Thread t2 = new Thread(e2);

        PrintCharRunnableInterleave e3 = new PrintCharRunnableInterleave('B', 5);
        Thread t3 = new Thread(e3);

        t2.start();
        t3.start();
        t1.start();

    }
}

// Approach 2: Implementing Runnable Interface
class PrintNumRunnableInterleave implements Runnable {
    private int times;

    public PrintNumRunnableInterleave(int times) {
        this.times = times;
    }

    public void run() {

        try {
            for (int i = 0; i < times; i++) {
                System.out.println(i + 1);
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
        }
    }

}

// Approach 2: Implementing Runnable Interface
class PrintCharRunnableInterleave implements Runnable {
    private char ch;
    private int times;

    public PrintCharRunnableInterleave(char ch, int times) {
        this.ch = ch;
        this.times = times;
    }

    public void run() {
        try {
            for (int i = 0; i < times; i++) {
                System.out.println(ch);
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
        }
    }
}