package hello.delivery.order.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProductRequest {

    private final long productId;
    private final int quantity;

    @Builder
    private OrderProductRequest(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
