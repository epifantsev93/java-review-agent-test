package agent.review.kafka.event;

import java.util.UUID;

public record ReviewRequestEvent(
        UUID reviewId,
        String project,
        String instruction
) {
}