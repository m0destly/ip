import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileDescriptor;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.lang.ClassNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.time.LocalDate;

public class TaskHandler {
    protected ArrayList<Task> tasks;

    public TaskHandler() {
        this.tasks = new ArrayList<Task>();
        loadTasks();
    }

    public void list() throws DarwinException {
        // No tasks
        if (tasks.isEmpty()) {
            throw new DarwinException(ErrorMessage.MISSING_TASK.message());
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
                throw new DarwinException(ErrorMessage.MISSING_INDEX_MARK.message());
            }
            int taskNumber = Integer.parseInt(index) - 1;
            Task task = tasks.get(taskNumber);
            // Already marked
            if (task.getStatusIcon().equals("X")) {
                throw new DarwinException(ErrorMessage.ALREADY_MARKED.message());
            }
            task.mark();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + task);
        } catch (NumberFormatException e) {
            throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException(ErrorMessage.OUT_OF_BOUND.message());
        }
    }

    public void unmarkTask(String input) throws DarwinException {
        try {
            String index = input.substring(6).trim();
            // No index
            if (index.isEmpty()) {
                throw new DarwinException(ErrorMessage.MISSING_INDEX_UNMARK.message());
            }
            int taskNumber = Integer.parseInt(index) - 1;
            Task task = tasks.get(taskNumber);
            // Already unmarked
            if (task.getStatusIcon().equals(" ")) {
                throw new DarwinException(ErrorMessage.ALREADY_UNMARKED.message());
            }
            task.unmark();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + task);
        } catch (NumberFormatException e) {
            throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException(ErrorMessage.OUT_OF_BOUND.message());
        }
    }

    public void addTask(String input) throws DarwinException {
        String[] inputs = input.split(" /");
        if (input.equals("todo") || input.startsWith("todo ")) {
            try {
                String description = input.substring(5).trim();
                // No description
                if (description.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_TODO.message());
                }
                tasks.add(new Todo(description));
            } catch (IndexOutOfBoundsException e) {
                throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_TODO.message());
            }
        } else if (input.equals("deadline") || input.startsWith("deadline ")) {
            try {
                String description = inputs[0].substring(9).trim();
                // No description
                if (description.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_DEADLINE.message());
                }
                String deadline = inputs[1];
                // Does not begin with by
                if (!(deadline.equals("by") || deadline.startsWith("by "))) {
                    throw new DarwinException(ErrorMessage.WRONG_DEADLINE.message());
                }
                // No deadline
                deadline = deadline.substring(2).trim();
                if (deadline.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_DEADLINE.message());
                }
                LocalDate date = LocalDate.parse(deadline);
                tasks.add(new Deadline(description, date));
            } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
                throw new DarwinException(ErrorMessage.WRONG_DEADLINE.message());
            } catch (IndexOutOfBoundsException e) {
                throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_DEADLINE.message());
            }
        } else if (input.equals("event") || input.startsWith("event ")) {
            try {
                String description = inputs[0].substring(6).trim();
                // No description
                if (description.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_EVENT.message());
                }
                String from = inputs[1];
                // Does not begin with from
                if (!(from.equals("from") || from.startsWith("from "))) {
                    throw new DarwinException(ErrorMessage.WRONG_EVENT.message());
                }
                from = from.substring(4).trim();
                // No start time
                if (from.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_START.message());
                }
                LocalDate fromDate = LocalDate.parse(from);
                String to = inputs[2];
                // Does not begin with to
                if (!(to.equals("to") || to.startsWith("to "))) {
                    throw new DarwinException(ErrorMessage.WRONG_EVENT.message());
                }
                to = to.substring(2).trim();
                // No end time
                if (to.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_END.message());
                }
                LocalDate toDate = LocalDate.parse(to);
                tasks.add(new Event(description, fromDate, toDate));
            } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
                throw new DarwinException(ErrorMessage.WRONG_EVENT.message());
            } catch (IndexOutOfBoundsException e) {
                throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_EVENT.message());
            }
        } else {
            throw new DarwinException(ErrorMessage.UNKNOWN.message());
        }
        // Retrieves current task from back
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
                throw new DarwinException(ErrorMessage.MISSING_INDEX_DELETE.message());
            }
            int taskNumber = Integer.parseInt(index) - 1;
            Task task = tasks.get(taskNumber);
            tasks.remove(taskNumber);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + (tasks.isEmpty() ? "no" : tasks.size()) +
                    (tasks.size() == 1 ? " task " : " tasks ") + "in the list.");
        } catch (NumberFormatException e) {
            throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException(ErrorMessage.OUT_OF_BOUND.message());
        }
    }

    public void saveTasks() {
        if (new File("./data").mkdir()) {
            System.out.println("New directory created.");
        }
        if (tasks.isEmpty()) {
            System.out.println("No tasks to save.");
            return;
        }
        File currTasks = new File("./data/darwin.txt");
        try {
            FileOutputStream fos = new FileOutputStream("./data/darwin.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.close();
            if (currTasks.createNewFile()) {
                System.out.println("New save file created.");
            }
            PrintStream fileOutput = new PrintStream(currTasks);
            System.setOut(fileOutput);
            list();
            fileOutput.close();
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            System.out.println(tasks.size() + " tasks saved.");
        } catch (IOException e) {
            System.out.println("An error occurred saving current tasks.");
        }
    }

    public void loadTasks() {
        try {
            FileInputStream fis = new FileInputStream("./data/darwin.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            tasks = (ArrayList<Task>) ois.readObject();
            ois.close();
            System.out.println(tasks.size() + " tasks loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved tasks found.");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("An error occurred loading saved tasks.");
        }
    }
}
