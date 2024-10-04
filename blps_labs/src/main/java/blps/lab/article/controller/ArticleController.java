package blps.lab.article.controller;

import blps.lab.article.dto.ArticleRequest;
import blps.lab.article.dto.ArticleResponse;
import blps.lab.article.service.ArticleService;
import blps.lab.security.entity.User;
import blps.lab.security.services.UserAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
@Tag(name = "Article")
public class ArticleController {
    private final ArticleService articleService;
    private final UserAuthenticationService userAuthenticationService;

    @GetMapping
    @Operation(
            summary = "Получение всех статей",
            description="Доступен всем пользователям"
    )
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        var articles = articleService.getAllArticles().stream()
                .map(ArticleResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{articleId}")
    @Operation(
            summary = "Получение статьи по id",
            description="Доступен всем пользователям"
    )
    public ResponseEntity<ArticleResponse> getArticleById(
            @PathVariable(value = "articleId") Long articleId
    ) {
        var article = articleService.findArticle(articleId);
        return article.map(ArticleResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @PostMapping("/{articleId}/comment")
    @Operation(
            summary = "Написание комментария",
            description="Доступен авторизованным пользователям"
    )
    public ResponseEntity<ArticleResponse> createComment(
            @PathVariable(value = "articleId") Long articleId,
            @RequestBody String comment
    ) {
        User currentUser = userAuthenticationService.getCurrentUser();
        var articleOptional = articleService.addComment(articleId, currentUser.getId(), comment);
        return articleOptional.map(ArticleResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/draft")
    @Operation(
            summary = "Получение всех черновиков",
            description="Доступен авторизованным пользователям"
    )
    public ResponseEntity<List<ArticleResponse>> getDraftArticles() {
        User currentUser = userAuthenticationService.getCurrentUser();
        var draftArticles = articleService.getAllDraftsByUser(currentUser.getId()).stream()
                .map(ArticleResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(draftArticles);
    }

    @GetMapping("/moderatedDraft")
    @Operation(
            summary = "Получение черновиков, на которые ответил модератор",
            description="Доступен авторизованным пользователям"
    )
    public ResponseEntity<List<ArticleResponse>> getModeratedDraftArticles() {
        User currentUser = userAuthenticationService.getCurrentUser();
        var moderatedDrafts = articleService.getAllModeratedDraftsByUser(currentUser.getId()).stream()
                .map(ArticleResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(moderatedDrafts);
    }

    @PostMapping("/{draftArticleId}/sendToModerate")
    @Operation(
            summary = "Отправка статьи на модерацию",
            description="Доступен авторизованным пользователям"
    )
    public ResponseEntity<Void> sendArticleToModerate(
            @PathVariable(value = "draftArticleId") Long draftArticleId
    ) {
        if (!articleService.isReadyToModerate(draftArticleId)) {
            return ResponseEntity.notFound().build();
        }
        articleService.sendToModerate(draftArticleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    @Operation(
            summary = "Создание черновика",
            description="Доступен авторизованным пользователям"
    )
    public ResponseEntity<ArticleResponse> createArticle(
            @RequestBody ArticleRequest request
    ) {
        User currentUser = userAuthenticationService.getCurrentUser();
        var article = ArticleRequest.toEntity(request);
        article.setOwnerId(currentUser.getId());
        var savedArticle = articleService.create(article);
        return ResponseEntity.ok(ArticleResponse.fromEntity(savedArticle));
    }
}