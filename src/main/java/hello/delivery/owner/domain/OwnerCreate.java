package hello.delivery.owner.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class OwnerCreate {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private final String name;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력 가능합니다.")
    private final String password;

    @Builder
    private OwnerCreate(String name, String password) {
        this.name = name;
        this.password = password;
    }

}
