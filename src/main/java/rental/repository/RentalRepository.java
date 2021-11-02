package rental.repository;

import book.entity.Book;
import datastore.DataStore;
import rental.entity.Rental;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class RentalRepository {

    private DataStore store;

    @Inject
    public RentalRepository(DataStore store) {
        this.store = store;
    }


    public Optional<Rental> find(Long id) {
        return store.findRental(id);
    }

    public List<Rental> findAll(Long id) {
        return store.findAllRentals(id);
    }

    public List<Rental> findAll() {
        return store.findAllRentals();
    }

    public void delete(Long id) {
        store.deleteRental(id);
    }

    public void create(Rental rental, Long id) {
        store.createRental(rental, id);
    }

    public void update(Rental rental, Long id) {
        store.updateRental(rental, id);
    }
}
