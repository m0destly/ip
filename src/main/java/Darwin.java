import java.util.Scanner;

public class Darwin {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Darwin(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DarwinException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                isExit = Parser.parse(fullCommand, tasks);
            } catch (DarwinException e) {
                ui.showError(e.getMessage());
            } finally {
                if (!isExit) {
                    ui.showLine();
                }
            }
        }
        storage.save(tasks);
        ui.showExit();
    }

    public static void main(String[] args) {
        new Darwin("data/darwin.tmp").run();
    }
}
