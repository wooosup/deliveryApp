package hello.delivery.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressUpdate {

    private final String address;

    @Builder
    private AddressUpdate(@JsonProperty("address") String address) {
        this.address = address;
    }
}
