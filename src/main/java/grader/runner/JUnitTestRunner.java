package grader.runner;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private Path studentSourceFile;
    private String testClassLoc;
    private Path outDir = Paths.get("target/student-classes");
    private Path testsDir = Paths.get("target/test-classes");

    public JUnitTestRunner(Path studentSourceFile, String testClassLoc) {
        this.studentSourceFile = Objects.requireNonNull(studentSourceFile, "studentSourceFile");
        this.testClassLoc = Objects.requireNonNull(testClassLoc, "testClassLoc");
    }

    @Override
    public List<PointResult> runTests() {
        List<PointResult> points = new ArrayList<>();
        // Compile student code (file most likely or directory) to
        // target/student-classes
        CompileResult compileResult = compileSingleFile(studentSourceFile, outDir);
        return points;
    }

    private class CompileResult {
        final boolean success;
        final List<String> diagnostics;

        CompileResult(boolean sucess, List<String> diagnostics) {
            this.success = sucess;
            this.diagnostics = diagnostics;
        }
    }

    private static CompileResult compileSingleFile(Path sourceFile, Path out) {
        List<String> diagnostics = new ArrayList<>();
    }
}
