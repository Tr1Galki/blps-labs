package lab.user_service.kafka.listener;

import lab.user_service.kafka.config.KafkaTopic;
import lab.user_service.transaction.service.ConfirmationService;
import lombok.RequiredArgsConstructor;
import shared.entity.SendModerationArticle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerImpl {
    private final ConfirmationService confirmationService;

    @KafkaListener(
            topics = KafkaTopic.CONFIRMATION_TOPIC,
            groupId = "moder-group",
            containerFactory = "userKafkaListenerContainerFactory"
    )
    public void handleConfirmation(SendModerationArticle message) {
        log.info("Received confirmation for transaction {}", message.getTransactionId());
        // Сохраняем транзакцию как подтвержденную
        confirmationService.confirmTransaction(message.getTransactionId());
    }
}
