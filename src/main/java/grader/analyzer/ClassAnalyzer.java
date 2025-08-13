package grader.analyzer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;

import grader.analyzer.configs.ClassConfig;
import grader.parser.AST;

public class ClassAnalyzer implements Analyzer {
    private final ClassConfig configuration;

    public ClassAnalyzer(ClassConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public AnalyzerResult analyze(AST ast) {
        List<String> feedback = new ArrayList<>();
        int score = 0;
        CompilationUnit cu = ast.getRoot();

        // see if name of class is correct
        if (configuration.requiredClassName != null) {
            // get all class Declarations
            boolean foundClass = false;
            List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);
            for (ClassOrInterfaceDeclaration singleClass : classes) {
                if (singleClass.getNameAsString().equals(configuration.requiredClassName)) {
                    feedback.add("Class: " + configuration.requiredClassName + "found");
                    foundClass = true;
                }
            }
            if (!foundClass) {
                feedback.add("Class: " + configuration.requiredClassName + " not found");
            }
        }
        // see if signature of constructor(s) is correct
        if (!configuration.requiredConstructors.isEmpty()) {
            List<ConstructorDeclaration> constructors = cu.findAll(ConstructorDeclaration.class);
            
        }

        return new AnalyzerResult((String.join("\n", feedback)), score);
    }
}
