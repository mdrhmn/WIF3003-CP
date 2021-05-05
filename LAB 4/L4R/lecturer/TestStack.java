package L4R.lecturer;
import java.util.concurrent.*;


public class TestStack {
    
    public static void main(String[] args) {
        StackAccess sa = new StackAccess();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(new ReadStack(sa));
        executor.execute(new WriteStack(sa));
//        executor.execute(new PeekStack(sa));
        executor.execute(new ReadStack(sa));
//        executor.execute(new WriteStack(sa));
//        executor.execute(new ReadStack(sa));
//        executor.execute(new PeekStack(sa));
        executor.shutdown();
    }
}
