package agent.review;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class ProjectFileService {

    private final Path projectRoot =
            Path.of(System.getProperty("user.dir"));

    public List<String> listJavaFiles() throws IOException {
        try (var paths = Files.walk(projectRoot)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(projectRoot::relativize)
                    .map(Path::toString)
                    .toList();
        }
    }

    public List<CodeMatch> searchCode(String query) throws IOException {
        try (var paths = Files.walk(projectRoot)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .flatMap(path -> searchInFile(path, query))
                    .toList();
        }
    }

    private Stream<CodeMatch> searchInFile(Path path, String query) {
        try {
            var lines = Files.readAllLines(path);

            return IntStream.range(0, lines.size())
                    .filter(i -> lines.get(i).contains(query))
                    .mapToObj(i -> new CodeMatch(
                            projectRoot.relativize(path).toString(),
                            i + 1,
                            lines.get(i).trim()
                    ));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String readFile(String relativePath) throws IOException {
        Path resolvedPath = projectRoot
                .resolve(relativePath)
                .normalize();

        if (!resolvedPath.startsWith(projectRoot)) {
            throw new IllegalArgumentException("Path outside project: " + relativePath);
        }

        if (!Files.isRegularFile(resolvedPath)) {
            throw new IllegalArgumentException("File not found: " + relativePath);
        }

        return Files.readString(resolvedPath);
    }
}