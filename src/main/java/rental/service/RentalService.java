package rental.service;

import book.entity.Book;
import book.repository.BookRepository;
import lombok.NoArgsConstructor;
import rental.entity.Rental;
import rental.repository.RentalRepository;
import user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class RentalService {

    private RentalRepository rentalRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;

    @Inject
    public RentalService(RentalRepository rentalRepository,UserRepository userRepository,BookRepository bookRepository)
    {
        this.rentalRepository = rentalRepository;
        this.userRepository =userRepository;
        this.bookRepository =bookRepository;
    }

    public Optional<Rental> find(Long id) {
        return rentalRepository.find(id);
    }

    public List<Rental> findAll(Long id) {
        return rentalRepository.findAll(id);
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        Rental rental = rentalRepository.find(id).orElseThrow();
        rental.getBook().getRentals().remove(rental);
        rental.getUser().getRentals().remove(rental);
        rentalRepository.delete(id);
    }

    @Transactional
    public void create(Rental rental) {
        rentalRepository.create(rental);
        userRepository.find(rental.getUser().getId()).ifPresent(user -> user.getRentals().add(rental));
        bookRepository.find(rental.getBook().getId()).ifPresent(book -> book.getRentals().add(rental));
    }

    @Transactional
    public void update(Rental rental) {
        Rental original = rentalRepository.find(rental.getId()).orElseThrow();
        rentalRepository.detach(original);
        if (!original.getUser().getId().equals(rental.getUser().getId())) {
            original.getUser().getRentals().removeIf(rentalToRemove -> rentalToRemove.getId().equals(rental.getId()));
            userRepository.find(rental.getUser().getId()).ifPresent(user -> user.getRentals().add(rental));
        }
        if (!original.getBook().getId().equals(rental.getBook().getId())) {
            original.getBook().getRentals().removeIf(rentalToRemove -> rentalToRemove.getId().equals(rental.getId()));
            bookRepository.find(rental.getBook().getId()).ifPresent(book -> book.getRentals().add(rental));
        }
        rentalRepository.update(rental);
    }
}
