public class Deadline extends Task {
    private final String due;

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