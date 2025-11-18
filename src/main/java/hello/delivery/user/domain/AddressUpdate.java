package hello.delivery.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressUpdate {

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private final String address;

    @Builder
    private AddressUpdate(@JsonProperty("address") String address) {
        this.address = address;
    }
}
