/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chiew
 */
class A implements Runnable {
	public void run() {
		for (int i=1; i<=5; i++)  {
			if (i==2) Thread.yield();
			System.out.println("A: " + i);
		}
		System.out.println("Exit from A");
	}
}

class B implements Runnable {
	public void run() {
		for (int j=1; j<=5; j++)  {
			System.out.println("B: " + j);
		}
		System.out.println("Exit from B");
	}
}


public class Yield {
    	public static void main(String[] args) {
		A a = new A();
 		B b = new B();
                Thread t1 = new Thread(a);
                Thread t2 = new Thread(b);
		t1.start();
		t2.start();
	}

    
}
