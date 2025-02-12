package darwin;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import exception.DarwinException;
import exception.ErrorMessage;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

public class TaskList {
    private static final int TASK_TYPE_DESCRIPTION = 0;
    private static final int DEADLINE_DATE = 1;
    private static final int EVENT_FROM = 1;
    private static final int EVENT_TO = 2;
    private static final int TODO_DESCRIPTION_INDEX = 5;
    private static final int DEADLINE_DESCRIPTION_INDEX = 9;
    private static final int EVENT_DESCRIPTION_INDEX = 6;
    private static final int DEADLINE_DATE_INDEX = 2;
    private static final int EVENT_FROM_INDEX = 4;
    private static final int EVENT_TO_INDEX = 2;
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
        // No tasks
        if (tasks.isEmpty()) {
            throw new DarwinException(ErrorMessage.MISSING_TASK.message());
        }
        String output = "Here are the tasks in your list:";
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
            String output = "Nice! I've marked this task as done:\n  " + task;
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
            String output = "OK, I've marked this task as not done yet:\n  " + task;
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
        if (inputs[TASK_TYPE_DESCRIPTION].equals("todo") || inputs[TASK_TYPE_DESCRIPTION].startsWith("todo ")) {
            try {
                String description = inputs[TASK_TYPE_DESCRIPTION].substring(TODO_DESCRIPTION_INDEX).trim();
                // No description
                if (description.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_TODO.message());
                }
                tasks.add(new Todo(description));
            } catch (IndexOutOfBoundsException e) {
                throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_TODO.message());
            }
        } else if (inputs[TASK_TYPE_DESCRIPTION].equals("deadline") || inputs[TASK_TYPE_DESCRIPTION].startsWith("deadline ")) {
            try {
                String description = inputs[TASK_TYPE_DESCRIPTION].substring(DEADLINE_DESCRIPTION_INDEX).trim();
                // No description
                if (description.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_DEADLINE.message());
                }
                String deadline = inputs[DEADLINE_DATE];
                // Does not begin with by
                if (!(deadline.equals("by") || deadline.startsWith("by "))) {
                    throw new DarwinException(ErrorMessage.WRONG_DEADLINE.message());
                }
                // No deadline
                deadline = deadline.substring(DEADLINE_DATE_INDEX).trim();
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
        } else if (inputs[TASK_TYPE_DESCRIPTION].equals("event") || inputs[TASK_TYPE_DESCRIPTION].startsWith("event ")) {
            try {
                String description = inputs[TASK_TYPE_DESCRIPTION].substring(EVENT_DESCRIPTION_INDEX).trim();
                // No description
                if (description.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_DESCRIPTION_EVENT.message());
                }
                String from = inputs[EVENT_FROM];
                // Does not begin with from
                if (!(from.equals("from") || from.startsWith("from "))) {
                    throw new DarwinException(ErrorMessage.WRONG_EVENT.message());
                }
                from = from.substring(EVENT_FROM_INDEX).trim();
                // No start time
                if (from.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_START.message());
                }
                LocalDate fromDate = LocalDate.parse(from);
                String to = inputs[EVENT_TO];
                // Does not begin with to
                if (!(to.equals("to") || to.startsWith("to "))) {
                    throw new DarwinException(ErrorMessage.WRONG_EVENT.message());
                }
                to = to.substring(EVENT_TO_INDEX).trim();
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
        String output = "Got it. I've added this task:\n  " + task + "\nNow you have ";
        if (tasks.size() == 1) {
            output = output.concat("1 task");
        } else {
            output = output.concat(tasks.size() + " tasks");
        }
        output = output.concat(" in the list.");
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
            Task task = tasks.get(index);
            tasks.remove(index);
            String output = "Noted. I've removed this task:\n  " + task + "\nNow you have ";
            if (tasks.size() == 1) {
                output = output.concat("1 task");
            } else {
                output = output.concat(tasks.size() + " tasks");
            }
            output = output.concat(" in the list.");
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
        ArrayList<Task> tasksContainKeyword = new ArrayList<>();
        if (tasks.isEmpty()) {
            throw new DarwinException(ErrorMessage.MISSING_TASK.message());
        }
        for (Task curr : tasks) {
            if (curr.getDescription().contains(keyword)) {
                tasksContainKeyword.add(curr);
            }
        }
        if (tasksContainKeyword.isEmpty()) {
            throw new DarwinException(ErrorMessage.NO_MATCHES.message());
        }
        String output = "Here are the matching tasks in your list:";
        for (int i = 1; i <= tasksContainKeyword.size(); i++) {
            Task containsKeyword = tasksContainKeyword.get(i - 1);
            output = output.concat("\n" + i + "." + containsKeyword);
        }
        return output;
    }
}
