package blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static blog.ArticleBuilder.anArticle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleTests {
    private ArticleBuilder articleBuilder;

    @BeforeEach
    void setup() {
        articleBuilder = anArticle();
    }

    @Test
    void should_add_comment_in_an_article() throws CommentAlreadyExistException {
        final var article = articleBuilder.build();
        article.addComment(ArticleBuilder.COMMENT_TEXT, ArticleBuilder.AUTHOR);

        assertThat(article.getComments()).hasSize(1);
        assertComment(article.getComments().get(0), ArticleBuilder.COMMENT_TEXT, ArticleBuilder.AUTHOR);
    }

    @Test
    void should_add_comment_in_an_article_containing_already_a_comment() throws CommentAlreadyExistException {
        final var newComment = "Finibus Bonorum et Malorum";
        final var newAuthor = "Al Capone";

        var article = articleBuilder
                .commented()
                .build();

        article.addComment(newComment, newAuthor);

        assertThat(article.getComments()).hasSize(2);
        assertComment(article.getComments().getLast(), newComment, newAuthor);
    }

    @Nested
    class Fail {
        @Test
        void when__adding_an_existing_comment() throws CommentAlreadyExistException {
            final var article = articleBuilder.build();
            article.addComment(ArticleBuilder.COMMENT_TEXT, ArticleBuilder.AUTHOR);

            assertThatThrownBy(() -> {
                article.addComment(ArticleBuilder.COMMENT_TEXT, ArticleBuilder.AUTHOR);
            }).isInstanceOf(CommentAlreadyExistException.class);
        }
    }

    private static void assertComment(Comment comment, String commentText, String author) {
        assertThat(comment.text()).isEqualTo(commentText);
        assertThat(comment.author()).isEqualTo(author);
    }
}
