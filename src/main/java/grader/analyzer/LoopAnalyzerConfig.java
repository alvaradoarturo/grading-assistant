package grader.analyzer;

import java.util.ArrayList;
import java.util.List;

public class LoopAnalyzerConfig {
    public boolean requireForLoop = false;
    public boolean requireWhileLoop = false;
    public boolean requireEnhancedForLoop = false;

    public List<String> acceptedLoopConditions = new ArrayList<>();

}
