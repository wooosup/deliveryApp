package hello.delivery.order.domain;

import hello.delivery.common.exception.OrderException;
import hello.delivery.store.domain.Store;
import hello.delivery.user.domain.User;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Order {

    private final Long id;
    private final int totalPrice;
    private final User user;
    private final Store store;
    private final List<OrderProduct> orderProducts;

    @Builder
    private Order(Long id, User user, Store store, List<OrderProduct> orderProducts) {
        this.id = id;
        this.user = user;
        this.store = store;
        this.orderProducts = orderProducts.stream()
                .map(o -> o.withOrder(this))
                .toList();
        this.totalPrice = calculateTotalPrice();
    }

    public static Order order(User user, Store store, List<OrderProduct> orderProducts) {
        Objects.requireNonNull(user, "주문하는 사용자는 필수입니다.");
        Objects.requireNonNull(store, "주문하는 가게는 필수입니다.");
        validate(orderProducts);
        return Order.builder()
                .user(user)
                .store(store)
                .orderProducts(orderProducts)
                .build();
    }

    private static void validate(List<OrderProduct> orderProducts) {
        if (orderProducts == null || orderProducts.isEmpty()) {
            throw new OrderException("주문에는 최소 1개 이상의 상품이 포함되어야 합니다.");
        }
    }

    private int calculateTotalPrice() {
        return orderProducts.stream()
                .mapToInt(OrderProduct::calculatePrice)
                .sum();
    }

}
