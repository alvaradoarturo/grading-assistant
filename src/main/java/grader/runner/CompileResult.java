package grader.runner;

import java.util.List;

public class CompileResult {
    final boolean success;
    final List<String> diagnostics;

    CompileResult(boolean sucess, List<String> diagnostics) {
        this.success = sucess;
        this.diagnostics = diagnostics;
    }

    public boolean isSucess() {
        return success;
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }
}