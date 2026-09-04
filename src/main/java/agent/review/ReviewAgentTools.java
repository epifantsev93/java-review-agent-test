package agent.review;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewAgentTools {

    private final ProjectFileService projectFileService;

    @Tool(description = "Returns all Java source files in the current project")
    public List<String> listJavaFiles() throws IOException {
        System.out.println(">>> listJavaFiles TOOL CALLED");
        return projectFileService.listJavaFiles();
    }

    @Tool(description = "Searches Java source files for text and returns matching file paths, line numbers and lines")
    public List<CodeMatch> searchCode(String query) throws IOException {
        System.out.println(">>> searchCode TOOL CALLED: " + query);
        return projectFileService.searchCode(query);
    }
}