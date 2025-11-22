package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UserException extends DeliveryAppException {

    public UserException(String message) {
        super(message, BAD_REQUEST);
    }

}
