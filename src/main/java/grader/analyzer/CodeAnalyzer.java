package grader.analyzer;

import grader.parser.AST;

public interface CodeAnalyzer {
    AnalysisResult analyze(AST ast);
}