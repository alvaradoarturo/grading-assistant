package grader;

public class StudentSample {
    private String name;
    private int id;
    public static int studentCount = 0;

    private int[] scores;

    public StudentSample(String name, int id) {
        this.name = name;
        this.id = id;
        this.scores = new int[] { 85, 90, 92, 88 };
        studentCount++;
    }

    public int getScoreAt(int index) {
        if (index < 0 || index >= scores.length)
            return -1;
        return scores[index];
    }

    public int calculateSum() {
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        return sum;
    }

    public int factorial(int n) {
        if (n <= 1)
            return 1;
        return n * factorial(n - 1); // recursion
    }

    public void printPerformance() {
        int total = calculateSum(); // method call
        switch (total / scores.length) {
            case 90:
            case 91:
            case 92:
            case 93:
                System.out.println("Excellent");
                break;
            case 80:
            case 81:
            case 82:
                System.out.println("Good");
                break;
            default:
                System.out.println("Needs improvement");
        }
    }

    public boolean hasPerfectScore() {
        for (int score : scores) {
            if (score == 100)
                return true;
        }
        return false;
    }

    // Overloaded method
    public boolean hasScoreAbove(int threshold) {
        for (int score : scores) {
            if (score > threshold)
                return true;
        }
        return false;
    }

    // Nested loop example
    pblic void compareScores(StudentSample other) {
        for (int i = 0; i < this.scores.length; i++) {
            for (int j = 0; j < other.scores.length; j++) {
                if (this.scores[i] == other.scores[j]) {
                    System.out.println("Common score: " + this.scores[i]);
                }
            }
        }
    }
}
