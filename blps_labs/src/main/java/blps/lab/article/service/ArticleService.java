package blps.lab.article.service;

import java.util.List;
import java.util.Optional;

import blps.lab.article.entity.Article;
import blps.lab.article.entity.Comment;
import blps.lab.article.repository.ArticleRepository;
import blps.lab.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public List<Article> getAllArticles() {
        return articleRepository.findArticlesByIsDraft(false);
    }

    public List<Article> getAllDraftsByUser(Long userId) {
        return articleRepository.findArticlesByIsDraftAndOwnerId(true, userId);
    }

    public List<Article> getAllModeratedDraftsByUser(Long userId) {
        return articleRepository.findArticlesByIsDraftAndModerationStatusAndOwnerId(
                true,
                true,
                userId
        );
    }

    public Optional<Article> findArticle(Long id) {
        return articleRepository.findById(id);
    }

    public Article create(Article article) {
        return articleRepository.save(article);
    }

    public boolean isReadyToModerate(Long id) {
        var articleOpt = articleRepository.findById(id);
        if (articleOpt.isEmpty()) {
            return false;
        }
        var article = articleOpt.get();
        return article.getIsDraft();
    }

    public void sendToModerate(Long id) {
        var article = articleRepository.findById(id).orElseThrow();
        article.setModerationStatus(true);
        //todo добавить логику отправки сообщения
    }

    public Optional<Article> addComment(Long articleId, Long authorId, String commentData) {
        var comment = Comment.builder()
                .articleId(articleId)
                .authorId(authorId)
                .comment(commentData)
                .build();
        commentRepository.save(comment);
        return articleRepository.findById(articleId);
    }
}
