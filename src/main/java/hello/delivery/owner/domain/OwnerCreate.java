package hello.delivery.owner.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OwnerCreate {

    private final String name;
    private final String password;

    @Builder
    private OwnerCreate(String name, String password) {
        this.name = name;
        this.password = password;
    }

}
