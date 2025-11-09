package hello.delivery.owner.domain;

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

}
