package hello.delivery.owner.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Owner {

    private final Long id;
    private final String name;
    private final int password;

    @Builder
    private Owner(Long id, String name, int password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public static Owner of(OwnerCreate ownerCreate) {
        return Owner.builder()
                .name(ownerCreate.getName())
                .password(ownerCreate.getPassword())
                .build();
    }
}
