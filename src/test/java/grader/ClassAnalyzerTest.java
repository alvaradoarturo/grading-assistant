// package grader;

// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.io.IOException;
// import java.util.Arrays;
// import java.util.List;

// import org.junit.jupiter.api.Test;

// import grader.analyzer.AnalyzerResult;
// import grader.analyzer.ClassAnalyzer;
// import grader.analyzer.LoopAnalyzer;
// import grader.analyzer.configs.ClassConfig;
// import grader.analyzer.configs.LoopAnalyzerConfig;
// import grader.parser.AST;
// import grader.parser.JavaParser;
// import grader.parser.Parser;

// public class ClassAnalyzerTest {
// @Test
// public void checkClassName() throws IOException {
// String code = FileUtil.readStudentCode(
// "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/ClassSample.java");
// Parser parser = new JavaParser();
// AST ast = parser.parse(code);

// // Custom Configuration
// ClassConfig config = new ClassConfig();
// config.requiredClassName = "MuffinMaker";

// ClassAnalyzer analyzer = new ClassAnalyzer(config);
// AnalyzerResult result = analyzer.analyze(ast);

// assertTrue(result.getDescription().contains(config.requiredClassName +
// "found"));
// }

// @Test
// public void checksMatchingListOfConstructors() throws IOException {
// String code = FileUtil.readStudentCode(
// "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/ClassSample.java");
// Parser parser = new JavaParser();
// AST ast = parser.parse(code);

// // Custom Configuration
// ClassConfig config = new ClassConfig();
// config.requiredConstructors = List.of(
// List.of("int"),
// List.of("String, int"));

// ClassAnalyzer analyzer = new ClassAnalyzer(config);
// AnalyzerResult result = analyzer.analyze(ast);

// assertTrue(result.getDescription().contains("Matched Constructor"));
// }

// @Test
// public void checksMissingConstructors() throws IOException {
// String code = FileUtil.readStudentCode(
// "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/ClassSample.java");
// Parser parser = new JavaParser();
// AST ast = parser.parse(code);

// // Custom Configuration
// ClassConfig config = new ClassConfig();
// config.requiredConstructors = List.of(
// List.of("boolean"));

// ClassAnalyzer analyzer = new ClassAnalyzer(config);
// AnalyzerResult result = analyzer.analyze(ast);

// assertTrue(result.getDescription().contains("Missing Constructor"));
// }

// @Test
// public void checksFieldTypes() throws IOException {
// String code = FileUtil.readStudentCode(
// "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/ClassSample.java");
// Parser parser = new JavaParser();
// AST ast = parser.parse(code);

// // Custom Configuration
// ClassConfig config = new ClassConfig();
// config.requiredFieldTypes = List.of("int", "int", "String");

// ClassAnalyzer analyzer = new ClassAnalyzer(config);
// AnalyzerResult result = analyzer.analyze(ast);

// assertTrue(result.getDescription().contains("All required"));
// }

// @Test
// public void checkForMissingFieldTypes() throws IOException {
// String code = FileUtil.readStudentCode(
// "/Users/theboy/Desktop/thesis/grading-assistant/grading-assistant/src/main/StudentSamples/ClassSample.java");
// Parser parser = new JavaParser();
// AST ast = parser.parse(code);

// // Custom Configuration
// ClassConfig config = new ClassConfig();
// config.requiredFieldTypes = List.of("int", "String", "String");

// ClassAnalyzer analyzer = new ClassAnalyzer(config);
// AnalyzerResult result = analyzer.analyze(ast);
// System.out.println("------------Missing Field Types------------");
// System.out.println(result.getDescription());

// assertTrue(result.getDescription().contains("Missing required field types"));
// }
// }
