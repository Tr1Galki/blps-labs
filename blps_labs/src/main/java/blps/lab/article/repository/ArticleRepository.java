package blps.lab.article.repository;

import blps.lab.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}

