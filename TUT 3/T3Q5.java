import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class T3Q5 {
    public static void main(String[] args) {
        Account account = new Account();

        ExecutorService executor = Executors.newCachedThreadPool();

        // Create and launch 100 threads
        for (int i = 0; i < 10; i++) {
            executor.execute(new addRM1(account));
        }

        executor.shutdown();

        // Wait until all tasks are finished
        while (!executor.isTerminated()) {
        }

        System.out.println("Final Balance (RM) = " + account.getBalance());
    }
}

class Account {

    AtomicInteger balance = new AtomicInteger(0);

    public int getBalance() {
        return balance.get();
    }

    public void deposit(int amount) {
        try {
            balance.set(balance.addAndGet(amount)); // Same as int newBalance = balance + lock
            // Using balance.get(), the print output may be incorrect due to lack of
            // synchronization. However, the final balance will still be correct
            System.out.println(Thread.currentThread().getName() + ":\tNew Balance (RM) = " + balance.get());
            Thread.sleep(5);

            // Alternative solution
            // System.out.println(Thread.currentThread().getName() + ":\tNew Balance (RM) =
            // " + balance.incrementAndGet());
            // Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class addRM1 implements Runnable {

    Account account;

    public addRM1(Account account) {
        this.account = account;
    }

    public void run() {
        account.deposit(1);
    }
}