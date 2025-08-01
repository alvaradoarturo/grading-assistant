package grader.analyzer;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;

import grader.parser.AST;

public class ArrayAnalyzer implements Analyzer {
    private final ArrayConfig configuration;

    public ArrayAnalyzer(ArrayConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public AnalyzerResult analyze(AST ast) {
        CompilationUnit cu = ast.getRoot();
        List<String> feedback = new ArrayList<>();
        int score = 0;

        if (configuration.requireArrayDeclaration) {
            List<VariableDeclarator> arrayDeclarations = cu.findAll(VariableDeclarator.class);
            boolean isFound = false;
            for (VariableDeclarator currentDeclarator : arrayDeclarations) {
                if (currentDeclarator.getType().isArrayType()) {
                    feedback.add("Array Declaration Found");
                    score += 1;
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                feedback.add("Array Declaration Not Found");
                score -= 1;
            }
        }

        return new AnalyzerResult((String.join("\n", feedback)), score);
    }
}