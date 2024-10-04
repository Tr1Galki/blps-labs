package blps.lab.moderation.service;

import java.util.List;

import blps.lab.article.entity.Article;
import blps.lab.moderation.entity.Review;
import blps.lab.article.repository.ArticleRepository;
import blps.lab.moderation.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModerationService {
    private final ArticleRepository articleRepository;
    private final ReviewRepository reviewRepository;

    public List<Article> getAllArticlesToModerate() {
        return articleRepository.findArticlesByIsDraftAndModerationStatus(true, false);
    }

    public void publish(Long articleId) {
        var article = articleRepository.findById(articleId).orElseThrow();
        if (!article.getIsDraft()) {
            throw new IllegalArgumentException("Article is not draft");
        }
        article.setIsDraft(false);
        articleRepository.save(article);
    }

    public void addReview(Long articleId, String reviewText) {
        var article = articleRepository.findById(articleId).orElseThrow();
        if (!article.getIsDraft()) {
            throw new IllegalArgumentException("Article is not draft");
        }
        var review = Review.builder()
                .articleId(articleId)
                .data(reviewText)
                .build();
        reviewRepository.save(review);
        article.setModerationStatus(true);
        articleRepository.save(article);
    }
}
