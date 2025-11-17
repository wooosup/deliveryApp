package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class UserException extends DeliveryException {

    public UserException(String message) {
        super(message, NOT_FOUND);
    }

}
