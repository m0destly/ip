import java.util.ArrayList;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public void mark() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    public void added(ArrayList<Task> tasks) {
        tasks.add(this);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + this);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }
}
