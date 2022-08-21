public class Deadlines extends Task {
    private String deadline;

    public Deadlines(String task, String deadline) {
        super(task);
        this.deadline = deadline;
    }

    public Deadlines(String task, String deadline, boolean done) {
        super(task, done);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", deadline);
    }

    @Override
    public String toSaveString() {
        return "deadline " + super.toSaveString() + deadline;
    }
}
