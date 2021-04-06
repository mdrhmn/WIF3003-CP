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
    // Signals when the node this is connected to changes
    Condition linkChanged;

    public Node(E value) {
        this.value = value;
        rest = null;
        lock = new ReentrantLock();
        valueChanged = lock.newCondition();
        linkChanged = lock.newCondition();
    }

    public void setValue(E value) {
        lock.lock();
        try {
            this.value = value;
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
                // This will wait until the value changes
                valueChanged.await();
            }
            // When we get here, the value is correct -- Run the task
            task.run();
        } finally {
            lock.unlock();
        }
    }

    public void append(E value) {
        // Start the pointer at this node
        Node<E> node = this;
        node.lock.lock();
        while (node.rest != null) {
            Node<E> next = node.rest;
            // Here's the hand-over-hand locking
            try {
                // Lock the next node
                next.lock.lock();
            } finally {
                // unlock the current node
                node.lock.unlock();
            }
            // Traverse
            node = next;
        }
        // We're at the final node, so append and then unlock
        try {
            node.rest = new Node<E>(value);
            // Let any waiting threads know that this node's link has changed
            node.linkChanged.signalAll();
        } finally {
            node.lock.unlock();
        }
    }

    public void printUntilInterrupted(String prefix) {
        // Start the pointer at this node
        Node<E> node = this;
        node.lock.lock();
        while (true) {
            Node<E> next;
            try {
                System.out.println(prefix + ": " + node.value);
                // Wait for the next node if not available
                while (node.rest == null) {
                    node.linkChanged.await();
                }
                // Get the next node
                next = node.rest;
                // Lock it - more hand-to-hand locking
                next.lock.lock();
            } catch (InterruptedException e) {
                // reset the interrupt status
                Thread.currentThread().interrupt();
                return;
            } finally {
                node.lock.unlock();
            }
            // Traverse
            node = next;
        }
    }
}