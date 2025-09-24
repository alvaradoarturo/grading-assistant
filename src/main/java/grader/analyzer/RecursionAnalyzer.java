package grader.analyzer;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

import grader.analyzer.configs.RecursionConfig;
import grader.model.PointResult;
import grader.parser.AST;

public class RecursionAnalyzer implements Analyzer {
    private final RecursionConfig configuration;

    public RecursionAnalyzer(RecursionConfig configuration) {
        this.configuration = configuration;
    }

    public List<PointResult> analyze(AST ast) {
        CompilationUnit cu = ast.getRoot();
        List<PointResult> points = new ArrayList<>();

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
                        points.add(PointResult.pass("recursion.usage.exists", "Solution Requires Recursion",
                                "Implementation uses Recursion"));
                        recurionFound = true;
                    }
                }
            }
            if (!recurionFound) {
                points.add(PointResult.fail("recursion.usage.missing", "Solution Requires Recursion",
                        "Implementation is missing Recursion"));
            }
        }
        return points;
    }

}
