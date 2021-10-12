package user;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

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
}
