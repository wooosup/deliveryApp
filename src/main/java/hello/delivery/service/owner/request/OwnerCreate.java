package hello.delivery.service.owner.request;

import hello.delivery.entity.user.Owner;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OwnerCreate {
    private final String name;
    private final int password;

    @Builder
    private OwnerCreate(String name, int password) {
        this.name = name;
        this.password = password;
    }

    public Owner toEntity() {
        return Owner.builder()
                .name(name)
                .password(password)
                .build();
    }
}
