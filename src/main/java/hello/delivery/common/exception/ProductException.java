package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ProductException extends DeliveryException{

    public ProductException(String message) {
        super(message, BAD_REQUEST);
    }

}
