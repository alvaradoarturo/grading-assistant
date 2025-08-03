package grader.analyzer;

import grader.analyzer.configs.ClassConfig;
import grader.parser.AST;

public class ClassAnalyzer implements Analyzer {
    private final ClassConfig configuration;

    public ClassAnalyzer(ClassConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public AnalyzerResult analyze(AST ast) {
        return new AnalyzerResult(null, 0);
    }
}
