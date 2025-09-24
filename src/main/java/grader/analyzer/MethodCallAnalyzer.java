package grader.analyzer;

import grader.parser.AST;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.WhileStmt;

import grader.analyzer.configs.MethodCallAnalyzerConfig;
import grader.model.PointResult;

public class MethodCallAnalyzer implements Analyzer {
    private final MethodCallAnalyzerConfig configuration;

    public MethodCallAnalyzer(MethodCallAnalyzerConfig configuration) {
        this.configuration = configuration;
    }

    public List<PointResult> analyze(AST ast) {
        CompilationUnit cu = ast.getRoot();
        List<PointResult> points = new ArrayList<>();

        // list of methodCalls
        List<MethodCallExpr> allMethods = new ArrayList<>();
        allMethods.addAll(cu.findAll(MethodCallExpr.class));
        // turn into set for easy use
        Set<String> methodCalls = allMethods.stream().map(MethodCallExpr::getNameAsString).collect(Collectors.toSet());

        if (methodCalls.isEmpty()) {
            points.add(
                    PointResult.fail("methods.usage.missing", "Missing Method Calls",
                            "Code contains no method calls"));
            return points;
        }

        // Sees if there is a list of forbidden methods
        if (!configuration.forbiddenMethodCalls.isEmpty()) {
            for (String method : configuration.forbiddenMethodCalls) {
                if (methodCalls.contains(method)) {
                    points.add(
                            PointResult.fail("methods.usage.forbidden", "Forbidden Method Calls",
                                    "Code contains calls to forbidden methods"));
                    return points;
                }
            }
        }
        // Sees if there is a list of required methods
        if (!configuration.requiredMethodCalls.isEmpty()) {
            // calls are met

            boolean allPresent = methodCalls.containsAll(configuration.requiredMethodCalls);
            if (allPresent) {
                points.add(PointResult.pass("methods.requiredCalls.present", "All required calls are used",
                        "Required Calls are all met"));
            }
            if (!allPresent) {
                points.add(PointResult.fail("methods.requiredCalls.missing", "All required calls are used",
                        "Required Calls are not met"));
            }
        }
        // Checks methods that need to be called in the context of a loop
        if (!configuration.requiredCallsInLoop.isEmpty()) {
            // Get all loops in same list
            List<Node> loops = new ArrayList<>();
            loops.addAll(cu.findAll(ForStmt.class));
            loops.addAll(cu.findAll(WhileStmt.class));
            if (loops.isEmpty()) {
                points.add(PointResult.fail("methods.methodsInLoop.missing",
                        "Required call to methods in context of a loop",
                        "Missing methods calls in context of a loop"));
            } else {
                // Get all method calls that exist within loops
                Set<String> methodsInLoops = new HashSet<>();

                for (Node loop : loops) {
                    List<MethodCallExpr> methodsFound = loop.findAll(MethodCallExpr.class);

                    for (MethodCallExpr method : methodsFound) {
                        methodsInLoops.add(method.getNameAsString());
                    }
                }
                // See if each required loop is found
                if (methodsInLoops.containsAll(configuration.requiredCallsInConditional)) {
                    points.add(PointResult.pass("methods.methodsInLoop.exists",
                            "Required call to methods in context of a loop",
                            "Correct methods calls in context of a loop"));
                } else {
                    points.add(PointResult.fail("methods.methodsInLoop.missing",
                            "Required call to methods in context of a loop",
                            "Missing methods calls in context of a loop"));
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
            if (methodsNamesInsideConditionals.containsAll(configuration.requiredCallsInConditional)) {
                points.add(PointResult.pass("methods.methodsInsideConditional.exists",
                        "Required call to methods in context of a conditional",
                        "Correct methods calls in context of a conditional"));
            } else {
                points.add(PointResult.fail("methods.methodsInsideConditional.missing",
                        "Required call to methods in context of a conditional",
                        "Missing methods calls in context of a conditional"));
            }
        }

        return points;
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
