package grader.analyzer;

import grader.analyzer.configs.ClassConfig;
import grader.analyzer.configs.MethodSignatureConfig;
import grader.parser.AST;

public class MethodSignatureAnalyzer implements Analyzer {
    private final MethodSignatureConfig configuration;

    public MethodSignatureAnalyzer(MethodSignatureConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public AnalyzerResult analyze(AST ast) {
        return new AnalyzerResult(null, 0);
    }
}
