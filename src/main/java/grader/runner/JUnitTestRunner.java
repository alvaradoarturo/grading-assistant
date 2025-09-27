package grader.runner;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.tools.ToolProvider;

import javax.tools.JavaCompiler;

import grader.model.PointResult;
/*
    1. compiles student code to target/student-classes
    2. loads own test from target/test-classes and student clases
    3. runs list of known test
    4. returns PointResult for each test
 */

public class JUnitTestRunner implements GradingTestRunner {
    private Path studentSource;
    private List<String> testClassNames;
    private Path studentOutputDirectory;

    public JUnitTestRunner(Path studentSource, List<String> testClassNames, Path studentOutputDirectory) {
        this.studentSource = Objects.requireNonNull(studentSource, "studentSource");
        this.testClassNames = List.copyOf(Objects.requireNonNull(testClassNames, "testClassNames"));
        this.studentOutputDirectory = Objects.requireNonNull(studentOutputDirectory, "studentOutputDirectory");
    }

    public JUnitTestRunner(Path studentSource, List<String> testClassNames) {
        this(studentSource, testClassNames, Paths.get("target/student-classes"));
    }

    @Override
    public List<PointResult> runTests() {
    }
}
