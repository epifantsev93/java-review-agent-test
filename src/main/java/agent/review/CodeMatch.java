package agent.review;

public record CodeMatch(
        String path,
        int line,
        String text
) {
}