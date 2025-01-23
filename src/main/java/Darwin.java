import java.util.Scanner;

public class Darwin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = "____________________________________________________________";
        System.out.println(line + "\nHello, I'm Darwin!\nWhat can I do for you?\n" + line);
        TaskHandler handler = new TaskHandler();
        while (true) {
            String input = scanner.nextLine().trim();
            System.out.println(line);
            try {
                if (input.equals("bye")) {
                    break;
                } else if (input.equals("list")) {
                    handler.list();
                } else if (input.equals("mark") || input.startsWith("mark ")) {
                    handler.markTask(input);
                } else if (input.equals("unmark") || input.startsWith("unmark ")) {
                    handler.unmarkTask(input);
                } else if (input.equals("delete") || input.startsWith("delete ")) {
                    handler.deleteTask(input);
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
