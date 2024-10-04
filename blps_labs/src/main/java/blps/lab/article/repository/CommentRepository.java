package blps.lab.article.repository;

import blps.lab.article.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
