package wowo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDate;
import java.util.Comparator;

/**
 * A list for all task managed by the chatbot
 */
public class TaskList {
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Initiate an empty list of tasks
     */
    public TaskList() { }

    /**
     * Initiate a task list with an initial value
     * @param init task that initiate
     */
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

    /**
     * Add a task to a list
     * @param task the task that the user wants to add
     * @return the task
     */
    public Task add(Task task) {
        tasks.add(task);
        return task;
    }

    public Task getTask(int n) throws InvalidTaskIndexException {
        checkIndexRange(n);
        return tasks.get(n - 1);
    }

    /**
     * Delete a task by index from the list
     * @param n the index number of the task
     * @return the task that got deleted
     * @throws InvalidTaskIndexException if the index does not point to a task
     */
    public Task deleteOneBased(int n) throws InvalidTaskIndexException {
        checkIndexRange(n);
        return tasks.remove(n - 1);
    }

    /**
     * Mark a task by index from the list
     * @param n the index number of the task
     * @return the task that wants to be marked
     * @throws InvalidTaskIndexException if the index does not point to a task
     */
    public Task markOneBased(int n) throws InvalidTaskIndexException {
        Task t = getTask(n);
        t.markDone();
        return t;
    }

    /**
     * Unmark a task by index from the list
     *
     * @param n the index number of the task
     * @return the task that wants to be unmarked
     * @throws InvalidTaskIndexException if the index does not point to a task
     */
    public Task unmarkOneBased(int n) throws InvalidTaskIndexException {
        Task t = getTask(n);
        t.markUndone();
        return t;
    }

    private void checkIndexRange(int n) throws InvalidTaskIndexException {
        if (n < 1 || n > tasks.size()) {
            throw new InvalidTaskIndexException();
        }
    }

    /**
     * Returns a list of tasks that match with the keyword
     *
     * @param keyword search term
     * @return list of matching tasks
     */
    public List<Task> find(String keyword) {
        List<Task> out = new ArrayList<>();
        for (Task t : tasks) {
            if (t.matches(keyword)) {
                out.add(t);
            }
        }
        return out;
    }

    public void sortByName() {
        // If Task has getName(), prefer that; otherwise fall back to toString()
        tasks.sort(Comparator.comparing(
                t -> (t instanceof TaskNameProvider)
                        ? ((TaskNameProvider) t).getName()
                        : t.toString(),
                String.CASE_INSENSITIVE_ORDER
        ));
    }

    public void sortByDateThenName() {
        tasks.sort(
                Comparator
                        .comparing(TaskList::dateOrMax) // deadlines/events by date; todos last
                        .thenComparing(t -> (t instanceof TaskNameProvider)
                                        ? ((TaskNameProvider) t).getName()
                                        : t.toString(),
                                String.CASE_INSENSITIVE_ORDER)
        );
    }

    private static LocalDate dateOrMax(Task t) {
        if (t instanceof Deadline d) return d.getDue();
        if (t instanceof Event e)    return e.getFrom();   // or e.getTo(), your call
        return LocalDate.MAX; // Todo (no date) goes after dated tasks
    }

    /**
     * Add multiple tasks
     *
     * @param tasks to be added
     */
    public void addMany(Task... tasks) {
        for (Task t : tasks) {
            add(t);
        }
    }

    public interface TaskNameProvider {
        String getName();
    }
}
