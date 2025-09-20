package wowo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses user input command into structured pieces that can be easily read by the app.
 */
public final class Parser {
    private static final DateTimeFormatter[] INPUT_DATE_PATTERNS = new DateTimeFormatter[]{
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ofPattern("d/M/uuuu"),
            DateTimeFormatter.ofPattern("dd/M/uuuu")
    };

    private Parser() {}

    /**
     * Parse a 1-based index from commands.
     * @param input The raw user command
     * @return the parsed 1-based index
     * @throws EmptyDescriptionException if the index part is missing
     * @throws NonIntegerIndexException if the index part is not a number
     */
    public static int parseIndex(String input) throws EmptyDescriptionException, NonIntegerIndexException {
        String[] parts = input.split("\\s+", 2);
        if (parts.length < 2 || parts[1].isBlank()) {
            throw new EmptyDescriptionException();
        }
        try {
            return Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new NonIntegerIndexException();
        }
    }

    /**
     * Parses a "deadline" command.
     * @param input The raw user command
     * @return a DeadlineParts carrying the description and due date of it
     * @throws WowoException if the input is missing description or due date
     */
    public static DeadlineParts parseDeadline(String input) throws WowoException {
        String rest = input.substring("deadline".length()).trim();
        int p = rest.indexOf("/by");
        String desc = (p == -1) ? rest : rest.substring(0, p).trim();
        String by = (p == -1) ? "" : rest.substring(p + 3).trim();
        if (desc.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        LocalDate due = parseUserDate(by);
        return new DeadlineParts(desc, due);
    }

    /**
     * Parses a "event" command.
     * @param input The raw user command
     * @return a EventParts carrying the description, initial date, and
     * @throws WowoException if the input is missing description or the time period
     */
    public static EventParts parseEvent(String input) throws WowoException {
        String rest = input.substring("event".length()).trim();
        int pf = rest.indexOf("/from");
        int pt = rest.indexOf("/to");
        String desc = (pf == -1) ? rest : rest.substring(0, pf).trim();
        String from = (pf == -1 || pt == -1) ? "" : rest.substring(pf + 5, pt).trim();
        String to = (pt == -1) ? "" : rest.substring(pt + 3).trim();
        if (desc.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        LocalDate fromDate = parseUserDate(from);
        LocalDate toDate = parseUserDate(to);
        return new EventParts(desc, fromDate, toDate);
    }

    /**
     * Parses a "todo" command
     * @param input The raw user command
     * @return the description of the task
     * @throws EmptyDescriptionException if the input is missing the description
     */
    public static String parseTodoDesc(String input) throws EmptyDescriptionException {
        String desc = input.length() > 4 ? input.substring(5).trim() : "";
        if (desc.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        return desc;
    }


    /** helper function to parses date */
    private static LocalDate parseUserDate(String text) throws WowoException {
        String s = text == null ? "" : text.trim();
        for (DateTimeFormatter f : INPUT_DATE_PATTERNS) {
            try {
                return LocalDate.parse(s, f);
            } catch (DateTimeParseException ignore) {
                // try next pattern
            }
        }
        throw new WowoException("Please use a date like yyyy-MM-dd (e.g., 2019-12-02) or d/M/yyyy.");
    }

    /**
     * Parses a find command.
     *
     * @param input the raw user input
     * @return the keyword to search for
     * @throws EmptyDescriptionException if the keyword is missing/blank
     */
    public static String parseFind(String input) throws EmptyDescriptionException {
        // "find" (length 4). Allow either exactly "find" (error) or "find <keyword>"
        String keyword = input.length() > 4 ? input.substring(5).trim() : "";
        if (keyword.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        return keyword;
    }

    /**
     * Parses a "sort" command.
     *
     * @param input The raw user command
     * @throws EmptyDescriptionException if there are extra invalid arguments
     */
    public static void parseSort(String input) throws EmptyDescriptionException {
        String rest = input.trim();
        if (!rest.equalsIgnoreCase("sort")) {
            throw new EmptyDescriptionException(); // or make a new exception if you prefer
        }
    }

    /**
     * Holder for deadline command.
     */
    public static final class DeadlineParts {
        public final String desc;
        public final LocalDate due;

        /**
         * Creates an information holder
         * @param desc the description of the task
         * @param due the due date of the deadline
         */
        public DeadlineParts(String desc, LocalDate due) {
            this.desc = desc;
            this.due = due;
        }
    }

    /**
     * Holder for event command.
     */
    public static final class EventParts {
        public final String desc;
        public final LocalDate from;
        public final LocalDate to;

        /**
         * Creates a Holder for description, from date, and to date
         * @param desc the description of the task
         * @param from the starting period of the event
         * @param to the ending date of the event
         */
        public EventParts(String desc, LocalDate from, LocalDate to) {
            this.desc = desc;
            this.from = from;
            this.to = to;
        }
    }
}
