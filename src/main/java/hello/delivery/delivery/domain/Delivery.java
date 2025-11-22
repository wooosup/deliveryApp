package hello.delivery.delivery.domain;

import static hello.delivery.delivery.domain.DeliveryStatus.PENDING;

import hello.delivery.common.exception.DeliveryException;
import hello.delivery.order.domain.Order;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Delivery {

    private final Long id;
    private final Order order;
    private final DeliveryStatus status;
    private final DeliveryAddress address;
    private final LocalDateTime startedAt;
    private final LocalDateTime completedAt;

    @Builder
    private Delivery(Long id, Order order, DeliveryStatus status, DeliveryAddress address,
                    LocalDateTime startedAt, LocalDateTime completedAt) {
        this.id = id;
        this.order = order;
        this.status = status;
        this.address = address;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
    }

    public static Delivery create(Order order, DeliveryAddress address) {
        validate(order, address);
        return Delivery.builder()
                .order(order)
                .status(PENDING)
                .address(address)
                .build();
    }

    private static void validate(Order order, DeliveryAddress address) {
        if (order == null) {
            throw new DeliveryException("배달에 필요한 주문 정보가 없습니다.");
        }
        if (address == null || address.getAddress() == null || address.getAddress().isBlank()) {
            throw new DeliveryException("배달지 주소는 필수입니다.");
        }
    }
}
