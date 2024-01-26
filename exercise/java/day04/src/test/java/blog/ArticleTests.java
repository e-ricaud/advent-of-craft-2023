package blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleTests {

    // les constantes permettent aussi la non duplication
    private static final String AUTHOR = "Pablo Escobar";
    private static final String COMMENT_TEXT = "Amazing article !!!";
    private Article article;

    // Evitez de la duplication en faisant une instance de Article dans un Before each
    @BeforeEach
    public void setup() {
        article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );
    }

    // On test la taille et la bonne alimentation
    @Test
    void it_should_add_valid_comment() throws CommentAlreadyExistException {
        article.addComment(COMMENT_TEXT, AUTHOR);

        assertThat(article.getComments())
                .hasSize(1)
                .anyMatch(comment -> comment.text().equals(COMMENT_TEXT))
                .anyMatch(comment -> comment.author().equals(AUTHOR))
                .anyMatch(comment -> comment.creationDate().equals(LocalDate.now()));
    }

    @Test
    void it_should_add_valid_comment_when_have_already_comment() throws CommentAlreadyExistException {
        article.addComment(COMMENT_TEXT, AUTHOR);

        String newText = "GREAT !!!";
        String newAuthor = "Johnny";

        article.addComment(newText, newAuthor);

        Comment lastComment = article.getComments().getLast();

        assertThat(article.getComments()).hasSize(2);
        assertThat(lastComment.text()).isEqualTo(newText);
        assertThat(lastComment.author()).isEqualTo(newAuthor);
    }

    @Test
    void it_should_throw_an_exception_when_adding_existing_comment() throws CommentAlreadyExistException {
        assertThatThrownBy(() -> {
            article.addComment(COMMENT_TEXT, AUTHOR);
            article.addComment(COMMENT_TEXT, AUTHOR);
        }).isInstanceOf(CommentAlreadyExistException.class);
    }
}
