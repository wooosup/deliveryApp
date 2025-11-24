package hello.delivery.rider.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RiderCreate {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private final String name;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private final String phone;

    @Builder
    private RiderCreate(
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone) {
        this.name = name;
        this.phone = phone;
    }

}
