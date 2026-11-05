package agent.review.execution;

import agent.review.kafka.event.ReviewRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiReviewExecutor implements ReviewExecutor {

    private final ChatClient chatClient;

    @Override
    public String execute(ReviewRequestEvent event) {
        return chatClient
                .prompt()
                .user("""
                        Project: %s
                        Instruction: %s
                        """.formatted(
                        event.project(),
                        event.instruction()
                ))
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        event.reviewId().toString()
                ))
                .call()
                .content();
    }
}