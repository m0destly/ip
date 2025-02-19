package task;

import java.io.Serializable;

/**
 * Class to contain information about the task.
 */
public class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor for task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Marks task as completed.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Unmarks task.
     */
    public void unmark() {
        isDone = false;
    }
}
