package grader.runner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.tools.ToolProvider;

import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import com.github.javaparser.ast.expr.ArrayAccessExpr;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;

import grader.model.PointResult;
/*
    1. compiles student code to target/student-classes
    2. loads own test from target/test-classes and student clases
    3. runs list of known test
    4. returns PointResult for each test
 */

public class JUnitTestRunner implements GradingTestRunner {
    private File studentSourceFile;
    private File testFile;
    private String testClassName;

    public JUnitTestRunner(File studentSourceFile, File testFile, String testClassName) {
        this.studentSourceFile = studentSourceFile;
        this.testFile = testFile;
        this.testClassName = testClassName;
    }

    @Override
    public List<PointResult> runTests() {
        // Compile student file to target/student-classes
        try {
            File studentOutput = new File("target/student-classes");
            studentOutput.mkdirs();
            CompileResult studentCompiledResult = compileSingleJavaFile(studentSourceFile, studentOutput);

            if (!studentCompiledResult.isSucess()) {
                return List.of(PointResult.fail(testClassName, testClassName));
            }

            // Compile test file to target/test-classes
            File testOutput = new File("target/test-classes");
            testOutput.mkdirs();
            CompileResult testCompiledResult = compileSingleJavaFile(testFile, testOutput);
            if (!testCompiledResult.isSucess()) {
                return List.of(PointResult.fail(testClassName, testClassName));
            }

            // Use a class loader with compiled student and test classes
            URL[] urls = new URL[] {
                    studentOutput.toURI().toURL(),
                    testOutput.toURI().toURL()
            };
            try (URLClassLoader classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader())) {
                Thread.currentThread().setContextClassLoader(classLoader);

                Class<?> testClass = classLoader.loadClass(testClassName);

                LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                        .selectors(DiscoverySelectors.selectClass(testClass))
                        .build();
                Launcher launcher = LauncherFactory.create();
                TestResultCollector listener = new TestResultCollector();
                launcher.registerTestExecutionListeners(listener);
                launcher.execute(request);
                return listener.getResults();
            }
        } catch (Exception e) {
            return null;
        }

    }

    private static CompileResult compileSingleJavaFile(File sourceFile, File outputDirectory) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singleton(outputDirectory));
            Iterable<? extends JavaFileObject> compilationUnits = fileManager
                    .getJavaFileObjectsFromFiles(Collections.singletonList(sourceFile));
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null,
                    compilationUnits);
            boolean success = task.call();
            List<String> errors = new ArrayList<>();
            if (!success) {
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    errors.add(diagnostic.toString());
                }
            }
            return new CompileResult(success, errors);
        }

    }

    private static class TestResultCollector implements TestExecutionListener {
        private List<PointResult> results = new ArrayList<>();

        @Override
        public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
            // test methods only
            if (testIdentifier.isTest()) {
                if (testExecutionResult.getStatus() == TestExecutionResult.Status.SUCCESSFUL) {
                    results.add(PointResult.pass("test", "test"));
                } else {
                    results.add(PointResult.fail("test", "test"));
                }
            }
        }

        public List<PointResult> getResults() {
            return results;
        }

    }
}
