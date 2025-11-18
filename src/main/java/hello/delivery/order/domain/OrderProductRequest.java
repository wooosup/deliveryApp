package hello.delivery.order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProductRequest {

    private final long productId;
    private final int quantity;

    @Builder
    private OrderProductRequest(
            @JsonProperty("productId") long productId,
            @JsonProperty("quantity") int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
