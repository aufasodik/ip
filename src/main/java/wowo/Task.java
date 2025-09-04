package wowo;

/**
 * Base class for all tasks managed by the chatbot
 */
public abstract class Task {
    private final String name;
    private boolean done;

    /**
     * Creates a task with a given name
     * The task are mark as not done by default.
     * @param name the description of the task
     */
    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    /**
     * Return a single letter that represent the type of the task
     * @return a single letter
     */
    public abstract String getType();

    /**
     * Turn the status as done
     */
    public void markDone() {
        this.done = true;
    }

    /**
     * Turn the status as undone
     */
    public void markUndone() {
        this.done = false;
    }

    /**
     * Show the status of the task
     * @return X when done, return whitespace otherwise
     */
    private String statusIcon() {
        return done ? "X" : " ";
    }

    /**
     * This is used for some task that have an extra information
     * @return based on the type of the task. nothing for todo,due date for deadline, or from and to date for event
     */
    public String extraString() {
        return "";
    }

    public String getName() {
        return name;
    }

    /**
     * Serialize task into a pipe-delimited record for disk storage purpose
     * @return the serialized record
     */
    public String serialize() {
        return String.format("%s|%d|%s", getType(), done ? 1 : 0, name);
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
        return "[" + getType() + "] " + "[" + statusIcon() + "] " + name + extraString();
    }
}
