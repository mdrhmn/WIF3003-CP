package L3Q2;

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
