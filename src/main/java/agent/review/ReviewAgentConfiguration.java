package agent.review;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

@Configuration
@RequiredArgsConstructor
public class ReviewAgentConfiguration {
    private final ReviewAgentTools reviewAgentTools;

    @Value("classpath:prompts/review-agent-system.txt")
    private Resource systemPrompt;

    @Bean
    public ChatClient reviewAgentChatClient(
            ChatClient.Builder builder,
            ChatMemory chatMemory) {

        return builder
                .defaultSystem(systemPrompt)
                .defaultTools(reviewAgentTools)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
    }
}