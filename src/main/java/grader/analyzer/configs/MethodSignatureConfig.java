package grader.analyzer.configs;

import java.util.ArrayList;
import java.util.List;

public class MethodSignatureConfig {
    public String targetClassName = null;
    public List<RequiredMethod> requiredMethods = new ArrayList<>();

    public static class RequiredMethod {
        public String name;
        public String returnType;
        public java.util.List<String> paramTypes;
        public boolean requirePublic = true;
        public boolean requireStatic = false;
    }
}
