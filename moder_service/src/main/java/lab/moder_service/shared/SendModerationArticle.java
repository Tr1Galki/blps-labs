package lab.moder_service.shared;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SendModerationArticle {
    private Long articleId;
    private String transactionId;
    private Instant timeout;

    public Long getArticleId() {
        return articleId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Instant getTimeout() {
        return timeout;
    }

    public SendModerationArticle setArticleId(Long articleId) {
        this.articleId = articleId;
        return this;
    }

    public SendModerationArticle setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public SendModerationArticle setTimeout(Instant timeout) {
        this.timeout = timeout;
        return this;
    }
}
