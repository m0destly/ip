package darwin;

import exception.DarwinException;
import task.Task;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StorageTest {
    @Test
    public void load_nonExistentFilePath_exceptionThrown() throws DarwinException {
        try {
            assertEquals(new ArrayList<Task>(), new Storage("fake filepath").load());
            fail();
        } catch (DarwinException e) {
            assertEquals("No saved tasks found.", e.getMessage());
        }
    }

    @Test
    public void load_emptyFilePath_exceptionThrown() throws DarwinException {
        try {
            assertEquals(new ArrayList<Task>(), new Storage("").load());
            fail();
        } catch (DarwinException e) {
            assertEquals("No saved tasks found.", e.getMessage());
        }
    }

    @Test
    public void load_existingFilePathSize_success() {
        ArrayList<Task> testList = new Storage("data/darwin.tmp").load();
        int testSize = testList.size();
        assertEquals(1, testSize);
    }

    @Test
    public void load_existingFilePathString_success() {
        ArrayList<Task> testList = new Storage("data/darwin.tmp").load();
        Task testTask = testList.get(0);
        String testString = testTask.toString();
        assertEquals("[T][ ] HELLO", testString);
    }
}
