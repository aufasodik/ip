/**
 * Represents a task that has a due date/time.
 * The due value is stored as free-form text.
 */
public class Deadline extends Task {
    private final String due;

    /**
     * Creates a new deadline task.
     *
     * @param name description of the task
     * @param due  free-form due date/time text
     */
    public Deadline(String name, String due) {
        super(name);
        this.due = due;
    }

    @Override
    public String type() {
        return "D";
    }

    @Override
    public String extra() {
        return " (by: " + due + ")";
    }
}