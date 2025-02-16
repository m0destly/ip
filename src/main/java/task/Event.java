package task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import exception.DarwinException;
import exception.ErrorMessage;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;
    protected DateTimeFormatter dateFormat;

    public Event(String description, LocalDate from, LocalDate to) throws DarwinException {
        super(description);
        if (to.isBefore(from)) {
            throw new DarwinException(ErrorMessage.ILLEGAL_DATES.message());
        }
        this.from = from;
        this.to = to;
        this.dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(dateFormat)
                + " to: " + to.format(dateFormat) + ")";
    }
}
