public class Guest implements Runnable {

    private final String guestName;
    Room room;

    public Guest(String guestName, Room room) {
        this.guestName = guestName;
        this.room = room;
    }

    public void guestEnter(String guestName) {
        room.guestEntry(guestName);
    }

    public void guestExit(String guestName) {
        room.guestExit(guestName);
    }

    @Override
    public void run() {
        guestEnter(this.guestName);
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
        guestExit(this.guestName);
    }

}