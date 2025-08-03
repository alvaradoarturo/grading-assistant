package grader;

import grader.parser.JavaParser;
import grader.parser.Parser;
import grader.runner.JUnitTestRunner;
import grader.analyzer.AnalyzerResult;
import grader.analyzer.MethodCallAnalyzer;
import grader.analyzer.configs.MethodCallAnalyzerConfig;
import grader.parser.AST;

import java.io.IOException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws IOException {
        String code = FileUtil.readStudentCode(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/MethodCallSample.java");

        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        MethodCallAnalyzerConfig config = new MethodCallAnalyzerConfig();
        config.requiredMethodCalls = Arrays.asList("simulate");
        config.forbiddenMethodCalls = Arrays.asList("printReport");
        MethodCallAnalyzer analyzer = new MethodCallAnalyzer(config);

        AnalyzerResult result = analyzer.analyze(ast);

        System.out.println(result.getDescription());

        JUnitTestRunner jRunner = new JUnitTestRunner();
        jRunner.runTests();

    }
}
