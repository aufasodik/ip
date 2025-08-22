import java.util.Scanner;

public class Wowo {
    private static final String name = "Wowo";
    private static final String line = "_".repeat(70);

    private static void greeting() {
        System.out.println(line);
        System.out.println("Hello! I'm Wowo");
        System.out.println("What can I do for you?");
        System.out.println(line);
    }

    private static void repeat(String msg) {
        System.out.println(line);
        System.out.println("  " + msg);
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
            if (input.equalsIgnoreCase("bye")) {
                goodbye();
                break;
            }
            repeat(input);
        }
    }
}
