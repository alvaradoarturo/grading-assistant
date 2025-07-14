package grader.analyzer;

public class AnalyzerResult {
    private final String description;
    private final int score;

    public AnalyzerResult(String description, int score) {
        this.description = description;
        this.score = score;
    }

    public String getDescription() {
        return this.description;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        return "Score: " + this.score + " Description: " + this.description;
    }
}
