package grader.analyzer;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.ForStmt;

import grader.analyzer.configs.ArrayConfig;
import grader.model.PointResult;
import grader.parser.AST;

public class ArrayAnalyzer implements Analyzer {
    private final ArrayConfig configuration;

    public ArrayAnalyzer(ArrayConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public List<PointResult> analyze(AST ast) {
        CompilationUnit cu = ast.getRoot();
        List<PointResult> points = new ArrayList<>();

        // sees if array is declared with required name
        if (configuration.requiredArrayName != "") {
            List<VariableDeclarator> arrayDeclarations = cu.findAll(VariableDeclarator.class);
            boolean isFound = false;
            for (VariableDeclarator currentDeclarator : arrayDeclarations) {
                if (currentDeclarator.getType().isArrayType()
                        && currentDeclarator.getNameAsString().equals(configuration.requiredArrayName)) {
                    points.add(PointResult.pass("array.initialized.exists",
                            "Array correctly initialized",
                            "Array Exists: " + configuration.requiredArrayName));
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                points.add(PointResult.fail("array.initialized.missing",
                        "Array correctly initialized",
                        "Array Does Not Exist: " + configuration.requiredArrayName));
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
                // TODO
            }

        }

        return points;
    }
}