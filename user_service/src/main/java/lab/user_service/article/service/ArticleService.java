package lab.user_service.article.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import lab.user_service.article.entity.Article;
import lab.user_service.article.entity.Comment;
import lab.user_service.article.repository.ArticleRepository;
import lab.user_service.article.repository.CommentRepository;
import lab.user_service.kafka.sender.KafkaSender;
import lab.user_service.transaction.service.ConfirmationService;
import lab.user_service.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shared.entity.SendModerationArticle;

import static java.lang.Thread.sleep;
import static lab.user_service.kafka.config.KafkaTopic.SEND_MODERATION_ARTICLE_TOPIC;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final TransactionService transactionService;
    private final KafkaSender kafkaSender;
    private final ConfirmationService confirmationService;

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

    public void updateTime(long articleId, Instant sentAt) {
        articleRepository.updateTime(articleId, sentAt);
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
        long timeout = 3;
        String transactionId = UUID.randomUUID().toString();
        transactionService.executeTransactional(
                transactionId,
                (int) timeout,
                () -> {
                    var article = articleRepository.findById(id).orElseThrow();

                    articleRepository.updateTime(id, Instant.now());

                    var message = new SendModerationArticle(article.getId(), transactionId, Instant.now());

                    kafkaSender.sendCustomMessage(message, SEND_MODERATION_ARTICLE_TOPIC);

                    waitForConfirmation(transactionId, timeout);
                    return article;
                }
        );
//        var res = transactionService.executeTransactional(
//                "trySendToModerate",
//                5,
//                () -> {
//                    var article = articleRepository.findById(id).orElseThrow();
//
//                    article.setSentAt(Instant.now());
//                    articleRepository.save(article);
//
//                    try {
//                        double random = Math.random() * 4000;
//                        sleep((long) random);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                    article.setReceivedAt(Instant.now());
//                    article.setModerationStatus(true);
//                    articleRepository.save(article);
//                    return article;
//                }
//        );
    }

    private void waitForConfirmation(String transactionId, long delay) {
        // Например, ожидание на неблокирующем методе
        // или использование механизма обратного вызова
        try {
            CompletableFuture<Boolean> future = confirmationService.waitForConfirmation(
                    transactionId,
                    delay,
                    TimeUnit.SECONDS
            );
            future.thenAccept(result -> {
                if (!result) {
                    throw new RuntimeException("Confirmation not received in time");
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Operation interrupted", e);
        }
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
