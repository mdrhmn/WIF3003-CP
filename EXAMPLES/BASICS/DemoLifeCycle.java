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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chiew
 */
class LifeCycle extends Thread {
   private Thread t;
   private String threadName;
   
   LifeCycle(String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
   }
   
   public void run() {
      System.out.println("Running " +  threadName );
      
      try {

         for(int i = 4; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            
            // Let the thread sleep for a while.
            Thread.sleep(50);
         }
      } catch (InterruptedException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
      }
      System.out.println("Thread " +  threadName + " exiting.");
   }
   
   public void start () {
      System.out.println("Starting " +  threadName );
      
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}

public class DemoLifeCycle {

   public static void main(String args[]) {
      LifeCycle T1 = new LifeCycle("Thread-1");
      T1.start();
      
      LifeCycle T2 = new LifeCycle("Thread-2");
      T2.start();
   }   
}
