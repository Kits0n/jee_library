package book.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer numberOfPages;
    private LocalDate publicationDate;
}
