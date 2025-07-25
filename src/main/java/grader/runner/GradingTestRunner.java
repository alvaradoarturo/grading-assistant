package grader.runner;

import java.util.List;
import grader.report.TestOutput;

public interface GradingTestRunner {
    public List<TestOutput> runTests();
}