package grader;

import com.github.javaparser.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import grader.FileUtil;

import grader.analyzer.AnalyzerResult;
import grader.analyzer.LoopAnalyzer;
import grader.analyzer.LoopAnalyzerConfig;
import grader.parser.Parser;
import grader.parser.AST;
import grader.parser.JavaParser;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class LoopAnalyzerTest {
    @Test
    public void checksForLoops() throws IOException {
        String code = FileUtil.readStudentCode(
                "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/java/grader/StudentSample2.java");

        Parser parser = new JavaParser();

        AST ast = parser.parse(code);

        // Custom Figuration
        LoopAnalyzerConfig config = new LoopAnalyzerConfig();
        config.requireForLoop = true;
        config.requireWhileLoop = true;
        config.acceptedLoopConditions = Arrays.asList("i < 5", "j < 5");

        LoopAnalyzer analyzer = new LoopAnalyzer(config);
        AnalyzerResult result = analyzer.analyze(ast);

        System.out.println("Score: " + result.getScore());
        System.out.println("Result: " + result.getDescription());

        assertTrue(result.getScore() > 1);
    }
}
