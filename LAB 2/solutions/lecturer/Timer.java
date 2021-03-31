package solutions.lecturer;

public class Timer {
    private long startTime;
    private long finishTime;
    
    public void start(){
        startTime = System.nanoTime();
    }
    
    public void finish(){
        finishTime = System.nanoTime();
    }
    
    public long timeTaken(){
        return finishTime - startTime;
    }
}
