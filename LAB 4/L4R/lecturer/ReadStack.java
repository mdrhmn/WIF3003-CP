package L4R.lecturer;
public class ReadStack implements Runnable {
    private StackAccess sa;
    private Integer value;
    
    public ReadStack(StackAccess sa) {
        this.sa = sa;
    }
    
    public void run() {
        try {
            for (int i = 0; i<4 ; i++) {
                value = sa.pop();
                Thread.sleep(50);
                
            }
        } catch(InterruptedException e) {
        }
    }
    
    public Integer getValue() {
        return value;
    }
}

