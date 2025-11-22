package hello.delivery.delivery.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DeliveryAddress {

    private final String address;

    @Builder
    private DeliveryAddress(@JsonProperty("address") String address) {
        this.address = address;
    }

}
