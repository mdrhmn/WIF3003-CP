package L3Q1;

public class Room {
    private final String roomName;
    private int guestNo;
    private boolean haveCleaner;

    public Room(String roomName) {
        this.roomName = roomName;
        this.guestNo = 0;
        this.haveCleaner = false;
    }

    // Getter
    public int getGuestNo() {
        return this.guestNo;
    }

    public boolean getCleanerStatus() {
        return this.haveCleaner;
    }

    // Setter
    public void setCleanerStatus(boolean cleanerStatus) {
        this.haveCleaner = cleanerStatus;
    }

    public void increaseGuest() {
        this.guestNo++;
    }

    public void decreaseGuest() {
        this.guestNo--;
    }

    // Cleaner entryy
    public synchronized void cleanerEntry(String cleanerName) {

        // If there is no cleaner in room or there is guest in room
        while (getCleanerStatus() || getGuestNo() > 0) {
            try {
                if (getCleanerStatus()) {
                    System.out.println(Thread.currentThread().getName() + ":  A cleaner is in room. Cleaner " + cleanerName + " is waiting to enter "
                            + this.roomName);
                } else if (getGuestNo() > 0) {
                    System.out.println(Thread.currentThread().getName() + ":  There is guest in room. Cleaner " + cleanerName + " is waiting to enter "
                            + this.roomName);
                }

                // Cleaner needs to wait until room is empty (notifyAll())
                wait();
            } catch (Exception e) {
            }

            // try {
            //     System.out.println(Thread.currentThread().getName() + ":  Cleaner " + cleanerName
            //             + " is waiting to Enter " + this.roomName);
            //     // Cleaner needs to wait until room is empty (notifyAll())
            //     wait();
            // } catch (Exception e) {
            // }
        }
        System.out.println(Thread.currentThread().getName() + ":  Cleaner " + cleanerName + " enters " + this.roomName);
        // Set Room status to occupied (true)
        setCleanerStatus(true);
    }

    public synchronized void cleanerExit(String cleanerName) {
        System.out.println(Thread.currentThread().getName() + ":  Cleaner " + cleanerName + " exits " + this.roomName);
        // Set Room status to available (false)
        setCleanerStatus(false);
        // Free other threads (guest/cleaner) to enter room
        notifyAll();
    }

    public synchronized void guestEntry(String guestName) {

        // While there is a cleaner or room is full of guests (6)
        while (getCleanerStatus() || getGuestNo() >= 6) {

            try {
                if (getCleanerStatus()) {
                    System.out.println(Thread.currentThread().getName() + ":  A cleaner is in room. Guest " + guestName + " is waiting to enter "
                            + this.roomName);
                } else if (getGuestNo() >= 6) {
                    System.out.println(Thread.currentThread().getName() + ":  Room is full of guests. Guest " + guestName + " is waiting to enter "
                            + this.roomName);
                }

                // Guest needs to wait until room is empty (notifyAll())
                wait();
            } catch (Exception e) {
            }

            // try {
            //     System.out.println(Thread.currentThread().getName() + ":  Guest " + guestName + " is waiting to enter "
            //             + this.roomName);
            //     // Guest needs to wait until room is empty (notifyAll())
            //     wait();
            // } catch (Exception e) {
            // }
        }

        System.out.println(Thread.currentThread().getName() + ":  Guest " + guestName + " enters " + this.roomName);
        // Guest has entered = increase number of guests
        increaseGuest();

    }

    public synchronized void guestExit(String guestName) {
        System.out.println(Thread.currentThread().getName() + ":  Guest " + guestName + " exits " + this.roomName);
        // Guest has left = decrease number of guests
        decreaseGuest();
        // Free other threads (guest/cleaner) to enter room
        notifyAll();
    }
}
