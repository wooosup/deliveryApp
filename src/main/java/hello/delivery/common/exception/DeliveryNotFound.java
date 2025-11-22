package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.*;

public class DeliveryNotFound extends DeliveryAppException{

    private static final String MESSAGE = "배달을 찾을 수 없습니다.";

    public DeliveryNotFound() {
        super(MESSAGE, NOT_FOUND);
    }
}
