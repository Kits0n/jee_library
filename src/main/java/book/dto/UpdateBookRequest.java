package book.dto;

import book.entity.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.function.BiFunction;
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateBookRequest {

    private String title;
    private String author;
    private Integer numberOfPages;
    private LocalDate publicationDate;

    public static BiFunction<Book, UpdateBookRequest, Book> dtoToEntityUpdater() {
        return (book, request) -> {
            book.setTitle(request.getTitle());
            book.setAuthor(request.getAuthor());
            book.setNumberOfPages(request.getNumberOfPages());
            return book;
        };
    }
}