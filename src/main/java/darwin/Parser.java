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
            try {
                String index = command.substring(Parser.MARK_INDEX).trim();
                // No index
                if (index.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_INDEX_MARK.message());
                }
                int taskNumber = Integer.parseInt(index) - 1;
                return taskList.mark(taskNumber);
            } catch (NumberFormatException e) {
                throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
            }
        } else if (command.equals("unmark") || command.startsWith("unmark ")) {
            try {
                String index = command.substring(Parser.UNMARK_INDEX).trim();
                // No index
                if (index.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_INDEX_UNMARK.message());
                }
                int taskNumber = Integer.parseInt(index) - 1;
                return taskList.unmark(taskNumber);
            } catch (NumberFormatException e) {
                throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
            }
        } else if (command.equals("delete") || command.startsWith("delete ")) {
            try {
                String index = command.substring(Parser.DELETE_INDEX).trim();
                // No index
                if (index.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_INDEX_DELETE.message());
                }
                int taskNumber = Integer.parseInt(index) - 1;
                return taskList.delete(taskNumber);
            } catch (NumberFormatException e) {
                throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
            }
        } else if (command.equals("find") || command.startsWith("find ")) {
            String keyword = command.substring(Parser.FIND_INDEX).trim();
            if (keyword.isEmpty()) {
                throw new DarwinException(ErrorMessage.MISSING_KEYWORD.message());
            }
            return taskList.find(keyword);
        } else {
            String[] inputs = command.split(" /");
            return taskList.add(inputs);
        }
    }
}
