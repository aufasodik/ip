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

    /**
     * Check if a task has a matching keyword
     * @param keyword keyword to check
     * @return true if the task matches, false otherwise
     */
    public boolean matches(String keyword) {
        return name.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public String toString() {
        return "[" + type() + "] " + "[" + statusIcon() + "] " + name + extra();
    }
}