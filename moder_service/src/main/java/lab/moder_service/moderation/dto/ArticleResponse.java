package lab.moder_service.moderation.dto;

import java.util.List;

import lab.moder_service.moderation.entity.Article;
import lab.moder_service.moderation.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    private Long id;

    private Long ownerId;

    private String title;

    private String content;

    private List<String> tags;

    private String category;

    private Integer views;

    private List<Comment> comments;

    public static ArticleResponse fromEntity(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .ownerId(article.getOwnerId())
                .title(article.getTitle())
                .content(article.getContent())
                .tags(article.getTags())
                .category(article.getCategory())
                .views(article.getViews())
                .comments(article.getComments())
                .build();
    }
}
