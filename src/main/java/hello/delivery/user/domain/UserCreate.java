package hello.delivery.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {

    private final String name;
    private final String username;
    private final String password;
    private final String address;

    @Builder
    private UserCreate(String name, String username, String password, String address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
    }
}
