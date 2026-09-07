package agent.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewAgentTools {

    private final ProjectFileService projectFileService;

    @Tool(description = "Returns all Java source files in the current project")
    public List<String> listJavaFiles() throws IOException {
        log.info("Tool called: listJavaFiles");
        return projectFileService.listJavaFiles();
    }

    @Tool(description = """
            Searches Java source files for an exact text fragment.
            The query is treated as plain text, not as a regular expression.
            Returns matching file paths, line numbers and lines.
            """)
    public List<CodeMatch> searchCode(String query) throws IOException {
        log.info("Tool called: searchCode, query={}", query);
        return projectFileService.searchCode(query);
    }

    @Tool(description = "Reads the contents of a file from the current project")
    public String readFile(String path) throws IOException {
        log.info("Tool called: readFile, path={}", path);
        return projectFileService.readFile(path);
    }
}