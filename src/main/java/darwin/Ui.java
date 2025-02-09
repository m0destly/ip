package darwin;

public class Ui {

    public static String showWelcome() {
        return "Hello, I'm Darwin! What can I do for you?";
    }

    public static String showExit() {
        return "Bye. Hope to see you again soon!";
    }

    public static void showError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
