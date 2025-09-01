import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving tasks to disk using a simple pipe-delimited format.
 * <p>Format per line:
 * <pre>
 *   T|0|task name
 *   D|1|task name|due
 *   E|0|task name|from|to
 * </pre>
 */
public final class Storage {

    private final Path file;

    /**
     * Creates a storage object that uses {@code ./data/duke.txt}.
     */
    public Storage() {
        this(Paths.get("data", "duke.txt"));
    }

    /**
     * Creates a storage object for a specific file.
     *
     * @param file path to the data file
     */
    public Storage(Path file) {
        this.file = file;
    }

    /**
     * Loads all tasks from disk. If the folder/file does not exist, they are created and an empty list is returned.
     *
     * @return list of tasks loaded from disk (never {@code null})
     * @throws WowoException if an I/O error happens
     */
    public List<Task> load() throws WowoException {
        try {
            ensureFileExists();

            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            List<Task> out = new ArrayList<>();

            for (String raw : lines) {
                if (raw == null || raw.isBlank()) {
                    continue;
                }
                String[] p = raw.split("\\s*\\|\\s*");
                if (p.length < 3) {
                    // Corrupted/unknown -> skip quietly
                    continue;
                }
                String type = p[0];
                boolean done = "1".equals(p[1]);
                String name = p[2];

                Task t;
                switch (type) {
                    case "T":
                        t = new Todo(name);
                        break;
                    case "D":
                        if (p.length < 4) {
                            continue; // corrupted line; skip
                        }
                        t = new Deadline(name, p[3]);
                        break;
                    case "E":
                        if (p.length < 5) {
                            continue; // corrupted line; skip
                        }
                        t = new Event(name, p[3], p[4]);
                        break;
                    default:
                        continue; // unknown type; skip
                }

                if (done) {
                    t.markDone();
                }
                out.add(t);
            }
            return out;

        } catch (IOException ex) {
            throw new WowoException("Unable to load data: " + ex.getMessage(), ex);
        }
    }

    /**
     * Saves the given tasks to disk, overwriting existing content.
     *
     * @param tasks tasks to save
     * @throws WowoException if an I/O error happens
     */
    public void save(List<Task> tasks) throws WowoException {
        try {
            ensureFileExists();

            List<String> lines = new ArrayList<>();
            for (Task t : tasks) {
                lines.add(t.serialize());
            }

            Files.write(
                    file,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );
        } catch (IOException ex) {
            throw new WowoException("Unable to save data: " + ex.getMessage(), ex);
        }
    }

    private void ensureFileExists() throws IOException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        if (Files.notExists(file)) {
            Files.createFile(file);
        }
    }
}