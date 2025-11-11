package hello.delivery.common.exception;

public class DuplicateProductException extends DeliveryException{

    public DuplicateProductException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
