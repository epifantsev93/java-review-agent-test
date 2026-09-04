package org.example.client;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class ReviewAgentTools {
    private final Path projectRoot =
            Path.of(System.getProperty("user.dir"));

    @Tool(description = "Returns all Java source files in the current project")
    public List<String> listJavaFiles() throws IOException {
        System.out.println(">>> listJavaFiles TOOL CALLED");
        try (var paths = Files.walk(projectRoot)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(projectRoot::relativize)
                    .map(Path::toString)
                    .toList();
        }
    }
}
