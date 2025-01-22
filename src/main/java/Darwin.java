import java.util.Scanner;
import java.util.ArrayList;

public class Darwin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        String line = "____________________________________________________________";
        System.out.println(line + "\nHello, I'm Darwin!\nWhat can I do for you?\n" + line);
        while (true) {
            String input = scanner.nextLine();
            String[] inputs = input.split(" /");
            System.out.println(line);
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                scanner.close();
                break;
            } else if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 1; i <= tasks.size(); i++) {
                    Task task = tasks.get(i - 1);
                    System.out.println(i + "." + task);
                }
            } else if (input.startsWith("mark" )) {
                int taskNumber = Integer.parseInt(input.substring(5)) - 1;
                Task task = tasks.get(taskNumber);
                task.mark();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + task);
            } else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7)) - 1;
                Task task = tasks.get(taskNumber);
                task.unmark();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + task);
            } else if (input.startsWith("todo ")) {
                Todo todo = new Todo(input.substring(5));
                todo.added(tasks);
            } else if (input.startsWith("deadline ")) {
                Deadline deadline = new Deadline(inputs[0].substring(9), inputs[1].substring(3));
                deadline.added(tasks);
            } else if (input.startsWith("event ")) {
                Event event = new Event(inputs[0].substring(6), inputs[1].substring(5), inputs[2].substring(3));
                event.added(tasks);
            } else {
                tasks.add(new Task(input));
                System.out.println("added: " + input);
            }
            System.out.println(line);
        }
    }
}
