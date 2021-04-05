package solutions.faidz;

public class Timer {
    long start;
    long finish;
    long duration;
    
    public Timer (){
        
    }
    
    public void start(){
        start = System.nanoTime();
    }
    
    public void end() {
        finish = System.nanoTime();
    }
    
    public long getDuration(){
        duration = finish - start;
        return duration;
    }
}
