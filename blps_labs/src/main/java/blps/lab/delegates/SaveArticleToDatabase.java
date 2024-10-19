package blps.lab.delegates;

import blps.lab.moderation.service.ModerationService;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Named
@Service
@AllArgsConstructor
public class SaveArticleToDatabase implements JavaDelegate {

    private final ModerationService moderationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String articleId = (String) delegateExecution.getVariable("article");

        moderationService.publish(Long.parseLong(articleId));
    }
}

