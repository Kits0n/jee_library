package user;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

//odpowiada domyślnemu zasięgowi z JSR 330
//tworzy nową instancję beana przy każdym wstrzyknięciu,
//instancja jest niszczona, kiedy obiekt, do którego została wstrzyknięta, jest niszczony.
@Dependent
public class Repository{
    private DataStore store;
    @Inject
    public Repository(DataStore store) {
        this.store = store;
    }

    public Optional<User> find(Long id) {
        return store.findUser(id);
    }

    public List<User> findAll() {
        return store.findAllUsers();
    }

    public void create(User entity) {
        store.createUser(entity);
    }

    public void updateAvatar(Long id, InputStream is) {
        store.updateAvatar(id, is);
    }
}

