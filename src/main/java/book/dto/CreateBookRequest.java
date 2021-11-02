package book.dto;

import book.entity.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateBookRequest {
    private String title;
    private String author;
    private Integer numberOfPages;
    private LocalDate publicationDate;

    public static Function<CreateBookRequest, Book> dtoToEntityMapper() {
        return request -> Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .numberOfPages(request.getNumberOfPages())
                .publicationDate(request.getPublicationDate())
                .build();
    }
}
