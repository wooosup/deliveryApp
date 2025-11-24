package hello.delivery.rider.controller.response;

import hello.delivery.rider.domain.Rider;
import hello.delivery.rider.domain.RiderStatus;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RiderResponse {

    private final Long id;
    private final String name;
    private final String phone;
    private final RiderStatus status;

    @Builder
    private RiderResponse(Long id, String name, String phone, RiderStatus status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = status;
    }

    public static RiderResponse of(Rider rider) {
        return RiderResponse.builder()
                .id(rider.getId())
                .name(rider.getName())
                .phone(rider.getPhone())
                .status(rider.getStatus())
                .build();
    }

    public static List<RiderResponse> of(List<Rider> riders) {
        return riders.stream()
                .map(RiderResponse::of)
                .toList();
    }
}