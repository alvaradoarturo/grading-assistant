package grader.analyzer;

import java.util.List;

import grader.model.PointResult;
import grader.parser.AST;

public interface Analyzer {
    public List<PointResult> analyze(AST ast);
}