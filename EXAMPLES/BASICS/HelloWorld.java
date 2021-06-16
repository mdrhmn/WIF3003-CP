package BASICS;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chiew
 */
public class HelloWorld {

    public static void main(String[] args) {
        System.out.println("Hello World");

        Runnable hwA = new ConcurrentHello('A', 100);
        Runnable hwB = new ConcurrentHello('B', 70);
        Thread t1 = new Thread(hwA);
        Thread t2 = new Thread(hwB);
        t1.start();
        t2.start();
    }

}

class ConcurrentHello implements Runnable {

    private char ID;
    private int times;

    public ConcurrentHello(char ID, int times) {
        this.ID = ID;
        this.times = times;
    }

    public void run() {
        for (int i = 0; i < times; i++) {
            System.out.println("Hello World from " + ID);
        }
    }
}
