package wowo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that has a due date/time.
 * The due value is stored as free-form text.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUT_FMT = DateTimeFormatter.ofPattern("MMM d yyyy");
    private final LocalDate due;

    /**
     * Creates a new deadline task.
     *
     * @param name description of the task
     * @param due  free-form due date/time text
     */
    public Deadline(String name, LocalDate due) {
        super(name);
        this.due = due;
    }

    @Override
    public String type() {
        return "D";
    }

    @Override
    public String extra() {
        return " (by: " + OUT_FMT.format(due) + ")";
    }

    @Override
    public String serialize() {
        return super.serialize() + "|" + due;
    }
}