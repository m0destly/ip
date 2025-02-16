package darwin;

import exception.DarwinException;
import exception.ErrorMessage;

public class Parser {
    private static final int MARK_INDEX = 4;
    private static final int UNMARK_INDEX = 6;
    private static final int DELETE_INDEX = 6;
    private static final int FIND_INDEX = 4;

    /**
     * Returns a String for the chatbot to output to the user.
     * Parses the command string and calls the relevant methods.
     *
     * @param command  The string passed into the program by the user.
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

    private static String parseMark(String markCommand, TaskList taskList) throws DarwinException {
        try {
            String index = markCommand.substring(Parser.MARK_INDEX).trim();
            // No index
            if (index.isEmpty()) {
                throw new DarwinException(ErrorMessage.MISSING_INDEX_MARK.message());
            }
            int taskNumber = Integer.parseInt(index) - 1;
            return taskList.mark(taskNumber);
        } catch (NumberFormatException e) {
            throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
        }
    }

    private static String parseUnmark(String unmarkCommand, TaskList taskList) throws DarwinException {
        try {
            String index = unmarkCommand.substring(Parser.UNMARK_INDEX).trim();
            // No index
            if (index.isEmpty()) {
                throw new DarwinException(ErrorMessage.MISSING_INDEX_UNMARK.message());
            }
            int taskNumber = Integer.parseInt(index) - 1;
            return taskList.unmark(taskNumber);
        } catch (NumberFormatException e) {
            throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
        }
    }

    private static String parseDelete(String deleteCommand, TaskList taskList) throws DarwinException {
        try {
            String index = deleteCommand.substring(Parser.DELETE_INDEX).trim();
            // No index
            if (index.isEmpty()) {
                throw new DarwinException(ErrorMessage.MISSING_INDEX_DELETE.message());
            }
            int taskNumber = Integer.parseInt(index) - 1;
            return taskList.delete(taskNumber);
        } catch (NumberFormatException e) {
            throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
        }
    }

    private static String parseFind(String findCommand, TaskList taskList) throws DarwinException {
        String keyword = findCommand.substring(Parser.FIND_INDEX).trim();
        if (keyword.isEmpty()) {
            throw new DarwinException(ErrorMessage.MISSING_KEYWORD.message());
        }
        return taskList.find(keyword);
    }

    private static String parseAdd(String addCommand, TaskList taskList) throws DarwinException {
        String[] inputs = addCommand.split(" /");
        return taskList.add(inputs);
    }

}