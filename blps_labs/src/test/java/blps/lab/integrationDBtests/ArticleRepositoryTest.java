package blps.lab.integrationDBtests;

import blps.lab.article.entity.Article;
import blps.lab.article.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    // ���� ��� ���������� ������ � ������ �� ID
    @Test
    public void testSaveAndFindById() {
        Article article = Article.builder()
                .ownerId(1L)
                .category("Technology")
                .isDraft(false)
                .tags(Arrays.asList("java", "spring"))
                .build();

        // ��������� ������
        Article savedArticle = articleRepository.save(article);

        // ��������� ���������� � ����� ������ �� ID
        Optional<Article> foundArticle = articleRepository.findById(savedArticle.getId());
        assertThat(foundArticle).isPresent();
        assertThat(foundArticle.get().getOwnerId()).isEqualTo(1L);
        assertThat(foundArticle.get().getTags()).contains("java", "spring");
    }

    // ���� ��� �������� ��������� ��������� ������
    @Test
    public void testArticleDraftValidation() {
        // ������� �������� ������ � ������������ ������
        Article draftArticle = Article.builder()
                .ownerId(2L)
                .isDraft(true)
                .build();

        // ���������, ��� �������� ����� ������������� ����
        assertThat(draftArticle.hasNullFieldsForDraft()).isTrue();

        // ��������� ����������� ������ ��� ����������
        draftArticle.setCategory("Science");
        draftArticle.setTags(Collections.singletonList("research"));

        // ���������, ��� ������ �������� ����� � ����������
        assertThat(draftArticle.hasNullFieldsForDraft()).isFalse();
    }

    // ���� ��� �������� ������
    @Test
    public void testDeleteArticle() {
        // ������� � ��������� ������
        Article article = Article.builder()
                .ownerId(3L)
                .category("Health")
                .isDraft(false)
                .tags(Arrays.asList("wellness", "nutrition"))
                .build();

        Article savedArticle = articleRepository.save(article);

        // ������� ������
        articleRepository.deleteById(savedArticle.getId());

        // ���������, ��� ������ ���� ������� �������
        Optional<Article> foundArticle = articleRepository.findById(savedArticle.getId());
        assertThat(foundArticle).isNotPresent();
    }
}
