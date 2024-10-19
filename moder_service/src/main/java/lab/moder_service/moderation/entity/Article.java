package lab.moder_service.moderation.entity;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "articles")
@ToString
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

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "tags")
    private List<String> tags;

    @Column(name = "category")
    private String category;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    private List<Review> moderationReview;

    @Column(name = "sent_at")
    private Instant sentAt;

    @Column(name = "received_at")
    private Instant receivedAt;

    @Column(name = "moderation_status", nullable = false)
    private Boolean moderationStatus;

    @Column(name = "is_draft", nullable = false)
    private Boolean isDraft;

    @Column(name = "views", nullable = false)
    private Integer views;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    private List<Comment> comments;

    /**
     * Проверяет, содержит ли статья все необходимые поля для публикации.
     *
     * @return true, если статья не готова к публикации (имеет незаполненные поля).
     */
    public boolean hasNullFieldsForDraft() {
        return this.title == null
                || this.title.isEmpty()
                || this.content == null
                || this.content.isEmpty()
                || this.category == null
                || this.category.isEmpty()
                || this.views == null
                || this.tags == null
                || this.tags.isEmpty();
    }

    /**
     * Увеличивает счётчик просмотров статьи на один.
     */
    public void addView() {
        ++this.views;
    }
}