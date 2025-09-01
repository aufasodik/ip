import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Command-Line chatbot that manages tasks (todos, deadlines, and events).
 * Supports adding, listing, marking/unmarking, deleting, and exiting.
 */
public class Wowo {
    private static final String BOT_NAME = "Wowo";
    private static final String LINE = "_".repeat(70);

    private static final List<Task> tasks = new ArrayList<>();
    private static final Storage storage = new Storage();

    /** Prints the horizontal line. */
    private static void printLine() {
        System.out.println(LINE);
    }

    /** Prints the startup greeting. */
    private static void greeting() {
        printLine();
        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("I'm your grumpy personal assistant");
        printLine();
    }

    /** Prints "added" acknowledgement for a task  */
    private static void showAdded(Task task) {
        printLine();
        System.out.println("Okay. I've added:");
        System.out.println("  " + task);
        System.out.println("You have " + tasks.size() + " tasks. Must do them all");
        printLine();
    }

    /** Parses the 1-based index from a command. */
    private static int parseIndex(String input)
            throws EmptyDescriptionException, NonIntegerIndexException {
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

    /** Validates that n is within [1, tasks.size()] */
    private static void checkIndexRange(int n) throws InvalidTaskIndexException {
        int size = tasks.size();
        if (n < 1 || n > size) {
            throw new InvalidTaskIndexException();
        }
    }

    /** Adds a todo task (description required). */
    private static void addTodo(String desc) throws EmptyDescriptionException, WowoException {
        if (desc == null || desc.trim().isEmpty()) {
            throw new EmptyDescriptionException();
        }
        Task task = new Todo(desc.trim());
        tasks.add(task);
        persist();
        showAdded(task);
    }

    /** Adds a deadline task */
    private static void addDeadline(String desc, String by) throws WowoException {
        Task task = new Deadline(desc, by);
        tasks.add(task);
        persist();
        showAdded(task);
    }

    /** Adds an event task */
    private static void addEvent(String desc, String from, String to) throws WowoException {
        Task t = new Event(desc, from, to);
        tasks.add(t);
        persist();
        showAdded(t);
    }

    /** Prints the list of task. */
    private static void listAll() {
        printLine();
        System.out.println("Your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        printLine();
    }

    /** Marks a task as done. */
    private static void mark(int n) throws InvalidTaskIndexException, WowoException {
        checkIndexRange(n);
        int idx = n - 1;
        tasks.get(idx).markDone();
        persist();
        printLine();
        System.out.println("Good! Now go back to work, I've marked:");
        System.out.println("  " + tasks.get(idx));
        printLine();
    }

    /** Marks a task as not done. */
    private static void unmark(int n) throws InvalidTaskIndexException, WowoException {
        checkIndexRange(n);
        int idx = n - 1;
        tasks.get(idx).markUndone();
        persist();
        printLine();
        System.out.println("Hey, I thought you've done this. I'm unmarking:");
        System.out.println("  " + tasks.get(idx));
        printLine();
    }

    /** Deletes a task. */
    private static void delete(int n) throws InvalidTaskIndexException, WowoException {
        checkIndexRange(n);
        int idx = n - 1;
        Task removed = tasks.remove(idx);
        persist();
        printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    /** Prints goodbye message */
    private static void goodbye() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    private static void persist() throws WowoException {
        storage.save(tasks);
    }

    private static void loadOnStartup() {
        try {
            tasks.addAll(storage.load());
        } catch (WowoException e) {
            printLine();
            System.out.println("  Warning: Could not load previous data.");
            System.out.println("  " + e.getMessage());
            printLine();
        }
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        loadOnStartup();
        greeting();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                goodbye();
                break;
            }

            try {
                if (input.equalsIgnoreCase("list")) {
                    listAll();
                } else if (input.startsWith("mark ")) {
                    mark(parseIndex(input));
                } else if (input.startsWith("unmark ")) {
                    unmark(parseIndex(input));
                } else if (input.startsWith("delete ")) {
                    delete(parseIndex(input));
                } else if (input.startsWith("todo")) {
                    // allow "todo" (empty) to trigger error
                    String desc = input.length() > 4 ? input.substring(5).trim() : "";
                    addTodo(desc);
                } else if (input.startsWith("deadline ")) {
                    String rest = input.substring(9).trim();
                    int p = rest.indexOf("/by");
                    String desc = (p == -1) ? rest : rest.substring(0, p).trim();
                    String by   = (p == -1) ? ""   : rest.substring(p + 3).trim();
                    addDeadline(desc, by);
                } else if (input.startsWith("event ")) {
                    String rest = input.substring(6).trim();
                    int pf = rest.indexOf("/from");
                    int pt = rest.indexOf("/to");
                    String desc = (pf == -1) ? rest : rest.substring(0, pf).trim();
                    String from = (pf == -1 || pt == -1) ? "" : rest.substring(pf + 5, pt).trim();
                    String to   = (pt == -1) ? "" : rest.substring(pt + 3).trim();
                    addEvent(desc, from, to);
                } else if (!input.isEmpty()) {
                    throw new UnknownCommandException();
                }
            } catch (WowoException e) {
                printLine();
                System.out.println("  " + e.getMessage());
                printLine();
            }
        }
    }
}