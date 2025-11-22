package hello.delivery.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class DeliveryAppException extends RuntimeException {

    private final HttpStatus status;

    public DeliveryAppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
