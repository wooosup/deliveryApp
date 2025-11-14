package hello.delivery.owner.domain;

import hello.delivery.owner.dto.OwnerCreate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Owner {

    private final Long id;
    private final String name;
    private final String password;

    @Builder
    private Owner(Long id, String name, String password) {
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

    public Owner changePassword(String newPassword) {
        return Owner.builder()
                .id(id)
                .name(name)
                .password(newPassword)
                .build();
    }
}
