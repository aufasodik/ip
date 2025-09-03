package wowo;

public abstract class Task {
    private final String name;
    private boolean done;

    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    public abstract String type();

    public void markDone() {
        this.done = true;
    }

    public void markUndone() {
        this.done = false;
    }

    private String statusIcon() {
        return done ? "X" : " ";
    }

    public String extra() {
        return "";
    }

    public String getName() {
        return name;
    }

    public String serialize() {
        return String.format("%s|%d|%s", type(), done ? 1 : 0, name);
    }

    @Override
    public String toString() {
        return "[" + type() + "] " + "[" + statusIcon() + "] " + name + extra();
    }
}
