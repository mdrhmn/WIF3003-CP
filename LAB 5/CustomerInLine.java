
public class CustomerInLine implements Runnable {
    private BankingQueue BQ;
    private int targetNumber;
    
    public CustomerInLine(BankingQueue BQ, int targetNumber) {
        this.BQ = BQ;
        this.targetNumber = targetNumber;
        
    }
    
    public void run() {
        while(true) {
            if(BQ.getNextInLine() >= targetNumber) {
                break;
            }
        }
        
        System.out.format("Great, finally #%d was called, now it is my turn\n", targetNumber);
    }
    
}
