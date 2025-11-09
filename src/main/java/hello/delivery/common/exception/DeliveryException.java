package hello.delivery.common.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public abstract class DeliveryException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public DeliveryException(String message) {
        super(message);
    }

    public abstract int getStatusCode();
}
