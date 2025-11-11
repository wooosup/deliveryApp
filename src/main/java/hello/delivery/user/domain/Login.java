package hello.delivery.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Login {

    private final String username;
    private final String password;

    @Builder
    private Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
