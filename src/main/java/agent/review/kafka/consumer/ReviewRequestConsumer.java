package agent.review.kafka.consumer;

import agent.review.execution.ReviewExecutor;
import agent.review.kafka.event.ReviewRequestEvent;
import agent.review.kafka.event.ReviewResultEvent;
import agent.review.kafka.producer.ReviewResultProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewRequestConsumer {

    private final ReviewExecutor reviewExecutor;
    private final ReviewResultProducer reviewResultProducer;

    @KafkaListener(topics = "review.requests.v1")
    public void consume(ConsumerRecord<String, ReviewRequestEvent> record) {

        ReviewRequestEvent event = record.value();

        log.info(
                "Review request received: reviewId={}, partition={}, offset={}",
                event.reviewId(),
                record.partition(),
                record.offset()
        );

        String result = reviewExecutor.execute(event);

        reviewResultProducer.send(
                new ReviewResultEvent(
                        event.reviewId(),
                        result
                )
        );

        log.info(
                "Review result sent: reviewId={}",
                event.reviewId()
        );
    }
    
    }
