package hello.delivery.owner.domain;

import hello.delivery.owner.infrastructure.Owner;
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
