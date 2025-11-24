package hello.delivery.rider.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RiderLogin {

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private final String phone;

    @Builder
    private RiderLogin(String phone) {
        this.phone = phone;
    }
}
