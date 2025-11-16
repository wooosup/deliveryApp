package hello.delivery.user.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
public class UserCreate {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Length(max = 4, message = "이름은 최대 4자까지 입력 가능합니다.")
    private final String name;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Length(min = 5, max = 20, message = "아이디는 5자 이상 20자 이하로 입력 가능합니다.")
    private final String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력 가능합니다.")
    private final String password;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private final String address;

    @Builder
    private UserCreate(String name, String username, String password, String address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
    }
}
