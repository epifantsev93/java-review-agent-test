package agent.review.kafka.producer;

import agent.review.kafka.event.ReviewResultEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewResultProducer {

    private static final String TOPIC = "review.results.v1";

    private final KafkaTemplate<String, ReviewResultEvent> kafkaTemplate;

    public void send(ReviewResultEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.reviewId().toString(),
                event
        );
    }

}