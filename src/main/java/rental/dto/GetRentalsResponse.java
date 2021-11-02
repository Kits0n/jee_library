package rental.dto;

import book.dto.GetBooksResponse;
import book.entity.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;
import user.entity.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetRentalsResponse {
    @Getter
    @Setter
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Rental {
        private Long id;
        private LocalDate date;
    }
    @Singular
    private List<GetRentalsResponse.Rental> rentals;

    public static Function<Collection<rental.entity.Rental>, GetRentalsResponse> entityToDtoMapper() {
        return rentals -> {
            GetRentalsResponse.GetRentalsResponseBuilder response = GetRentalsResponse.builder();
            rentals.stream()
                    .map(rental -> GetRentalsResponse.Rental.builder()
                            .id(rental.getId())
                            .date(rental.getDate())
                            .build())
                    .forEach(response::rental);
            return response.build();
        };
    }
}
