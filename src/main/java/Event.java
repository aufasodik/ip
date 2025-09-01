public class Event extends Task {
    private final String from;
    private final String to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String type() {
        return "E";
    }

    @Override
    public String extra() {
        return " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String serialize() {
        return super.serialize() + "|" + from + "|" + to;
    }
}