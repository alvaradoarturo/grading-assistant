package grader.analyzer;

import grader.parser.AST;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;

import grader.analyzer.AnalyzerResult;
import grader.analyzer.MethodCallAnalyzerConfig;

public class MethodCallAnalyzer implements Analyzer {
    private final MethodCallAnalyzerConfig configuration;

    public MethodCallAnalyzer(MethodCallAnalyzerConfig configuration) {
        this.configuration = configuration;
    }

    public AnalyzerResult analyze(AST ast) {
        CompilationUnit cu = ast.getRoot();
        List<String> feedback = new ArrayList<>();
        int score = 0;

        // list of methodCalls
        List<MethodCallExpr> methodCalls = new ArrayList<>();
        methodCalls.addAll(cu.findAll(MethodCallExpr.class));

        if (methodCalls.isEmpty()) {
            feedback.add("No method calls");
            return new AnalyzerResult((String.join("\n'", feedback)), score);
        }

        for (MethodCallExpr method : methodCalls) {
            String methodName = method.getNameAsString();
            if (configuration.requiredMethodCalls.contains(methodName)) {
                feedback.add("Method call: " + methodName + " found");
                score++;
            }
            if (configuration.forbiddenMethodCalls.contains(methodName)) {
                feedback.add("Forbidden method call: " + methodName + " found");
                score = -1000;
            }
        }
        return new AnalyzerResult((String.join("\n", feedback)), score);
    }
}
