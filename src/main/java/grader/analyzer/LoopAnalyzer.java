package grader.analyzer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.*;

import grader.parser.AST;

import java.util.ArrayList;
import java.util.List;

public class LoopAnalyzer implements Analyzer {
    private final LoopAnalyzerConfig configuration;

    public LoopAnalyzer(LoopAnalyzerConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public AnalyzerResult analyze(AST ast) {
        CompilationUnit cu = ast.getRoot();
        List<String> feedback = new ArrayList<>();
        int score = 0;

        // Get all loops in same list
        List<Node> loops = new ArrayList<>();
        loops.addAll(cu.findAll(ForStmt.class));
        loops.addAll(cu.findAll(WhileStmt.class));

        // No loops are used
        if (loops.size() == 0) {
            feedback.add("No loops are used");
            return new AnalyzerResult((String.join("\n", feedback)), score);
        }

        // iterate over list of loops
        for (Node loop : loops) {
            // checks for presence and requirement of loop
            if (loop instanceof ForStmt && configuration.requireForLoop) {
                feedback.add("For Loop was found!");
                score++;
            }
            if (loop instanceof WhileStmt && configuration.requireWhileLoop) {
                feedback.add("While Loop was found");
                score++;
            }

            // check loop condition and see if there are requirements
            if (!configuration.acceptedLoopConditions.isEmpty()) {
                String loopCondition = getCondition(loop);
                for (String condition : configuration.acceptedLoopConditions) {
                    if (condition.replaceAll("\\s+", "").equals(loopCondition)) {
                        feedback.add("+ Matching Loop Condition");
                        break;
                    }
                }
            }
        }

        return new AnalyzerResult((String.join("\n", feedback)), score);

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