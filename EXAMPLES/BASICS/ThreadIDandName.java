/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BASICS;

/**
 *
 * @author Chiew
 */
public class ThreadIDandName {

    public static void main(String[] args) {
        WorkerThread workerthreadobject = new WorkerThread();

        //First thread : using setName method
        Thread t1 = new Thread(workerthreadobject);
        t1.setName("First Thread");
        t1.start();

        //Second thread : setting name as argument    
        Thread t2 = new Thread(workerthreadobject, "Second Thread");
        t2.start();

        //Third thread : not setting any name of the thread
        Thread t3 = new Thread(workerthreadobject);
        t3.start();

        //Getting the current thread object i.e Main thread 
        Thread currentThread = Thread.currentThread();
        // Printing Main thread : name and id  
        System.out.println("Executing  thread : " + currentThread.getName());
        System.out.println("ID of the thread is " + currentThread.getId());

    }
}



class WorkerThread implements Runnable {

    @Override
    public void run() {
        // get current thread instance
        Thread t = Thread.currentThread();
        // call thread.getId() on the current t instance
        System.out.println("WorkerThread Name : " + t.getName());
        System.out.println("WorkerThread ID : " + t.getId());
    }
}
