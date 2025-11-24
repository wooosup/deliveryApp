package hello.delivery.rider.domain;

import static hello.delivery.rider.domain.RiderStatus.*;

import hello.delivery.common.exception.RiderException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Rider {

    private final Long id;
    private final String name;
    private final String phone;
    private final RiderStatus status;

    @Builder
    private Rider(Long id, String name, String phone, RiderStatus status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = status;
    }

    public static Rider signup(RiderCreate riderCreate) {
        return Rider.builder()
                .name(riderCreate.getName())
                .phone(riderCreate.getPhone())
                .status(OFFLINE)
                .build();
    }

    public Rider login(RiderLogin request) {
        return Rider.builder()
                .id(id)
                .name(name)
                .phone(request.getPhone())
                .status(AVAILABLE)
                .build();
    }

    public Rider changeStatus(RiderStatusUpdate newStatus) {
        return Rider.builder()
                .id(id)
                .name(name)
                .phone(phone)
                .status(newStatus.getStatus())
                .build();
    }

    public void validateAvailable() {
        if (status == OFFLINE) {
            throw new RiderException("오프라인 상태에서는 배달 업무를 수행할 수 없습니다.");
        }
    }

    public void validateCanStartDelivery() {
        if (status != AVAILABLE) {
            throw new RiderException("배달을 시작할 수 없는 상태입니다. (현재 상태: " + status.getDescription() + ")");
        }
    }

    public void validateCanCompleteDelivery() {
        if (status != DELIVERING) {
            throw new RiderException("배달 중이 아닌 상태에서는 완료할 수 없습니다.");
        }
    }
}
