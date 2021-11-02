package user.service;
import lombok.NoArgsConstructor;
import user.entity.User;
import user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

// zasięg - CDI zasięg aplikacji, aktywny od momentu wdrożenia do
//momentu usunięcia z serwera
@ApplicationScoped
@NoArgsConstructor
public class UserService {
    private UserRepository repository;
    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;

    }
    public Optional<User> find(Long id) {
        return repository.find(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }
    @Transactional
    public void create(User user) {
        repository.create(user);
    }

    public byte[] findAvatar(Long id) throws IOException {
        return repository.findAvatar(id);
    }

    public void updateAvatar(Long id, InputStream is) throws IOException {
        repository.updateAvatar(id, is);
    }

    public void createAvatar(Long id, InputStream is) throws IOException {
        repository.createAvatar(id, is);
    }

    public void deleteAvatar(Long id) throws IOException {
        repository.deleteAvatar(id);
    }
}
