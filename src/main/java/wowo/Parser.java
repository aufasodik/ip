package wowo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class Parser {
    private Parser() {}

    // Accept ISO and d/M/yyyy inputs
    private static final DateTimeFormatter[] INPUT_DATE_PATTERNS = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE,          // 2019-12-02
            DateTimeFormatter.ofPattern("d/M/uuuu"),   // 2/12/2019
            DateTimeFormatter.ofPattern("dd/M/uuuu")   // 02/12/2019
    };

    public static int parseIndex(String input)
            throws EmptyDescriptionException, NonIntegerIndexException {
        String[] parts = input.split("\\s+", 2);
        if (parts.length < 2 || parts[1].isBlank()) throw new EmptyDescriptionException();
        try {
            return Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new NonIntegerIndexException();
        }
    }

    public static DeadlineParts parseDeadline(String input) throws WowoException {
        String rest = input.substring("deadline".length()).trim();
        int p = rest.indexOf("/by");
        String desc = (p == -1) ? rest : rest.substring(0, p).trim();
        String by   = (p == -1) ? ""   : rest.substring(p + 3).trim();
        if (desc.isEmpty()) throw new EmptyDescriptionException();
        LocalDate due = parseUserDate(by);
        return new DeadlineParts(desc, due);
    }

    public static EventParts parseEvent(String input) throws WowoException {
        String rest = input.substring("event".length()).trim();
        int pf = rest.indexOf("/from");
        int pt = rest.indexOf("/to");
        String desc = (pf == -1) ? rest : rest.substring(0, pf).trim();
        String from = (pf == -1 || pt == -1) ? "" : rest.substring(pf + 5, pt).trim();
        String to   = (pt == -1) ? "" : rest.substring(pt + 3).trim();
        if (desc.isEmpty()) throw new EmptyDescriptionException();
        LocalDate fromDate = parseUserDate(from);
        LocalDate toDate   = parseUserDate(to);
        return new EventParts(desc, fromDate, toDate);
    }

    public static String parseTodoDesc(String input) throws EmptyDescriptionException {
        String desc = input.length() > 4 ? input.substring(5).trim() : "";
        if (desc.isEmpty()) throw new EmptyDescriptionException();
        return desc;
    }

    // ---- helpers ----
    private static LocalDate parseUserDate(String text) throws WowoException {
        String s = text == null ? "" : text.trim();
        for (DateTimeFormatter f : INPUT_DATE_PATTERNS) {
            try { return LocalDate.parse(s, f); }
            catch (DateTimeParseException ignore) { }
        }
        throw new WowoException("Please use a date like yyyy-MM-dd (e.g., 2019-12-02) or d/M/yyyy.");
    }

    // holders
    public static final class DeadlineParts {
        public final String desc;
        public final LocalDate due;
        public DeadlineParts(String desc, LocalDate due) { this.desc = desc; this.due = due; }
    }

    public static final class EventParts {
        public final String desc;
        public final LocalDate from;
        public final LocalDate to;
        public EventParts(String desc, LocalDate from, LocalDate to) {
            this.desc = desc; this.from = from; this.to = to;
        }
    }
}