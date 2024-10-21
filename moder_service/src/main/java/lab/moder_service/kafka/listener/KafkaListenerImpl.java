package lab.moder_service.kafka.listener;

import lab.moder_service.kafka.config.KafkaTopic;
import lab.moder_service.kafka.sender.KafkaSender;
import lab.moder_service.moderation.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lab.moder_service.shared.SendModerationArticle;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerImpl {
    public static final String WRONG_TOPIC = "wrong-topic";

    private final ArticleService articleService;
    private final KafkaSender kafkaSender;

    @KafkaListener(
//            topics = KafkaTopic.SEND_MODERATION_ARTICLE_TOPIC,
            topics = WRONG_TOPIC,
            groupId = "user-group",
            containerFactory = "userKafkaListenerContainerFactory"
    )
    void listenerWithMessageConverter(SendModerationArticle message) {
        var article = articleService.findArticle(message.getArticleId()).orElseThrow();
//        if (true) {
//            throw new RuntimeException("QWEWQEQWEQWE");
//        }
        articleService.updateTime(message.getArticleId(), message.getTimeout());
        log.warn("Received article from user!!!");
        log.info("Received message from user [{}]", article);
        log.warn("Received article from user!!!");

        kafkaSender.sendCustomMessageTransactional(message, KafkaTopic.CONFIRMATION_TOPIC);
    }

}
