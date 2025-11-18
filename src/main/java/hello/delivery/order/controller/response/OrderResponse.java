package hello.delivery.order.controller.response;

import hello.delivery.order.domain.Order;
import hello.delivery.store.controller.response.StoreResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponse {

    private final Long id;
    private final int totalPrice;
    private final String address;
    private final StoreResponse store;
    private final List<OrderProductResponse> orderProducts;

    @Builder
    private OrderResponse(Long id, int totalPrice, String address, StoreResponse store, List<OrderProductResponse> orderProducts) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.address = address;
        this.store = store;
        this.orderProducts = orderProducts;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .address(order.getUser().getAddress())
                .store(StoreResponse.of(order.getStore()))
                .orderProducts(order.getOrderProducts().stream()
                        .map(OrderProductResponse::of)
                        .toList())
                .build();
    }

    public static List<OrderResponse> of(List<Order> orders) {
        return orders.stream()
                .map(OrderResponse::of)
                .toList();
    }

}
