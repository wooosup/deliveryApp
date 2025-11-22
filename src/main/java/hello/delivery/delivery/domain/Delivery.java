package hello.delivery.delivery.domain;

import static hello.delivery.delivery.domain.DeliveryStatus.*;

import hello.delivery.common.exception.DeliveryException;
import hello.delivery.common.service.port.ClockHolder;
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

    public static Delivery create(Order order) {
        validate(order);
        return Delivery.builder()
                .order(order)
                .status(PENDING)
                .address(order.getAddress())
                .build();
    }

    public Delivery start(ClockHolder clockHolder) {
        if (status != ASSIGNED) {
            throw new DeliveryException("배달을 시작할 수 없는 상태입니다.");
        }
        return Delivery.builder()
                .id(id)
                .order(order)
                .status(PICKED_UP)
                .address(address)
                .startedAt(clockHolder.nowDateTime())
                .completedAt(completedAt)
                .build();
    }

    public Delivery complete(ClockHolder clockHolder) {
        if (status != PICKED_UP) {
            throw new DeliveryException("배달을 완료할 수 없는 상태입니다.");
        }
        return Delivery.builder()
                .id(id)
                .order(order)
                .status(DELIVERED)
                .address(address)
                .startedAt(startedAt)
                .completedAt(clockHolder.nowDateTime())
                .build();
    }

    private static void validate(Order order) {
        if (order == null) {
            throw new DeliveryException("배달에 필요한 주문 정보가 없습니다.");
        }
        if (order.getAddress() == null) {
            throw new DeliveryException("배달에 필요한 주소 정보가 없습니다.");
        }
    }

}
