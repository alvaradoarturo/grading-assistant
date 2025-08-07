package grader;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import grader.analyzer.AnalyzerResult;
import grader.analyzer.ClassAnalyzer;
import grader.analyzer.LoopAnalyzer;
import grader.analyzer.configs.ClassConfig;
import grader.analyzer.configs.LoopAnalyzerConfig;
import grader.parser.AST;
import grader.parser.JavaParser;
import grader.parser.Parser;

public class ClassAnalyzerTest {
    @Test
    public void checksForLoops() throws IOException {
        String code = FileUtil.readStudentCode(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/ClassSample.java");
        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        // Custom Configuration
        ClassConfig config = new ClassConfig();
        config.requiredClassName = "MuffinMaker";

        ClassAnalyzer analyzer = new ClassAnalyzer(config);
        AnalyzerResult result = analyzer.analyze(ast);

        assertTrue(result.getDescription().contains(config.requiredClassName + "found"));
    }
}
