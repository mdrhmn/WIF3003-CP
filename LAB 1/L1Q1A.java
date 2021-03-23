//Approach 2: Implementing Runnable Interface
class PrintChar implements Runnable {

    private char ch;
    private int times;


    public PrintChar(char ch, int times) {
        this.ch = ch;
        this.times = times;
    }
    
    public void run() {
        for (int i = 0; i < times; i++) {
            System.out.println(ch);
        }
    }
}

public class L1Q1A {
    public static void main(String args[]) {
        PrintChar e1 = new PrintChar('A', 5);
        Thread t1 = new Thread(e1);
        t1.start();
    }
}