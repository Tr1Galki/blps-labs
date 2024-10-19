package blps.lab.delegates;

import blps.lab.article.service.ArticleService;
import blps.lab.moderation.service.ModerationService;
import blps.lab.security.entity.User;
import blps.lab.security.roles.Role;
import blps.lab.security.services.UserService;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Named
@Service
@AllArgsConstructor
public class SaveReview implements JavaDelegate {

    private final ModerationService moderationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String article = (String) delegateExecution.getVariable("article");
        String review = (String) delegateExecution.getVariable("review");

        moderationService.addReview(Long.parseLong(article), review);
    }
}
