package grader.analyzer.configs;

public class MethodSignatureConfig {
    public java.util.List<RequiredMethod> requiredMethods = java.util.List.of();

    public static class RequiredMethod {
        public String name;
        public String returnType;
        public java.util.List<String> paramTypes;
        public boolean requirePublic = true;
        public boolean requireStatic = false;
    }
}
