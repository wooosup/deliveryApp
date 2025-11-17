package hello.delivery.user.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    CUSTOMER("고객"),
    OWNER("사장");

    private final String description;
}
