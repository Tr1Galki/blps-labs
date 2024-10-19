package blps.lab.article.controller;

import blps.lab.article.dto.ArticleRequest;
import blps.lab.article.dto.ArticleResponse;
import blps.lab.article.entity.Article;
import blps.lab.article.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
@Tag(name = "Article")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    @Operation(
            summary = "Получение всех статей",
            description="Доступен всем пользователям"
    )
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        List<ArticleResponse> articles = new ArrayList<>();
        articleService.getAllArticles().stream()
                .map(ArticleResponse::fromEntity)
                .forEach(articles::add);
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
        Optional<Article> article = articleService.findArticle(articleId);
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
        Optional<Article> articleOptional = articleService.addComment(articleId, comment);
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
        List<ArticleResponse> draftArticles = new ArrayList<>();
        articleService.getAllArticles().stream()
                .map(ArticleResponse::fromEntity)
                .forEach(draftArticles::add);
        return ResponseEntity.ok(draftArticles);
    }

    @GetMapping("/moderatedDraft")
    @Operation(
            summary = "Получение черновиков, на которые ответил модератор",
            description="Доступен авторизованным пользователям"
    )
    public ResponseEntity<List<ArticleResponse>> getModeratedDraftArticles() {
        List<ArticleResponse> moderatedDrafts = new ArrayList<>();
        articleService.getAllArticles().stream()
                .map(ArticleResponse::fromEntity)
                .forEach(moderatedDrafts::add);
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
        Article article = ArticleRequest.toEntity(request);
        Article savedArticle = articleService.create(article);
        return ResponseEntity.ok(ArticleResponse.fromEntity(savedArticle));
    }
}