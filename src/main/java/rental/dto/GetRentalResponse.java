package rental.dto;

import book.dto.GetBookResponse;
import book.entity.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;
import rental.entity.Rental;
import user.entity.User;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetRentalResponse {
    private Long id;
    private LocalDate date;
    private String type;
    private Long bookId;
    private Long userId;

    public static Function<Rental, GetRentalResponse> entityToDtoMapper() {
        return rental -> GetRentalResponse.builder()
                .id(rental.getId())
                .date(rental.getDate())
                .type(rental.getType())
                .bookId(rental.getBook().getId())
                .userId(rental.getUser().getId())
                .build();
    }
}
