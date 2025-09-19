package grader.model;

import java.util.List;
import java.util.Objects;

public class PointResult {
    // points to where it came from
    private final String id;
    // what the point is for
    private final String label;
    // point earned or not
    private final boolean earned;
    // notes that might help me or student
    private final List<String> notes;

    public PointResult(String id, String label, boolean earned, List<String>notes)}

    {
        this.id = id == null ? "" : id;
        this.label = Objects.requireNonNull(label, "label"); // has to have a label
        this.earned = earned;
        this.notes = List.copyOf(notes == null ? List.of() : notes);
    }

    public static PointResult pass() {
        // TODO
    }

    public static PointResult fail() {
        // TODO
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public boolean isEarned() {
        return this.earned;
    }

    public List<String> getNotes() {
        return notes;
    }

    public int points() {
        return earned ? 1 : 0;
    }

    public String symbol() {
        return earned ? "✅" : "❌";
    }

    // get point on one line
    public String formatOneLine() {
        if (notes.isEmpty()) {
            return symbol() + " " + label;
        }
        return symbol() + " " + label + " - " + String.join(" | ", notes);
    }

    @Override
    public String toString() {
        return "PointResult{id'" + id + "', label='" + label + "', earned=" + earned + ", notes=" + notes + "}";
    }

}
