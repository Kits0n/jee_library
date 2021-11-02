package rental.dto;

import book.dto.UpdateBookRequest;
import book.entity.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;
import rental.entity.Rental;
import user.entity.User;

import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateRentalRequest {
    private LocalDate date;
    private String type;
    private Long userId;

    public static BiFunction<Rental, UpdateRentalRequest, Rental> dtoToEntityUpdater(Function<Long, User> userFunction) {
        return (rental, request) -> {
            rental.setDate(request.getDate());
            rental.setType(request.getType());
            rental.setUser(userFunction.apply(request.getUserId()));
            return rental;
        };
    }
}
