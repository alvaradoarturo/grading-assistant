package grader.parser;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class JavaParser implements Parser {

    @Override
    public AST parse(String code) {
        // page 11 of JavaParser: Visited
        try {
            CompilationUnit cu = StaticJavaParser.parse(code);
            return new AST(cu);
        } catch (ParseProblemException e) {
            System.out.println("Parse error in file: ");
            e.getProblems().forEach(problem -> System.out.println("• " + problem.toString()));
            return null;
        }

    }
}