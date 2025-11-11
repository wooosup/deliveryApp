package hello.delivery.user.domain;

import hello.delivery.common.exception.LoginException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private final Long id;
    private final String name;
    private final String username;
    private final String password;
    private final String address;

    @Builder
    private User(Long id, String name, String username, String password, String address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public static User signup(UserCreate userCreate) {
        return User.builder()
                .name(userCreate.getName())
                .username(userCreate.getUsername())
                .password(userCreate.getPassword())
                .address(userCreate.getAddress())
                .build();
    }

    public void checkNicknameAndPassword(String username,String password) {
        if (isNotSameUsernameAndPassword(username, password)) {
            throw new LoginException();
        }
    }

    private boolean isNotSameUsernameAndPassword(String username, String password) {
        return !this.username.equals(username) || !this.password.equals(password);
    }
}
