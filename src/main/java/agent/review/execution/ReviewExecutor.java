package agent.review.execution;

import agent.review.kafka.event.ReviewRequestEvent;

public interface ReviewExecutor {

    String execute(ReviewRequestEvent event);
}