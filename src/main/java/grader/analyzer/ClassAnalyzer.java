package grader.analyzer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;

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
            List<List<String>> constructorParamTypes = new ArrayList<>();

            for (ConstructorDeclaration constructor : constructors) {
                List<String> paramTypes = new ArrayList<>();
                for (Parameter param : constructor.getParameters()) {
                    paramTypes.add(param.getType().asString()); // "int", "String", etc.
                }
                constructorParamTypes.add(paramTypes);
            }

            for (List<String> requiredParameters : configuration.requiredConstructors) {
                boolean found = false;
                for (List<String> studentParameters : constructorParamTypes) {
                    if (requiredParameters.equals(studentParameters)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    feedback.add("Matched Constructor: " + requiredParameters);
                } else {
                    feedback.add("Missing Constructor: " + requiredParameters);
                }
            }
        }
        if (!configuration.requiredFieldTypes.isEmpty()) {
            List<FieldDeclaration> fields = cu.findAll(FieldDeclaration.class);
            List<String> fieldTypes = new ArrayList<>();

            for (FieldDeclaration feild : fields) {
                fieldTypes.add(feild.getElementType().asString());

                if (!feild.isPrivate()) {
                    feedback.add("field: " + feild.toString() + " is not private");
                }
            }

            List<String> missing = new ArrayList<>(configuration.requiredFieldTypes);
            for (String studentType : fieldTypes) {
                missing.remove(studentType);
            }
            if (missing.isEmpty()) {
                feedback.add("All required fields were declared");
            } else {
                feedback.add("Missing required field types " + missing);
            }

        }

        return new AnalyzerResult((String.join("\n", feedback)), score);
    }
}
