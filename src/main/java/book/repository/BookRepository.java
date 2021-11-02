package book.repository;

import book.entity.Book;
import datastore.DataStore;
import user.entity.User;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class BookRepository {


    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    public Optional<Book> find(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    public void delete(Long id) {
        em.remove(em.find(Book.class, id));
    }

    public void create(Book book) {
        em.persist(book);
    }

    public void update(Book book) {
        em.merge(book);
    }
}
