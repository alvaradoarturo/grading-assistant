package grader.analyzer;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;

import grader.analyzer.configs.ClassConfig;
import grader.model.PointResult;
import grader.parser.AST;

public class ClassAnalyzer implements Analyzer {
    private final ClassConfig configuration;

    public ClassAnalyzer(ClassConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public List<PointResult> analyze(AST ast) {
        List<PointResult> points = new ArrayList<>();
        CompilationUnit cu = ast.getRoot();

        // see if name of class is correct// will earn a point
        if (configuration.requiredClassName != null) {
            String expected = configuration.requiredClassName;
            // get all class Declarations
            boolean foundClass = false;
            List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);
            for (ClassOrInterfaceDeclaration singleClass : classes) {
                if (singleClass.getNameAsString().equals(expected)) {
                    foundClass = true;
                }
            }
            if (foundClass) {
                points.add(PointResult.pass("class.name.exists", "Correct class header (must not be private)",
                        "Found class: " + expected));
            }

            if (!foundClass) {
                points.add(PointResult.fail("class.name.missing", "Correct class header (must not be private)",
                        "Missing class: " + expected));
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
                    points.add(PointResult.pass("class.constructors.exists",
                            "Correct constructor header (must not be private)",
                            "Found constructors: " + configuration.requiredConstructors));
                } else {
                    points.add(PointResult.fail("class.constructors.missing",
                            "Correct class header (must not be private)",
                            "Missing constructors: " + configuration.requiredConstructors));
                }
            }
        }
        if (!configuration.requiredFieldTypes.isEmpty()) {
            List<FieldDeclaration> fields = cu.findAll(FieldDeclaration.class);
            List<String> fieldTypes = new ArrayList<>();
            boolean madePrivate = true;

            for (FieldDeclaration feild : fields) {
                fieldTypes.add(feild.getElementType().asString());

                if (!feild.isPrivate()) {
                    points.add(PointResult.fail("class.fields.notPrivate",
                            "Constructor correctly initializes instance variables",
                            "Instance Variables are not private"));
                    madePrivate = false;
                }
            }
            if (madePrivate) {
                List<String> missing = new ArrayList<>(configuration.requiredFieldTypes);

                for (String studentType : fieldTypes) {
                    missing.remove(studentType);
                }
                if (missing.isEmpty()) {
                    points.add(PointResult.pass("class.fields.exists",
                            "Constructor correctly initializes instance variables",
                            "Correct instance variables: " + configuration.requiredFieldTypes));
                } else {
                    points.add(PointResult.fail("class.fields.missing",
                            "Constructor correctly initializes instance variables",
                            "Correct instance variables: " + configuration.requiredFieldTypes));
                }
            }
        }
        return points;

    }
}
