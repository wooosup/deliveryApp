package hello.delivery.order.controller.response;

import hello.delivery.order.domain.Order;
import hello.delivery.store.controller.response.StoreResponse;
import hello.delivery.user.controller.response.UserResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponse {

    private final Long id;
    private final int totalPrice;
    private final UserResponse user;
    private final StoreResponse store;
    private final List<OrderProductResponse> orderProducts;

    @Builder
    private OrderResponse(Long id, int totalPrice, UserResponse user, StoreResponse store, List<OrderProductResponse> orderProducts) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.user = user;
        this.store = store;
        this.orderProducts = orderProducts;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .user(UserResponse.of(order.getUser()))
                .store(StoreResponse.of(order.getStore()))
                .orderProducts(order.getOrderProducts().stream()
                        .map(OrderProductResponse::of)
                        .toList())
                .build();
    }
}
