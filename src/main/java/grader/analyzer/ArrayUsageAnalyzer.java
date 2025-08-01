package grader.analyzer;

import grader.parser.AST;

public class ArrayUsageAnalyzer implements Analyzer {
    private final ArrayUsageConfig configuration;

    public ArrayUsageAnalyzer(ArrayUsageConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public AnalyzerResult analyze(AST ast) {
        return new AnalyzerResult(null, 0);
    }
}