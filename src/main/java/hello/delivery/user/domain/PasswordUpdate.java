package hello.delivery.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PasswordUpdate {

    private final String password;

    @Builder
    private PasswordUpdate(@JsonProperty("password") String password) {
        this.password = password;
    }

}
