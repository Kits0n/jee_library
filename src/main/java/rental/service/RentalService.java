package rental.service;

import book.entity.Book;
import lombok.NoArgsConstructor;
import rental.entity.Rental;
import rental.repository.RentalRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class RentalService {

    private RentalRepository rentalRepository;

    @Inject
    public RentalService(RentalRepository rentalRepository)
    {
        this.rentalRepository = rentalRepository;
    }

    public Optional<Rental> find(Long id) {
        return rentalRepository.find(id);
    }

    public List<Rental> findAll(Long id) {
        return rentalRepository.findAll(id);
    }

    public void delete(Long id) {
        rentalRepository.delete(id);
    }

    public void create(Rental rental) {
        rentalRepository.create(rental);
    }
}
