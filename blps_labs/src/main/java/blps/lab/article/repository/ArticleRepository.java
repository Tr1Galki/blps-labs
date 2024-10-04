package blps.lab.article.repository;

import blps.lab.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findArticlesByIsDraft(boolean moderStatus);

    List<Article> findArticlesByIsDraftAndModerationStatus(boolean isDraft, boolean moderationStatus);
}

