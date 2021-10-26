package book.model;

import book.entity.Book;
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
public class BooksModel {

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

    public static Function<Collection<book.entity.Book>, BooksModel> entityToModelMapper() {
        return books -> {
            BooksModel.BooksModelBuilder model = BooksModel.builder();
            books.stream()
                    .map(book -> BooksModel.Book.builder()
                            .id(book.getId())
                            .title(book.getTitle())
                            .build())
                    .forEach(model::book);
            return model.build();
        };
    }
}
