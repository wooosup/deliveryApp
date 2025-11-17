package hello.delivery.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class DeliveryException extends RuntimeException {

    private final HttpStatus status;

    public DeliveryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
