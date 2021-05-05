package L4R.lecturer;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class StackAccess {

    Stack<Integer> stack = new Stack<>();
    final int CAPACITY = 6;
    ReentrantLock lock = new ReentrantLock();
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    Condition stackEmptyCondition = lock.newCondition();
    Condition stackFullCondition = lock.newCondition();

    public void push(Integer item) {
        boolean found = true;
        try {
            lock.lock();
            while (stack.size() == CAPACITY) {
                System.out.format("Stack is full. Thread #" + Thread.currentThread().getId() + " waiting to push %d \n", item);
//                stackFullcondition.await();
                found = stackFullCondition.await(1, TimeUnit.SECONDS);
                if (!found) {
                    break;
                }
            }

            if (found) {
                stack.push(item);
                System.out.println("Thread #" + Thread.currentThread().getId() + " push " + item + ". Stack: " + stack.toString());
                stackEmptyCondition.signalAll();
            } else {
                System.out.println("Times up. Thread #" + Thread.currentThread().getId() + " failed to push " + item);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Integer pop() {
        boolean found = true;
        try {
            lock.lock();
            while (stack.size() == 0) {
                System.out.format("Stack is empty. Thread #" + Thread.currentThread().getId() + " waiting to pop...\n");
                found = stackEmptyCondition.await(1, TimeUnit.SECONDS);
                if (!found) {
                    break;
                }
            }

            if (found) {
                Integer value = stack.pop();
                stackFullCondition.signalAll();
                System.out.println("Thread #" + Thread.currentThread().getId() + " pop " + value + ". Stack: " + stack.toString());
                return value;
            } else {
                System.out.println("Times up. Thread #" + Thread.currentThread().getId() + " failed to pop");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stackFullCondition.signalAll();
            lock.unlock();
        }
        return -1;
    }

    public Integer peek() {
        boolean found = true;
        try {
//            readWriteLock.readLock().lock();
            while (stack.size() == 0) {
                System.out.format("Stack is empty. Thread #" + Thread.currentThread().getId() + " waiting to peek...\n");
                found = stackEmptyCondition.await(1, TimeUnit.SECONDS);
                if (!found) {
                    break;
                }
            }

            if (found) {
                int value = stack.peek();
                System.out.println("Thread #" + Thread.currentThread().getId() + " peek " + value + ". Stack: " + stack.toString());
                return value;
            } else {
                System.out.println("Times up. Thread #" + Thread.currentThread().getId() + " failed to peek");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return -1;
    }

}
