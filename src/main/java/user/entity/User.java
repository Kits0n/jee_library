package user.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import rental.entity.Rental;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String password;
    private String email;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user")
    private List<Rental> rentals;
}
