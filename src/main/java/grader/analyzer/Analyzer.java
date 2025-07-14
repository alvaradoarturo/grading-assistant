package grader.analyzer;

import grader.parser.AST;

public interface Analyzer {
    public AnalyzerResult analyze(AST ast);
}