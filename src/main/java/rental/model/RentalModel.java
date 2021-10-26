package rental.model;

import book.entity.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;
import rental.entity.Rental;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RentalModel {
    private Long id;
    private LocalDate date;
    private String type;
    private Book book;

    public static Function<Rental, RentalModel> entityToModelMapper() {
        return rental -> RentalModel.builder()
                .date(rental.getDate())
                .type(rental.getType())
                .book(rental.getBook())
                .build();
    }
}