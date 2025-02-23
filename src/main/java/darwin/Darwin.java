package darwin;

import exception.DarwinException;

/**
 * Class that initiates the chatbot.
 */
public class Darwin {
    private Storage storage;
    private TaskList tasks;

    /**
     * Constructor loads the saved tasks, if any.
     * @param filePath File to save tasks to.
     */
    public Darwin(String filePath) {
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DarwinException e) {
            Ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Processes user input and returns message to be shown.
     * @param input The input by the user in String format.
     * @return A string that contains the output message.
     */
    public String getResponse(String input) {
        try {
            String output = Parser.parse(input.trim(), tasks);
            saveTasks();
            return output;
        } catch (DarwinException e) {
            return e.getMessage();
        }
    }

    /**
     * Saves current tasks to the TaskList.
     */
    public void saveTasks() {
        storage.save(tasks);
    }
}
