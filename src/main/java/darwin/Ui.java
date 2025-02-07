package darwin;

import java.util.Scanner;

public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(DIVIDER + "\nHello, I'm Darwin!\nWhat can I do for you?\n" + DIVIDER);
    }

    public void showExit() {
        System.out.println(DIVIDER + "\nBye. Hope to see you again soon!\n" + DIVIDER);
    }

    public void showLine() {
        System.out.println(DIVIDER);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
