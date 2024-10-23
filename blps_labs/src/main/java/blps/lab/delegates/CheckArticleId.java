package blps.lab.delegates;

import blps.lab.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckArticleId implements JavaDelegate  {
    private final ArticleService articleService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String articleId = (String) delegateExecution.getVariable("article");

        var article = articleService.findArticle(Long.parseLong(articleId))
                .orElseThrow(() -> {
                    delegateExecution.setVariable("exception", "ARTICLE_DID_NOT_FOUND");
                    return new BpmnError("ArticleDidNotFound");
                });

        if (!article.getIsDraft()) {
            delegateExecution.setVariable("exception", "ARTICLE_ALREADY_PUBLISHED");
            throw new  BpmnError("ArticleIsAlreadyPublished");
        }
    }
}
