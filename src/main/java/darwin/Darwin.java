package darwin;

import exception.DarwinException;

public class Darwin {
    private Storage storage;
    private TaskList tasks;

    public Darwin(String filePath) {
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DarwinException e) {
            Ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    public String getResponse(String input) {
        try {
            String output = Parser.parse(input.trim(), tasks);
            if (output.equals(Ui.showExit())) {
                saveTasks();
            }
            return output;
        } catch (DarwinException e) {
            return e.getMessage();
        }
    }

    public void saveTasks() {
        storage.save(tasks);
    }

}
