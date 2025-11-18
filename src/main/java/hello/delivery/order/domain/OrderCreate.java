package hello.delivery.order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreate {

    private final long storeId;
    private final long userId;
    private final List<OrderProductRequest> orderProducts;

    @Builder
    private OrderCreate(
            @JsonProperty("storeId") long storeId,
            @JsonProperty("userId") long userId,
            @JsonProperty("orderProducts") List<OrderProductRequest> orderProducts) {
        this.storeId = storeId;
        this.userId = userId;
        this.orderProducts = orderProducts;
    }

}
