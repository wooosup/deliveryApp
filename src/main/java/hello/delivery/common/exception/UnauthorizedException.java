package hello.delivery.common.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnauthorizedException extends DeliveryAppException {

    public UnauthorizedException(String message) {
        super(message, UNAUTHORIZED);
    }
}
