package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class OrderException extends DeliveryException{

    public OrderException(String message) {
        super(message, BAD_REQUEST);
    }

}
