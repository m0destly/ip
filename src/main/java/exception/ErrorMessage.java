package exception;

public enum ErrorMessage {
    MISSING_TASK("No tasks currently."),
    MISSING_INDEX_MARK("Missing index.\nUsage: mark [task number]"),
    MISSING_INDEX_UNMARK("Missing index.\nUsage: unmark [task number]"),
    MISSING_INDEX_DELETE("Missing index.\nUsage: delete [task number]"),
    ALREADY_MARKED("This task has already been marked."),
    ALREADY_UNMARKED("This task has already been unmarked."),
    NOT_NUMBER("Try again with a valid integer."),
    OUT_OF_BOUND("This task doesn't exist."),
    MISSING_DESCRIPTION_TODO("Missing description.\nUsage: todo [description]"),
    MISSING_DESCRIPTION_DEADLINE("Missing description.\nUsage: deadline [description] /by [yyyy-mm-dd]"),
    MISSING_DESCRIPTION_EVENT("Missing description.\nUsage: event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]"),
    MISSING_DEADLINE("Missing deadline.\nUsage: deadline [description] /by [yyyy-mm-dd]"),
    WRONG_DEADLINE("Wrong format.\nUsage: deadline [description] /by [yyyy-mm-dd]"),
    WRONG_EVENT("Wrong format.\nUsage: event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]"),
    MISSING_START("Missing start time.\nUsage: event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]"),
    MISSING_END("Missing end time.\nUsage: event [description] /from [yyyy-mm-dd] /to [yyyy-mm-dd]"),
    UNKNOWN("This command is yet to be understood.");

    private final String error;
    ErrorMessage(String error) {
        this.error = error;
    }

    public String message() {
        return error;
    }
}
