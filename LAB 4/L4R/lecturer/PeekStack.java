package L4R.lecturer;

public class PeekStack implements Runnable {
    private StackAccess sa;
    private Integer value;
    
    public PeekStack(StackAccess sa) {
        this.sa = sa;
    }
    
    public void run() {
        try {
            for (int i = 0; i<4 ; i++) {
                value = sa.peek();
                Thread.sleep(50);
                
                
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public Integer getValue() {
        return value;
    }
}
