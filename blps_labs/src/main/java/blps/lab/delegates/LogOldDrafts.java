package blps.lab.delegates;

import blps.lab.article.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.*;

@Slf4j
@Component
@AllArgsConstructor
public class LogOldDrafts implements JavaDelegate {
    private final ArticleService articleService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("!!Scheduler task starts!!");
        articleService.getAllArticles().stream()
                .filter(article -> article.getIsDraft()
                        && article.getSentAt() != null
                        && Duration.between(article.getSentAt(), Instant.now()).toDays() > 0
                )
                .forEach(article -> log.warn("Article with id {} did not reviewed more then day", article.getId()));
    }
}
