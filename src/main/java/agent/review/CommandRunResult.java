package agent.review;

public record CommandRunResult(
        int exitCode,
        String output
) {
}