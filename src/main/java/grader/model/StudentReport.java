package grader.model;

import java.util.ArrayList;
import java.util.List;

public class StudentReport {
    private String labId;
    private String studentId;
    private List<PointResult> points = new ArrayList<>();

    public StudentReport(String labId, String studentId) {
        this.labId = labId;
        this.studentId = studentId;
    }

    // add points to studentReport
    public void addResults(List<PointResult> results) {
        if (results != null) {
            this.points.addAll(results);
        }
    }

    // for single points
    public void addResult(PointResult result) {
        if (result != null) {
            this.points.add(result);
        }
    }

    public List<PointResult> getResults() {
        return this.points;
    }

    public int getTotalPoints() {
        return this.points.size();
    }

    public int getPointsEarned() {
        int earned = 0;
        for (PointResult point : points) {
            if (point.isEarned())
                earned++;
        }
        return earned;
    }

    @Override
    public String toString() {
        return String.format("Student %s - Lab %s: %d/%d points",
                studentId, labId, getPointsEarned(), getTotalPoints());
    }
}
