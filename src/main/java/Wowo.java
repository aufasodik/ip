import java.util.Scanner;

public class Wowo {
    private static final String name = "Wowo";
    private static final String line = "_".repeat(70);
    private static final Task[] list = new Task[100];
    private static int index = 0;

    private static void line() { System.out.println(line); }

    private static void greeting() {
        line();
        System.out.println("Hello! I'm Wowo");
        System.out.println("What can I do for you?");
        line();
    }

    private static void showAdded(Task t) {
        line();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + index + " tasks in the list.");
        line();
    }

    private static void addTodo(String desc) {
        Task t = new Todo(desc);
        list[index++] = t;
        showAdded(t);
    }

    private static void addDeadline(String desc, String by) {
        Task t = new Deadline(desc, by);
        list[index++] = t;
        showAdded(t);
    }

    private static void addEvent(String desc, String from, String to) {
        Task t = new Event(desc, from, to);
        list[index++] = t;
        showAdded(t);
    }

    private static void listAll() {
        line();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < index; i++) {
            System.out.println((i + 1) + ". " + list[i]);
        }
        line();
    }

    private static void mark(int n) {
        int i = n - 1;
        list[i].markDone();
        line();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + list[i]);
        line();
    }

    private static void unmark(int n) {
        int i = n - 1;
        list[i].markUndone();
        line();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + list[i]);
        line();
    }

    private static void goodbye() {
        line();
        System.out.println("Bye. Hope to see you again soon!");
        line();
    }

    private static boolean tryMark(String input, boolean done) {
        String[] parts = input.split("\\s+", 2);
        if (parts.length < 2) return false;
        try {
            int n = Integer.parseInt(parts[1].trim());
            if (done) mark(n); else unmark(n);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        greeting();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                goodbye();
                break;

            } else if (input.equalsIgnoreCase("list")) {
                listAll();

            } else if (input.startsWith("mark ")) {
                tryMark(input, true);

            } else if (input.startsWith("unmark ")) {
                tryMark(input, false);

            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                if (!desc.isEmpty()) addTodo(desc);

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
            }
        }
    }
}