package grader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class FileUtil {
    public static String readStudentCode(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }
}
