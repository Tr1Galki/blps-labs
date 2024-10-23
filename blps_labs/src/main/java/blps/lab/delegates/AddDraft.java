package blps.lab.delegates;

import blps.lab.article.entity.Article;
import blps.lab.article.service.ArticleService;
import blps.lab.security.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddDraft implements JavaDelegate {
    private final ArticleService articleService;
    private final UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String title = (String) delegateExecution.getVariable("title");
        String content = (String) delegateExecution.getVariable("content");
        String category = (String) delegateExecution.getVariable("category");
        String ownerName = (String) delegateExecution.getVariable("owner");

        Long ownerId;

        try {
            var owner = userService.getByUsername(ownerName);
            ownerId = owner.getId();
        } catch (UsernameNotFoundException e) {
            delegateExecution.setVariable("exception", "USER_DOES_NOT_EXIST");
            throw new BpmnError("UserDoesNotExist");
        }

        Article article = Article.builder()
                .title(title)
                .content(content)
                .category(category)
                .isDraft(true)
                .moderationStatus(false)
                .sentAt(Instant.now())
                .ownerId(ownerId)
                .views(0)
                .build();

        articleService.create(article);
    }
}
