package datastore;
import user.entity.User;

import javax.enterprise.context.ApplicationScoped;
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
        Path file = Paths.get("D:\\Studia\\Semestr VII\\NiAJEE\\jee_library\\src\\main\\resources\\META-INF\\app.properties" );
        System.out.println(Files.readString(file));
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
        Path file = Paths.get(getPath()+id+".jpg");
        if(Files.exists(file)){
            return Files.readAllBytes(file);
        }
        else{
            return new byte[0];
        }
    }

    public synchronized void updateAvatar(Long id, InputStream is) throws IOException {
        Path file = Paths.get(getPath()+id+".jpg");
        if(Files.exists(file)) {
            Files.write(file, is.readAllBytes());
        }else{
            throw new IllegalArgumentException(
                    String.format("Avatar with id \"%d\" does not exist", id));
        }
    }

    public synchronized void createAvatar(Long id, InputStream is) throws IOException {
        Path file = Paths.get(getPath()+id+".jpg");
        Files.write(file, is.readAllBytes());
    }

    public void deleteAvatar(Long id) throws IOException, IllegalArgumentException {
        Path file = Paths.get(getPath()+id+".jpg");
        if(Files.exists(file)) {
            Files.delete(file);
        }else{
            throw new IllegalArgumentException(
                    String.format("Avatar with id \"%d\" does not exist", id));
        }
    }
}
