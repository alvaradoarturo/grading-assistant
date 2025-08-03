package grader.analyzer.configs;

import java.util.ArrayList;
import java.util.List;

public class MethodCallAnalyzerConfig {
    public List<String> requiredMethodCalls = new ArrayList<>();
    public List<String> forbiddenMethodCalls = new ArrayList<>();
    public List<String> requiredCallsInLoop = new ArrayList<>();
    public List<String> requiredCallsInConditional = new ArrayList<>();

}
