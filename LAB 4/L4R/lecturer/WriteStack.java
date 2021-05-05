package L4R.lecturer;
import java.util.Random;

public class WriteStack implements Runnable {
    private StackAccess sa;
    
    public WriteStack(StackAccess sa) {
        this.sa = sa;
    }
    
    public void run() {
        int value;
        try {
            for (int i = 0; i<4 ; i++) {
                value = new Random().nextInt(100);
                sa.push(value);
                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


