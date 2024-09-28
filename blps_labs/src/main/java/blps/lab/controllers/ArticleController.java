package blps.lab.controllers;

import blps.lab.entities.Article;
import blps.lab.entities.Review;
import blps.lab.entities.User;
import blps.lab.repositories.ArticleRepository;
import blps.lab.repositories.ReviewRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final ReviewRepository reviewRepository;
    @GetMapping("show/draft")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<?> showDraft(@RequestParam(value = "id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }

        // Проверяем, является ли текущий пользователь владельцем статьи
        if (! (Objects.equals(article.getOwnerId(), currentUser.getId()))) {
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        }

        // Если проверки пройдены, возвращаем данные статьи
        return ResponseEntity.ok(article);
    }
    @GetMapping("show")
    public ResponseEntity<?> show(@RequestParam(value = "id") Long id) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        if (articleOptional.isPresent()) {
            boolean isDraft = articleOptional.get().hasNullFieldsForDraft();
            if (isDraft){
                return new ResponseEntity<>("you can't", HttpStatus.CREATED);
            } else {
                return ResponseEntity.ok(articleOptional.get());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("reviews")
    public ResponseEntity<Optional<List<Review>>> reviews(@RequestParam(value = "id") Long id) {
        Optional<List<Review>> reviews = reviewRepository.findByEntityId(id);
        if (reviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviews);
    }
    @PostMapping("add/draft")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<Article> addDraft(@RequestBody Article article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        article.setOwnerId(currentUser.getId());
        article.setIsDraft(true);
        Article savedDraft = articleRepository.save(article);
        return new ResponseEntity<>(savedDraft, HttpStatus.CREATED);
    }
    @PostMapping("add")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<Article> add(@RequestBody Article article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        article.setOwnerId(currentUser.getId());
        boolean isDraft = article.hasNullFieldsForDraft();
        article.setIsDraft(isDraft);
        Article savedArticle = articleRepository.save(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }
}