package shared.entity;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendModerationArticle {
    Long articleId;
    String transactionId;
    Instant timeout;
}
