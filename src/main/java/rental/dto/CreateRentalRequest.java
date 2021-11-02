package rental.dto;

import book.dto.CreateBookRequest;
import book.entity.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;
import rental.entity.Rental;
import user.entity.User;

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
public class CreateRentalRequest {
    private LocalDate date;
    private String type;
    private Long userId;

    public static Function<CreateRentalRequest, Rental> dtoToEntityMapper(Function<Long, User> userFunction) {
        return request -> Rental.builder()
                .date(request.getDate())
                .type(request.getType())
                .user(userFunction.apply(request.getUserId()))
                .build();
    }
}
