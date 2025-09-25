package grader.runner;

import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.tools.ToolProvider;

import javax.tools.JavaCompiler;

import grader.model.PointResult;

public class JUnitTestRunner implements GradingTestRunner {
    public List<PointResult> runTests() {
        File studentFile = new File(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/");
        // compile student code first
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compileResult = compiler.run(null, null, System.err, studentFile.getPath());

        // 0 if compiled, if not compilation error
        if (compileResult != 0) {
            System.err.println("Compilation Error");
            return Collections.emptyList();
        }

        // Run JUnit tests against compiled code
        return Collections.emptyList();
    }
}
