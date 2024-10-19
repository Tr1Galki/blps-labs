package lab.moder_service.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {
    public static final String SEND_MODERATION_ARTICLE_TOPIC = "send-to-moderation-topic";
    public static final String CONFIRMATION_TOPIC = "confirmation-topic";

    @Bean
    public NewTopic sendModerationArticleTopic() {
        return TopicBuilder.name(SEND_MODERATION_ARTICLE_TOPIC).build();
    }

    @Bean
    public NewTopic sendConfirmationTopic() {
        return TopicBuilder.name(CONFIRMATION_TOPIC).build();
    }
}
