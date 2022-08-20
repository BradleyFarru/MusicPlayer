import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileManager {
    public String saveFile(String filePath, String content) {
       try {
           Path path = Paths.get(filePath);
           BufferedWriter fileWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
           fileWriter.write(content);
       } catch (IOException e) {
            return e.toString();
       }
       return "File saved successfully!";
    }
}
