package grader.analyzer;

import grader.parser.AST;

public class RecursionAnalyzer implements Analyzer {
    private final RecursionConfig configuration;

    public RecursionAnalyzer(RecursionConfig configuration) {
        this.configuration = configuration;
    }

    public AnalyzerResult analyze(AST ast) {
        return new AnalyzerResult("", 0);
    }

}
