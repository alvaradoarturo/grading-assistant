package grader.runner;

import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.tools.ToolProvider;

import javax.tools.JavaCompiler;

import grader.report.TestOutput;

public class JUnitTestRunner implements GradingTestRunner {
    public List<TestOutput> runTests() {
        File studentFile = new File(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/StudentSample.java");
        // compile student code first
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compileResult = compiler.run(null, null, System.err, studentFile.getPath());

        // 0 if compiled, if not compilation error
        if (compileResult != 0) {
            System.err.println("Compilation Error");
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }
}
