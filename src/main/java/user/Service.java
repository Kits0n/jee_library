package user;
import lombok.NoArgsConstructor;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

// zasięg - CDI zasięg aplikacji, aktywny od momentu wdrożenia do
//momentu usunięcia z serwera
@ApplicationScoped
@NoArgsConstructor
public class Service {
    private Repository repository;
    @Inject
    public Service(Repository repository) {
        this.repository = repository;

    }
    public Optional<User> find(Long id) {
        return repository.find(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void create(User user) {
        repository.create(user);
    }

    public byte[] findAvatar(Long id) throws IOException {
        return repository.findAvatar(id);
    }

    public void updateAvatar(Long id, InputStream is) throws IOException {
        repository.updateAvatar(id, is);
    }

    public void deleteAvatar(Long id) throws IOException {
        repository.deleteAvatar(id);
    }
}
