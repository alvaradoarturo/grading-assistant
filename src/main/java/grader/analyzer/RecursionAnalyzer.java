package grader.analyzer;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

import grader.parser.AST;

public class RecursionAnalyzer implements Analyzer {
    private final RecursionConfig configuration;

    public RecursionAnalyzer(RecursionConfig configuration) {
        this.configuration = configuration;
    }

    public AnalyzerResult analyze(AST ast) {
        CompilationUnit cu = ast.getRoot();
        List<String> feedback = new ArrayList<>();
        int score = 0;

        // get all methods
        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);

        if (configuration.recursionRequired) {
            boolean recurionFound = false;
            for (MethodDeclaration method : methods) {
                String methodName = method.getNameAsString();
                // get a list of all methods called inside of each declared method
                List<MethodCallExpr> methodCalls = method.findAll(MethodCallExpr.class);
                for (MethodCallExpr methodCall : methodCalls) {
                    if (methodCall.getNameAsString().equals(methodName)) {
                        feedback.add("Recursion Found");
                        recurionFound = true;
                    }
                }
            }
            if (!recurionFound) {
                feedback.add("Recursion Not Found");
            }
        }
        return new AnalyzerResult((String.join("/n", feedback)), score);
    }

}
