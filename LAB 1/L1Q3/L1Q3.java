package L1Q3;

public class L1Q3 {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        final double AMOUNT = 100;
        final int REPETITIONS = 10;

        DepositRunnable d = new DepositRunnable(account, AMOUNT, REPETITIONS);
        WithdrawRunnable w = new WithdrawRunnable(account, AMOUNT, REPETITIONS);
        Thread dt = new Thread(d);
        Thread wt = new Thread(w);
        dt.setName("Ray");
        wt.setName("Faidz");
        dt.start();
        wt.start();
    }
}

class BankAccount {
    private double balance;

    public BankAccount() {
        balance = 0;
    }

    // Critical section
    public synchronized void deposit(double amount) {
        System.out.println(Thread.currentThread().getName() + " is going to deposit " + amount);
        double newBalance = balance + amount;
        System.out.println(Thread.currentThread().getName() + " completes the deposit. New balance is " + newBalance);
        balance = newBalance;

    }

    // Critical section
    public synchronized void withdraw(double amount) {
        if (this.getBalance() >= amount) {
            System.out.println(Thread.currentThread().getName() + " is going to withdraw " + amount);
            double newBalance = balance - amount;
            balance = newBalance;
            System.out.println(
                    Thread.currentThread().getName() + " completes the withdrawal. New balance is " + newBalance);
        } else {
            System.out.println("Not enough in account for " + Thread.currentThread().getName() + " to withdraw "
                    + this.getBalance());
        }
    }

    public double getBalance() {
        return balance;
    }
}

class WithdrawRunnable implements Runnable {
    private static final int DELAY = 1;
    private BankAccount account;
    private double amount;
    private int count;

    public WithdrawRunnable(BankAccount account, double amount, int count) {
        this.account = account;
        this.amount = amount;
        this.count = count;
    }

    public void run() {
        try {
            for (int i = 1; i <= count; i++) {
                account.withdraw(amount);
                Thread.sleep(DELAY);
            }
        } catch (InterruptedException exception) {
        }
    }
}

class DepositRunnable implements Runnable {
    private static final int DELAY = 1;
    private BankAccount account;
    private double amount;
    private int count;

    public DepositRunnable(BankAccount account, double amount, int count) {
        this.account = account;
        this.amount = amount;
        this.count = count;
    }

    public void run() {
        try {
            for (int i = 1; i <= count; i++) {
                account.deposit(amount);
                Thread.sleep(DELAY);
            }
        } catch (InterruptedException exception) {
        }
    }
}