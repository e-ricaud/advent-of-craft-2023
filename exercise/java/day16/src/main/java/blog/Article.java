package blog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Article {
    private final String name;
    private final String content;
    private final ArrayList<Comment> comments;

    public Article(String name, String content) {
        this.name = name;
        this.content = content;
        this.comments = new ArrayList<>();
    }

    public Article(String name, String content, List<Comment> comments) {
        this.name = name;
        this.content = content;
        this.comments = new ArrayList<>(comments);
    }

    private Article addComment(
            String text,
            String author,
            LocalDate creationDate) throws CommentAlreadyExistException {
        var comment = new Comment(text, author, creationDate);

        if (comments.contains(comment)) {
            throw new CommentAlreadyExistException();
        }

        var newComments = new ArrayList<>(comments);
        // Not really immutable...
        newComments.add(comment);

        return new Article(name, content, unmodifiableList(newComments));
    }

    public Article addComment(String text, String author) throws CommentAlreadyExistException {
        return addComment(text, author, LocalDate.now());
    }

    public List<Comment> getComments() {
        return comments;
    }
}

