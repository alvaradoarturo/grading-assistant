package grader.analyzer;

import grader.parser.AST;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        List<MethodCallExpr> allMethods = new ArrayList<>();
        allMethods.addAll(cu.findAll(MethodCallExpr.class));
        // turn into set of easy use
        Set<String> methodCalls = allMethods.stream().map(MethodCallExpr::getNameAsString).collect(Collectors.toSet());

        if (methodCalls.isEmpty()) {
            feedback.add("No method calls");
            return new AnalyzerResult((String.join("\n'", feedback)), score);
        }

        // Sees if there is a list of forbidden methods
        if (!configuration.forbiddenMethodCalls.isEmpty()) {
            for (String method : configuration.forbiddenMethodCalls) {
                if (methodCalls.contains(method)) {
                    feedback.add("Forbidden Method Called: " + method);
                    score = -1000;
                }
            }
        }
        // Sees if there is a list of required methods
        if (!configuration.requiredMethodCalls.isEmpty()) {
            for (String method : configuration.requiredMethodCalls) {
                if (methodCalls.contains(method)) {
                    feedback.add("Required Method Called: " + method);
                    score++;
                } else {
                    feedback.add("Missing Method: " + method);
                    score--;
                }
            }
        }

        return new AnalyzerResult((String.join("\n", feedback)), score);
    }
}
