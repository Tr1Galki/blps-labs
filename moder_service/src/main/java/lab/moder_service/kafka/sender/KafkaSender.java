package lab.moder_service.kafka.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import lab.moder_service.shared.SendModerationArticle;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaSender {

    private final KafkaTemplate<String, SendModerationArticle> userKafkaTemplate;

    public void sendCustomMessage(SendModerationArticle message, String topicName) {
        log.info("Sending Json Serializer : {}", message);
        log.info("--------------------------------");

        userKafkaTemplate.send(topicName, message);
    }

    public void sendCustomMessageTransactional(SendModerationArticle message, String topicName) {
        log.info("Sending Json Serializer : {}", message);
        log.info("--------------------------------");

        userKafkaTemplate.executeInTransaction(operations -> {
            operations.send(topicName, message);
            return true;
        });
    }
}
