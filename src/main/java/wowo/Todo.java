package wowo;

/**
 * Represents a simple task that has a description
 */
public class Todo extends Task {
    /**
     * Creates a todo task
     * @param name The description of the task
     */
    public Todo(String name) {
        super(name);
    }

    @Override
    public String type() {
        return "T";
    }
}