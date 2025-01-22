import java.util.ArrayList;

public class TaskHandler {
    protected ArrayList<Task> tasks;

    public TaskHandler(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void list() throws DarwinException {
        // No tasks
        if (tasks.isEmpty()) {
            throw new DarwinException("Add a task first...");
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i <= tasks.size(); i++) {
            Task task = tasks.get(i - 1);
            System.out.println(i + "." + task);
        }
    }

    public void markTask(String input) throws DarwinException {
        try {
            String index = input.substring(4).trim();
            // No index
            if (index.isEmpty()) {
                throw new DarwinException("Where's the index...");
            }
            int taskNumber = Integer.parseInt(index) - 1;
            Task task = tasks.get(taskNumber);
            // Already marked
            if (task.getStatusIcon().equals("X")) {
                throw new DarwinException("It's marked already...");
            }
            task.mark();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + task);
        } catch (NumberFormatException e) {
            throw new DarwinException("Not a number...");
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException("Can't reach...");
        }
    }

    public void unmarkTask(String input) throws DarwinException {
        try {
            String index = input.substring(6).trim();
            // No index
            if (index.isEmpty()) {
                throw new DarwinException("Where's the index...");
            }
            int taskNumber = Integer.parseInt(index) - 1;
            Task task = tasks.get(taskNumber);
            // Already unmarked
            if (task.getStatusIcon().equals(" ")) {
                throw new DarwinException("It's already unmarked...");
            }
            task.unmark();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + task);
        } catch (NumberFormatException e) {
            throw new DarwinException("Not a number...");
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException("Can't reach...");
        }
    }

    public void addTask(String input) throws DarwinException {
        String[] inputs = input.split(" /");
        if (input.startsWith("todo ")) {
            String description = input.substring(5);
            // No description
            if (description.isEmpty()) {
                throw new DarwinException("Where's the description...");
            }
            tasks.add(new Todo(description));
        } else if (input.startsWith("deadline ")) {
            String description = inputs[0].substring(9).trim();
            // No description
            if (description.isEmpty()) {
                throw new DarwinException("Where's the description...");
            }
            try {
                String deadline = inputs[1];
                // Does not begin with by
                if (!deadline.startsWith("by ")) {
                    throw new DarwinException("Wrong format...\nUsage: deadline [description] /by [deadline]");
                }
                // No deadline
                deadline = deadline.substring(3).trim();
                if (deadline.isEmpty()) {
                    throw new DarwinException("Where's the deadline...");
                }
                tasks.add(new Deadline(description, deadline));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DarwinException("Is it separated properly...");
            }
        } else if (input.startsWith("event ")) {
            String description = inputs[0].substring(6).trim();
            // No description
            if (description.isEmpty()) {
                throw new DarwinException("Where's the description...");
            }
            try {
                String from = inputs[1];
                // Does not begin with from
                if (!from.startsWith("from ")) {
                    throw new DarwinException("Wrong format...\n" +
                            "Usage: event [description] /from [start time] /to [end time]");
                }
                from = from.substring(5).trim();
                // No start time
                if (from.isEmpty()) {
                    throw new DarwinException("Where's the start time...");
                }
                String to = inputs[2];
                // Does not begin with to
                if (!to.startsWith("to ")) {
                    throw new DarwinException("Wrong format...\n" +
                            "Usage: event [description] /from [start time] /to [end time]");
                }
                to = to.substring(3).trim();
                // No end time
                if (to.isEmpty()) {
                    throw new DarwinException("Where's the end time...");
                }
                tasks.add(new Event(description, from, to));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DarwinException("Is it separated properly...");
            }
        } else {
            throw new DarwinException("This is beyond my understanding...");
        }
        // Retrives current task from back
        Task task = tasks.get(tasks.size() - 1);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        // Checks for singular task
        System.out.println("Now you have " + tasks.size() +
                (tasks.size() == 1 ? " task " : " tasks ") + "in the list.");
    }

    public void deleteTask(String input) throws DarwinException {
        try {
            String index = input.substring(6).trim();
            // No index
            if (index.isEmpty()) {
                throw new DarwinException("Where's the index...");
            }
            int taskNumber = Integer.parseInt(index) - 1;
            Task task = tasks.get(taskNumber);
            tasks.remove(taskNumber);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + (tasks.isEmpty() ? "no" : tasks.size()) +
                    (tasks.size() == 1 ? " task " : " tasks ") + "in the list.");
        } catch (NumberFormatException e) {
            throw new DarwinException("Not a number...");
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException("Can't reach...");
        }
    }
}
