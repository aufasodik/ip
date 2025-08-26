public abstract class Task {
    private final String name;
    private boolean done;

    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    public void markDone() { this.done = true; }
    public void markUndone() { this.done = false; }

    private String statusIcon() {
        return done ? "X" : " ";
    }

    public abstract String type();

    public String extra() {
        return "";
    }

    @Override
    public String toString() {
        return "[" + type() + "] " + "[" + statusIcon() + "] " + name + extra();
    }
}