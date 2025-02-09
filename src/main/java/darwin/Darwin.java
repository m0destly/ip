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
            String output = Parser.parse(input, tasks);
            if (output.equals(Ui.showExit())) {
                storage.save(tasks);
            }
            return output;
        } catch (DarwinException e) {
            return e.getMessage();
        }
    }

}
