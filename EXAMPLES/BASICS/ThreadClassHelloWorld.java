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
public class ThreadClassHelloWorld extends Thread {
    private char ID;
    private int times;

    public ThreadClassHelloWorld(char ID, int times) {
        this.ID = ID;
        this.times = times;
    }
   
    public void run() {
    for (int i = 0; i < times; i++) {
            System.out.println("Hello World from " + ID);
        }
    }

    public static void main(String[] args) {
        Thread t1 = new ThreadClassHelloWorld('A', 100);
        Thread t2 = new ThreadClassHelloWorld('B', 70);
        t1.start();
        t2.start();
        
    }
}
