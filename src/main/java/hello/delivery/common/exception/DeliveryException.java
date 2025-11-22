package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class DeliveryException extends DeliveryAppException{

    public DeliveryException(String message) {
        super(message, BAD_REQUEST);
    }

}
