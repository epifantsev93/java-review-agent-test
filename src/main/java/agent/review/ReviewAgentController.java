package agent.review;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewAgentController {

    private final ChatClient chatClient;

    @PostMapping("/chat")
    public String generation(@RequestBody ChatRequest request) {
        System.out.println(">>> generation CALLED");
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