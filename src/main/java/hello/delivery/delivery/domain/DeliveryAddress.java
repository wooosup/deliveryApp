package hello.delivery.delivery.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DeliveryAddress {

    @NotBlank(message = "배달 주소는 필수 입력 값입니다.")
    private final String address;

    @Builder
    private DeliveryAddress(@JsonProperty("address") String address) {
        this.address = address;
    }

    public static DeliveryAddress of(String address) {
        return DeliveryAddress.builder()
                .address(address)
                .build();
    }

}
