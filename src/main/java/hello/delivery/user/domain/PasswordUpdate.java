package hello.delivery.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PasswordUpdate {

    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력 가능합니다.")
    private final String password;

    @Builder
    private PasswordUpdate(@JsonProperty("password") String password) {
        this.password = password;
    }

}
