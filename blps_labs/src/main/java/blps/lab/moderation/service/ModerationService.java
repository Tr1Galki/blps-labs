package blps.lab.moderation.service;

import java.util.List;

import blps.lab.article.entity.Article;
import blps.lab.moderation.entity.Review;
import blps.lab.article.repository.ArticleRepository;
import blps.lab.moderation.exceptions.NoSuchDraftArticleException;
import blps.lab.moderation.repository.ReviewRepository;
import blps.lab.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModerationService {
    private final ArticleRepository articleRepository;
    private final ReviewRepository reviewRepository;
    private final TransactionService transactionService;

    public List<Article> getAllArticlesToModerate() {
        return articleRepository.findArticlesByIsDraftAndModerationStatus(true, false);
    }

    public void publish(Long articleId) {
        Article article = articleRepository.findById(articleId).orElse(null);
        if (!article.getIsDraft()) {
            throw new NoSuchDraftArticleException();
        }
        article.setIsDraft(false);
        articleRepository.save(article);
    }

    public void addReview(Long articleId, String reviewText) {
        transactionService.executeTransactional(
                () -> {
                    Article article = articleRepository.findById(articleId).orElse(null);
                    if (!article.getIsDraft()) {
                        throw new NoSuchDraftArticleException();
                    }
                    Review review = Review.builder()
                            .articleId(articleId)
                            .data(reviewText)
                            .build();
                    reviewRepository.save(review);
                    article.setModerationStatus(true);
                    if (true) {
                        throw new RuntimeException();
                    }
                    articleRepository.save(article);
                    return article;
                }
        );
    }
}
