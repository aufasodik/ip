import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskList {
    private final List<Task> tasks = new ArrayList<>();

    public TaskList() { }

    public TaskList(List<Task> init) {
        if (init != null) {
            tasks.addAll(init);
        }
    }

    public List<Task> asList() {
        return Collections.unmodifiableList(tasks);
    }

    public int size() {
        return tasks.size();
    }

    public Task add(Task t) {
        tasks.add(t);
        return t;
    }

    public Task getOneBased(int n) throws InvalidTaskIndexException {
        checkIndexRange(n);
        return tasks.get(n - 1);
    }

    public Task deleteOneBased(int n) throws InvalidTaskIndexException {
        checkIndexRange(n);
        return tasks.remove(n - 1);
    }

    public Task markOneBased(int n) throws InvalidTaskIndexException {
        Task t = getOneBased(n);
        t.markDone();
        return t;
    }

    public Task unmarkOneBased(int n) throws InvalidTaskIndexException {
        Task t = getOneBased(n);
        t.markUndone();
        return t;
    }

    private void checkIndexRange(int n) throws InvalidTaskIndexException {
        if (n < 1 || n > tasks.size()) {
            throw new InvalidTaskIndexException();
        }
    }
}