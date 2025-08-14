package grader.analyzer.configs;

import java.util.ArrayList;
import java.util.List;

public class ClassConfig {
    // class name
    public String requiredClassName = null;

    // Correct constructor header
    public List<List<String>> requiredConstructors = new ArrayList<>();

    // Declares appropriate instance variables
    public List<String> requiredFieldTypes = new ArrayList<>();
}
