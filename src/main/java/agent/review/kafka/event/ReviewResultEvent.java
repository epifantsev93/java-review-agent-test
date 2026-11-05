package agent.review.kafka.event;

import java.util.UUID;

public record ReviewResultEvent(
        UUID reviewId,
        String result
) {}