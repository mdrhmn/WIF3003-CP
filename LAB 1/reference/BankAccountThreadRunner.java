package reference;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This program runs threads that deposit and withdraw money from the same bank
 * account.
 */
public class BankAccountThreadRunner {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        final double AMOUNT = 100;
        final int REPETITIONS = 10;
        // final int THREADS = 100;

        // for (int i = 1; i <= THREADS; i++) {
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

/**
 * A bank account has a balance that can be changed by deposits and withdrawals.
 */
class BankAccount {
    private double balance;
    private Lock balanceChangeLock;
    private Condition sufficientFundsCondition;

    /**
     * Constructs a bank account with a zero balance.
     */
    public BankAccount() {
        balance = 0;
        balanceChangeLock = new ReentrantLock();
        sufficientFundsCondition = balanceChangeLock.newCondition();
    }

    /**
     * Deposits money into the bank account.
     * 
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        balanceChangeLock.lock();
        try {
            // System.out.print("Depositing " + amount);
            System.out.println(Thread.currentThread().getName() + " is going to deposit " + amount);
            double newBalance = balance + amount;
            // System.out.println(", new balance is " + newBalance);
            balance = newBalance;
            System.out.println(Thread.currentThread().getName() + " completes the deposit. New balance is " + newBalance);

            sufficientFundsCondition.signalAll();
        } finally {
            balanceChangeLock.unlock();
        }
    }

    /**
     * Withdraws money from the bank account.
     * 
     * @param amount the amount to withdraw
     */
    public void withdraw(double amount) throws InterruptedException {
        balanceChangeLock.lock();
        try {
            while (balance < amount) {
                sufficientFundsCondition.await();
            }
            // System.out.print("Withdrawing " + amount);
            System.out.println(Thread.currentThread().getName() + " is going to withdraw " + amount);
            double newBalance = balance - amount;
            // System.out.println(", new balance is " + newBalance);
            System.out.println(
                        Thread.currentThread().getName() + " completes the withdrawal. New balance is " + newBalance);
            balance = newBalance;
        } finally {
            balanceChangeLock.unlock();
        }
    }

    /**
     * Gets the current balance of the bank account.
     * 
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }
}

/**
 * A withdraw runnable makes periodic withdrawals from a bank account.
 */
class WithdrawRunnable implements Runnable {
    private static final int DELAY = 1;
    private BankAccount account;
    private double amount;
    private int count;

    /**
     * Constructs a withdraw runnable.
     * 
     * @param anAccount the account from which to withdraw money
     * @param anAmount  the amount to withdraw in each repetition
     * @param aCount    the number of repetitions
     */
    public WithdrawRunnable(BankAccount anAccount, double anAmount, int aCount) {
        account = anAccount;
        amount = anAmount;
        count = aCount;
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

/**
 * A deposit runnable makes periodic deposits to a bank account.
 */
class DepositRunnable implements Runnable {
    private static final int DELAY = 1;
    private BankAccount account;
    private double amount;
    private int count;

    /**
     * Constructs a deposit runnable.
     * 
     * @param anAccount the account into which to deposit money
     * @param anAmount  the amount to deposit in each repetition
     * @param aCount    the number of repetitions
     */
    public DepositRunnable(BankAccount anAccount, double anAmount, int aCount) {
        account = anAccount;
        amount = anAmount;
        count = aCount;
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