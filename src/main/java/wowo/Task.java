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
     * @return
     */
    public abstract String type();

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
    public String extra() {
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
        return String.format("%s|%d|%s", type(), done ? 1 : 0, name);
    }

    @Override
    public String toString() {
        return "[" + type() + "] " + "[" + statusIcon() + "] " + name + extra();
    }
}