package orchestrator;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import grader.FileUtil;
import grader.analyzer.Analyzer;
import grader.model.PointResult;
import grader.model.StudentReport;
import grader.parser.AST;
import grader.parser.JavaParser;
import grader.parser.Parser;
import grader.runner.AnalyzerRunner;

public class Grader {
    /*
     * labID - which lab
     * studentId - might be firstName_lastName
     * sourcePath - where student source file is located
     * testPATH - where test for that lab is located
     * testClass - name of class
     * 
     * returns StudentReport
     */

    public StudentReport grade(String labId, String studentId, String sourcePath,
            String testPath, String testClass) {
        StudentReport report = new StudentReport(labId, studentId);

        // static analyzers first
        try {
            String code = FileUtil.readStudentCode(sourcePath);
            Parser parser = new JavaParser();
            AST ast = parser.parse(sourcePath);
            List<Analyzer> analyzers = Arrays.asList(

            );
            List<PointResult> analysisResults = AnalyzerRunner.runAll(ast, analyzers);
            report.addResults(analysisResults);

        } catch (Exception e) {
            PointResult compileError = new PointResult("Compiler Error", "Compilation", false,
                    Arrays.asList("Compilation Error: " + e.getMessage()));
            report.addResult(compileError);
        }

        return report;
    }
}
