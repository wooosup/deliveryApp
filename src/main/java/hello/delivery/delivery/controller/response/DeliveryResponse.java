package hello.delivery.delivery.controller.response;

import hello.delivery.delivery.domain.Delivery;
import hello.delivery.delivery.domain.DeliveryStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DeliveryResponse {
    private final Long deliveryId;
    private final Long orderId;
    private final DeliveryStatus status;
    private final String address;
    private final LocalDateTime startedAt;
    private final LocalDateTime completedAt;

    @Builder
    private DeliveryResponse(Long deliveryId, Long orderId, DeliveryStatus status, String address, LocalDateTime startedAt, LocalDateTime completedAt) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.status = status;
        this.address = address;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
    }

    public static DeliveryResponse of(Delivery delivery) {
        return DeliveryResponse.builder()
                .deliveryId(delivery.getId())
                .orderId(delivery.getOrder().getId())
                .status(delivery.getStatus())
                .address(delivery.getAddress().getAddress())
                .startedAt(delivery.getStartedAt())
                .completedAt(delivery.getCompletedAt())
                .build();
    }
}