package book.model;

import book.entity.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BookModel {
    private Long id;
    private String title;
    private String author;
    private Integer numberOfPages;
    private LocalDate publicationDate;

    public static Function<Book, BookModel> entityToModelMapper() {
        return book -> BookModel.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .numberOfPages(book.getNumberOfPages())
                .publicationDate(book.getPublicationDate())
                .build();
    }
}
