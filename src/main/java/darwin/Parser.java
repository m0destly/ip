package darwin;

import exception.DarwinException;
import exception.ErrorMessage;

/**
 * The class that handles all methods related to parsing the commands.
 */
public class Parser {
    private static final int MARK_INDEX = 4;
    private static final int UNMARK_INDEX = 6;
    private static final int DELETE_INDEX = 6;
    private static final int FIND_INDEX = 4;
    private static final int TASK_TYPE = 0;

    /**
     * Returns a String for the chatbot to output to the user.
     * Parses the command string by calling relevant parse methods.
     *
     * @param command The string passed into the program by the user.
     * @param taskList The tasklist containing the tasks.
     * @return String containing output determined by task operation.
     * @throws DarwinException If the format of the command is violated.
     */
    public static String parse(String command, TaskList taskList) throws DarwinException {
        if (command.equals("bye")) {
            return Ui.showExit();
        } else if (command.equals("list")) {
            return taskList.list();
        } else if (command.equals("help")) {
            return Ui.showHelp();
        } else if (command.equals("mark") || command.startsWith("mark ")) {
            return parseMark(command, taskList);
        } else if (command.equals("unmark") || command.startsWith("unmark ")) {
            return parseUnmark(command, taskList);
        } else if (command.equals("delete") || command.startsWith("delete ")) {
            return parseDelete(command, taskList);
        } else if (command.equals("find") || command.startsWith("find ")) {
            return parseFind(command, taskList);
        } else {
            return parseAdd(command, taskList);
        }
    }

    /**
     * Returns a String for the chatbot to output to the user.
     * Parses the String as a mark command and calls mark method on the TaskList object.
     *
     * @param markCommand The string recognised as a mark command.
     * @param taskList The tasklist containing the tasks.
     * @return String containing output for the mark operation.
     * @throws DarwinException If the format of the command is violated.
     */
    private static String parseMark(String markCommand, TaskList taskList) throws DarwinException {
        try {
            String index = markCommand.substring(Parser.MARK_INDEX).trim();
            // No index
            if (index.isEmpty()) {
                throw new DarwinException(ErrorMessage.MISSING_INDEX_MARK.getMessage());
            }
            int taskNumber = Integer.parseInt(index) - 1;
            return taskList.mark(taskNumber);
        } catch (NumberFormatException e) {
            throw new DarwinException(ErrorMessage.NOT_NUMBER.getMessage());
        }
    }

    /**
     * Returns a String for the chatbot to output to the user.
     * Parses the String as an unmark command and calls unmark method on the TaskList object.
     *
     * @param unmarkCommand The string recognised as an unmark command.
     * @param taskList The tasklist containing the tasks.
     * @return String containing output for the unmark operation.
     * @throws DarwinException If the format of the command is violated.
     */
    private static String parseUnmark(String unmarkCommand, TaskList taskList) throws DarwinException {
        try {
            String index = unmarkCommand.substring(Parser.UNMARK_INDEX).trim();
            // No index
            if (index.isEmpty()) {
                throw new DarwinException(ErrorMessage.MISSING_INDEX_UNMARK.getMessage());
            }
            int taskNumber = Integer.parseInt(index) - 1;
            return taskList.unmark(taskNumber);
        } catch (NumberFormatException e) {
            throw new DarwinException(ErrorMessage.NOT_NUMBER.getMessage());
        }
    }

    /**
     * Returns a String for the chatbot to output to the user.
     * Parses the String as a delete command and calls delete method on the TaskList object.
     *
     * @param deleteCommand The string recognised as a delete command.
     * @param taskList The tasklist containing the tasks.
     * @return String containing output for the delete operation.
     * @throws DarwinException If the format of the command is violated.
     */
    private static String parseDelete(String deleteCommand, TaskList taskList) throws DarwinException {
        try {
            String index = deleteCommand.substring(Parser.DELETE_INDEX).trim();
            // No index
            if (index.isEmpty()) {
                throw new DarwinException(ErrorMessage.MISSING_INDEX_DELETE.getMessage());
            }
            int taskNumber = Integer.parseInt(index) - 1;
            return taskList.delete(taskNumber);
        } catch (NumberFormatException e) {
            throw new DarwinException(ErrorMessage.NOT_NUMBER.getMessage());
        }
    }

    /**
     * Returns a String for the chatbot to output to the user.
     * Parses the String as a find command and calls find method on the TaskList object.
     *
     * @param findCommand The string recognised as a find command.
     * @param taskList The tasklist containing the tasks.
     * @return String containing output for the find operation.
     * @throws DarwinException If the format of the command is violated.
     */
    private static String parseFind(String findCommand, TaskList taskList) throws DarwinException {
        String keyword = findCommand.substring(Parser.FIND_INDEX).trim();
        if (keyword.isEmpty()) {
            throw new DarwinException(ErrorMessage.MISSING_KEYWORD.getMessage());
        }
        return taskList.find(keyword);
    }

    /**
     * Returns a String for the chatbot to output to the user.
     * Parses the String as an add command and calls the add method on the TaskList object.
     *
     * @param addCommand The string recognised as an add command.
     * @param taskList The tasklist containing the tasks.
     * @return String containing output for the add operation.
     * @throws DarwinException If the format of the command is violated.
     */
    private static String parseAdd(String addCommand, TaskList taskList) throws DarwinException {
        String[] inputs = addCommand.split(" /");
        if (inputs[TASK_TYPE].equals("todo") || inputs[TASK_TYPE].startsWith("todo ")) {
            return taskList.addTodo(inputs);
        } else if (inputs[TASK_TYPE].equals("deadline") || inputs[TASK_TYPE].startsWith("deadline ")) {
            return taskList.addDeadline(inputs);
        } else if (inputs[TASK_TYPE].equals("event") || inputs[TASK_TYPE].startsWith("event ")) {
            return taskList.addEvent(inputs);
        } else {
            // Not a command
            throw new DarwinException(ErrorMessage.UNKNOWN.getMessage());
        }
    }
}
