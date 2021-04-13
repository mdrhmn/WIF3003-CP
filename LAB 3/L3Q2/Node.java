package L3Q2;

/*
License for Java 1.5 'Tiger': A Developer's Notebook
     (O'Reilly) example package

Java 1.5 'Tiger': A Developer's Notebook (O'Reilly) 
by Brett McLaughlin and David Flanagan.
ISBN: 0-596-00738-8

You can use the examples and the source code any way you want, but
please include a reference to where it comes from if you use it in
your own products or services. Also note that this software is
provided by the author "as is", with no expressed or implied warranties. 
In no event shall the author be liable for any direct or indirect
damages arising in any way out of the use of this software.
*/

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node<E> {
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
    // Max number of tasks
    // final int maxTask = 2;
    // Number of tasks
    // int taskCount;

    public Node(E value) {
        this.value = value;
        rest = null;
        // ReentrantLock = lock can be used to lock more than 1 monitor
        lock = new ReentrantLock();
        valueChanged = lock.newCondition();
        isMatch = false;
        // taskCount = 0;
    }

    public E getValue() {
        return value;
    }

    // Producer
    public void setValue(E value) {
        lock.lock();
        try {
            this.value = value;
            System.out.println(Thread.currentThread().getName() + ":    Value set to " + value + ".");

            // Let waiting threads that the value has changed
            valueChanged.signalAll();
            // Thread.sleep(200);
        } finally {
            lock.unlock();
        }
    }

    // Consumer
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

            // Lecturer's solution
            // Thread t = new Thread(task);
            // t.start();
            // taskCount++;

            // try {
            // t.join();
            // Thread.sleep(100);
            // } catch (InterruptedException e) {
            // }

            // if (taskCount == maxTask) {
            // System.exit(0);
            // }

        } finally {
            lock.unlock();
        }
    }

}