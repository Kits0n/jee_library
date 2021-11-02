package rental.entity;

import book.entity.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;
import user.entity.User;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;
    private String type;

    @ManyToOne
    @JoinColumn(name = "book")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}
