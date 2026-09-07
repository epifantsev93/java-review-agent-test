package agent.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
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

    @PostMapping("/chat")
    public String generation(@RequestBody ChatRequest request) {
        log.info("Generation rest called");
        return chatClient
                .prompt()
                .user(request.userInput())
                .call()
                .content();
    }

    @GetMapping("/ai_test")
    public String test() {
        return "Hello World!";
    }
}