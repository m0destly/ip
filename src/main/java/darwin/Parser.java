package darwin;

import exception.DarwinException;
import exception.ErrorMessage;

public class Parser {

    /**
     * Returns a boolean to indicate if the program is passed an exit command.
     * Parses the command string and calls the relevant methods.
     *
     * @param command The string passed into the program by the user.
     * @param tasks The tasklist containing the tasks.
     * @return True if the command is parsed as the exit command, otherwise false.
     * @throws DarwinException If the format of the command is violated.
     */
    public static boolean parse(String command, TaskList tasks) throws DarwinException {
        if (command.equals("bye")) {
            return true;
        } else if (command.equals("list")) {
            tasks.list();
        } else if (command.equals("mark") || command.startsWith("mark ")) {
            try {
                String index = command.substring(4).trim();
                // No index
                if (index.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_INDEX_MARK.message());
                }
                int taskNumber = Integer.parseInt(index) - 1;
                tasks.mark(taskNumber);
            } catch (NumberFormatException e) {
                throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
            }
        } else if (command.equals("unmark") || command.startsWith("unmark ")) {
            try {
                String index = command.substring(6).trim();
                // No index
                if (index.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_INDEX_UNMARK.message());
                }
                int taskNumber = Integer.parseInt(index) - 1;
                tasks.unmark(taskNumber);
            } catch (NumberFormatException e) {
                throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
            }
        } else if (command.equals("delete") || command.startsWith("delete ")) {
            try {
                String index = command.substring(6).trim();
                // No index
                if (index.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_INDEX_DELETE.message());
                }
                int taskNumber = Integer.parseInt(index) - 1;
                tasks.delete(taskNumber);
            } catch (NumberFormatException e) {
                throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
            }
        } else if (command.equals("find") || command.startsWith("find ")) {
            String keyword = command.substring(4).trim();
            if (keyword.isEmpty()) {
                throw new DarwinException(ErrorMessage.MISSING_KEYWORD.message());
            }
            tasks.find(keyword);
        } else {
            String[] inputs = command.split(" /");
            tasks.add(inputs);
        }
        return false;
    }
}
