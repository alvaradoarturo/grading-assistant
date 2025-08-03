package grader.analyzer;

import grader.parser.AST;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.WhileStmt;

import grader.analyzer.AnalyzerResult;
import grader.analyzer.configs.MethodCallAnalyzerConfig;

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
        // turn into set for easy use
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
        // Checks methods that need to be called in the context of a loop
        if (!configuration.requiredCallsInLoop.isEmpty()) {
            // Get all loops in same list
            List<Node> loops = new ArrayList<>();
            loops.addAll(cu.findAll(ForStmt.class));
            loops.addAll(cu.findAll(WhileStmt.class));
            if (loops.isEmpty()) {
                feedback.add("No method called in context of a loop");
                score--;
            } else {
                // Get all method calls that exist within loops
                Set<String> methodsInLoops = new HashSet<>();
                List<String> found = new ArrayList<>();
                List<String> missing = new ArrayList<>();

                for (Node loop : loops) {
                    List<MethodCallExpr> methodsFound = loop.findAll(MethodCallExpr.class);

                    for (MethodCallExpr method : methodsFound) {
                        methodsInLoops.add(method.getNameAsString());
                    }
                }
                // See if each required loop is found
                for (String method : configuration.requiredCallsInLoop) {
                    if (methodsInLoops.contains(method)) {
                        found.add(method);
                    } else {
                        missing.add(method);
                    }
                }

                // Add results to feedback
                if (!found.isEmpty()) {
                    feedback.add("Calls inside loops: " + String.join(", ", found));
                }

                if (!missing.isEmpty()) {
                    feedback.add("Missing required method calls inside loops: " + String.join(", ", missing));
                }
            }
        }
        // Checks methods that need to be called in the context of a conditional stmt
        if (!configuration.requiredCallsInConditional.isEmpty()) {
            // Get all ifs
            List<IfStmt> allIfs = cu.findAll(IfStmt.class);
            // Method calls inside of conditionals
            List<MethodCallExpr> methodsInsideConditionals = new ArrayList<>();
            Set<String> methodsNamesInsideConditionals = new HashSet<>();

            List<String> found = new ArrayList<>();
            List<String> missing = new ArrayList<>();

            // get all methods at appear in conditionals.... just once!
            for (IfStmt conditionalStmt : allIfs) {
                if (isPartOfElseIf(conditionalStmt))
                    continue;

                methodsInsideConditionals.addAll(conditionalStmt.getThenStmt().findAll(MethodCallExpr.class));

                Optional<Statement> currentElse = conditionalStmt.getElseStmt();
                while (currentElse.isPresent()) {
                    Statement stmt = currentElse.get();

                    if (stmt instanceof IfStmt) {
                        IfStmt elseIf = (IfStmt) stmt;
                        methodsInsideConditionals.addAll(elseIf.getThenStmt().findAll(MethodCallExpr.class));
                        currentElse = elseIf.getElseStmt();
                    } else {
                        methodsInsideConditionals.addAll(stmt.findAll(MethodCallExpr.class));
                        break;
                    }
                }
            }

            // take all method calls and abstract method names
            for (MethodCallExpr method : methodsInsideConditionals) {
                methodsNamesInsideConditionals.add(method.getNameAsString());
            }

            // see if we find methods needed based on config file
            for (String method : configuration.requiredCallsInConditional) {
                if (methodsNamesInsideConditionals.contains(method)) {
                    found.add(method);
                } else {
                    missing.add(method);
                }
            }
            // Add results to feedback
            if (!found.isEmpty()) {
                feedback.add("Calls inside conditionals: " + String.join(", ", found));
            }
            if (!missing.isEmpty()) {
                feedback.add("Missing required method calls inside conditionals: " + String.join(", ", missing));
            }
        }

        return new AnalyzerResult((String.join("\n", feedback)), score);
    }

    public boolean isPartOfElseIf(IfStmt stmt) {
        if (!stmt.getParentNode().isPresent())
            return false;

        Node parent = stmt.getParentNode().get();
        if (parent instanceof IfStmt) {
            IfStmt parentIf = (IfStmt) parent;
            return parentIf.getElseStmt().isPresent() && parentIf.getElseStmt().get() == stmt;
        }

        return false;
    }
}
