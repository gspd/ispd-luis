package gspd.ispd.motor.filas;

public class LockDAG {
    private boolean available;

    public LockDAG(boolean available) {
        this.available = available;
    }

    public LockDAG() {
        this(true);
    }

    public boolean isAvailable() {
        return available;
    }

    public void acquire() {
        available = false;
    }

    public void release() {
        available = true;
    }
}
