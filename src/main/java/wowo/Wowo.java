package wowo;

import java.util.List;

/**
 * Command-Line chatbot that manages tasks (todos, deadlines, and events).
 * Supports adding, listing, marking/unmarking, deleting, and exiting.
 */
public class Wowo {
    private static final String BOT_NAME = "Wowo";

    private final Ui ui = new Ui();
    private final Storage storage = new Storage();
    private final TaskList tasks = new TaskList();

    private void persist() throws WowoException {
        storage.save(tasks.asList());
    }

    private void loadOnStartup() {
        try {
            List<Task> loaded = storage.load();
            // no-op; we just want the list
            tasks.asList();
            loaded.forEach(tasks::add);
        } catch (WowoException e) {
            ui.showWarning("Warning: Could not load previous data.\n  " + e.getMessage());
        }
    }

    private void run() {
        ui.showWelcome(BOT_NAME);

        while (true) {
            String input = ui.readCommand().trim();
            if (input.equalsIgnoreCase("bye")) {
                ui.showBye();
                break;
            }

            try {
                if (input.equalsIgnoreCase("list")) {
                    ui.showList(tasks.asList());

                } else if (input.startsWith("mark ")) {
                    var t = tasks.markOneBased(Parser.parseIndex(input));
                    persist();
                    ui.showMarked(t);

                } else if (input.startsWith("unmark ")) {
                    var t = tasks.unmarkOneBased(Parser.parseIndex(input));
                    persist();
                    ui.showUnmarked(t);

                } else if (input.startsWith("delete ")) {
                    var removed = tasks.deleteOneBased(Parser.parseIndex(input));
                    persist();
                    ui.showDeleted(removed, tasks.size());

                } else if (input.startsWith("todo")) {
                    String desc = Parser.parseTodoDesc(input);
                    var t = tasks.add(new Todo(desc));
                    persist();
                    ui.showAdded(t, tasks.size());

                } else if (input.startsWith("deadline ")) {
                    var p = Parser.parseDeadline(input);
                    var t = tasks.add(new Deadline(p.desc, p.due));
                    persist();
                    ui.showAdded(t, tasks.size());

                } else if (input.startsWith("event ")) {
                    var p = Parser.parseEvent(input);
                    var t = tasks.add(new Event(p.desc, p.from, p.to));
                    persist();
                    ui.showAdded(t, tasks.size());

                } else if (input.startsWith("find ")) {
                    String keyword = Parser.parseFind(input);
                    var matches = tasks.find(keyword);
                    ui.showMatches(matches);

                } else if (!input.isEmpty()) {
                    throw new UnknownCommandException();
                }

            } catch (WowoException e) {
                ui.showWarning(e.getMessage());
            }
        }
    }

    public String getResponse(String raw) {
        String input = raw == null ? "" : raw.trim();
        if (input.isEmpty()) {
            return "";
        }

        if (input.equalsIgnoreCase("bye")) {
            return "Bye. Don't forget to do your chores!";
        }

        try {
            if (input.equalsIgnoreCase("list")) {
                return formatList(tasks.asList());

            } else if (input.startsWith("mark ")) {
                Task t = tasks.markOneBased(Parser.parseIndex(input));
                persist();
                return "Marked as done:\n  " + t;

            } else if (input.startsWith("unmark ")) {
                Task t = tasks.unmarkOneBased(Parser.parseIndex(input));
                persist();
                return "Marked as not done yet:\n  " + t;

            } else if (input.startsWith("delete ")) {
                Task removed = tasks.deleteOneBased(Parser.parseIndex(input));
                persist();
                return "Removed:\n  " + removed + "\nNow you have " + tasks.size() + " tasks.";

            } else if (input.startsWith("todo")) {
                String desc = Parser.parseTodoDesc(input);
                Task t = tasks.add(new Todo(desc));
                persist();
                return "Added:\n  " + t + "\nNow you have " + tasks.size() + " tasks.";

            } else if (input.startsWith("deadline ")) {
                var p = Parser.parseDeadline(input);
                Task t = tasks.add(new Deadline(p.desc, p.due));
                persist();
                return "Added:\n  " + t + "\nNow you have " + tasks.size() + " tasks.";

            } else if (input.startsWith("event ")) {
                var p = Parser.parseEvent(input);
                Task t = tasks.add(new Event(p.desc, p.from, p.to));
                persist();
                return "Added:\n  " + t + "\nNow you have " + tasks.size() + " tasks.";

            } else if (input.startsWith("find ")) {
                String keyword = Parser.parseFind(input);
                var matches = tasks.find(keyword);
                return formatMatches(matches);

            } else {
                throw new UnknownCommandException();
            }
        } catch (WowoException e) {
            return e.getMessage();
        }
    }

    private String formatList(List<Task> list) {
        if (list.isEmpty()) {
            return "Your list is empty.";
        }

        StringBuilder sb = new StringBuilder("Here are your tasks:\n");

        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1).append(". ").append(list.get(i)).append('\n');
        }

        return sb.toString().trim();
    }

    private String formatMatches(List<Task> list) {
        if (list.isEmpty()) {
            return "No matching tasks found.";
        }

        StringBuilder sb = new StringBuilder("Here are the matching tasks:\n");

        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1).append(". ").append(list.get(i)).append('\n');
        }

        return sb.toString().trim();
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Wowo app = new Wowo();
        app.loadOnStartup();
        app.run();
    }
}
