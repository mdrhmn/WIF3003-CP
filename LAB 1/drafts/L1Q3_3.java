package drafts;

public class L1Q3_3 {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        final double AMOUNT = 100;
        final int REPETITIONS = 10;
        // final int THREADS = 2;

        // for (int i = 0; i < THREADS; i++) {
        // DepositRunnable d = new DepositRunnable(account, AMOUNT, REPETITIONS);
        // WithdrawRunnable w = new WithdrawRunnable(account, AMOUNT, REPETITIONS);
        // Thread dt = new Thread(d);
        // Thread wt = new Thread(w);
        // dt.start();
        // wt.start();
        // }

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

    public void deposit(double amount) {
        // System.out.println("Depositing " + amount);
        double newBalance = balance + amount;
        // System.out.println("New balance is " + newBalance);
        balance = newBalance;
    }

    public void withdraw(double amount) {
        // System.out.println("Withdrawing " + amount);
        double newBalance = balance - amount;
        // System.out.println("New balance is " + newBalance);
        balance = newBalance;
    }

    public double getBalance() {
        return balance;
    }
}

class WithdrawRunnable implements Runnable {
    // private static final int DELAY = 1;
    private BankAccount account;
    private double amount;
    private int count;

    public WithdrawRunnable(BankAccount account, double amount, int count) {
        this.account = account;
        this.amount = amount;
        this.count = count;
    }

    @Override
    public void run() {
        // try {
        //     for (int i = 0; i < count; i++) {
        //         makeWithdrawal(amount);
        //         Thread.sleep(DELAY);
        //     }
        // } catch (InterruptedException e) {
        // }

        for (int i = 0; i < count; i++) {
            makeWithdrawal(amount);
        }
    }

    private synchronized void makeWithdrawal(double amount) {
        if (account.getBalance() >= amount) {
            try {
                System.out.println(Thread.currentThread().getName() + " is going to withdraw " + amount);
                account.withdraw(amount);
                System.out.println(
                        Thread.currentThread().getName() + " completes the withdrawal. New balance is " + account.getBalance());
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
        } else {
            System.out.println("Not enough in account for " + Thread.currentThread().getName() + " to withdraw "
                    + account.getBalance());
        }
    }
}

class DepositRunnable implements Runnable {
    // private static final int DELAY = 1;
    private BankAccount account;
    private double amount;
    private int count;

    public DepositRunnable(BankAccount account, double amount, int count) {
        this.account = account;
        this.amount = amount;
        this.count = count;
    }

    @Override
    public void run() {
        // try {
        //     for (int i = 0; i < count; i++) {
        //         makeDeposit(amount);
        //         Thread.sleep(DELAY);
        //     }
        // } catch (InterruptedException e) {
        // }

        for (int i = 0; i < count; i++) {
            makeDeposit(amount);
        }
    }

    private synchronized void makeDeposit(double amount) {
        try {
            System.out.println(Thread.currentThread().getName() + " is going to deposit " + amount);
            account.deposit(amount);
            System.out.println(Thread.currentThread().getName() + " completes the deposit. New balance is " + account.getBalance());
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
    }

}