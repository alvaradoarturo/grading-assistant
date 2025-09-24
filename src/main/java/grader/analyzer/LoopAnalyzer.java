package grader.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.*;

import grader.analyzer.configs.LoopAnalyzerConfig;
import grader.model.PointResult;
import grader.parser.AST;

import java.util.ArrayList;
import java.util.List;

public class LoopAnalyzer implements Analyzer {
    private final LoopAnalyzerConfig configuration;

    public LoopAnalyzer(LoopAnalyzerConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public List<PointResult> analyze(AST ast) {
        List<PointResult> points = new ArrayList<>();
        CompilationUnit cu = ast.getRoot();

        // Get all loops in same list
        List<Node> loops = new ArrayList<>();
        loops.addAll(cu.findAll(ForStmt.class));
        loops.addAll(cu.findAll(WhileStmt.class));

        // No loops are used
        if (loops.isEmpty()) {
            points.add(PointResult.fail("loops.usage.missing", "Missing Loops", "Code contains no loops"));
            return points;
        }

        // iterate over list of loops
        for (Node loop : loops) {
            // checks for presence and requirement of loop
            // if (loop instanceof ForStmt && configuration.requireForLoop) {
            // feedback.add("For Loop was found!");
            // score++;
            // } else {
            // feedback.add("Missing For Loop");
            // }
            // if (loop instanceof WhileStmt && configuration.requireWhileLoop) {
            // feedback.add("While Loop was found");
            // score++;
            // } else {
            // feedback.add("Missing While Loop");
            // }

            // check loop condition and see if there are requirements
            if (!configuration.acceptedLoopConditions.isEmpty()) {
                String loopCondition = getCondition(loop);
                for (String condition : configuration.acceptedLoopConditions) {
                    if (condition.replaceAll("\\s+", "").equals(loopCondition)) {
                        points.add(PointResult.pass("loops.conditions.match", "Iterates using correct conditions",
                                "Correct Condition Used: " + loopCondition));
                        break;
                    }
                }
                points.add(PointResult.fail("loops.conditions.missing", "Iterates using correct conditions",
                        "Incorrect Condition Used"));
            }
        }

        return points;

    }

    private String getCondition(Node loop) {
        if (loop instanceof ForStmt) {
            return (((ForStmt) loop).getCompare()).toString().replaceAll("\\s+", "");
        } else if (loop instanceof WhileStmt) {
            return (((WhileStmt) loop).getCondition().toString()).replaceAll("\\s+", "");
        } else {
            return "Error getting condition";
        }

    }
}