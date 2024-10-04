package blps.lab.article.dto;

import java.util.List;

import blps.lab.article.entity.Article;
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
public class ArticleRequest {
    private String title;

    private String content;

    private List<String> tags;

    private String category;

    public static Article toEntity(ArticleRequest articleRequest) {
        //todo поменять id овнера на текущего пользователя
        return Article.builder()
                .ownerId(null)
                .title(articleRequest.getTitle())
                .content(articleRequest.getContent())
                .tags(articleRequest.getTags())
                .category(articleRequest.getCategory())
                .moderationStatus(false)
                .isDraft(true)
                .views(0)
                .build();
    }
}
