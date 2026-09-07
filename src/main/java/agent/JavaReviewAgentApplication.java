package agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaReviewAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaReviewAgentApplication.class, args);
//        ApplicationContext context = SpringApplication.run(JavaReviewAgentApplication.class, args);
//        AIController controller = context.getBean(AIController.class);
//        System.out.println(controller.generation("What is Java HashMap?"));
    }
}