package grader;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import grader.analyzer.AnalyzerResult;
import grader.analyzer.LoopAnalyzer;
import grader.analyzer.LoopAnalyzerConfig;
import grader.parser.AST;
import grader.parser.JavaParser;
import grader.parser.Parser;

public class MethodCallAnalyzerTest {
    @Test
    public void callsCorrectMethodOnly() throws Exception {
        String code = FileUtil.readStudentCode(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/OnlyForLoop.java");
        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        LoopAnalyzerConfig config = new LoopAnalyzerConfig();
        config.requireWhileLoop = true;

        LoopAnalyzer analyzer = new LoopAnalyzer(config);
        AnalyzerResult result = analyzer.analyze(ast);

        assertTrue(result.getScore() < 2);
        assertTrue(result.getDescription().contains("Missing While Loop"));
    }
}
