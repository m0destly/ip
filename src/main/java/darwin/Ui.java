package darwin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ui {

    public static String showWelcome() {
        return "Hello, I'm Darwin! What can I do for you?";
    }

    public static String showExit() {
        return "Bye. Hope to see you again soon!";
    }

    public static String showHelp() {
        String output = "Here are the list of commands:\n\n";
        output = output.concat("List existing tasks: list\n");
        output = output.concat("Add todo task: todo [description]\n");
        output = output.concat("Add deadline task: deadline [description] /by [yyyy-mm-dd]\n");
        output = output.concat("Add event task: event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]\n");
        output = output.concat("Mark task: mark [task number]\n");
        output = output.concat("Unmark task: unmark [task number]\n");
        output = output.concat("Delete task: delete [task number]\n");
        output = output.concat("Find task containing keyword: todo [description]\n");
        output = output.concat("Exit Darwin: bye");
        return output;
    }

    public static void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public static String showDateFormat(LocalDate date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return date.format(dateFormat);
    }
}
