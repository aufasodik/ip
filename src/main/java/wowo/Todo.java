package wowo;

public class Todo extends Task {
    public Todo(String name) {
        super(name);
    }

    @Override
    public String type() {
        return "T";
    }
}