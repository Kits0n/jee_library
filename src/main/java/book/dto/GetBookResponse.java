package book.dto;

import lombok.*;
import book.entity.Book;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetBookResponse {

    private Long id;
    private String title;
    private String author;
    private Integer numberOfPages;
    private LocalDate publicationDate;

    public static Function<Book, book.dto.GetBookResponse> entityToDtoMapper() {
        return book -> GetBookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publicationDate(book.getPublicationDate())
                .numberOfPages(book.getNumberOfPages())
                .build();
    }
}

