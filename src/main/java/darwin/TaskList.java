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
     * Returns a String of all tasks currently in the tasklist.
     *
     * @return String to display all tasks currently in the tasklist.
     * @throws DarwinException If there are no tasks currently.
     */
    public String list() throws DarwinException {
        String output = "";
        // No tasks
        if (tasks.isEmpty()) {
            throw new DarwinException(ErrorMessage.MISSING_TASK.message());
        }
        output = output.concat("Here are the tasks in your list:");
        for (int i = 1; i <= tasks.size(); i++) {
            Task task = tasks.get(i - 1);
            output = output.concat("\n" + i + "." + task);
        }
        return output;
    }

    /**
     * Marks the task specified by the index.
     *
     * @param index The integer representing the index position of the task.
     * @return String to inform user that the task has been marked.
     * @throws DarwinException If the task is already marked or there is no task at the index position.
     */
    public String mark(int index) throws DarwinException {
        try {
            Task task = tasks.get(index);
            // Already marked
            if (task.getStatusIcon().equals("X")) {
                throw new DarwinException(ErrorMessage.ALREADY_MARKED.message());
            }
            task.mark();
            String output = "";
            output = output.concat("Nice! I've marked this task as done:\n");
            output = output.concat("  " + task);
            return output;
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException(ErrorMessage.OUT_OF_BOUND.message());
        }
    }

    /**
     * Unmarks the task specified by the index.
     *
     * @param index The integer representing the index position of the task.
     * @return String to inform user that the task has been unmarked.
     * @throws DarwinException If the task is already unmarked or there is no task at the index position.
     */
    public String unmark(int index) throws DarwinException {
        try {
            Task task = tasks.get(index);
            // Already unmarked
            if (task.getStatusIcon().equals(" ")) {
                throw new DarwinException(ErrorMessage.ALREADY_UNMARKED.message());
            }
            task.unmark();
            String output = "";
            output = output.concat("OK, I've marked this task as not done yet:\n");
            output = output.concat("  " + task);
            return output;
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException(ErrorMessage.OUT_OF_BOUND.message());
        }
    }

    /**
     * Adds a new task to the tasklist.
     *
     * @param inputs The string array that splits the original string into respective fields.
     * @return String to inform user that the task has been added.
     * @throws DarwinException If the format of the command is violated or command is not understood.
     */
    public String add(String[] inputs) throws DarwinException {
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
        String output = "";
        // Retrieves current task from back
        Task task = tasks.get(tasks.size() - 1);
        output = output.concat("Got it. I've added this task:\n");
        output = output.concat("  " + task + "\n");
        // Checks for singular task
        output = output.concat("Now you have " + tasks.size() +
                (tasks.size() == 1 ? " task " : " tasks ") + "in the list.");
        return output;
    }

    /**
     * Deletes the task at the specified index from the tasklist.
     *
     * @param index The integer representing the index position of the task.
     * @return String to inform the user that the task has been deleted.
     * @throws DarwinException If there is no task at the index position.
     */
    public String delete(int index) throws DarwinException {
        try {
            String output = "";
            Task task = tasks.get(index);
            tasks.remove(index);
            output = output.concat("Noted. I've removed this task:\n");
            output = output.concat("  " + task + "\n");
            output = output.concat("Now you have " + (tasks.isEmpty() ? "no" : tasks.size()) +
                    (tasks.size() == 1 ? " task " : " tasks ") + "in the list.");
            return output;
        } catch (IndexOutOfBoundsException e) {
            throw new DarwinException(ErrorMessage.OUT_OF_BOUND.message());
        }
    }

    /**
     * Finds tasks containing the keyword and returns it similar to the list format.
     *
     * @param keyword The string that represents the keyword to be searched.
     * @return String to display all tasks currently in the tasklist that contain the keyword.
     * @throws DarwinException If there are no tasks in the tasklist or none that match.
     */
    public String find(String keyword) throws DarwinException {
        ArrayList<Task> findKeyword = new ArrayList<>();
        if (tasks.isEmpty()) {
            throw new DarwinException(ErrorMessage.MISSING_TASK.message());
        }
        for (Task curr : tasks) {
            if (curr.getDescription().contains(keyword)) {
                findKeyword.add(curr);
            }
        }
        if (findKeyword.isEmpty()) {
            throw new DarwinException(ErrorMessage.NO_MATCHES.message());
        }
        String output = "";
        output = output.concat("Here are the matching tasks in your list:");
        for (int i = 1; i <= findKeyword.size(); i++) {
            Task containsKeyword = findKeyword.get(i - 1);
            output = output.concat("\n" + i + "." + containsKeyword);
        }
        return output;
    }
}
