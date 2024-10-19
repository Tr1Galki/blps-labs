package blps.lab.delegates;

import blps.lab.article.service.ArticleService;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Named
@Service
@AllArgsConstructor
public class ArticleReadingFromDatabase implements JavaDelegate {
    private final ArticleService articleService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String articleId = (String) delegateExecution.getVariable("article");
        var article = articleService.findArticle(Long.parseLong(articleId)).orElseThrow();

        delegateExecution.setVariable("title", article.getTitle());
        delegateExecution.setVariable("content", article.getContent());
        delegateExecution.setVariable("category", article.getCategory());
    }
}
