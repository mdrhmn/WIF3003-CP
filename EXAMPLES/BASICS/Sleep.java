/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chiew
 */
class C extends Thread {

    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                if (i % 2 == 0) {
                    sleep(2000);
                }
            } catch (Exception e) {
            }
            System.out.println("C: " + i);
        }
        System.out.println("Exit from C");
    }
}

class Sleep {

    public static void main(String[] args) {
        C c = new C();
        c.start();
    }
}
