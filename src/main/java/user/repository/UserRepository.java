package user.repository;
import datastore.DataStore;
import user.entity.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

// Dependent - każde wstrzyknięcie tworzy nową instancję
@Dependent
public class UserRepository {
    private DataStore store;
    @Inject
    public UserRepository(DataStore store) {
        this.store = store;
    }

    public Optional<User> find(Long id) {
        return store.findUser(id);
    }

    public List<User> findAll() {
        return store.findAllUsers();
    }

    public void create(User user) {
        store.createUser(user);
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

