import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Wowo {
    private static final String name = "Wowo";
    private static final String line = "_".repeat(70);
    private static final List<Task> list = new ArrayList<>();

    private static void line() { System.out.println(line); }

    private static void greeting() {
        line();
        System.out.println("Hello! I'm Wowo");
        System.out.println("I'm your grumpy personal assistant");
        line();
    }

    private static void showAdded(Task t) {
        line();
        System.out.println("Okay. I've added:");
        System.out.println("  " + t);
        System.out.println("You have " + list.size() + " tasks. Must do them all");
        line();
    }

    // ---- helpers that can throw ------------------------------------------------
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

    private static void checkIndexRange(int n) throws InvalidTaskIndexException {
        int size = list.size();
        if (n < 1 || n > size) {
            throw new InvalidTaskIndexException();
        }
    }

    // ---- operations (throw on error) -------------------------------------------
    private static void addTodo(String desc)
            throws EmptyDescriptionException {
        if (desc == null || desc.trim().isEmpty()) {
            throw new EmptyDescriptionException();
        }
        Task t = new Todo(desc.trim());
        list.add(t);
        showAdded(t);
    }

    private static void addDeadline(String desc, String by) {
        Task t = new Deadline(desc, by);
        list.add(t);
        showAdded(t);
    }

    private static void addEvent(String desc, String from, String to) {
        Task t = new Event(desc, from, to);
        list.add(t);
        showAdded(t);
    }

    private static void listAll() {
        line();
        System.out.println("Your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
        line();
    }

    private static void mark(int n) throws InvalidTaskIndexException {
        checkIndexRange(n);
        int i = n - 1;
        list.get(i).markDone();
        line();
        System.out.println("Good! Now go back to work, I've marked:");
        System.out.println("  " + list.get(i));
        line();
    }

    private static void unmark(int n) throws InvalidTaskIndexException {
        checkIndexRange(n);
        int i = n - 1;
        list.get(i).markUndone();
        line();
        System.out.println("Hey, I thought you've done this. I'm unmarking:");
        System.out.println("  " + list.get(i));
        line();
    }

    private static void delete(int n) throws InvalidTaskIndexException {
        checkIndexRange(n);
        int i = n - 1;
        Task removed = list.remove(i);
        line();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + list.size() + " tasks in the list.");
        line();
    }

    private static void goodbye() {
        line();
        System.out.println("Bye. Hope to see you again soon!");
        line();
    }

    public static void main(String[] args) {
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
                    int n = parseIndex(input);
                    mark(n);

                } else if (input.startsWith("unmark ")) {
                    int n = parseIndex(input);
                    unmark(n);

                } else if (input.startsWith("delete ")) {
                    int n = parseIndex(input);
                    delete(n);

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
                    // any other non-empty command
                    throw new UnknownCommandException();
                }

            } catch (WowoException e) {
                line();
                System.out.println("  " + e.getMessage());
                line();
            }
        }
    }
}