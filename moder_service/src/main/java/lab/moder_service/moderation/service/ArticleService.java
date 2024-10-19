package lab.moder_service.moderation.service;

import java.time.Instant;
import java.util.Optional;

import lab.moder_service.moderation.entity.Article;
import lab.moder_service.moderation.repository.ArticleRepository;
import lab.moder_service.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final TransactionService transactionService;

    public Article create(Article article) {
        return articleRepository.save(article);
    }

    public void updateTime(Long id, Instant time) {
        var article = articleRepository.findById(id).orElseThrow();
        article.setSentAt(time);
        article.setReceivedAt(Instant.now());
        articleRepository.save(article);
    }

    public Optional<Article> findArticle(Long id) {
        return articleRepository.findById(id);
    }

}
