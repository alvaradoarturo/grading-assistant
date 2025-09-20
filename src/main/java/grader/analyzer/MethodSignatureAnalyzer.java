package grader.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import grader.analyzer.configs.ClassConfig;
import grader.analyzer.configs.MethodSignatureConfig;
import grader.model.PointResult;
import grader.parser.AST;

public class MethodSignatureAnalyzer implements Analyzer {
    private final MethodSignatureConfig configuration;

    public MethodSignatureAnalyzer(MethodSignatureConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public List<PointResult> analyze(AST ast) {
        List<String> feedback = new ArrayList<>();
        List<PointResult> points = new ArrayList<>();
        int score = 0;
        CompilationUnit cu = ast.getRoot();

        if (!configuration.requiredMethods.isEmpty()) {
            // get all methods
            // for each method check name, return type, and paramater list
            // check if it is public, and static if required to be static
            Optional<ClassOrInterfaceDeclaration> foundClass = cu.getClassByName(configuration.targetClassName);

        }
        return points;
    }
}
