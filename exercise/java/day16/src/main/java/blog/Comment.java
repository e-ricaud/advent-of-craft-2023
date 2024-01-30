package blog;

import java.time.LocalDate;

//Record -> Immuable
public record Comment(String text, String author, LocalDate creationDate) {
}
