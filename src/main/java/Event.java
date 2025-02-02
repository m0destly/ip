import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;
    protected DateTimeFormatter dateFormat;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
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
