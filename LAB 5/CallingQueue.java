
    public class CallingQueue implements Runnable{
        private BankingQueue BQ;
        
        public CallingQueue (BankingQueue BQ) {
            this.BQ = BQ;
        }
        
        public void run() {
            while (BQ.getNextInLine() <= 10) {
                System.out.format("Calling for the customer %d\n", BQ.getNextInLine() );
                BQ.incrementNextInLine();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    
}
