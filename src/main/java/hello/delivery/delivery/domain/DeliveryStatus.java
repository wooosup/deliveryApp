package hello.delivery.delivery.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {
    PENDING("배달 대기"),
    ASSIGNED("배차 완료"),
    PICKED_UP("픽업 완료"),
    DELIVERED("배달 완료");

    private final String description;

}