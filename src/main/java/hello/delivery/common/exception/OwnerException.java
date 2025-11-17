package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class OwnerException extends DeliveryException{

    public OwnerException(String message) {
        super(message, BAD_REQUEST);
    }

}
