package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class StoreException extends DeliveryException{

    public StoreException(String message) {
        super(message, BAD_REQUEST);
    }

}
