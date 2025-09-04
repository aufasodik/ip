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
            // Instead, you can new TaskList(loaded) and keep a mutable reference:
            // but for minimal change, do:
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
