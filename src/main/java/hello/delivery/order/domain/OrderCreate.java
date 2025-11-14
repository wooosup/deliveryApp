package hello.delivery.order.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreate {

    private long userId;
    private long storeId;
    private List<OrderProductRequest> orderProducts;

    @Builder
    private OrderCreate(long userId, long storeId, List<OrderProductRequest> orderProducts) {
        this.userId = userId;
        this.storeId = storeId;
        this.orderProducts = orderProducts;
    }

}
