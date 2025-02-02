package darwin;

import java.util.Scanner;

public class Ui {
    private static final String divider = "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(divider + "\nHello, I'm Darwin!\nWhat can I do for you?\n" + divider);
    }

    public void showExit() {
        System.out.println(divider + "\nBye. Hope to see you again soon!\n" + divider);
    }

    public void showLine() {
        System.out.println(divider);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
