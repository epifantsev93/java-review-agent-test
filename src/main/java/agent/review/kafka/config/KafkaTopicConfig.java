package agent.review.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic reviewResultsTopic() {
        return TopicBuilder
                .name("review.results.v1")
                .partitions(1)
                .replicas(1)
                .build();
    }
}