package user;
import lombok.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUsersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {
        private Long id;
        private String login;
        private String name;
        private String surname;
    }
    @Singular
    private List<User> users;
    public static Function<Collection<user.User>, GetUsersResponse> entityToDtoMapper() {
        return users -> {
            GetUsersResponse.GetUsersResponseBuilder response = GetUsersResponse.builder();
            users.stream()
                    .map(user -> User.builder()
                            .id(user.getId())
                            .login(user.getLogin())
                            .name(user.getName())
                            .surname(user.getSurname())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }
}
