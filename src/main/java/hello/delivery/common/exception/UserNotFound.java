package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class UserNotFound extends DeliveryAppException {

    private static final String MESSAGE = "사용자를 찾을 수 없습니다.";

    public UserNotFound() {
        super(MESSAGE, NOT_FOUND);
    }

}
