package grader.analyzer;

import java.util.List;

public class LoopAnalyzerConfig {
    public boolean requireForLoop = false;
    public boolean requireWhileLoop = false;
    public boolean requireEnhancedForLoop = false;
    public boolean requireLoopVariable = false;

    public List<String> acceptedLoopConditions;
    public List<String> acceptedArrayNames;
    public List<String> acceptedMethodCalls;

}
