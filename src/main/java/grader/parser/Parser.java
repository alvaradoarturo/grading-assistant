package grader.parser;

public interface Parser {
    AST parse(String code); // will possibly be Compilation Unit or custom AST????
}