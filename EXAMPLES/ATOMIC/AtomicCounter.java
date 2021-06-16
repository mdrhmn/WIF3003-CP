package ATOMIC;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Chiew
 */
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter{

   static class Counter {
      private AtomicInteger c = new AtomicInteger(0);

      public void increment() {
         c.getAndIncrement();
      }

      public int value() {
         return c.get();
      }
   }
   
   public static void main(final String[] arguments) throws InterruptedException {
      final Counter counter = new Counter();
      
      //3000 threads
      for(int i = 0; i < 3000 ; i++) {

         new Thread(new Runnable() {
            public void run() {
               counter.increment();
            }
         }).start(); 
      }  
      Thread.sleep(60);
      System.out.println("Final number (should be 3000): " + counter.value());
   }
}