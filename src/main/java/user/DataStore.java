package user;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;

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
}
