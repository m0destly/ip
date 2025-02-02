package task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDate by;
    protected DateTimeFormatter dateFormat;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
        this.dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(dateFormat) + ")";
    }
}
