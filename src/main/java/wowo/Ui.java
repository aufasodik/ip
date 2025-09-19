package wowo;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;
/**
 * This class is responsible for reading and printing user command
 */
public class Ui {
    private static final String LINE = "_".repeat(70);
    private final Scanner sc = new Scanner(System.in);

    private void printLine() {
        System.out.println(LINE);
    }

    /**
     * Show the user the welcome message when the user enters the
     * @param botName the name of the bot
     */
    public void showWelcome(String botName) {
        printLine();
        System.out.println("Hello! I'm " + botName);
        System.out.println("I'm your grumpy personal assistant");
        printLine();
    }

    /**
     * Read the input given
     * @return a string data
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Give confirmation that the task is successfully added
     * @param task the task that the user wants to add
     * @param size the remaining task on the list
     */
    public void showAdded(Task task, int size) {
        printLine();
        System.out.println("Okay. I've added:");
        System.out.println("  " + task);
        System.out.println("You have " + size + " tasks. Must do them all");
        printLine();
    }

    /**
     * Prints all tasks as a list
     * @param tasks the list of tasks
     */
    public void showList(Iterable<Task> tasks) {
        printLine();
        System.out.println("Your list:");

        var list = new ArrayList<Task>();
        tasks.forEach(list::add);

        IntStream.range(0, list.size())
                .forEach(i -> System.out.println((i + 1) + ". " + list.get(i)));

        printLine();
    }

    /**
     * Show a confirmation message that the task is successfully marked
     * @param task the task that the user wants to mark
     */
    public void showMarked(Task task) {
        printLine();
        System.out.println("Good! Now go back to work, I've marked:");
        System.out.println("  " + task);
        printLine();
    }

    /**
     * Show a confirmation message that the task is successfully unmarked
     * @param task the task that the user wants to mark
     */
    public void showUnmarked(Task task) {
        printLine();
        System.out.println("Hey, I thought you've done this. I'm unmarking:");
        System.out.println("  " + task);
        printLine();
    }

    /**
     * Show a confirmation message that the task is successfully deleted
     * @param removed the task that the user wants to delete
     * @param remaining the number of task that remains in the list
     */
    public void showDeleted(Task removed, int remaining) {
        printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + remaining + " tasks in the list.");
        printLine();
    }

    /**
     * Prints goodbye message
     */
    public void showBye() {
        printLine();
        System.out.println("Bye. Don't forget to do your chores!");
        printLine();
    }

    /**
     * Prints a warning or error message
     * @param message error message to show
     */
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
