package darwin;

import exception.DarwinException;
import exception.ErrorMessage;

public class Parser {
    public static boolean parse(String command, TaskList taskList) throws DarwinException {
        if (command.equals("bye")) {
            return true;
        } else if (command.equals("list")) {
            taskList.list();
        } else if (command.equals("mark") || command.startsWith("mark ")) {
            try {
                String index = command.substring(4).trim();
                // No index
                if (index.isEmpty()) {
                    throw new DarwinException(ErrorMessage.MISSING_INDEX_MARK.message());
                }
                int taskNumber = Integer.parseInt(index) - 1;
                taskList.mark(taskNumber);
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
                taskList.unmark(taskNumber);
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
                taskList.delete(taskNumber);
            } catch (NumberFormatException e) {
                throw new DarwinException(ErrorMessage.NOT_NUMBER.message());
            }
        } else {
            String[] inputs = command.split(" /");
            taskList.add(inputs);
        }
        return false;
    }
}
