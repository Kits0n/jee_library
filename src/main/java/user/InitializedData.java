package user;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;

// zasięg - CDI zasięg aplikacji, aktywny od momentu wdrożenia do
//momentu usunięcia z serwera
@ApplicationScoped
public class InitializedData {

    private final Service service;

    @Inject
    public InitializedData(Service service) {
        this.service = service;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private void init(){
        User user1 = User.builder()
                .login("user1")
                .password("password1")
                .name("name1")
                .surname("surname1")
                .birthDate(LocalDate.of(2001, 1, 1))
                .email("email1@gmail.com")
                .build();
        User user2 = User.builder()
                .login("user2")
                .password("password2")
                .name("name2")
                .surname("surname2")
                .birthDate(LocalDate.of(2002, 2, 2))
                .email("email2@gmail.com")
                .build();
        User user3 = User.builder()
                .login("user3")
                .password("password3")
                .name("name3")
                .surname("surname3")
                .birthDate(LocalDate.of(2003, 3, 3))
                .email("email3@gmail.com")
                .build();
        User user4 = User.builder()
                .login("user4")
                .password("password4")
                .name("name4")
                .surname("surname4")
                .birthDate(LocalDate.of(2004, 4, 4))
                .email("email4@gmail.com")
                .build();

        service.create(user1);
        service.create(user2);
        service.create(user3);
        service.create(user4);
    }
}
