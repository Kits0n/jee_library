package user;
import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

// zasięg - CDI zasięg aplikacji, aktywny od momentu wdrożenia do
//momentu usunięcia z serwera
@ApplicationScoped
public class DataStore {
    private Set<User> users = new HashSet<>();

    public synchronized Optional<User> findUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
    public synchronized List<User> findAllUsers() {
        return new ArrayList<>(users);
    }

    public synchronized void createUser(User user){
        user.setId((long) users.size());
        users.add(user);
    }

    public synchronized void updateAvatar(Long id, InputStream is) {
        Path file = Paths.get("D:\\Studia\\Semestr VII\\NiAJEE\\jee_library\\src\\main\\resources\\"+id+".jpg");
        System.out.println(file);
        byte[] fileArray = Files.readAllBytes(file);
    }
}
