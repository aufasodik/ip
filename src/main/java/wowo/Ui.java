package wowo;

import java.util.Scanner;

public class Ui {
    private static final String LINE = "_".repeat(70);
    private final Scanner sc = new Scanner(System.in);

    public void printLine() { System.out.println(LINE); }

    public void showWelcome(String botName) {
        printLine();
        System.out.println("Hello! I'm " + botName);
        System.out.println("I'm your grumpy personal assistant");
        printLine();
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void showAdded(Task task, int size) {
        printLine();
        System.out.println("Okay. I've added:");
        System.out.println("  " + task);
        System.out.println("You have " + size + " tasks. Must do them all");
        printLine();
    }

    public void showList(Iterable<Task> tasks) {
        printLine();
        System.out.println("Your list:");
        int i = 1;
        for (Task t : tasks) {
            System.out.println(i++ + ". " + t);
        }
        printLine();
    }

    public void showMarked(Task t) {
        printLine();
        System.out.println("Good! Now go back to work, I've marked:");
        System.out.println("  " + t);
        printLine();
    }

    public void showUnmarked(Task t) {
        printLine();
        System.out.println("Hey, I thought you've done this. I'm unmarking:");
        System.out.println("  " + t);
        printLine();
    }

    public void showDeleted(Task removed, int remaining) {
        printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + remaining + " tasks in the list.");
        printLine();
    }

    public void showBye() {
        printLine();
        System.out.println("Bye. Don't forget to do your chores!");
        printLine();
    }

    public void showWarning(String message) {
        printLine();
        System.out.println("  " + message);
        printLine();
    }

    /**
     * Shows tasks that matched a find search.
     *
     * @param matches tasks that matched
     */
    public void showMatches(Iterable<Task> matches) {
        printLine();
        System.out.println("Here are the matching tasks in your list:");
        int i = 1;
        boolean any = false;
        for (Task t : matches) {
            any = true;
            System.out.println(i++ + ". " + t);
        }
        if (!any) {
            System.out.println("(none)");
        }
        printLine();
    }
}