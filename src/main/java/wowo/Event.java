package wowo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private static final DateTimeFormatter OUT_FMT = DateTimeFormatter.ofPattern("MMM d yyyy");
    private final LocalDate from;
    private final LocalDate to;

    public Event(String name, LocalDate from, LocalDate to) {
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
        return " (from: " + OUT_FMT.format(from) + " to: " + OUT_FMT.format(to) + ")";
    }

    @Override
    public String serialize() {
        return super.serialize() + "|" + from + "|" + to;
    }
}
