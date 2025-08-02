package grader;

public class StudentSample {
    private String name;
    private int id;
    public static int studentCount = 0;

    private int[] allQuestions;

    public StudentSample(String name, int id) {
        this.name = name;
        this.id = id;
        this.allQuestions = new int[] { 85, 90, 92, 88 };
        studentCount++;
    }

    public int getScoreAt(int index) {
        if (index < 0 || index >= allQuestions.length)
            return -1;
        return allQuestions[index];
    }

    public int calculateSum() {
        int sum = 0;
        for (int score : allQuestions) {
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
        switch (total / allQuestions.length) {
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
        for (int score : allQuestions) {
            if (score == 100)
                return true;
        }
        return false;
    }

    // Overloaded method
    public boolean hasScoreAbove(int threshold) {
        for (int score : allQuestions) {
            if (score > threshold)
                return true;
        }
        return false;
    }

    // Nested loop example
    public void compareallQuestions(StudentSample other) {
        for (int i = 0; i < this.allQuestions.length; i++) {
            for (int j = 0; j < other.allQuestions.length; j++) {
                if (this.allQuestions[i] == other.allQuestions[j]) {
                    System.out.println("Common score: " + this.allQuestions[i]);
                }
            }
        }
    }
}
