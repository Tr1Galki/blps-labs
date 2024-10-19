package lab.moder_service.moderation.service;

import java.util.List;

import lab.moder_service.moderation.entity.Article;
import lab.moder_service.moderation.repository.ArticleRepository;
import lab.moder_service.moderation.entity.Review;
import lab.moder_service.moderation.exceptions.NoSuchDraftArticleException;
import lab.moder_service.moderation.repository.ReviewRepository;
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
            throw new NoSuchDraftArticleException();
        }
        article.setIsDraft(false);
        articleRepository.save(article);
    }

    public void addReview(Long articleId, String reviewText) {
        var article = articleRepository.findById(articleId).orElseThrow();
        if (!article.getIsDraft()) {
            throw new NoSuchDraftArticleException();
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
