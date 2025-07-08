package grader;

import grader.parser.JavaParser;
import grader.parser.Parser;
import grader.parser.AST;

import java.io.IOException;
import java.util.List;

import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.SwitchStmt;

public class App {
    public static void main(String[] args) throws IOException {
        String code = FileUtil.readStudentCode(
                "src/main/java/grader/StudentSample.java");

        Parser parser = new JavaParser();
        AST ast = parser.parse(code);

        // Practice finding occurences
        List<FieldDeclaration> fields = ast.getRoot().findAll(FieldDeclaration.class);
        List<MethodCallExpr> calls = ast.getRoot().findAll(MethodCallExpr.class);
        List<SwitchStmt> switches = ast.getRoot().findAll(SwitchStmt.class);

        for (FieldDeclaration field : fields) {
            System.out.println("Filed Declartion: " + field);
        }
        for (MethodCallExpr call : calls) {
            System.out.println("Method call: " + call);
        }
        for (SwitchStmt switchCall : switches) {
            System.out.println("Switch: " + switchCall.getEntries());
        }

    }
}
