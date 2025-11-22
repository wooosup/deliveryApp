package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class OrderException extends DeliveryAppException {

    public OrderException(String message) {
        super(message, BAD_REQUEST);
    }

}
