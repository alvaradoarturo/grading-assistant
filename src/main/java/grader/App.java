package grader;

import grader.parser.JavaParser;
import grader.parser.Parser;
import grader.parser.AST;

public class App {
    public static void main(String[] args) {
        String code = "public class Test { void hellur() { System.out.println(\"Hello World\"); } }";

        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        System.out.println(ast.getRoot()); // compilation unit
    }
}
