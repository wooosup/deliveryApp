package hello.delivery.delivery.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryAddress {

    @NotBlank(message = "배달 주소는 필수 입력 값입니다.")
    private String address;

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
