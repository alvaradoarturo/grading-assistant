package grader.runner;

import java.util.ArrayList;
import java.util.List;

import grader.analyzer.*;
import grader.model.*;
import grader.parser.AST;

public class AnalyzerRunner {
    public static List<PointResult> runAll(AST ast, List<Analyzer> analyzers) {
        List<PointResult> allResults = new ArrayList<>();

        for (Analyzer analyzer : analyzers) {
            try {
                List<PointResult> results = analyzer.analyze(ast);
                if (results != null) {
                    allResults.addAll(results);
                }
            } catch (Exception e) {
                String analyzerName = analyzer.getClass().getSimpleName();
                if (analyzerName.endsWith("Analyzer")) {
                    analyzerName = analyzerName.substring(0, analyzerName.length() - "Analyzer".length());
                }
                String pointId = analyzerName + ".crashed";
                String description = analyzerName.substring(0, 1).toUpperCase() + analyzerName.substring(1)
                        + " analyzer execution";
                String note = "Analyzer: " + analyzerName + " crashed: " + e.getMessage();

                allResults.add(PointResult.fail(pointId, description, note));

                e.printStackTrace();

            }
        }
        return allResults;
    }
}
