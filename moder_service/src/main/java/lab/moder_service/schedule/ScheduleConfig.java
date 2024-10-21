package lab.moder_service.schedule;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import lab.moder_service.moderation.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleConfig {
    private final ArticleService articleService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void warnTimoutDrafts() {
        log.info("Scheduler task starts");
        articleService.getAllArticles().stream()
                .filter(article -> article.getIsDraft()
                        && article.getSentAt() != null
                        && article.getSentAt().isBefore(Instant.now().minus(1, ChronoUnit.DAYS))
                )
                .forEach(article -> log.warn("Article with id {} did not reviewed more then day", article.getId()));
    }
}
