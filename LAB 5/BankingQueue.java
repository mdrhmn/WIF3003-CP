
import java.util.Random;


public class BankingQueue {
    static volatile int nextInLine = 1;
    
    public int getNextInLine() {
        return nextInLine;
    }
    
    public void incrementNextInLine() {
        nextInLine++;
    }
    
    public static void main(String[] args) throws Exception {
        BankingQueue BQ = new BankingQueue();
        Runnable cq = new CallingQueue(BQ);
        int x = new Random().nextInt(10) + 1;
        System.out.println("Target number: " + x);
        Runnable cil = new CustomerInLine(BQ, x);
        Thread t1 = new Thread(cq);
        Thread t2 = new Thread(cil);
        t1.start();
        t2.start();
    }
}
