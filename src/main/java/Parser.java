import java.util.Optional;

public final class Parser {
    private Parser() { }

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

    public static DeadlineParts parseDeadline(String input) {
        String rest = input.substring("deadline".length()).trim();
        int p = rest.indexOf("/by");
        String desc = (p == -1) ? rest : rest.substring(0, p).trim();
        String by   = (p == -1) ? ""   : rest.substring(p + 3).trim();
        return new DeadlineParts(desc, by);
    }

    public static EventParts parseEvent(String input) {
        String rest = input.substring("event".length()).trim();
        int pf = rest.indexOf("/from");
        int pt = rest.indexOf("/to");
        String desc = (pf == -1) ? rest : rest.substring(0, pf).trim();
        String from = (pf == -1 || pt == -1) ? "" : rest.substring(pf + 5, pt).trim();
        String to   = (pt == -1) ? "" : rest.substring(pt + 3).trim();
        return new EventParts(desc, from, to);
    }

    public static String parseTodoDesc(String input) throws EmptyDescriptionException {
        String desc = input.length() > 4 ? input.substring(5).trim() : "";
        if (desc.isEmpty()) throw new EmptyDescriptionException();
        return desc;
    }

    public static final class DeadlineParts {
        public final String desc, by;
        public DeadlineParts(String desc, String by) { this.desc = desc; this.by = by; }
    }
    public static final class EventParts {
        public final String desc, from, to;
        public EventParts(String desc, String from, String to) {
            this.desc = desc; this.from = from; this.to = to;
        }
    }
}