package rental.repository;

import book.entity.Book;
import datastore.DataStore;
import rental.entity.Rental;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class RentalRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    public Optional<Rental> find(Long id) {
        return Optional.ofNullable(em.find(Rental.class, id));
    }

    public List<Rental> findAll(Long id) {
        return em.createQuery("select r from Rental r where r.book.id = :id", Rental.class).getResultList();
    }

    public List<Rental> findAll() {
        return em.createQuery("select r from Rental r", Rental.class).getResultList();
    }

    public void delete(Long id) {
        em.remove(em.find(Rental.class, id));
    }

    public void create(Rental rental) {
        em.persist(rental);
    }

    public void update(Rental rental) {
        em.merge(rental);
    }
}
