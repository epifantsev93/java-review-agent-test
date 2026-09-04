package agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JavaReviewAgentApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JavaReviewAgentApplication.class, args);
//        AIController controller = context.getBean(AIController.class);
//        System.out.println(controller.generation("What is Java HashMap?"));
    }
}