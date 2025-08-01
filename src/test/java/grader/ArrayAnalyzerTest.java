package grader;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import grader.analyzer.AnalyzerResult;
import grader.analyzer.ArrayAnalyzer;
import grader.analyzer.ArrayConfig;
import grader.analyzer.LoopAnalyzer;
import grader.analyzer.LoopAnalyzerConfig;
import grader.parser.AST;
import grader.parser.JavaParser;
import grader.parser.Parser;

public class ArrayAnalyzerTest {
    @Test
    public void checkForDeclaredArray() throws IOException {
        String code = FileUtil.readStudentCode(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/StudentSample2.java");
        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        // Custom Figuration
        ArrayConfig config = new ArrayConfig();
        config.requireArrayDeclaration = true;

        ArrayAnalyzer analyzer = new ArrayAnalyzer(config);
        AnalyzerResult result = analyzer.analyze(ast);

        System.out.println("Score: " + result.getScore());
        System.out.println("Result: " + result.getDescription());

        assertTrue(result.getScore() < 1);
        assertTrue(result.getDescription().contains("Declaration Not Found"));
    }
}
