import java.util.Scanner;

public class Wowo {
    private static final String name = "Wowo";
    private static final String line = "_".repeat(70);
    private static final Task[] list = new Task[100];
    private static int index = 0;

    private static void line() {
        System.out.println(line);
    }

    private static void greeting() {
        line();
        System.out.println("Hello! I'm Wowo");
        System.out.println("What can I do for you?");
        line();
    }

    private static void addTask(String msg) {
        list[index++] = new Task(msg);

        line();
        System.out.println("  added: " + msg);
        line();
    }

    private static void list() {
        line();
        for (int i = 0; i < index; i++) {
            System.out.println("  " + (i + 1) + ". " + list[i]);
        }
        line();
    }

    private static void goodbye() {
        line();
        System.out.println("Bye. Hope to see you again soon!");
        line();
    }

    private static void mark(int index) {
        int idx = index - 1;

        list[idx].markDone();
        line();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + list[idx]);
        line();
    }

    private static void unmark(int index) {
        int idx = index - 1;

        list[idx].markUndone();
        line();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + list[idx]);
        line();
    }

    private static boolean tryMarkOrUnmark(String input, boolean mark) {
        // input is "mark n" or "unmark n"
        String[] parts = input.split("\\s+", 2);
        if (parts.length < 2) return false;
        try {
            int n = Integer.parseInt(parts[1].trim());
            if (mark) mark(n); else unmark(n);
            return true;
        } catch (NumberFormatException e) {
            line();
            System.out.println("  (Please provide a valid number)");
            line();
            return true; // we handled the command even if invalid
        }
    }

    public static void main(String[] args) {
        greeting();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("list")) {
                list();
                continue;
            } else if (input.equalsIgnoreCase("bye")) {
                goodbye();
                break;
            } else if (input.startsWith("mark ")) {
                if (!tryMarkOrUnmark(input, true)) {
                    line();
                    System.out.println("  Usage: mark <number>");
                    line();
                }
            } else if (input.startsWith("unmark ")) {
                if (!tryMarkOrUnmark(input, false)) {
                    line();
                    System.out.println("  Usage: unmark <number>");
                    line();
                }
            } else if (!input.isEmpty()) {
                addTask(input);
            }
        }
    }
}
