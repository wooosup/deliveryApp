package hello.delivery.common.exception;

public class OwnerException extends DeliveryException{

    public OwnerException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
