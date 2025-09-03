package wowo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represent an event task with start and end date
 */
public class Event extends Task {
    private static final DateTimeFormatter OUT_FMT = DateTimeFormatter.ofPattern("MMM d yyyy");
    private final LocalDate from;
    private final LocalDate to;

    /**
     * Creates an Event with a start and end date
     * @param name Description of the event
     * @param from The start date in "yyyy-MM-dd"
     * @param to The end date in "yyyy-MM-dd"
     */
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
