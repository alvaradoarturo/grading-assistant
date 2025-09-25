package grader.runner;

import java.util.List;

import grader.model.PointResult;

public interface GradingTestRunner {
    public List<PointResult> runTests();
}