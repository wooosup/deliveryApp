package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class StoreNotFound extends DeliveryException{

    private static final String MESSAGE = "가게를 찾을 수 없습니다.";

    public StoreNotFound() {
        super(MESSAGE, NOT_FOUND);
    }

}
