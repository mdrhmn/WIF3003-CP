package L3Q1;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {
        Room r1 = new Room("Room A");
        String[] nameArr = { "C1", "C2", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8" };

        ArrayList<Thread> threadObj = new ArrayList<>();
        for (int i = 0; i < nameArr.length; i++) {
            if (nameArr[i].charAt(0) == 'G') {
                threadObj.add(new Thread(new Guest(nameArr[i], r1)));
            } else if (nameArr[i].charAt(0) == 'C') {
                threadObj.add(new Thread(new CleanerRunnable(nameArr[i], r1)));
            }
        }

        for (Thread t:threadObj) {
            t.start();
        }
    }
}
