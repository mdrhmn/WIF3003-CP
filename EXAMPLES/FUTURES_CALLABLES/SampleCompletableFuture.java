package FUTURES_CALLABLES;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SampleCompletableFuture {
    public static void main(String[] args) {

        /*
         * You can use thenApply() method to process and transform the result of a
         * CompletableFuture when it arrives. That way, we won’t need to wait for the
         * result, and we can write the logic that needs to be executed after the
         * completion of the Future inside our callback function.
         */
        CompletableFuture<String> data = createCompletableFuture().thenApply((Integer count) -> {
            int transformedValue = count * 10;
            return transformedValue;
        }).thenApply(transformed -> "Finally creates a string: " + transformed);

        /*
         * Run a task asynchronously and return the result using supplyAsync() and
         * lambda expression. The supplyAsync() method returns CompletableFuture on
         * which we can apply other methods.
         * 
         * CompletableFuture.thenAccept() takes a Consumer<T> and returns
         * CompletableFuture<Void>. It has access to the result of the CompletableFuture
         * on which it is attached.
         */
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Meow");
        CompletableFuture<Void> future = completableFuture
                .thenAccept(string -> System.out.println("Do not return a value. Computation returned: " + string));

        try {
            System.out.println(data.get());
        } catch (InterruptedException | ExecutionException e) {
        }

    }

    public static CompletableFuture<Integer> createCompletableFuture() {
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            System.out.println("First returns an integer...");
            return 20;
        });
        return result;
    }
}
