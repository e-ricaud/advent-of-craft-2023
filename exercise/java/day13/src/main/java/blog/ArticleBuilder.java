package blog;

import java.util.HashMap;
import java.util.Map;

public class ArticleBuilder {

    // Object Mother
    public static final String AUTHOR = "Pablo Escobar";
    public static final String COMMENT_TEXT = "Amazing article !!!";
    private final HashMap<String, String> comments;

    public ArticleBuilder() {
        comments = new HashMap<>();
    }
    
    public static ArticleBuilder anArticle() {
        return new ArticleBuilder();
    }

    public ArticleBuilder commented() {
        this.comments.put(COMMENT_TEXT, AUTHOR);
        return this;
    }

    // Only one place from our tests that contains this construction logic
    // If Article constructor changes, we just have to change this Builder and not the 1000 related tests...
    public Article build() throws CommentAlreadyExistException {
        var article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );
        for (Map.Entry<String, String> comment : comments.entrySet()) {
            article.addComment(comment.getKey(), comment.getValue());
        }
        return article;
    }
}