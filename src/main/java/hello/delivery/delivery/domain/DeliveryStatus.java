package hello.delivery.delivery.domain;

import hello.delivery.common.exception.DeliveryException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {
    PENDING("배달 대기"),
    ASSIGNED("배차 완료"),
    PICKED_UP("픽업 완료"),
    DELIVERED("배달 완료"),
    CANCELED("배달 취소");

    private final String description;

    public boolean canBeCanceled() {
        return this == PENDING || this == ASSIGNED;
    }

    public DeliveryStatus next() {
        return switch (this) {
            case PENDING -> ASSIGNED;
            case ASSIGNED -> PICKED_UP;
            case PICKED_UP -> DELIVERED;
            default -> throw new DeliveryException("더 이상 상태를 변경할 수 없습니다.");
        };
    }
}