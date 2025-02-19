package task;

/**
 * Subclass of Task that contains a description.
 */
public class Todo extends Task {

    /**
     * Constructor for todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
