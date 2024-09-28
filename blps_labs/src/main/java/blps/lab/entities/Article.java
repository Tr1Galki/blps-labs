package blps.lab.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @ElementCollection
    @Column(name = "tags")
    private List<String> tags;

    @Column(name = "category")
    private String category;


    @Column(name = "moder_comment")
    private String moderComment;  // Комментарий модератора

    @Column(name = "moder_status", nullable = false)
    private Boolean moderStatus;  // Статус модерации (true - отправлен на модерацию)

    @Column(name = "is_draft", nullable = false)
    private Boolean isDraft;  // Флаг черновика

    @Column(name = "views", nullable = false)
    private Integer views;  // Количество просмотров

    /**
     * Проверяет, содержит ли статья все необходимые поля для публикации.
     *
     * @return true, если статья не готова к публикации (имеет незаполненные поля).
     */
    public boolean hasNullFieldsForDraft() {
        return this.title == null || this.title.isEmpty() ||
                this.content == null || this.content.isEmpty() ||
                this.category == null || this.category.isEmpty() ||
                this.views == null ||
                this.tags == null || this.tags.isEmpty();
    }

    /**
     * Увеличивает счётчик просмотров статьи на один.
     */
    public void addView() {
        ++this.views;
    }
}