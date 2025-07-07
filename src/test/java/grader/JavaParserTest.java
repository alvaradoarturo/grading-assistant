package grader;

import grader.parser.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import java.util.List;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Node;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JavaParserTest {

    @Test
    public void shouldHaveMethod() {
        boolean foundMethod = false;
        String code = "public class Test { void hellur() { System.out.println(\"Hello World\"); } }";

        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        // Look for the method hellur
        List<MethodDeclaration> methods = ast.getRoot().findAll(MethodDeclaration.class);
        for (MethodDeclaration method : methods) {
            if (method.getNameAsString().equals("hellur"))
                foundMethod = true;
        }
        assertTrue(foundMethod);
    }
}
