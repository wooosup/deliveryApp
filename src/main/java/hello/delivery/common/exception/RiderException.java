package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

public class RiderException extends DeliveryAppException{

    public RiderException(String message) {
        super(message, BAD_REQUEST);
    }

}
