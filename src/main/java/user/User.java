package user;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class User {
    private Long id;
    private String login;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String password;
    private String email;
}
