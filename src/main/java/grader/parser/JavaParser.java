package grader.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class JavaParser implements Parser {

    @Override
    public AST parse(String code) {
        // page 11 of JavaParser: Visited
        CompilationUnit cu = StaticJavaParser.parse(code);
        return new AST(cu);

    }
}