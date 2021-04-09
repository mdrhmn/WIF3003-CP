package L3S;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class Main {
    public static void main(String args[]) throws InterruptedException {

        Node<Integer> node = new Node<>(1);
        Write<Integer> w = new Write<>(node);
        Operate<Integer> o = new Operate<>(node, 3, new Dummy());
        Thread wt = new Thread(w);
        Thread ot = new Thread(o);

        ot.start();
        wt.start();
    }
}

class Node<E> {
    // The value of this node
    E value;
    // The rest of the list
    Node<E> rest;
    // A lock for this node
    Lock lock;
    // Signals when the value of this node changes
    Condition valueChanged;
    // Flag if desiredValue matches value
    boolean isMatch;

    public Node(E value) {
        this.value = value;
        rest = null;
        lock = new ReentrantLock();
        valueChanged = lock.newCondition();
        isMatch = false;
    }

    public void setValue(E value) {
        lock.lock();
        try {
            this.value = value;
            System.out.println(Thread.currentThread().getName() + ":    Value set to " + value + ".");

            // Let waiting threads that the value has changed
            valueChanged.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void executeOnValue(E desiredValue, Runnable task) throws InterruptedException {
        lock.lock();
        try {
            // Checks the value against the desired value
            while (!value.equals(desiredValue)) {
                System.out.println(Thread.currentThread().getName() + ":    " + value + " does not match "
                        + desiredValue + ". Keep searching...");

                // This will wait until the value changes
                valueChanged.await();
            }

            // When we get here, the value is correct -- Run the task
            isMatch = true;
            task.run();
        } finally {
            lock.unlock();
        }
    }

}

class Operate<E> implements Runnable {
    private Node<E> node;
    private Integer target;
    private Dummy dummy;

    public Operate(Node<E> node, Integer target, Dummy dummy) {
        this.node = node;
        this.target = target;
        this.dummy = dummy;
    }

    @SuppressWarnings("unchecked")
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(
                        Thread.currentThread().getName() + ":    Running at Operate - Searching for value " + target);
                this.node.executeOnValue((E) target, dummy);
            }

            // System.out.println(Thread.currentThread().getName() + " has stopped.");

        } catch (InterruptedException exception) {
        }
    }

}

class Write<E> implements Runnable {
    private Node<E> node;

    public Write(Node<E> node) {
        this.node = node;
    }

    @SuppressWarnings("unchecked")
    public E generateValue() {
        Random random = new Random();
        Integer value = Integer.valueOf(random.nextInt(5));
        return (E) value;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + ":    Running at Write");

        while (!Thread.currentThread().isInterrupted()) {
            try {
                this.node.setValue(generateValue());

                Thread.sleep(100);

                if (this.node.isMatch)
                    Thread.currentThread().interrupt();

            } catch (InterruptedException e) {
            }
        }

        // System.out.println(Thread.currentThread().getName() + " has stopped.");
    }
}

class Dummy implements Runnable {

    public void run() {
        System.out.println("The desired value is found!");
        Thread.currentThread().interrupt();
    }
}
