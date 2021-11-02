package user.repository;
import datastore.DataStore;
import rental.entity.Rental;
import user.entity.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

// Dependent - każde wstrzyknięcie tworzy nową instancję
@Dependent
public class UserRepository {
    private DataStore store;
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Inject
    public UserRepository(DataStore store) {
        this.store = store;
    }

    public Optional<User> find(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    public List<User> findAll() {
        return em.createQuery("select u from user u", User.class).getResultList();
    }

    public void create(User user) {
        em.persist(user);
    }

    public byte[] findAvatar(Long id) throws IOException {
        return store.findAvatar(id);
    }

    public void updateAvatar(Long id, InputStream is) throws IOException {
        store.updateAvatar(id, is);
    }

    public void createAvatar(Long id, InputStream is) throws IOException {
        store.createAvatar(id, is);
    }

    public void deleteAvatar(Long id) throws IOException {
        store.deleteAvatar(id);
    }
}

