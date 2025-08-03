package grader;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import grader.analyzer.AnalyzerResult;
import grader.analyzer.MethodCallAnalyzer;
import grader.analyzer.configs.MethodCallAnalyzerConfig;
import grader.parser.AST;
import grader.parser.JavaParser;
import grader.parser.Parser;

public class MethodCallAnalyzerTest {
    @Test
    public void callsCorrectMethodOnly() throws Exception {
        String code = FileUtil.readStudentCode(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/MethodCallSample.java");
        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        MethodCallAnalyzerConfig config = new MethodCallAnalyzerConfig();
        config.requiredMethodCalls = Arrays.asList("simulate");
        config.forbiddenMethodCalls = Arrays.asList("printReport");
        MethodCallAnalyzer analyzer = new MethodCallAnalyzer(config);

        AnalyzerResult result = analyzer.analyze(ast);

        assertTrue(result.getScore() < 0);
        assertTrue(result.getDescription().contains("Required Method"));

    }

    @Test
    public void checksForCallsInsideLoop() throws Exception {
        String code = FileUtil.readStudentCode(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/MethodCallSample.java");
        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        MethodCallAnalyzerConfig config = new MethodCallAnalyzerConfig();
        config.requiredCallsInLoop = Arrays.asList("step", "doesntExist");
        MethodCallAnalyzer analyzer = new MethodCallAnalyzer(config);

        AnalyzerResult result = analyzer.analyze(ast);

        assertTrue(result.getDescription().contains("Calls inside loops: step"));
        assertTrue(result.getDescription().contains("Missing required method calls inside loops: doesntExist"));
    }

    @Test
    public void checksForCallsInsideConditional() throws Exception {
        String code = FileUtil.readStudentCode(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/MethodCallSample.java");
        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        MethodCallAnalyzerConfig config = new MethodCallAnalyzerConfig();
        config.requiredCallsInConditional = Arrays.asList("simulate", "momma");
        MethodCallAnalyzer analyzer = new MethodCallAnalyzer(config);

        AnalyzerResult result = analyzer.analyze(ast);

        assertTrue(result.getDescription().contains("Calls inside conditionals: simulate"));
        assertTrue(result.getDescription().contains("Missing required method calls inside conditionals: momma"));
    }
}
