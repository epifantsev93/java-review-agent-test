package agent.review;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    public CommandRunResult runPmd() {
        return runMavenCommand("pmd:check", "PMD");
    }

    public CommandRunResult runTests() {
        return runMavenCommand("test", "tests");
    }

    private CommandRunResult runMavenCommand(String argument, String commandName) {
        try {
            Process process = createMavenProcess(argument)
                    .redirectErrorStream(true)
                    .start();

            String output;
            try (var reader = process.inputReader()) {
                output = reader.lines()
                        .collect(Collectors.joining(System.lineSeparator()));
            }

            int exitCode = process.waitFor();

            return new CommandRunResult(exitCode, output);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    "Interrupted while running " + commandName,
                    e
            );
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to run " + commandName,
                    e
            );
        }
    }

    private ProcessBuilder createMavenProcess(String... arguments) {
        List<String> command = new ArrayList<>();

        if (isWindows()) {
            command.add("cmd");
            command.add("/c");
            command.add("mvnw.cmd");
        } else {
            command.add("./mvnw");
        }

        command.addAll(List.of(arguments));

        return new ProcessBuilder(command)
                .directory(projectRoot.toFile());
    }

    private boolean isWindows() {
        return System.getProperty("os.name")
                .toLowerCase()
                .contains("win");
    }

    public void writeTestFile(String relativePath, String content) {
        Path testRoot = projectRoot
                .resolve("src/test/java")
                .normalize();

        Path target = testRoot
                .resolve(relativePath)
                .normalize();

        if (!target.startsWith(testRoot)) {
            throw new IllegalArgumentException("Path must be inside src/test/java");
        }

        if (!target.toString().endsWith(".java")) {
            throw new IllegalArgumentException("Only Java test files are allowed");
        }

        try {
            Files.createDirectories(target.getParent());
            Files.writeString(target, content);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write test file", e);
        }
    }
}