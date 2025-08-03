package grader.analyzer;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.ForStmt;

import grader.analyzer.configs.ArrayConfig;
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

        // sees if array is declared with required name
        if (configuration.requiredArrayName != "") {
            List<VariableDeclarator> arrayDeclarations = cu.findAll(VariableDeclarator.class);
            boolean isFound = false;
            for (VariableDeclarator currentDeclarator : arrayDeclarations) {
                if (currentDeclarator.getType().isArrayType()
                        && currentDeclarator.getNameAsString().equals(configuration.requiredArrayName)) {
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

        if (configuration.requireLoopOverArray) {
            // get all for loops
            List<ForStmt> forLoops = cu.findAll(ForStmt.class);
            // check each for loop and see if the condition, intitialization, and size all
            // are correct
            for (ForStmt loop : forLoops) {
                String intitialization = loop.getInitialization().toString();
                String condition = "";
                if (loop.getCompare().isPresent()) {
                    condition = loop.getCompare().get().toString();
                }

                feedback.add("Loops over correctly: " + (intitialization.contains("=0")
                        && condition.contains("<" + configuration.requiredArrayName + ".length")));
            }

        }

        return new AnalyzerResult((String.join("\n", feedback)), score);
    }
}