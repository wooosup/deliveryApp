package hello.delivery.order.domain;

import static hello.delivery.user.domain.UserRole.CUSTOMER;

import hello.delivery.common.exception.OrderException;
import hello.delivery.common.service.port.ClockHolder;
import hello.delivery.delivery.domain.DeliveryAddress;
import hello.delivery.store.domain.Store;
import hello.delivery.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Order {

    private final Long id;
    private final int totalPrice;
    private final User user;
    private final Store store;
    private final DeliveryAddress address;
    private final LocalDateTime orderedAt;
    private final List<OrderProduct> orderProducts;

    @Builder
    private Order(Long id, User user, Store store, DeliveryAddress address, LocalDateTime orderedAt, List<OrderProduct> orderProducts) {
        this.id = id;
        this.user = user;
        this.store = store;
        this.address = address;
        this.orderedAt = orderedAt;
        this.orderProducts = orderProducts.stream()
                .map(o -> o.getOrder() == null ? o.withOrder(this) : o)
                .toList();
        this.totalPrice = calculateTotalPrice();
    }

    public static Order order(User user, Store store, List<OrderProduct> orderProducts, String address, ClockHolder clockHolder) {
        validateUserAndStore(user, store);
        validate(orderProducts);

        DeliveryAddress deliveryAddress = DeliveryAddress.of(address);

        return Order.builder()
                .user(user)
                .store(store)
                .address(deliveryAddress)
                .orderedAt(clockHolder.nowDateTime())
                .orderProducts(orderProducts)
                .build();
    }

    private static void validateUserAndStore(User user, Store store) {
        if (user == null) {
            throw new OrderException("주문하는 사용자는 필수입니다.");
        }
        if (user.getRole() != CUSTOMER) {
            throw new OrderException("주문자는 고객이어야 합니다.");
        }
        if (store == null) {
            throw new OrderException("주문하는 가게는 필수입니다.");
        }
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
