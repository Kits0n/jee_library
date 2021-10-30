package configuration;
import book.entity.Book;
import book.service.BookService;
import rental.entity.Rental;
import rental.service.RentalService;
import user.entity.User;
import user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;

// zasięg - CDI zasięg aplikacji, aktywny od momentu wdrożenia do
//momentu usunięcia z serwera
@ApplicationScoped
public class InitializedData {

    private final UserService userService;
    private final BookService bookService;
    private final RentalService rentalService;

    @Inject
    public InitializedData(UserService userService, BookService bookService, RentalService rentalService) {
        this.userService = userService;
        this.bookService = bookService;
        this.rentalService = rentalService;
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

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.create(user4);

        Book book1 = Book.builder()
                .title("title1")
                .author("author1")
                .numberOfPages(1)
                .publicationDate(LocalDate.of(2001, 1, 1))
                .build();

        Book book2 = Book.builder()
                .title("title2")
                .author("author2")
                .numberOfPages(12)
                .publicationDate(LocalDate.of(2002, 2, 2))
                .build();
        Book book3 = Book.builder()
                .title("title3")
                .author("author3")
                .numberOfPages(3)
                .publicationDate(LocalDate.of(2003, 3, 3))
                .build();

        Book book4 = Book.builder()
                .title("title4")
                .author("author4")
                .numberOfPages(14)
                .publicationDate(LocalDate.of(2004, 4, 4))
                .build();

        bookService.create(book1);
        bookService.create(book2);
        bookService.create(book3);
        bookService.create(book4);

        Rental rental1 = Rental.builder()
                .type("COLLECTION")
                .book(book1)
                .date(LocalDate.of(2002, 2, 2))
                .user(user1)
                .build();

        Rental rental2 = Rental.builder()
                .type("RETURN")
                .book(book1)
                .date(LocalDate.of(2002, 3, 2))
                .user(user1)
                .build();

        Rental rental3 = Rental.builder()
                .type("COLLECTION")
                .book(book2)
                .date(LocalDate.of(2002, 2, 2))
                .user(user2)
                .build();

        Rental rental4 = Rental.builder()
                .type("COLLECTION")
                .book(book2)
                .date(LocalDate.of(2002, 3, 2))
                .user(user3)
                .build();
        rentalService.create(rental1);
        rentalService.create(rental2);
        rentalService.create(rental3);
        rentalService.create(rental4);
    }
}
