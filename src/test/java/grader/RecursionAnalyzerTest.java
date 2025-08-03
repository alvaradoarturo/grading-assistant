package grader;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import grader.analyzer.AnalyzerResult;
import grader.analyzer.LoopAnalyzer;
import grader.analyzer.RecursionAnalyzer;
import grader.analyzer.configs.LoopAnalyzerConfig;
import grader.analyzer.configs.RecursionConfig;
import grader.parser.AST;
import grader.parser.JavaParser;
import grader.parser.Parser;

public class RecursionAnalyzerTest {
    @Test
    public void checksForLoops() throws IOException {
        String code = FileUtil.readStudentCode(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/StudentSample.java");
        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        // Custom Figuration
        RecursionConfig config = new RecursionConfig();
        config.recursionRequired = true;

        RecursionAnalyzer analyzer = new RecursionAnalyzer(config);
        AnalyzerResult result = analyzer.analyze(ast);

        System.out.println("Result For Recursion Test: " + result.getDescription());

        assertTrue(result.getDescription().contains("Recursion Found"));
    }
}
