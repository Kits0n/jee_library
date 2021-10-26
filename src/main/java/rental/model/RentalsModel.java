package rental.model;

import lombok.*;
import rental.entity.Rental;

import java.time.LocalDate;
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
public class RentalsModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Rental {

        private Long id;
        private LocalDate date;

    }

    @Singular
    private List<Rental> rentals;

    public static Function<Collection<rental.entity.Rental>, RentalsModel> entityToModelMapper() {
        return rentals -> {
            RentalsModel.RentalsModelBuilder model = RentalsModel.builder();
            rentals.stream()
                    .map(rental -> RentalsModel.Rental.builder()
                            .id(rental.getId())
                            .date(rental.getDate())
                            .build())
                    .forEach(model::rental);
            return model.build();
        };
    }
}
