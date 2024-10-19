package lab.user_service.article.repository;

import java.time.Instant;
import java.util.List;

import lab.user_service.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findArticlesByIsDraft(boolean isDraft);

    List<Article> findArticlesByIsDraftAndOwnerId(Boolean isDraft, Long ownerId);

    List<Article> findArticlesByIsDraftAndModerationStatus(boolean isDraft, boolean moderationStatus);

    List<Article> findArticlesByIsDraftAndModerationStatusAndOwnerId(
            Boolean isDraft,
            Boolean moderationStatus,
            Long ownerId
    );

    @Modifying
    @Query(
            """
            update Article
            set sentAt = :sentAt
            WHERE id = :articleId
            """
    )
    void updateTime(long articleId, Instant sentAt);
}

