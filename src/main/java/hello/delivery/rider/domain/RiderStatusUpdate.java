package hello.delivery.rider.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RiderStatusUpdate {

    @NotNull
    private final RiderStatus status;

    @Builder
    private RiderStatusUpdate(RiderStatus status) {
        this.status = status;
    }
}
