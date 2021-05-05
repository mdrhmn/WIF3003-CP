import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Random;

class Consumer implements Runnable {

    BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        for (int i = 0; i < 9; i++) {
            try {

                System.out.println("Consumed: " + queue.take() + " Queue size : " + queue.size());
                Thread.sleep(200);

            } catch (InterruptedException ex) {
                System.out.println("Consumer is interrupted.");
            }
        }
    }
}

class Producer implements Runnable {

    Random random = new Random();
    BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        for (int i = 0; i < 7; i++) {
            try {
                int num = random.nextInt(50);
                queue.put(num);
                System.out.println("Produced: " + num + " Queue size : " + queue.size());
                Thread.sleep(100);

            } catch (InterruptedException ex) {
                System.out.println("Producer is interrupted.");
            }
        }
    }
}
