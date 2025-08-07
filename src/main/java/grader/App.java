package grader;

import grader.parser.JavaParser;
import grader.parser.Parser;
import grader.runner.JUnitTestRunner;
import grader.analyzer.AnalyzerResult;
import grader.analyzer.ClassAnalyzer;
import grader.analyzer.MethodCallAnalyzer;
import grader.analyzer.configs.ClassConfig;
import grader.analyzer.configs.MethodCallAnalyzerConfig;
import grader.parser.AST;

import java.io.IOException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws IOException {
        String code = FileUtil.readStudentCode(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/ClassSample.java");

        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        ClassConfig config = new ClassConfig();
        config.requiredClassName = "yo";
        ClassAnalyzer analyzer = new ClassAnalyzer(config);

        AnalyzerResult result = analyzer.analyze(ast);

        // System.out.println(result.getDescription());

        // JUnitTestRunner jRunner = new JUnitTestRunner();
        // jRunner.runTests();

    }
}
