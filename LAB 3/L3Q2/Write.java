package L3Q2;

import java.util.Random;

public class Write<E> implements Runnable {
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