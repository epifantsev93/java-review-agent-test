package agent.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewAgentController {

    private final ChatClient chatClient;
    private final ConversationSession conversationSession;

    @PostMapping("/chat")
    public String generation(@RequestBody ChatRequest request) {

        log.info("Generation rest called, conversationId={}",
                conversationSession.getConversationId());

        return chatClient
                .prompt()
                .user(request.userInput())
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationSession.getConversationId()
                ))
                .call()
                .content();
    }

    @GetMapping("/ai_test")
    public String test() {
        return "Hello World!";
    }
}