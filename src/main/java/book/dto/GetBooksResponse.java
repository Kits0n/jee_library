package book.dto;

import lombok.*;


import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetBooksResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Book {
        private Long id;
        private String title;

    }

    @Singular
    private List<Book> books;

    public static Function<Collection<book.entity.Book>, GetBooksResponse> entityToDtoMapper() {
        return books -> {
            GetBooksResponse.GetBooksResponseBuilder response = GetBooksResponse.builder();
            books.stream()
                    .map(book -> GetBooksResponse.Book.builder()
                            .id(book.getId())
                            .title(book.getTitle())
                            .build())
                    .forEach(response::book);
            return response.build();
        };
    }
}
