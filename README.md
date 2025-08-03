# grading-assistant
Designed and built for my AP Computer Science A classes

## Parsing
This project uses the JavaParser library to transform raw Java source code into an Abstract Syntax Tree (AST) for static analysis.

### Analyzers
The following analyzers are currently implemented in the Grading Assistant:

| Analyzer       | Description                                                                                  | Example Use Case                                                   |
|----------------|----------------------------------------------------------------------------------------------|----------------------------------------------------------------------|
| **LoopAnalyzer**       | Checks for loop usage, specific loop types (`for`, `while`), bounds correctness, and more.      | Verify student loops from index `0` to `array.length - 1`.          |
| **MethodCallAnalyzer**| Detects presence or absence of required method calls, forbidden method usage, and call context. | Ensure a student calls `simulate()` inside a loop or conditional.   |
| **ArrayAnalyzer**     | Verifies array declaration, initialization, and traversal (including enhanced `for` loops).      | Check that `allQuestions` is properly declared and fully traversed. |

Each analyzer is **configurable per lab**, allowing for flexible grading of various AP CSA FRQ-style problems.
