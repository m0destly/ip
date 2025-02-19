package task;

import java.time.LocalDate;

import darwin.Ui;

/**
 * Subclass of Task that contains a deadline.
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Constructor for deadline task.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + Ui.showDateFormat(by) + ")";
    }
}
