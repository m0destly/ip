package darwin;

import exception.DarwinException;
import task.Todo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskListTest {
    @Test
    public void parse_exitTrue_success() {
        TaskList tasks = new TaskList();
        assertEquals(true, Parser.parse("bye", tasks));
    }

    @Test
    public void parse_listFalse_success() {
        TaskList tasks = new TaskList();
        tasks.getTasks().add(new Todo("Test"));
        assertEquals(false, Parser.parse("list", tasks));
    }

    @Test
    public void parse_emptyList_exceptionThrown() throws DarwinException {
        TaskList tasks = new TaskList();
        try {
            assertEquals(false, Parser.parse("list", tasks));
            fail();
        } catch (DarwinException e) {
            assertEquals("No tasks currently.", e.getMessage());
        }
    }

    @Test
    public void parse_unknown_exceptionThrown() throws DarwinException {
        TaskList tasks = new TaskList();
        try {
            assertEquals(false, Parser.parse("test", tasks));
            fail();
        } catch (DarwinException e) {
            assertEquals("This command is yet to be understood.", e.getMessage());
        }
    }
}
