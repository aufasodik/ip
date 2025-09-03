package wowo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    private TaskList tasks;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
    }

    @Test
    public void deleting_success() throws WowoException {
        tasks.add(new Todo("Read book"));
        tasks.deleteOneBased(1);

        assertEquals(0, tasks.size());
    }

    @Test
    public void adding_success() throws WowoException {
        Task task = new Todo("Read book");
        tasks.add(task);

        assertEquals(1, tasks.size());
        assertEquals("Read book", tasks.getOneBased(1).getName());
    }
}
