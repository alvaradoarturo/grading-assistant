package grader.parser;

public interface Parser {
    public AST parse(String code); // will possibly be Compilation Unit or custom AST????
}