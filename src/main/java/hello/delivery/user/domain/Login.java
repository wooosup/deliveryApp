package hello.delivery.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Login {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private final String username;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private final String password;

    @Builder
    private Login(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
