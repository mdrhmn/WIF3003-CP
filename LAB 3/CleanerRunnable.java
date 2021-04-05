public class CleanerRunnable implements Runnable {
    private final String cleanerName;
    Room room;

    public CleanerRunnable(String cleanerName, Room room) {
        this.cleanerName = cleanerName;
        this.room = room;
    }

    public void cleanerEnter(String cleanerName) {
        room.cleanerEntry(cleanerName);
    }

    public void cleanerExit(String cleanerName) {
        room.cleanerExit(cleanerName);
    }

    @Override
    public void run() {
        cleanerEnter(this.cleanerName);
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
        cleanerExit(this.cleanerName);
    }
}
