package L3Q2;

public class Operate <E> implements Runnable {
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
                System.out.println(Thread.currentThread().getName() + ":    Running at Operate - Searching for value " + target);
                this.node.executeOnValue((E) target, dummy);
            }

            // System.out.println(Thread.currentThread().getName() + " has stopped.");
        
        } catch (InterruptedException exception) {
        }
    }

}