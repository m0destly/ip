import java.util.Scanner;
import java.util.ArrayList;

public class Darwin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        String line = "____________________________________________________________";
        System.out.println(line + "\nHello, I'm Darwin!\nWhat can I do for you?\n" + line);
        while (true) {
            String command = scanner.next();
            System.out.println(line);
            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                scanner.close();
                break;
            } else if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 1; i <= tasks.size(); i++) {
                    Task task = tasks.get(i - 1);
                    System.out.println(i + ".[" + task.getStatusIcon() + "] " + task.getDescription());
                }
            } else if (command.equals("mark") && scanner.hasNextInt()) {
                int taskNumber = scanner.nextInt() - 1;
                Task task = tasks.get(taskNumber);
                task.mark();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("[" + task.getStatusIcon() + "] " + task.getDescription());

            } else if (command.equals("unmark") && scanner.hasNextInt()) {
                int taskNumber = scanner.nextInt() - 1;
                Task task = tasks.get(taskNumber);
                task.unmark();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("[" + task.getStatusIcon() + "] " + task.getDescription());
            } else {
                String task = scanner.nextLine();
                command += task;
                tasks.add(new Task(command));
                System.out.println("added: " + command);
            }
            System.out.println(line);
        }
    }
}
