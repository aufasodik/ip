import java.util.Scanner;

public class Wowo {
    private static final String name = "Wowo";
    private static final String line = "_".repeat(70);
    private static final String[] list = new String[100];
    private static int index = 0;

    private static void greeting() {
        System.out.println(line);
        System.out.println("Hello! I'm Wowo");
        System.out.println("What can I do for you?");
        System.out.println(line);
    }

    private static void addTask(String msg) {
        list[index++] = msg;

        System.out.println(line);
        System.out.println("  added: " + msg);
        System.out.println(line);
    }

    private static void list() {
        System.out.println(line);
        for (int i = 0; i < index; i++) {
            System.out.println("  " + (i + 1) + ". " + list[i]);
        }
        System.out.println(line);
    }

    private static void goodbye() {
        System.out.println(line);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(line);
    }

    public static void main(String[] args) {
        greeting();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("list")) {
                list();
                continue;
            }

            if (input.equalsIgnoreCase("bye")) {
                goodbye();
                break;
            }

            addTask(input);
        }
    }
}
