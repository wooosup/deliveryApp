package hello.delivery.service.owner.response;

import hello.delivery.entity.user.Owner;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OwnerResponse {
    private final Long id;
    private final String name;
    private final int password;

    @Builder
    private OwnerResponse(Long id, String name, int password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public static OwnerResponse of(Owner owner) {
        return OwnerResponse.builder()
                .id(owner.getId())
                .name(owner.getName())
                .password(owner.getPassword())
                .build();
    }
}
