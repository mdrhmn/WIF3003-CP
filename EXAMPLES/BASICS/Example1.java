/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wif3003.pkg2020;

//creating a thread
//Approach 1: Extending Thread 

/*
public class Example1 extends Thread {

    public void run() {
        System.out.println("Thread is running...");
    }

    public static void main(String[] args) {
     
        Example1 e1 = new Example1();
        e1.start();
    }
}
 */
//Approach 2: Implementing Runnable Interface
class Example1 implements Runnable {

    public void run() {
        System.out.println("Thread is running...");
    }

    public static void main(String args[]) {
        Example1 e1 = new Example1();
        Thread t1 = new Thread(e1);
        t1.start();
    }
}
