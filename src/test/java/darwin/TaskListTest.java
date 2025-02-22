package darwin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import exception.DarwinException;
import task.Todo;

public class TaskListTest {
    @Test
    public void parse_exit_success() {
        TaskList tasks = new TaskList();
        assertEquals("Bye. Hope to see you again soon!", Parser.parse("bye", tasks));
    }

    @Test
    public void parse_list_success() {
        TaskList tasks = new TaskList();
        tasks.getTasks().add(new Todo("Test"));
        assertEquals("Here are the tasks in your list:\n1.[T][ ] Test", Parser.parse("list", tasks));
    }

    @Test
    public void parse_emptyList_exceptionThrown() throws DarwinException {
        TaskList tasks = new TaskList();
        try {
            assertEquals("", Parser.parse("list", tasks));
            fail();
        } catch (DarwinException e) {
            assertEquals("No tasks currently.", e.getMessage());
        }
    }

    @Test
    public void parse_unknown_exceptionThrown() throws DarwinException {
        TaskList tasks = new TaskList();
        try {
            assertEquals("", Parser.parse("test", tasks));
            fail();
        } catch (DarwinException e) {
            assertEquals("This command is yet to be understood.", e.getMessage());
        }
    }
}
