package user;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
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

    public static String getPath() throws IOException {
        Path file = Paths.get("/home/student/175548/jee_library/src/main/resources/META-INF/app.properties");
        return Files.readString(file);
    }

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

    public synchronized byte[] findAvatar(Long id) throws IOException {
        System.out.println(getPath());
        Path file = Paths.get(getPath()+id+".jpg");
        return Files.readAllBytes(file);
    }

    public synchronized void updateAvatar(Long id, InputStream is) throws IOException {
        Path file = Paths.get(getPath()+id+".jpg");
        Files.write(file, is.readAllBytes());
    }

    public void deleteAvatar(Long id) throws IOException {
        Path file = Paths.get(getPath()+id+".jpg");
        Files.delete(file);
    }
}
