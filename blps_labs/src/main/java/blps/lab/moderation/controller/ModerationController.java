package blps.lab.moderation.controller;

import java.util.List;

import blps.lab.article.dto.ArticleResponse;
import blps.lab.auth.service.UserAuthenticationService;
import blps.lab.moderation.service.ModerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moderate")
@RequiredArgsConstructor
@Tag(name = "Moderating")
public class ModerationController {
    private final ModerationService moderationService;
    private final UserAuthenticationService userAuthenticationService;

    @PostMapping("/{draftArticleId}/publish")
    @Operation(
            summary = "Опубликование черновика",
            description="Доступен только модераторам"
    )
    public ResponseEntity<Void> publishArticle(
            @PathVariable(value = "draftArticleId") Long draftArticleId
    ) {
        userAuthenticationService.getCurrentUser();
        moderationService.publish(draftArticleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/draftArticle")
    @Operation(
            summary = "Получение всех черновиков для модерации",
            description="Доступен только модераторам"
    )
    public ResponseEntity<List<ArticleResponse>> getAllDraftArticlesToModerate() {
        userAuthenticationService.getCurrentUser();
        var articles = moderationService.getAllArticlesToModerate().stream()
                .map(ArticleResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(articles);
    }

    @PostMapping("/{draftArticleId}/review")
    @Operation(
            summary = "Написание отзыва на статью",
            description="Доступен только модераторам"
    )
    public ResponseEntity<Void> reviewArticle(
            @PathVariable(value = "draftArticleId") Long draftArticleId,
            @RequestBody String review
    ) {
        userAuthenticationService.getCurrentUser();
        moderationService.addReview(draftArticleId, review);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
