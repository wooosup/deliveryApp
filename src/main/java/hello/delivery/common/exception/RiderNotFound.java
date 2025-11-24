package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class RiderNotFound extends DeliveryAppException{

    private static final String MESSAGE = "라이더를 찾을 수 없습니다.";

    public RiderNotFound() {
        super(MESSAGE, NOT_FOUND);
    }
}
