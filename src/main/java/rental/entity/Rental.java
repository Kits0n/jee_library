package rental.entity;

import book.entity.Book;
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
public class Rental {
    private Long id;
    private LocalDate date;
    private String type;
    private Book book;
}
