import java.util.Scanner;
import java.util.ArrayList;

public class Darwin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = "____________________________________________________________";
        System.out.println(line + "\nHello, I'm Darwin!\nWhat can I do for you?\n" + line);
        TaskHandler handler = new TaskHandler(new ArrayList<Task>());
        while (true) {
            String input = scanner.nextLine();
            System.out.println(line);
            try {
                if (input.equals("bye")) {
                    break;
                } else if (input.equals("list")) {
                    handler.list();
                } else if (input.startsWith("mark ")) {
                    handler.markTask(input);
                } else if (input.startsWith("unmark ")) {
                    handler.unmarkTask(input);
                } else {
                    handler.addTask(input);
                }
                System.out.println(line);
            } catch (DarwinException e) {
                System.out.println(e.getMessage());
                System.out.println(line);
            }
        }
        System.out.println("Bye. Hope to see you again soon!\n" + line);
        scanner.close();
    }
}
