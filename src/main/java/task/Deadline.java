package task;

import java.time.LocalDate;

import darwin.Ui;

public class Deadline extends Task {
    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + Ui.showDateFormat(by) + ")";
    }
}
