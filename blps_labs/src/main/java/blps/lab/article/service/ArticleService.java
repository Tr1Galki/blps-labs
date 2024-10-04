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

    public List<Article> getAllDrafts() {
        return articleRepository.findArticlesByIsDraft(true);
    }

    public List<Article> getAllModeratedDrafts() {
        return articleRepository.findArticlesByIsDraftAndModerationStatus(true, true);
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

    public Optional<Article> addComment(Long articleId, String commentData) {
        var comment = Comment.builder()
                .articleId(articleId)
                .comment(commentData)
                .build();
        commentRepository.save(comment);
        return articleRepository.findById(articleId);
    }
}
