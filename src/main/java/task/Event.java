package task;

import java.time.LocalDate;

import darwin.Ui;
import exception.DarwinException;
import exception.ErrorMessage;

/**
 * Subclass of Task that contains start and end dates.
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    /**
     * Constructor for event task.
     * @throws DarwinException If the start date is after the end date.
     */
    public Event(String description, LocalDate from, LocalDate to) throws DarwinException {
        super(description);
        if (to.isBefore(from)) {
            throw new DarwinException(ErrorMessage.ILLEGAL_DATES.getMessage());
        }
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + Ui.showDateFormat(from)
                + " to: " + Ui.showDateFormat(to) + ")";
    }
}
