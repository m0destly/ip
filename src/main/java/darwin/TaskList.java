package darwin;

import exception.DarwinException;
import exception.ErrorMessage;
import task.Task;
import task.Todo;
import task.Deadline;
import task.Event;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the ArrayList of tasks.
     *
     * @return ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Prints all tasks currently in the tasklist.
     *
     * @throws DarwinException If there are no tasks currently.
     */
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

    /**
     * Marks the task specified by the index.
     *
     * @param index The integer representing the index position of the task.
     * @throws DarwinException If the task is already marked or there is no task at the index position.
     */
    public void mark(int index) throws DarwinException {
        try {
            Task task = tasks.get(index);
            // Already marked
            if (task.getStatusIcon().equals("X")) {
                throw new DarwinException(ErrorMessage.ALREADY_MARKED.message());
            }
            task.mark();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + task);
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException(ErrorMessage.OUT_OF_BOUND.message());
        }
    }

    /**
     * Unmarks the task specified by the index.
     *
     * @param index The integer representing the index position of the task.
     * @throws DarwinException If the task is already unmarked or there is no task at the index position.
     */
    public void unmark(int index) throws DarwinException {
        try {
            Task task = tasks.get(index);
            // Already unmarked
            if (task.getStatusIcon().equals(" ")) {
                throw new DarwinException(ErrorMessage.ALREADY_UNMARKED.message());
            }
            task.unmark();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + task);
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException(ErrorMessage.OUT_OF_BOUND.message());
        }
    }

    /**
     * Adds a new task to the tasklist.
     *
     * @param inputs The string array that splits the original string into respective fields.
     * @throws DarwinException If the format of the command is violated or command is not understood.
     */
    public void add(String[] inputs) throws DarwinException {
        if (inputs[0].equals("todo") || inputs[0].startsWith("todo ")) {
            try {
                String description = inputs[0].substring(5).trim();
                // No description
                if (description.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_TODO.message());
                }
                tasks.add(new Todo(description));
            } catch (IndexOutOfBoundsException e) {
                throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_TODO.message());
            }
        } else if (inputs[0].equals("deadline") || inputs[0].startsWith("deadline ")) {
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
        } else if (inputs[0].equals("event") || inputs[0].startsWith("event ")) {
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

    /**
     * Deletes the task at the specified index from the tasklist.
     *
     * @param index The integer representing the index position of the task.
     * @throws DarwinException If there is no task at the index position.
     */
    public void delete(int index) throws DarwinException {
        try {
            Task task = tasks.get(index);
            tasks.remove(index);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + (tasks.isEmpty() ? "no" : tasks.size()) +
                    (tasks.size() == 1 ? " task " : " tasks ") + "in the list.");
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException(ErrorMessage.OUT_OF_BOUND.message());
        }
    }

    public void find(String keyword) throws DarwinException {
        ArrayList<Task> findKeyword = new ArrayList<>();
        if (taskList.isEmpty()) {
            throw new DarwinException(ErrorMessage.MISSING_TASK.message());
        }
        for (Task curr : taskList) {
            if (curr.getDescription().contains(keyword)) {
                findKeyword.add(curr);
            }
        }
        if (findKeyword.isEmpty()) {
            throw new DarwinException(ErrorMessage.NO_MATCHES.message());
        }
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 1; i <= findKeyword.size(); i++) {
            Task containsKeyword = findKeyword.get(i - 1);
            System.out.println(i + "." + containsKeyword);
        }
    }
}
