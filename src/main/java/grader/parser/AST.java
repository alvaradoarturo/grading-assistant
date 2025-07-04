package grader.parser;

import com.github.javaparser.ast.CompilationUnit;

public class AST {
    private final CompilationUnit root;

    public AST(CompilationUnit root) {
        this.root = root;
    }

    public CompilationUnit getRoot() {
        return this.root;
    }
}