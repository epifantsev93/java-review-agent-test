package agent.review;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Component
public class ConversationSession {
    private final String conversationId = UUID.randomUUID().toString();
}